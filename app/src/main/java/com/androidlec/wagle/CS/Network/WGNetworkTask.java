package com.androidlec.wagle.CS.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.androidlec.wagle.CS.Model.WagleList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WGNetworkTask extends AsyncTask<Integer, String, ArrayList<WagleList>> {

    private Context context;
    private String mAddr;
    private ProgressDialog progressDialog;

    private ArrayList<WagleList> data;

    public WGNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.data = new ArrayList<>();
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
    protected void onPostExecute(ArrayList<WagleList> aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected ArrayList<WagleList> doInBackground(Integer... integers) {

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

        return data;
    }

    protected void parser(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("wagle_list"));
            data.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                // JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String wcSeqno = jsonObject1.getString("wcSeqno");
                String Moim_wmSeqno = jsonObject1.getString("Moim_wmSeqno");
                String MoimUser_muSeqno = jsonObject1.getString("MoimUser_muSeqno");
                String WagleBook_wbSeqno = jsonObject1.getString("WagleBook_wbSeqno");
                String wcName = jsonObject1.getString("wcName");
                String wcType = jsonObject1.getString("wcType");
                String wcStartDate = jsonObject1.getString("wcStartDate");
                String wcEndDate = jsonObject1.getString("wcEndDate");
                String wcDueDate = jsonObject1.getString("wcDueDate");
                String wcLocate = jsonObject1.getString("wcLocate");
                String wcEntryFee = jsonObject1.getString("wcEntryFee");
                String wcWagleDetail = jsonObject1.getString("wcWagleDetail");
                String wcWagleAgreeRefund = jsonObject1.getString("wcWagleAgreeRefund");

                data.add(new WagleList(wcSeqno, Moim_wmSeqno, MoimUser_muSeqno, WagleBook_wbSeqno, wcName, wcType, wcStartDate, wcEndDate, wcDueDate, wcLocate, wcEntryFee, wcWagleDetail, wcWagleAgreeRefund));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
