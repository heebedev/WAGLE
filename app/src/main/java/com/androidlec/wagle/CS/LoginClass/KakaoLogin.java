package com.androidlec.wagle.CS.LoginClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.androidlec.wagle.CS.Model.User;
import com.androidlec.wagle.CS.Network.CSNetworkTask;
import com.androidlec.wagle.HomeActivity;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import org.json.JSONException;
import org.json.JSONObject;

public class KakaoLogin {

    private Context mContext;

    private String resultJson;

    public KakaoLogin(Context mContext) {
        this.mContext = mContext;
    }

    // 세션 콜백 구현
    public ISessionCallback sessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {

                @Override
                public void onFailure(ErrorResult errorResult) {
                    super.onFailure(errorResult);
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    String userId = getUserId(result.toString());
                    if(!findUserFromDB(userId)) {
                        InputUserDataToDB(result.toString());
                    }

                    Intent intent = new Intent(mContext, HomeActivity.class);
                    mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
        }
    };

    private String getUserId(String json) {
        return kakaoJsonParsing(json);
    }

    private String kakaoJsonParsing(String json) {
        String id = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            id = jsonObject.getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    private boolean findUserFromDB(String userId) {
        boolean result = false;

        String urlAddr = "http://192.168.0.79:8080/wagle/csFindUserWAGLE.jsp?";

        urlAddr = urlAddr + "userId=" + userId;

        try {
            CSNetworkTask csNetworkTask = new CSNetworkTask(mContext, urlAddr);
            result = csNetworkTask.execute().get(); // doInBackground 의 리턴값
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void InputUserDataToDB(String json) {
        User user = jsonParsing(json);
        String urlAddr = "http://192.168.0.79:8080/wagle/csInputUserWAGLE.jsp?";

        urlAddr = urlAddr + "uId=" + user.getuId() + "&uEmail=" + user.getuEmail() + "&uName=" + user.getuName() + "&uImageName=" + user.getuImageName() + "&uBirthDate=" + user.getuBirthDate() + "&uLoginType=KAKAO";

        try {
            CSNetworkTask csNetworkTask = new CSNetworkTask(mContext, urlAddr);
            csNetworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User jsonParsing(String json) {
//        Log.e("Chance", "kakao : "+json);
        User result = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            String uId = jsonObject.getString("id");

            JSONObject properties = jsonObject.getJSONObject("properties");
            String uName = properties.getString("nickname");
            String uImageName = properties.getString("profile_image");

            JSONObject kakao_account = jsonObject.getJSONObject("kakao_account");
            String uEmail = kakao_account.getString("email");
            String uBirthDate = kakao_account.getString("birthday");

//            Log.e("Chance", uImageName);

            result = new User(uId, uEmail, uName, uImageName, uBirthDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void logout() {
        UserManagement.getInstance()
                .requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        ((Activity) mContext).finish();
                    }
                });
    }

    public void unlink() {
        UserManagement.getInstance()
                .requestUnlink(new UnLinkResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("TAG", "세션이 닫혀 있음: " + errorResult);
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("TAG", "연결 끊기 실패: " + errorResult);

                    }

                    @Override
                    public void onSuccess(Long result) {
                        Log.i("TAG", "연결 끊기 성공. id: " + result);
                        ((Activity) mContext).finish();
                    }
                });
    }

}
