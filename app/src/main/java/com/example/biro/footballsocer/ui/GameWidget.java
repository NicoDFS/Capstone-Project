package com.example.biro.footballsocer.ui;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.biro.footballsocer.R;
import com.example.biro.footballsocer.sync.GameWidgetService;

/**
 * Created by Biro on 9/9/2017.
 */

public class GameWidget extends AppWidgetProvider {

    public static final String ACTION_UPDATE_WIDGET = "com.example.biro.footballsocer";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(ACTION_UPDATE_WIDGET)) {
            Log.d("recived", "onReceive: reciverd");
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
            ComponentName thisWidget = new ComponentName(context.getApplicationContext(), GameWidget.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

            onUpdate(context, appWidgetManager, appWidgetIds);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listWidget);


        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.game_widget);
            Intent svcIntent = new Intent(context, GameWidgetService.class);
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            view.setRemoteAdapter(R.id.listWidget, svcIntent);
            appWidgetManager.updateAppWidget(appWidgetIds[i], view);
        }

    }

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(ACTION_UPDATE_WIDGET);
        intent.setComponent(new ComponentName(context, GameWidget.class));
        context.sendBroadcast(intent);
    }

}
