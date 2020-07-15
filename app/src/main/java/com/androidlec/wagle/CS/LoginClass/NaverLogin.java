package com.androidlec.wagle.CS.LoginClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.androidlec.wagle.CS.Model.User;
import com.androidlec.wagle.CS.Network.CSNetworkTask;
import com.androidlec.wagle.MainMoimListActivity;
import com.androidlec.wagle.MakeMoimActivity;
import com.androidlec.wagle.TempActivity;
import com.androidlec.wagle.UserInfo;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class NaverLogin {

    private Context mContext;
    private static OAuthLogin mOAuthLoginInstance;

    private String responseBody;

    private String strResult;

    public NaverLogin(Context mContext) {
        this.mContext = mContext;

        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.showDevelopersLog(true);

        String OAUTH_CLIENT_ID = "LqvFwgGknIp0QLbG8MNf";
        String OAUTH_CLIENT_SECRET = "KwQctVr1t1";
        String OAUTH_CLIENT_NAME = "WAGLE";
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
    }

    /**
     * startOAuthLoginActivity() 호출시 인자로 넘기거나, OAuthLoginButton 에 등록해주면 인증이 종료되는 걸 알 수 있다.
     */
    public OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {

                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String userId = getUserId(accessToken);
                if(!findUserFromDB(userId)) {
                    InputUserDataToDB();
                } else {
                    setUserInfo(userId);
                }

                Intent intent = new Intent(mContext, MainMoimListActivity.class);
                mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Log.e("TAG", "errorCode:" + errorCode + ", errorDesc:" + errorDesc);
            }
        }

    };

    private static String get(final String apiUrl, final Map<String, String> requestHeaders) {
        String result = null;

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... strings) {
                HttpURLConnection con = connect(apiUrl);
                try {
                    con.setRequestMethod("GET");
                    for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                        con.setRequestProperty(header.getKey(), header.getValue());
                    }

                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                        return readBody(con.getInputStream());
                    } else { // 에러 발생
                        return readBody(con.getErrorStream());
                    }
                } catch (IOException e) {
                    throw new RuntimeException("API 요청과 응답 실패", e);
                } finally {
                    con.disconnect();
                }
            }
        };

        try {
            result = asyncTask.execute(apiUrl).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    private String getUserId(String token) {
        String header = "Bearer " + token; // Bearer 다음에 공백 추가

        String apiURL = "https://openapi.naver.com/v1/nid/me";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);
        responseBody = get(apiURL, requestHeaders);

        return naverJsonParsing(responseBody);
    }

    private boolean findUserFromDB(String userId) {
        boolean result = false;

        String urlAddr = "http://192.168.0.79:8080/wagle/csFindUserWAGLE.jsp?";

        urlAddr = urlAddr + "userId=" + userId;

        try {
            CSNetworkTask csNetworkTask = new CSNetworkTask(mContext, urlAddr);
            strResult = csNetworkTask.execute().get(); // doInBackground 의 리턴값
            result = strResult.length() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void InputUserDataToDB() {
        User user = jsonParsing(responseBody);
        String urlAddr = "http://192.168.0.79:8080/wagle/csInputUserWAGLE.jsp?";

        urlAddr = urlAddr + "uId=" + user.getuId() + "&uEmail=" + user.getuEmail() + "&uName=" + user.getuName() + "&uImageName=" + user.getuImageName() + "&uBirthDate=" + user.getuBirthDate() + "&uLoginType=NAVER";

        try {
            CSNetworkTask csNetworkTask = new CSNetworkTask(mContext, urlAddr);
            csNetworkTask.execute().get();
            setUserInfo(user.getuId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUserInfo(String userId) {
        String urlAddr = "http://192.168.0.79:8080/wagle/csFindUserWAGLE.jsp?";

        urlAddr = urlAddr + "userId=" + userId;

        try {
            CSNetworkTask csNetworkTask = new CSNetworkTask(mContext, urlAddr);
            strResult = csNetworkTask.execute().get(); // doInBackground 의 리턴값

            String[] user = strResult.split(", ");
            UserInfo.USEQNO = Integer.parseInt(user[0]);
            UserInfo.UID = user[1];
            UserInfo.UEMAIL = user[2];
            UserInfo.UNAME = user[3];
            UserInfo.ULOGINTYPE = user[4];

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String naverJsonParsing(String json) {
        String id = "";
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject response = jsonObject.getJSONObject("response");
            id = response.getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    private User jsonParsing(String json) {
        User result = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject response = jsonObject.getJSONObject("response");

            String uId = response.getString("id");
            String uEmail = response.getString("email");
            String uName = response.getString("name");
            String uImageName = response.getString("profile_image");
            String uBirthDate = response.getString("birthday");

            result = new User(uId, uEmail, uName, uImageName, uBirthDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void logout() {
        mOAuthLoginInstance.logout(mContext);
        ((Activity) mContext).finish();
    }

    public void deleteToken() {
        AsyncTask<Void, Void, Boolean> asyncTask = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                boolean isSuccessDeleteToken = false;
                try {
                    isSuccessDeleteToken = mOAuthLoginInstance.logoutAndDeleteToken(mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return isSuccessDeleteToken;
            }
        };

        boolean isSuccess = false;
        try {
            isSuccess = asyncTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ((Activity) mContext).finish();

        if (!isSuccess) {
            // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
            // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
            Log.d("Chance", "errorCode:" + mOAuthLoginInstance.getLastErrorCode(mContext));
            Log.d("Chance", "errorDesc:" + mOAuthLoginInstance.getLastErrorDesc(mContext));
        }
    }

}
