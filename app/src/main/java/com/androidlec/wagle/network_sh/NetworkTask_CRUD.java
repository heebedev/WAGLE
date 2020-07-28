package com.androidlec.wagle.network_sh;

import android.content.Context;
import android.os.AsyncTask;


import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask_CRUD extends AsyncTask<Integer, String, Void> {

    Context context;
    String mAddr;

    public NetworkTask_CRUD(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
