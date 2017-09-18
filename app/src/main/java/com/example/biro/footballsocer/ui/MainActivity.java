package com.example.biro.footballsocer.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.biro.footballsocer.adapters.PagerAdapter;
import com.example.biro.footballsocer.data.Contract;
import com.example.biro.footballsocer.R;
import com.example.biro.footballsocer.sync.Requests;
import com.example.biro.footballsocer.utils.SharedPref;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements FetchDataListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;


    private FirebaseAnalytics mFirebaseAnalytics;


    public static String getRoundId() {
        return roundId;
    }

    private static String roundId;
    PagerAdapter adapter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);




        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);

        Toast.makeText(this, Build.VERSION.SDK_INT+""
        ,Toast.LENGTH_LONG).show();
        SecondaryDrawerItem item1 = new SecondaryDrawerItem().withIdentifier(0).withName(R.string.drawer_item_1).withIcon(R.drawable.premierleague);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_2).withIcon(R.drawable.bundsliga);
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_3).withIcon(R.drawable.serieaa);
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_4).withIcon(R.drawable.ligue1);


        DividerDrawerItem dividerDrawerItem = new DividerDrawerItem();



        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(false)
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .addDrawerItems(
                        item1, dividerDrawerItem, item2, dividerDrawerItem, item3, dividerDrawerItem
                        , item4, dividerDrawerItem
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {

                        switch (position) {
                            case 0:
                                roundId = getString(R.string.premierLeague);
                                toolbar.setTitle(getString(R.string.drawer_item_1));
                                viewPager.getAdapter().notifyDataSetChanged();
                                SharedPref.getInstance(getApplicationContext()).save(Contract.TAG, roundId);
                                break;
                            case 2:
                                roundId = getString(R.string.bundesliga);
                                toolbar.setTitle(getString(R.string.drawer_item_2));
                                viewPager.getAdapter().notifyDataSetChanged();
                                SharedPref.getInstance(getApplicationContext()).save(Contract.TAG, roundId);
                                break;
                            case 4:
                                roundId = getString(R.string.serieA);
                                toolbar.setTitle(getString(R.string.drawer_item_3));
                                viewPager.getAdapter().notifyDataSetChanged();
                                SharedPref.getInstance(getApplicationContext()).save(Contract.TAG, roundId);
                                break;
                            case 6:
                                roundId = getString(R.string.ligue1);
                                toolbar.setTitle(getString(R.string.drawer_item_4));
                                viewPager.getAdapter().notifyDataSetChanged();
                                SharedPref.getInstance(getApplicationContext()).save(Contract.TAG, roundId);
                                break;
                            default:
                                Log.d("error", "onItemNavi: " + position);
                                break;

                        }
                        return false;
                    }

                })


                .build();


        roundId = SharedPref.getInstance(this).load(Contract.TAG);
        if (roundId.equals(getString(R.string.premierLeague))) {
            toolbar.setTitle(getString(R.string.drawer_item_1));
            result.setSelectionByIdentifier(0);
        } else if (roundId.equals(getString(R.string.bundesliga))) {
            toolbar.setTitle(getString(R.string.drawer_item_2));
            result.setSelectionByIdentifier(1);
        } else if (roundId.equals(getString(R.string.serieA))) {
            toolbar.setTitle(getString(R.string.drawer_item_3));
            result.setSelectionByIdentifier(2);
        } else if (roundId.equals(getString(R.string.ligue1))) {
            toolbar.setTitle(getString(R.string.drawer_item_4));
            result.setSelectionByIdentifier(3);
        }

        viewPager.setOffscreenPageLimit(3);
        Bundle params = new Bundle();
        params.putString("activity_name", "MainActivity");

        mFirebaseAnalytics.logEvent("user_activity", params);



    }


    private void setupViewPager(ViewPager viewPager) {
         adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GameDay(), getString(R.string.GameDayFragment));
        adapter.addFragment(new Standing(), getString(R.string.StandingFragment));
        adapter.addFragment(new Teams(), getString(R.string.TeamFragment));


        viewPager.setAdapter(adapter);
    }


    @Override
    public void fetchTeamsData() {


        String key[] = {getString(R.string.subscriptionKey)};
        String value[] = {getString(R.string.api_key)};


        Requests.getInstance(this).getRequest(Contract.baseUrl + Contract.standing + roundId, key, value, new Requests.VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) throws JSONException {




                int diff = 20;
                if (roundId.equals(getString(R.string.bundesliga))) {
                    diff = 18;

                }


                for (int i = 0; i < diff; i++) {
                    JSONObject teamObject;

                    teamObject = result.getJSONObject(i);

                    /*
                    parse json
                     */
                    String name = teamObject.getString(Contract.Teams.name);
                    int teamId = teamObject.getInt(Contract.Teams.teamID);
                    int wins = teamObject.getInt(Contract.Teams.wins);
                    int losses = teamObject.getInt(Contract.Teams.losses);
                    int draws = teamObject.getInt(Contract.Teams.draws);
                    int points = teamObject.getInt(Contract.Teams.points);
                    int gA = teamObject.getInt(Contract.Teams.gA);
                    int goals = teamObject.getInt(Contract.Teams.goals);
                    int games = teamObject.getInt(Contract.Teams.games);
                    int position = teamObject.getInt(Contract.Teams.position);
                    int roundId = teamObject.getInt(Contract.Teams.round_id);


                    ContentValues teamCV = new ContentValues();
                    teamCV.put(Contract.Teams.COLUMN_NAME, name);
                    teamCV.put(Contract.Teams.COLUMN_TEAM_ID, teamId);
                    teamCV.put(Contract.Teams.COLUMN_WINS, wins);
                    teamCV.put(Contract.Teams.COLUMN_DRAWS, draws);
                    teamCV.put(Contract.Teams.COLUMN_LOSES, losses);
                    teamCV.put(Contract.Teams.COLUMN_POINTS, points);
                    teamCV.put(Contract.Teams.COLUMN_GOALS, goals);
                    teamCV.put(Contract.Teams.COLUMN_PLAYED_GAMES, games);
                    teamCV.put(Contract.Teams.COLUMN_GOALS_AGAINST, gA);
                    teamCV.put(Contract.Teams.COLUMN_POSITION, position);
                    teamCV.put(Contract.Teams.COLUMN_ROUND_ID, roundId);


//
////
                    getContentResolver()
                            .update(Contract.Teams.URI, teamCV, Contract.Teams.COLUMN_TEAM_ID, new String[]{String.valueOf(teamId)});

                }
//                if (mInterstitialAd.isLoaded())
//                    mInterstitialAd.show();

////
//                Log.d("uri", "onSuccess: Insertedtable" + getContentResolver()
//                        .bulkInsert(
//                                Contract.Teams.URI,
//                                teamsCVs.toArray(new ContentValues[teamsCVs.size()])));



            }
        });


        Requests.getInstance(this).getRequest(Contract.baseUrl + Contract.teams, key, value, new Requests.VolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(JSONArray result) throws JSONException {


                Cursor mCursor = getContentResolver().query(
                        Contract.Teams.URI,
                        new String[]{Contract.Teams.COLUMN_TEAM_ID},
                        null,
                        null,
                        null);


                for (int i = 0; i < result.length(); i++) {

                    JSONObject teamObject = result.getJSONObject(i);
                    int externalTeamID = teamObject.getInt("TeamId");

                    assert mCursor != null;
                    for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {


                        int teamID = mCursor.getInt(mCursor.getColumnIndex(Contract.Teams.COLUMN_TEAM_ID));
                        if (teamID == externalTeamID) {

                            ContentValues cV = new ContentValues();
                            cV.put(Contract.Teams.COLUMN_PIC_URL, teamObject.getString("WikipediaLogoUrl"));
                            cV.put(Contract.Teams.COLUMN_STADIUM, teamObject.getString("VenueName"));
                            cV.put(Contract.Teams.COLUMN_COUNTRY, teamObject.getString("AreaName"));
                            getContentResolver()
                                    .update(Contract.Teams.URI, cV, Contract.Teams.COLUMN_TEAM_ID, new String[]{String.valueOf(externalTeamID)});


                        }
                    }


                }
                mCursor.close();

            }
        });


    }

    @Override
    public void fetchScheduleData() {

        String key[] = {getString(R.string.subscriptionKey)};
        String value[] = {getString(R.string.api_key)};

        Requests.getInstance(this).getRequest(Contract.baseUrl + Contract.schedule + roundId, key, value, new Requests.VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) throws JSONException {
                ArrayList<ContentValues> matchesCVs = new ArrayList();
//                Cursor homeCursor=null;
//                Cursor awayCursor = null;


                for (int i = 0; i < result.length(); i++) {
                    JSONObject matchObject = result.getJSONObject(i);

                    String homeTeamGoals = matchObject.getString(Contract.Match.homeTeamScore);
                    String awayTeamGoals = matchObject.getString(Contract.Match.awayTeamScore);
                    String date = matchObject.getString(Contract.Match.dateTime);
                    int week = matchObject.getInt(Contract.Match.week);
                    int gameId = matchObject.getInt(Contract.Match.gameId);
                    int homeTeamId = matchObject.getInt(Contract.Match.homeTeamId);
                    int awayTeamId = matchObject.getInt(Contract.Match.awayTeamId);

                    String status = matchObject.getString(Contract.Match.status);


//
//                   homeCursor =getContentResolver().query(Contract.Teams.URI,
//                            new String[]{Contract.Teams.COLUMN_PIC_URL,Contract.Teams.COLUMN_NAME},
//                            Contract.Teams.COLUMN_TEAM_ID, new String[]{String.valueOf(homeTeamId)}, null);
//                    assert homeCursor != null;
//                    homeCursor.moveToNext();
//
//                   awayCursor = getContentResolver().query(Contract.Teams.URI,
//                            new String[]{Contract.Teams.COLUMN_PIC_URL,Contract.Teams.COLUMN_NAME},
//                            Contract.Teams.COLUMN_TEAM_ID, new String[]{String.valueOf(awayTeamId)}, null);
//                    assert awayCursor != null;
//                    awayCursor.moveToNext();
//
//                    String homePic = homeCursor.getString(homeCursor.getColumnIndex(Contract.Teams.COLUMN_PIC_URL));
//                    String awayPic = awayCursor.getString(awayCursor.getColumnIndex(Contract.Teams.COLUMN_PIC_URL));
//                    String homeTeam = homeCursor.getString(homeCursor.getColumnIndex(Contract.Teams.COLUMN_NAME));
//                    String awayTeam = awayCursor.getString(awayCursor.getColumnIndex(Contract.Teams.COLUMN_NAME));


                    ContentValues matchCV = new ContentValues();
                    matchCV.put(Contract.Match.COLUMN_WEEK, week);
                    matchCV.put(Contract.Match.COLUMN_GAME_ID, gameId);
//                    matchCV.put(Contract.Match.COLUMN_AWAY_TEAM, awayTeam);
//                    matchCV.put(Contract.Match.COLUMN_HOME_TEAM, homeTeam);
                    matchCV.put(Contract.Match.COLUMN_AWAY_TEAM_GOALS, awayTeamGoals);
                    matchCV.put(Contract.Match.COLUMN_HOME_TEAM_GOALS, homeTeamGoals);
                    matchCV.put(Contract.Match.COLUMN_DATE, date);
                    matchCV.put(Contract.Match.COLUMN_STATUS, status);
                    matchCV.put(Contract.Match.COLUMN_ROUND_ID, roundId);
                    matchCV.put(Contract.Match.COLUMN_HOME_ID, homeTeamId);
                    matchCV.put(Contract.Match.COLUMN_AWAY_ID, awayTeamId);
//                    matchCV.put(Contract.Match.COLUMN_HOME_PIC,homePic);
//                    matchCV.put(Contract.Match.COLUMN_AWAY_PIC,awayPic);

                    matchesCVs.add(matchCV);


                    Log.d("updated", "onSuccess: " + getContentResolver()
                            .update(Contract.Match.URI, matchCV, Contract.Match.COLUMN_GAME_ID, new String[]{String.valueOf(gameId)}));
                    ;


                }
//                homeCursor.close();
//                awayCursor.close();

//                Log.d("uri", "onSuccess: InsertedtableMatches" + getContentResolver()
//                        .bulkInsert(
//                                Contract.Match.URI,
//                                matchesCVs.toArray(new ContentValues[matchesCVs.size()])));


            }
        });
    }


}