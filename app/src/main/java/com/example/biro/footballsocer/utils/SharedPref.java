package com.example.biro.footballsocer.utils;

import android.content.Context;

/**
 * Created by Biro on 9/1/2017.
 */

public class SharedPref {

    private android.content.SharedPreferences sharedPreferences;
    private static SharedPref instance = null;
    private static final String DEFAULT = "144";


    public static SharedPref getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPref(context);
        }
        return instance;
    }

    private  SharedPref(Context context) {

        this.sharedPreferences = context.getSharedPreferences("FootballSoccer", Context.MODE_PRIVATE);
    }

    public void save(String tag,String data) {

        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(tag,data);
        editor.apply();


    }
    public String load(String tag)
    {
        return sharedPreferences.getString(tag,DEFAULT);
    }
}
