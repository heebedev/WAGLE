package com.androidlec.wagle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.androidlec.wagle.activity.user.LoginActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv_splash = findViewById(R.id.iv_splash);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(iv_splash);
        Glide.with(this).load(R.drawable.wagleintro).into(gifImage);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }, 3200);

    }

}