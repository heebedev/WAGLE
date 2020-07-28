package com.androidlec.wagle.activity.wagleSub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidlec.wagle.HomeActivity;
import com.androidlec.wagle.R;

public class AddWagleActivity extends AppCompatActivity {

    private Button addNormWagle, addTodayWagle;
    private TextView goback;

    private Intent intent;

    private int REQUEST_TEST = 1;

    private void init() {
        addNormWagle = findViewById(R.id.bt_addwaglehome_normwaglebtn);
        addTodayWagle = findViewById(R.id.bt_addwaglehome_todaywaglebtn);
        goback = findViewById(R.id.tv_addwaglehome_goback);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wagle);

        init();

        addNormWagle.setOnClickListener(wagleClickListener);
        addTodayWagle.setOnClickListener(wagleClickListener);
        goback.setOnClickListener(wagleClickListener);

    }

    Button.OnClickListener wagleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_addwaglehome_normwaglebtn:
                    intent = new Intent(AddWagleActivity.this, AddNormWagleActivity.class);
                    startActivityForResult(intent, REQUEST_TEST);
                    break;
                case R.id.bt_addwaglehome_todaywaglebtn:
                    intent = new Intent(AddWagleActivity.this, AddTodayWagleActivity.class);
                    startActivityForResult(intent, REQUEST_TEST);
                    finish();
                    break;
                case R.id.tv_addwaglehome_goback:
                    finish();
            }
        }
    };


}