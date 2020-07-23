package com.androidlec.wagle.network_sh;

import android.content.Context;
import android.os.AsyncTask;

import com.androidlec.wagle.dto.BookInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask_BookInfo extends AsyncTask<Integer, String, Object> {

    Context context;
    String mAddr;
    BookInfo bookinfoData;

    public NetworkTask_BookInfo(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.bookinfoData = new BookInfo();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                // 잇풋 스트림으로 가져온 것을 인풋스트림 리더로 가져온다.
                //버퍼드 리더가 인풋스트림리더가 포장한 것을 임시보관함에 휙 올린다.
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true) {
                    String strline = bufferedReader.readLine();
                    if (strline == null) break;
                    stringBuffer.append(strline + "\n");
                }

                Parser(stringBuffer.toString());

            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bookinfoData;
    }

    private void Parser(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("book_info"));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int wbSeqno = Integer.parseInt(jsonObject1.getString("wbSeqno"));
                String wbTitle = jsonObject1.getString("wbTitle");
                String wbWriter = jsonObject1.getString("wbWriter");
                int wbMaxPage = Integer.parseInt(jsonObject1.getString("wbMaxPage"));
                String wbIntro = jsonObject1.getString("wbIntro");
                String wbData = jsonObject1.getString("wbData");
                String wbImage = jsonObject1.getString("wbImage");

                bookinfoData.setTitle(wbTitle);
                bookinfoData.setWriter(wbWriter);
                bookinfoData.setMaxpage(wbMaxPage);
                bookinfoData.setIntro(wbIntro);
                bookinfoData.setData(wbData);
                bookinfoData.setImgName(wbImage);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
