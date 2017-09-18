package com.example.biro.footballsocer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Biro on 8/29/2017.
 */

public class Utils {

    Context context;
    private static Utils instance = null;

    private Utils(Context context) {
        this.context = context;
    }

    public static Utils getInstance(Context context) {
        if (instance == null) {
            instance = new Utils(context);
        }
        return instance;
    }

    public boolean networkUp() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
