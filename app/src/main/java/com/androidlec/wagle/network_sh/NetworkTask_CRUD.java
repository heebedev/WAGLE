package com.androidlec.wagle.network_sh;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask_CRUD extends AsyncTask<Integer, String, Object> {

    Context context;
    String mAddr;

    public NetworkTask_CRUD(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        boolean result = false;
        try {
            URL url = new URL(mAddr);
            //Log.e("status", "CRUD TASK : " + mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
