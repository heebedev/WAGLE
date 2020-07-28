package com.androidlec.wagle.CS.LoginClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.androidlec.wagle.CS.Model.User;
import com.androidlec.wagle.CS.Network.CSNetworkTask;
import com.androidlec.wagle.MainMoimListActivity;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.activity.menu.MyInfoActivity;
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

    private String strResult;
    private User user;

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
                    if (!findUserFromDB(userId)) {
                        InputUserDataToDB(result.toString());
                        Intent intent = new Intent(mContext, MyInfoActivity.class);
                        intent.putExtra("LoginType", "KAKAO");
                        intent.putExtra("UserProfile", user.getuImageName());
                        intent.putExtra("UserName", user.getuName());
                        intent.putExtra("UserBirth", user.getuBirthDate());
                        intent.putExtra("UserEmail", user.getuEmail());
                        mContext.startActivity(intent);
                    } else {
                        setUserInfo(userId);
                        Intent intent = new Intent(mContext, MainMoimListActivity.class);
                        mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }

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

    private void InputUserDataToDB(String json) {
        user = jsonParsing(json);
        String urlAddr = "http://192.168.0.79:8080/wagle/csInputUserWAGLE.jsp?";

        urlAddr = urlAddr + "uId=" + user.getuId() + "&uEmail=" + user.getuEmail() + "&uName=" + user.getuName() + "&uImageName=" + user.getuImageName() + "&uBirthDate=" + user.getuBirthDate() + "&uLoginType=KAKAO";

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
            UserInfo.ULOGINTYPE = user[3];
            UserInfo.UNAME = user[4];

        } catch (Exception e) {
            e.printStackTrace();
        }

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

    private User jsonParsing(String json) {
        User result = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            String uId = jsonObject.getString("id");

            JSONObject properties = jsonObject.getJSONObject("properties");
            String uName = properties.getString("nickname");
            String uImageName = properties.getString("profile_image");

            JSONObject kakao_account = jsonObject.getJSONObject("kakao_account");
            String emailOK = kakao_account.getString("email_needs_agreement");
            String uEmail = "";
            if (!emailOK.equals("true")) {
                uEmail = kakao_account.getString("email");
            }
            String birthdayOK = kakao_account.getString("birthday_needs_agreement");
            String uBirthDate = "";
            if (!birthdayOK.equals("true")) {
                uBirthDate = kakao_account.getString("birthday");
            }

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
