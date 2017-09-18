package com.example.biro.footballsocer.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biro.footballsocer.data.Contract;
import com.example.biro.footballsocer.ui.DetailActivity;
import com.example.biro.footballsocer.models.Team;
import com.example.biro.footballsocer.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Biro on 9/1/2017.
 */

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {

    Cursor cursor;
    Activity context;
    private FirebaseAnalytics mFirebaseAnalytics;

    public TeamsAdapter(Activity context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_row, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {



                cursor.moveToPosition(vh.getAdapterPosition());
                String teamImage = cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_PIC_URL));
                String teamName = cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_NAME));
                String teamCountry = cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_COUNTRY));
                String teamStadium = cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_STADIUM));
                int teamMatches = cursor.getInt(cursor.getColumnIndex(Contract.Teams.COLUMN_PLAYED_GAMES));
                int teamLeague = cursor.getInt(cursor.getColumnIndex(Contract.Teams.COLUMN_ROUND_ID));
                int teamPosition = cursor.getInt(cursor.getColumnIndex(Contract.Teams.COLUMN_POSITION));
                int teamPoints = cursor.getInt(cursor.getColumnIndex(Contract.Teams.COLUMN_POINTS));
                int teamsGoals = cursor.getInt(cursor.getColumnIndex(Contract.Teams.COLUMN_GOALS));
                int teamGoalsAg = cursor.getInt(cursor.getColumnIndex(Contract.Teams.COLUMN_GOALS_AGAINST));
                int teamWins = cursor.getInt(cursor.getColumnIndex(Contract.Teams.COLUMN_WINS));
                int teamLoses = cursor.getInt(cursor.getColumnIndex(Contract.Teams.COLUMN_LOSES));
                int teamDraws = cursor.getInt(cursor.getColumnIndex(Contract.Teams.COLUMN_DRAWS));



                Team team = new Team(teamImage, teamCountry, teamName, teamStadium,
                        teamPosition, teamPoints, teamWins, teamLoses, teamDraws, teamsGoals, teamGoalsAg, teamLeague,teamMatches);

                Bundle b = new Bundle();
                b.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(vh.getAdapterPosition()));
                b.putString(FirebaseAnalytics.Param.ITEM_NAME, teamName);
                b.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, b);

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(context.getString(R.string.teamkey), team);
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(context, vh.teamImage,vh.teamImage.getTransitionName()).toBundle();

                context.startActivity(intent,bundle);
            }
        });
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        cursor.moveToPosition(position);
        int width;
        int height;
        int placeHolder;
        if (Build.VERSION.SDK_INT < 21) {

            width = 75;
            height = 75;
            placeHolder = R.drawable.progressimage;


        } else {

            width = 200;
            height = 200;
            placeHolder = R.drawable.progress_animation;
        }
        Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_PIC_URL)))
                .resize(width, height)
                .placeholder(placeHolder)
                .into(holder.teamImage);


        holder.teamName.setText(cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_NAME)));
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (cursor != null) {
            count = cursor.getCount();
        }
        return count;
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.teamImage)
        ImageView teamImage;
        @BindView(R.id.teamName)
        TextView teamName;
        @BindView(R.id.teamCard)
        CardView teamCard;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}