package com.androidlec.wagle.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import com.androidlec.wagle.CS.LoginClass.GoogleLogin;
import com.androidlec.wagle.CS.LoginClass.KakaoLogin;
import com.androidlec.wagle.CS.LoginClass.NaverLogin;
import com.androidlec.wagle.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

public class LoginActivity extends Activity {

    // 로그인
    // 카카오 로그인
    private LinearLayout kakaoLoginButton;
    private LoginButton loginButton;
    private KakaoLogin kakaoLogin;
    // 네이버 로그인
    private LinearLayout naverLoginButton;
    private OAuthLoginButton mOAuthLoginButton;
    private NaverLogin naverLogin;
    // 구글 로그인
    private LinearLayout googleLoginButton;
    private SignInButton signInButton;
    private GoogleLogin googleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    private void init() {
        // 로그인
        // 카카오
        kakaoLogin = new KakaoLogin(getApplicationContext());
        kakaoLoginButton = findViewById(R.id.lvbt_login_kakaologin);
        kakaoLoginButton.setOnClickListener(loginButtonClickListener);
        loginButton = findViewById(R.id.login_btn_socialKaKao_provided);
        Session.getCurrentSession().addCallback(kakaoLogin.sessionCallback);
        // 네이버
        naverLogin = new NaverLogin(getApplicationContext());
        naverLoginButton = findViewById(R.id.lvbt_login_naverlogin);
        naverLoginButton.setOnClickListener(loginButtonClickListener);
        mOAuthLoginButton = findViewById(R.id.login_btn_socialNaver_provided);
        mOAuthLoginButton.setOAuthLoginHandler(naverLogin.mOAuthLoginHandler);
        // 구글
        googleLogin = new GoogleLogin(getApplicationContext());
        googleLoginButton = findViewById(R.id.lvbt_login_googlelogin);
        googleLoginButton.setOnClickListener(loginButtonClickListener);
        signInButton = findViewById(R.id.login_btn_socialGoogle_provided);

    }

    View.OnClickListener loginButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lvbt_login_kakaologin:
                    loginButton.performClick();
                    break;
                case R.id.lvbt_login_naverlogin:
                    mOAuthLoginButton.performClick();
                    break;
                case R.id.lvbt_login_googlelogin:
                    signIn();
                    break;
            }
        }
    };

    public void signIn() {
        Intent signInIntent = GoogleLogin.mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GoogleLogin.RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GoogleLogin.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            googleLogin.handleSignInResult(task);
        }

        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
//        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
//            Log.i("Chance", "requestCode : "+requestCode);
//            Log.i("Chance", "resultCode : "+resultCode);
//            Log.i("Chance", "data : "+data);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(kakaoLogin.sessionCallback);
    }

}