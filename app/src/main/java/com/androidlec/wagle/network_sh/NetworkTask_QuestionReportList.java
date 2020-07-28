package com.androidlec.wagle.network_sh;

import android.content.Context;
import android.os.AsyncTask;


import com.androidlec.wagle.dto.SgstRptList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask_QuestionReportList extends AsyncTask<Integer, String, Object> {

    private Context context;
    private String mAddr;
    private ArrayList<SgstRptList> questionlist;

    public NetworkTask_QuestionReportList(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.questionlist = new ArrayList<SgstRptList>();
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

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                // 잇풋 스트림으로 가져온 것을 인풋스트림 리더로 가져온다.
                //버퍼드 리더가 인풋스트림리더가 포장한 것을 임시보관함에 휙 올린다.
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true) {
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");
                }

                Parser(stringBuffer.toString());

            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufferedReader != null) bufferedReader.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(inputStream != null) inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return questionlist;
    }

    private void Parser(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("suggestion_list"));

            for(int i = 0 ; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int sSeqno = Integer.parseInt(jsonObject1.getString("sSeqno"));
                String wcSeqno = jsonObject1.getString("wcSeqno");
                String sType = jsonObject1.getString("sType");
                String sContent = jsonObject1.getString("sContent");

                SgstRptList sgstRptList = new SgstRptList(sSeqno, wcSeqno, sType, sContent);

                questionlist.add(sgstRptList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
