package com.example.biro.footballsocer.ui;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.biro.footballsocer.data.Contract;
import com.example.biro.footballsocer.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Biro on 9/9/2017.
 */

public class GameViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Cursor cursor;
    private Context context;
    private int appWidgetId;
    final String selection = "InProgress";
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
    final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");


    public GameViewFactory(Context context, Intent intent) {
        Log.d("factory", "GameViewFactory:");
        this.context = context;
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);


    }

    @Override
    public void onCreate() {
        Log.d("Test", "onCreate: created ");
        this.cursor = context.getContentResolver().query(Contract.Match.URI,
                Contract.Match.MATCH_COLUMNS, Contract.Match.COLUMN_STATUS, new String[]{selection}, null);
    }

    @Override
    public void onDataSetChanged() {
        Log.d("updateWidget", "onDataSetChanged: ");
        try {
            this.cursor = context.getContentResolver().query(Contract.Match.URI,
                    Contract.Match.MATCH_COLUMNS, Contract.Match.COLUMN_STATUS, new String[]{selection}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {

        cursor = null;
    }

    @Override
    public int getCount() {
        if (cursor != null)
            return cursor.getCount();
        else
            return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews row = new RemoteViews(context.getPackageName(),
                R.layout.widget_row);
        if (cursor != null) {
            cursor.moveToPosition(position);
            final Picasso picasso = Picasso.with(context);
            String homeTeamScore = cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_HOME_TEAM_GOALS));
            String awayTeamScore = cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_AWAY_TEAM_GOALS));
            String dT = cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_DATE));
            String homePic = cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_HOME_PIC));
            String awayPic = cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_AWAY_PIC));
            String[] dateTime = dT.split("T");
            String time = dateTime[1];
            try {
                Date t = sdf.parse(time);
                time = timeFormat.format(t);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            row.setTextViewText(R.id.homeTeamName, cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_HOME_TEAM)));
            row.setTextViewText(R.id.awayTeamName, cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_AWAY_TEAM)));
            row.setTextViewText(R.id.time, time.substring(0, time.length() - 3));


            row.setTextViewText(R.id.status, context.getString(R.string.colon));
            row.setTextViewText(R.id.homeTeamScore, homeTeamScore);
            row.setTextViewText(R.id.awayTeamScore, awayTeamScore);
            row.setTextViewText(R.id.inProgress, context.getString(R.string.inProgress));
            row.setTextViewText(R.id.nowPlaying, context.getString(R.string.noMplayingnow));


            try {
                Bitmap homeImage = picasso.load(homePic).get();
                if (homeImage != null)
                    row.setImageViewBitmap(R.id.homeTeamPic, homeImage);
                else
                    row.setImageViewResource(R.id.homeTeamPic, R.drawable.notfoundpng);

                Bitmap awayImage = picasso.load(awayPic).get();
                if (awayImage != null)
                    row.setImageViewBitmap(R.id.awayTeamPic, awayImage);
                else
                    row.setImageViewResource(R.id.awayTeamPic, R.drawable.notfoundpng);
            } catch (IOException e) {
                e.printStackTrace();
            }


//
//            picasso.load(cursor.getString(cursor.getColumnIndex(Contract.Match.COLUMN_AWAY_PIC)))
//                    .into(row, R.id.awayTeamPic, new int[]{appWidgetId});
        } else
            Log.d("cursor", "getViewAt: null");


//
        ;


        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
