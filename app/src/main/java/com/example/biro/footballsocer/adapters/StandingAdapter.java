package com.example.biro.footballsocer.adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biro.footballsocer.data.Contract;
import com.example.biro.footballsocer.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Biro on 9/2/2017.
 */

public class StandingAdapter extends RecyclerView.Adapter<StandingAdapter.ViewHolder> {


    Cursor cursor;
    Context context;

    public StandingAdapter(Cursor cursor, Context context) {
        this.cursor = cursor;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.standing_row, parent, false);
        return new StandingAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        int placeHolder;
        int width;
        int height;

        if (Build.VERSION.SDK_INT < 21) {

            placeHolder = R.drawable.progressimage;
            width = 50;
            height = 50;

        } else {
            placeHolder = R.drawable.progress_animation;
            width = 100;
            height = 100;
        }

        Picasso.with(context)
                .load(cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_PIC_URL)))
                .resize(width, height)
                .placeholder(placeHolder)
                .into(holder.clubImage);

        holder.games.setText(cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_PLAYED_GAMES)));
        holder.points.setText(cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_POINTS)));
        holder.wins.setText(cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_WINS)));
        holder.loses.setText(cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_LOSES)));
        holder.draws.setText(cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_DRAWS)));
        holder.gA.setText(cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_GOALS_AGAINST)));
        holder.goals.setText(cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_GOALS)));
        holder.position.setText(cursor.getString(cursor.getColumnIndex(Contract.Teams.COLUMN_POSITION)));

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


        @BindView(R.id.clubImage)
        ImageView clubImage;

        @BindView(R.id.games)
        TextView games;
        @BindView(R.id.wins)
        TextView wins;
        @BindView(R.id.loses)
        TextView loses;
        @BindView(R.id.draws)
        TextView draws;
        @BindView(R.id.points)
        TextView points;
        @BindView(R.id.gA)
        TextView gA;
        @BindView(R.id.position)
        TextView position;
        @BindView(R.id.goals)
        TextView goals;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}
