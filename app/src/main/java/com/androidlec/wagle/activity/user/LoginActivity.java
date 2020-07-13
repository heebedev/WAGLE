package com.androidlec.wagle.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.androidlec.wagle.HomeActivity;
import com.androidlec.wagle.MainMoimListActivity;
import com.androidlec.wagle.R;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button testLoginButton = findViewById(R.id.testLoginButton);
        testLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FindIdPwActivity.class));
                finish();
            }
        });
    }
}