package com.androidlec.wagle.CS.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.androidlec.wagle.CS.Model.BoardList;
import com.androidlec.wagle.CS.Model.BoardTitleList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BDTNetworkTask extends AsyncTask<Integer, String, ArrayList<BoardTitleList>> {

    private Context context;
    private String mAddr;
    private ProgressDialog progressDialog;

    private ArrayList<BoardTitleList> boardTitleLists;

    public BDTNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.boardTitleLists = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<BoardTitleList> aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected ArrayList<BoardTitleList> doInBackground(Integer... integers) {

        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true) {
                    String strline = bufferedReader.readLine();
                    if (strline == null) break;
                    stringBuffer.append(strline + "\n");
                }

                parser(stringBuffer.toString().trim());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return boardTitleLists;
    }

    protected void parser(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("board_title_list"));
            boardTitleLists.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                // JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String bSeqno = jsonObject1.getString("bSeqno");
                String bName = jsonObject1.getString("bName");
                String bDate = jsonObject1.getString("bDate");
                String bOrder = jsonObject1.getString("bOrder");

                boardTitleLists.add(new BoardTitleList(bSeqno, bName, bDate, bOrder));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}