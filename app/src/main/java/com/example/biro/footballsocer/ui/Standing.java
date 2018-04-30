package com.example.biro.footballsocer.ui;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.biro.footballsocer.adapters.StandingAdapter;
import com.example.biro.footballsocer.data.Contract;
import com.example.biro.footballsocer.R;
import com.example.biro.footballsocer.utils.SharedPref;
import com.example.biro.footballsocer.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class Standing extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.standingTable)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRef)
    SwipeRefreshLayout swipeRefreshLayout;
    StandingAdapter adapter;
    FetchDataListener fetchDataListener;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_standing, container, false);
        ButterKnife.bind(this, view);


        adapter = new StandingAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        return view;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fetchDataListener = (FetchDataListener) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
        this.context = context;


    }

    @Override
    public void onRefresh() {

        if (Utils.getInstance(context).networkUp()) {
            fetchDataListener.fetchTeamsData();

        } else {
            Toast.makeText(context, R.string.connectionError, Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
        }
        getLoaderManager().restartLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(context,
                Contract.Teams.URI,
                Contract.Teams.TEAM_COLUMNS,
                Contract.Teams.COLUMN_ROUND_ID, new String[]{SharedPref.getInstance(context).load(Contract.TAG)}, Contract.Teams.COLUMN_POSITION);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        adapter.setCursor(data);

        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.setAdapter(null);
    }


}
