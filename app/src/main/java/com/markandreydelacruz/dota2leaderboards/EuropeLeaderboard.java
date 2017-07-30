package com.markandreydelacruz.dota2leaderboards;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.markandreydelacruz.dota2leaderboards.Adapter.LeaderboardsAdapter;
import com.markandreydelacruz.dota2leaderboards.Models.LeaderboardsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class EuropeLeaderboard extends Fragment {

    View rootView;
    private ListView listViewEuropeLeaderboards;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_europeleaderboard, container, false);

        if(MainActivity.isNetworkAvailable(getContext())) {
            getEuropeLeaderboards(MainActivity.URL_EUROPE_FEED);
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

        listViewEuropeLeaderboards = (ListView) rootView.findViewById(R.id.listViewEuropeLeaderboards);

        return rootView;
    }

    private void getEuropeLeaderboards(String urlEuropeFeed) {
        final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(3000);
        asyncHttpClient.setResponseTimeout(3000);
        asyncHttpClient.setMaxRetriesAndTimeout(0, 0);
        asyncHttpClient.get(getActivity(), urlEuropeFeed, new AsyncHttpResponseHandler() {

            ProgressDialog dialog;

            @Override
            public void onStart() {
                super.onStart();
                dialog = new ProgressDialog(getContext());
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Please wait...");
                dialog.setIndeterminate(false);
                dialog.setCancelable(false);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(getContext(), "Canceled", Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, final byte[] responseBody) {
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(new String(responseBody));
                    JSONArray jsonArray = jsonObject.getJSONArray("leaderboard");
                    List<LeaderboardsModel> leaderboardsModelList = new ArrayList<>();
                    Gson gson = new Gson();
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject finalObject = jsonArray.getJSONObject(i);
                        LeaderboardsModel leaderboardsModel = gson.fromJson(finalObject.toString(), LeaderboardsModel.class); // a single line json parsing using Gson
                        leaderboardsModelList.add(leaderboardsModel);
                    }

                    LeaderboardsAdapter adapter = new LeaderboardsAdapter(getContext(), R.layout.row_leaderboards_item, leaderboardsModelList);
                    listViewEuropeLeaderboards.setAdapter(adapter);
                    if (this.dialog.isShowing()) {
                        this.dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                asyncHttpClient.cancelAllRequests(true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }
                Toast.makeText(getContext(), "Connection failed. Try again.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }
            }
        });
    }
}
