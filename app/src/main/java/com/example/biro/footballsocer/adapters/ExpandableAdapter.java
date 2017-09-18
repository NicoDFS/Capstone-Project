package com.example.biro.footballsocer.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biro.footballsocer.models.Game;
import com.example.biro.footballsocer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Biro on 9/8/2017.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {

    Context context;
    private ArrayList<String> listDataHeader; // header titles;
    // child data in format of header title, child title
    private HashMap<String, ArrayList<Game>> listDataChild;


    public ExpandableAdapter(Context context, ArrayList<String> listDataHeader, HashMap<String, ArrayList<Game>> listDataChild) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ExpandableAdapter.ViewHolder holder;

        String header = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exp_header, null);
            holder = new ExpandableAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        assert holder.icon != null;

        if (isExpanded) {
            holder.icon.setImageResource(R.drawable.downarrow);
        } else
            holder.icon.setImageResource(R.drawable.rightarrow);


        holder.header.setText(header);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ExpandableAdapter.ViewHolder holder;

        Game gameObj = (Game) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exp_child, null);
            holder = new ExpandableAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.awayTeamName.setText(gameObj.getAwayTeamName());
        holder.homeTeamName.setText(gameObj.getHomeTeamName());

        if (gameObj.getDate().equals("null")) {

            holder.date.setText("Not Scheduled Yet");
        } else
            holder.date.setText(gameObj.getDate());

        if (gameObj.getTime().equals("null")) {
            holder.time.setText("");
        } else
            holder.time.setText(gameObj.getTime().substring(0, gameObj.getTime().length() - 3));


        int width;
        int height;
        int placeHolder;
        if (Build.VERSION.SDK_INT < 21) {

            width = 50;
            height = 50;
            placeHolder = R.drawable.progressimage;
        } else {
            width = 200;
            height = 200;
            placeHolder = R.drawable.progress_animation;
        }

        Picasso.with(context).load(gameObj.getHomeTeamPic()).resize(width, height).placeholder(placeHolder).into(holder.homeTeamPic);
        Picasso.with(context).load(gameObj.getAwayTeamPic()).resize(width, height).placeholder(placeHolder).into(holder.awayTeamPic);

        if (gameObj.getStatus().equals("Scheduled")) {

            holder.status.setText("VS");
            holder.awayTeamScore.setText("");
            holder.homeTeamScore.setText("");
        } else if (gameObj.getStatus().equals("Final")) {

            holder.awayTeamScore.setText(gameObj.getAwayTeamGoals());
            holder.homeTeamScore.setText(gameObj.getHomeTeamGoals());
            holder.status.setText(":");
        } else if (gameObj.getStatus().equals("InProgress")) {
            holder.awayTeamScore.setText(gameObj.getAwayTeamGoals());
            holder.homeTeamScore.setText(gameObj.getHomeTeamGoals());
            holder.inProgress.setText("In Progress");
            holder.status.setText(":");

        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public class ViewHolder {

        //View line;
        @Nullable
        @BindView(R.id.listHeader)
        TextView header;
        @Nullable
        @BindView(R.id.status)
        TextView status;
        @Nullable
        @BindView(R.id.date)
        TextView date;
        @Nullable
        @BindView(R.id.time)
        TextView time;
        @Nullable
        @BindView(R.id.homeTeamName)
        TextView homeTeamName;
        @Nullable
        @BindView(R.id.homeTeamScore)
        TextView homeTeamScore;
        @Nullable
        @BindView(R.id.awayTeamName)
        TextView awayTeamName;
        @Nullable
        @BindView(R.id.awayTeamScore)
        TextView awayTeamScore;
        @Nullable
        @BindView(R.id.homeTeamPic)
        ImageView homeTeamPic;
        @Nullable
        @BindView(R.id.awayTeamPic)
        ImageView awayTeamPic;
        @Nullable
        @BindView(R.id.inProgress)
        TextView inProgress;
        @Nullable
        @BindView(R.id.arrow)
        ImageView icon;

        ViewHolder(View v) {

            ButterKnife.bind(this, v);
        }
    }
}
