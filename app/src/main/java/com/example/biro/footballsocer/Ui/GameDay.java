package com.example.biro.footballsocer.Ui;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.biro.footballsocer.Adapters.ExpandableAdapter;
import com.example.biro.footballsocer.Data.Contract;
import com.example.biro.footballsocer.Models.Game;
import com.example.biro.footballsocer.R;
import com.example.biro.footballsocer.Utils.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameDay extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> {


    @BindView(R.id.swipeRef)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.exp)
    ExpandableListView expandableListView;
    ExpandableAdapter adapter;
    FetchDataListener fetchDataListener;
    ArrayList<Game> games;
    HashMap<String, ArrayList<Game>> listDataChild = new HashMap<>();
    ArrayList<String> listDataHeader;
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
    final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
    final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_day, container, false);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        getLoaderManager().initLoader(1, null, this);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());




        return view;
    }

    //
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fetchDataListener = (FetchDataListener) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }


    }

    @Override
    public void onStart() {
        super.onStart();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                onRefresh();
            }
        }, 2000);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                Contract.Match.URI,
                Contract.Match.MATCH_COLUMNS,
                Contract.Match.COLUMN_ROUND_ID, new String[]{MainActivity.getRoundId()}, Contract.Match.COLUMN_WEEK);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        try {

            prepareData(data);
            adapter = new ExpandableAdapter(getContext(), listDataHeader, listDataChild);
            expandableListView.setAdapter(adapter);
            swipeRefreshLayout.setRefreshing(false);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {


        if (Utils.getInstance(getContext()).networkUp()) {
            fetchDataListener.fetchScheduleData();
            GameWidget.sendRefreshBroadcast(getActivity());

        } else {
            Toast.makeText(getContext(), "Can not sync check your internet connection", Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
        }
        getLoaderManager().restartLoader(1, null, this);
    }
//
    void prepareData(Cursor cursor) throws ParseException {

        int diff;
        int count = 0;
        int roundId = Integer.valueOf(MainActivity.getRoundId());
        listDataHeader = new ArrayList<String>();
        if (roundId == Integer.valueOf(getString(R.string.bundesliga))) {
            diff = 34;

        } else
            diff = 38;


        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex(Contract.Match.COLUMN_WEEK)) == 1)
                count++;
            else
                break;

        }

        cursor.moveToFirst();



        for (int i = 1; i <= diff; i++) {

            games = new ArrayList<Game>();
            listDataHeader.add("Week " + String.valueOf(i));

            for (int j = 0; j < count; j++) {


                String status = cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_STATUS));
                String homeTeamName = cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_HOME_TEAM));
                String awayTeamName = cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_AWAY_TEAM));
                String dT = cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_DATE));
                String homePic = cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_HOME_PIC));
                String awayPic = cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_AWAY_PIC));

                String date = "null";
                String time = "null";
                if (!dT.equals("null")) {
                    String[] dateTime = dT.split("T");
                    date = dateTime[0];
                    time = dateTime[1];
                    Date t = sdf2.parse(time);
                    Date d = sdf.parse(date);
                    date = dateFormat.format(d);
                    time = timeFormat.format(t);
                }

                String homeTeamScore = cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_HOME_TEAM_GOALS));
                String awayTeamScore = cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_AWAY_TEAM_GOALS));
                games.add(new Game(status, homeTeamName, awayTeamName, homePic, awayPic, date, time, homeTeamScore, awayTeamScore));
                if (!cursor.isLast()) {
                    cursor.moveToNext();
                }

            }

            listDataChild.put(listDataHeader.get(i - 1), games);

        }


    }
}
