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

public class TempActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
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

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);
        btn3.setOnClickListener(onClickListener);
        btn4.setOnClickListener(onClickListener);
        btn5.setOnClickListener(onClickListener);
        btn6.setOnClickListener(onClickListener);
        btn7.setOnClickListener(onClickListener);
        btn8.setOnClickListener(onClickListener);
        btn9.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn1:
                    kakaoLogin.logout();
                    break;
                case R.id.btn2:
                    naverLogin.logout();
                    break;
                case R.id.btn3:
                    googleLogin.signOut();
                    break;
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
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    break;
                case R.id.btn8:
                    startActivity(new Intent(TempActivity.this, AddBJMActivity.class));
                    break;
                case R.id.btn9:
                    startActivity(new Intent(TempActivity.this, AddDHGActivity.class));
                    break;


            }
        }
    };

}