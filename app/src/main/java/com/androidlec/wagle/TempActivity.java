package com.androidlec.wagle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.androidlec.wagle.CS.LoginClass.GoogleLogin;
import com.androidlec.wagle.CS.LoginClass.KakaoLogin;
import com.androidlec.wagle.CS.LoginClass.NaverLogin;
import com.androidlec.wagle.activity.wagleSub.AddBJMActivity;
import com.androidlec.wagle.activity.wagleSub.AddDHGActivity;
import com.kakao.auth.AccessTokenCallback;
import com.kakao.auth.Session;
import com.kakao.auth.authorization.accesstoken.AccessToken;
import com.kakao.friends.AppFriendContext;
import com.kakao.friends.AppFriendOrder;
import com.kakao.friends.response.AppFriendsResponse;
import com.kakao.friends.response.model.AppFriendInfo;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.response.KakaoTalkProfile;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.network.ErrorResult;

import java.util.Arrays;
import java.util.List;

public class TempActivity extends AppCompatActivity {

    Button btn4, btn5, btn6, btn7, btn8, btn9, btn10;
    private KakaoLogin kakaoLogin;
    private NaverLogin naverLogin;
    private GoogleLogin googleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        kakaoLogin = new KakaoLogin(TempActivity.this);
        naverLogin = new NaverLogin(TempActivity.this);
        googleLogin = new GoogleLogin(TempActivity.this);

        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn10 = findViewById(R.id.btn10);

        btn4.setOnClickListener(onClickListener);
        btn5.setOnClickListener(onClickListener);
        btn6.setOnClickListener(onClickListener);
        btn7.setOnClickListener(onClickListener);
        btn8.setOnClickListener(onClickListener);
        btn9.setOnClickListener(onClickListener);
        btn10.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn4:
                    kakaoLogin.unlink();
                    break;
                case R.id.btn5:
                    naverLogin.deleteToken();
                    break;
                case R.id.btn6:
                    googleLogin.revokeAccess();
                    break;
                case R.id.btn7:
                    startActivity(new Intent(TempActivity.this, MainMoimListActivity.class));
                    break;
                case R.id.btn8:
                    startActivity(new Intent(TempActivity.this, AddBJMActivity.class));
                    break;
                case R.id.btn9:
                    startActivity(new Intent(TempActivity.this, AddDHGActivity.class));
                    break;
                case R.id.btn10:
                    getMyInfo();
                    break;
            }
        }
    };

    private void getMyInfo() {
        KakaoTalkService.getInstance()
                .requestProfile(new TalkResponseCallback<KakaoTalkProfile>() {
                    @Override
                    public void onNotKakaoTalkUser() {
                        Log.e("KAKAO_API", "카카오톡 사용자가 아님");
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "카카오톡 프로필 조회 실패: " + errorResult);
                    }

                    @Override
                    public void onSuccess(KakaoTalkProfile result) {
                        Log.i("KAKAO_API", "카카오톡 닉네임: " + result.getNickName());
                        Log.i("KAKAO_API", "카카오톡 프로필이미지: " + result.getProfileImageUrl());
                        getFriendList();
                    }
                });
    }

    private void getFriendList() {
        // 컨텍스트 생성
        //   - 닉네임, 처음(index 0)부터, 100명까지, 오름차순 예시
        AppFriendContext context =
                new AppFriendContext(AppFriendOrder.NICKNAME, 0, 100, "asc");

        // 조회 요청
        KakaoTalkService.getInstance()
                .requestAppFriends(context, new TalkResponseCallback<AppFriendsResponse>() {
                    @Override
                    public void onNotKakaoTalkUser() {
                        Log.e("KAKAO_API", "카카오톡 사용자가 아님");
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "친구 조회 실패: " + errorResult);
                        if(errorResult.getErrorCode() == -3){
                            getCurrentSession();
                        }
                    }

                    @Override
                    public void onSuccess(AppFriendsResponse result) {
                        Log.i("KAKAO_API", "친구 조회 성공");

                        for (AppFriendInfo friend : result.getFriends()) {
                            Log.d("KAKAO_API", friend.toString());

                            String uuid = friend.getUUID();     // 메시지 전송 시 사용
                        }
                    }
                });
    }

    private void getCurrentSession() {
        // 필요한 동의항목의 scope ID (개발자사이트 해당 동의항목 설정에서 확인 가능)
        List<String> scopes = Arrays.asList("friends","talk_message");

        // 사용자 동의 요청
        Session.getCurrentSession()
                .updateScopes(this, scopes, new AccessTokenCallback() {
                    @Override
                    public void onAccessTokenReceived(AccessToken accessToken) {
                        Log.i("KAKAO_SESSION", "새로운 동의항목 추가 완료");

                        // 요청한 scope이 추가되어 토큰이 재발급 됨

                        // TODO: 사용자 동의 획득 이후 프로세스
                        getFriendList();
                    }

                    @Override
                    public void onAccessTokenFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_SESSION", "사용자 동의 실패: " + errorResult);
                    }
                });
    }

}