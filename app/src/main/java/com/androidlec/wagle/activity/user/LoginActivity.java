package com.androidlec.wagle.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.androidlec.wagle.CS.LoginClass.GoogleLogin;
import com.androidlec.wagle.CS.LoginClass.KakaoLogin;
import com.androidlec.wagle.CS.LoginClass.NaverLogin;
import com.androidlec.wagle.MainMoimListActivity;
import com.androidlec.wagle.R;
import com.androidlec.wagle.network_sh.NetworkTask_Login;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;


public class LoginActivity extends Activity {

    // 로그인
    private Button btnGenLogin;
    private TextView loginResult;
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
    //아이디 비밀번호 찾기
    private TextView findidpw;
    //아이디 비밀번호 입력
    private EditText userid, userpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//        Button testLoginButton = findViewById(R.id.testLoginButton);
//        testLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), FindIdPwActivity.class));
//                finish();
//            }
//
//        });

        init();

    }

    private void init() {
        // 로그인
        btnGenLogin = findViewById(R.id.bt_login_login);
        btnGenLogin.setOnClickListener(genloginClickListener);
        userid = findViewById(R.id.et_login_loginID);
        userpw = findViewById(R.id.et_login_loginPW);
        loginResult = findViewById(R.id.tv_login_loginresult);
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
        // 아이디비밀번호 찾기
        findidpw = findViewById(R.id.tvbt_login_findidpw);
        findidpw.setOnClickListener(findidpwClickListener);


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


    TextView.OnClickListener findidpwClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this, FindIdPwActivity.class));
        }
    };

    Button.OnClickListener genloginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = userid.getText().toString();
            String pw = userpw.getText().toString();
            if(getUserData(email, pw)) {
                startActivity(new Intent(LoginActivity.this, MainMoimListActivity.class));
            } else {
                loginResult.setVisibility(View.VISIBLE);
            }

        }
    };

    private boolean getUserData(String uEmail, String uPw) {
        String centIP = "192.168.0.138";
        String urlAddr = "http://" + centIP + ":8080/test/wagle_genlogin.jsp?uEmail=" + uEmail + "&uPw="+uPw;
        boolean result = false;
        try {
            NetworkTask_Login networkTask = new NetworkTask_Login(LoginActivity.this, urlAddr);
            Object obj = networkTask.execute().get();
            result = (boolean) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }  // connectGetData
}