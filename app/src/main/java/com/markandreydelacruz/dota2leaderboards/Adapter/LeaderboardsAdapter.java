package com.markandreydelacruz.dota2leaderboards.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.markandreydelacruz.dota2leaderboards.Models.LeaderboardsModel;
import com.markandreydelacruz.dota2leaderboards.R;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by mark on 6/30/2017.
 */

public class LeaderboardsAdapter extends ArrayAdapter {
    private List<LeaderboardsModel> leaderboardsModelList;
    private int resource;
    private LayoutInflater inflater;
    public LeaderboardsAdapter(Context context, int resource, List<LeaderboardsModel> objects) {
        super(context, resource, objects);
        leaderboardsModelList = objects;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LeaderboardsAdapter.ViewHolder holder = null;

        if(convertView == null){
            holder = new LeaderboardsAdapter.ViewHolder();
            convertView = inflater.inflate(resource, null);
            holder.textViewRank = (TextView)convertView.findViewById(R.id.textViewRank);
            holder.textViewName = (TextView)convertView.findViewById(R.id.textViewName);
            holder.textViewSoloMMR = (TextView)convertView.findViewById(R.id.textViewSoloMMR);
            convertView.setTag(holder);
        } else {
            holder = (LeaderboardsAdapter.ViewHolder) convertView.getTag();
        }

        holder.textViewRank.setText(String.valueOf(leaderboardsModelList.get(position).getRank()));
        if(leaderboardsModelList.get(position).getTeam_tag() == null && leaderboardsModelList.get(position).getSponsor() == null && leaderboardsModelList.get(position).getName() == null) {
            holder.textViewName.setText("");
        } else if (leaderboardsModelList.get(position).getTeam_tag() == null && leaderboardsModelList.get(position).getSponsor() == null && leaderboardsModelList.get(position).getName() != null) {
            holder.textViewName.setText(leaderboardsModelList.get(position).getName());
        } else if (leaderboardsModelList.get(position).getTeam_tag() == null && leaderboardsModelList.get(position).getSponsor() != null && leaderboardsModelList.get(position).getName() != null){
            holder.textViewName.setText(leaderboardsModelList.get(position).getSponsor() + "." + leaderboardsModelList.get(position).getName());
        } else if (leaderboardsModelList.get(position).getTeam_tag().trim().isEmpty() && leaderboardsModelList.get(position).getSponsor() == null && leaderboardsModelList.get(position).getName() != null) {
            holder.textViewName.setText(leaderboardsModelList.get(position).getName());
        } else if (leaderboardsModelList.get(position).getTeam_tag().trim().isEmpty() && leaderboardsModelList.get(position).getSponsor() != null && leaderboardsModelList.get(position).getName() != null){
            holder.textViewName.setText(leaderboardsModelList.get(position).getSponsor() + "." + leaderboardsModelList.get(position).getName());
        } else if (leaderboardsModelList.get(position).getTeam_tag() != null && leaderboardsModelList.get(position).getSponsor() == null && leaderboardsModelList.get(position).getName() != null){
            holder.textViewName.setText(leaderboardsModelList.get(position).getTeam_tag() + "." + leaderboardsModelList.get(position).getName());
        } else {
            holder.textViewName.setText(leaderboardsModelList.get(position).getTeam_tag() + "." + leaderboardsModelList.get(position).getSponsor() + "." + leaderboardsModelList.get(position).getName());
        }
//        holder.textViewSoloMMR.setText(String.valueOf(leaderboardsModelList.get(position).getSolo_mmr()));
        holder.textViewSoloMMR.setText("");
        return convertView;
    }

    class ViewHolder{
        private TextView textViewRank;
        private TextView textViewName;
        private TextView textViewSoloMMR;
    }
}
