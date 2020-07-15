package com.androidlec.wagle.network_sh;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask_AddBJM extends AsyncTask<Integer, String, Object> {

    Context context;
    String mAddr;

    public NetworkTask_AddBJM(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
