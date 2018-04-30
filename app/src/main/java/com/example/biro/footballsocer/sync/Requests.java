package com.example.biro.footballsocer.sync;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.biro.footballsocer.R;
import com.example.biro.footballsocer.utils.ApplicationController;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by Biro on 7/4/2017.
 */

public class Requests {

    private Activity context;
    private static Requests instance = null;


    private Requests(Activity context) {
        this.context = context;

    }

    public static Requests getInstance(Activity context) {
        if (instance == null) {
            instance = new Requests(context);

        }
        return instance;
    }


    public HashMap putParams(String keys[], String data[]) {
        HashMap<String, String> params = new HashMap<String, String>();
        for (int i = 0; i < keys.length; i++) {
            params.put(keys[i], data[i]);
        }
        return params;
    }


    public void getRequest(String url, final String[] keys, final String[] values, final VolleyCallback callback) {

        JsonArrayRequest  getReq = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                Log.d("response", response.toString());

                try {
                    callback.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {




            }

        }) {

            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError {

                return putParams(keys, values);
            }
        };

        ApplicationController.getInstance().addToRequestQueue(getReq);



    }


    public interface VolleyCallback {
        void onSuccess(JSONArray result) throws JSONException;
    }

}
