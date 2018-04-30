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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.biro.footballsocer.adapters.TeamsAdapter;
import com.example.biro.footballsocer.data.Contract;
import com.example.biro.footballsocer.R;
import com.example.biro.footballsocer.utils.SharedPref;
import com.example.biro.footballsocer.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Teams extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> {


    @BindView(R.id.swipeRef)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.team_List)
    RecyclerView teamList;

    private FetchDataListener fetchDataListener;
    private TeamsAdapter adapter;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teams, container, false);
        ButterKnife.bind(this, view);


        adapter = new TeamsAdapter(getActivity());
        teamList.setAdapter(adapter);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        teamList.setLayoutManager(sglm);

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
    public void onDestroyView() {
        super.onDestroyView();
        teamList.setAdapter(null);
    }

    @Override
    public void onRefresh() {


        if (Utils.getInstance(context).networkUp()) {
            fetchDataListener.fetchTeamsData();
            getLoaderManager().restartLoader(1, null, this);
        } else {
            Toast.makeText(context, R.string.connectionError, Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
        }


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

        swipeRefreshLayout.setRefreshing(false);

        adapter.notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        swipeRefreshLayout.setRefreshing(false);
        teamList.setAdapter(null);
    }
}
