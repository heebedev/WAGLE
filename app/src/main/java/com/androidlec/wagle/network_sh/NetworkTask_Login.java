package com.androidlec.wagle.network_sh;

import android.content.Context;
import android.os.AsyncTask;
import com.androidlec.wagle.UserInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask_Login extends AsyncTask<Integer, String, Object> {

    Context context;
    String mAddr;
    int uSeqno;
    String uId;
    String uEmail;
    String uName;
    String uLoginType;
    String uPassword;
    String uImageName;
    String uBirthDate;
    String uDate;
    boolean result = false;


    public NetworkTask_Login(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
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
        return result;
    }

    private void Parser(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));
            if (jsonArray.isNull(0) != true) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                uSeqno = jsonObject1.getInt("uSeqno");
                uId = jsonObject1.getString("uId");
                uPassword = jsonObject1.getString("uPassword");
                uEmail = jsonObject1.getString("uEmail");
                uLoginType = jsonObject1.getString("uLoginType");
                uName = jsonObject1.getString("uName");
                uImageName = jsonObject1.getString("uImageName");
                uBirthDate = jsonObject1.getString("uBirthDate");
                uDate = jsonObject1.getString("uDate");

                UserInfo.USEQNO = uSeqno;
                UserInfo.UID = uId;
                UserInfo.UPASSWORD = uPassword;
                UserInfo.UEMAIL = uEmail;
                UserInfo.ULOGINTYPE = uLoginType;
                UserInfo.UNAME = uName;
                UserInfo.UIMAGENAME = uImageName;
                UserInfo.UBIRTHDATE = uBirthDate;
                UserInfo.UDATE = uDate;

                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
