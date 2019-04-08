package com.markandreydelacruz.dota2leaderboards;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private boolean doubleBackToExitPressedOnce = false;

//    REAL DATA
    static final String URL_AMERICA_FEED = "http://www.dota2.com/webapi/ILeaderboard/GetDivisionLeaderboard/v0001?division=americas";
    static final String URL_EUROPE_FEED = "http://www.dota2.com/webapi/ILeaderboard/GetDivisionLeaderboard/v0001?division=europe";
    static final String URL_SEA_FEED = "http://www.dota2.com/webapi/ILeaderboard/GetDivisionLeaderboard/v0001?division=se_asia";
    static final String URL_CHINA_FEED = "http://www.dota2.com/webapi/ILeaderboard/GetDivisionLeaderboard/v0001?division=china";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("DOTA2 Leaderboard");

        if(!isNetworkAvailable(getApplicationContext())) {
            Snackbar snack = Snackbar.make(getWindow().getDecorView().getRootView(), "Connect to WiFi or Mobile Data", Snackbar.LENGTH_LONG)
                    .setAction("Action", null);
            ViewGroup group = (ViewGroup) snack.getView();
            group.setBackgroundColor(Color.parseColor("#dd4e40"));
            snack.show();
        }


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            AmericaLeaderboard americaLeaderboard = new AmericaLeaderboard();
            EuropeLeaderboard europeLeaderboard = new EuropeLeaderboard();
            SEALeaderboard seaLeaderboard = new SEALeaderboard();
            ChinaLeaderboard chinaLeaderboard = new ChinaLeaderboard();
            switch (position) {
                case 0:
                    return americaLeaderboard;
                case 1:
                    return europeLeaderboard;
                case 2:
                    return seaLeaderboard;
                case 3:
                    return chinaLeaderboard;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "America";
                case 1:
                    return "Europe";
                case 2:
                    return "SEA";
                case 3:
                    return "China";
            }
            return null;
        }
    }
}
