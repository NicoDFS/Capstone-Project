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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.biro.footballsocer.Adapters.StandingAdapter;
import com.example.biro.footballsocer.Data.Contract;
import com.example.biro.footballsocer.R;
import com.example.biro.footballsocer.Utils.SharedPref;
import com.example.biro.footballsocer.Utils.Utils;

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




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_standing, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                getLoaderManager().restartLoader(0, null, Standing.this);

            }
        }, 6000);
    }


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
    public void onRefresh() {

        if (Utils.getInstance(getContext()).networkUp()) {
            fetchDataListener.fetchTeamsData();

        } else {
            Toast.makeText(getContext(), R.string.connectionError, Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
        }

        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(getActivity(),
                Contract.Teams.URI,
                Contract.Teams.TEAM_COLUMNS,
                Contract.Teams.COLUMN_ROUND_ID, new String[]{SharedPref.getInstance(getContext()).load(Contract.TAG)}, Contract.Teams.COLUMN_POSITION);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        adapter = new StandingAdapter(data, getContext());
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.setAdapter(null);
    }


}
