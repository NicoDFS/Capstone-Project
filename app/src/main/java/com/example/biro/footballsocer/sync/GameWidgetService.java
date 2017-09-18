package com.example.biro.footballsocer.sync;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.example.biro.footballsocer.ui.GameViewFactory;

/**
 * Created by Biro on 9/9/2017.
 */

public class GameWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        Log.d("service", "onGetViewFactory: service fired");

        return new GameViewFactory(this, intent);
    }
}
