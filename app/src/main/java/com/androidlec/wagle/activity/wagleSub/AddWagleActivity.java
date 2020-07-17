package com.androidlec.wagle.activity.wagleSub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidlec.wagle.R;

public class AddWagleActivity extends AppCompatActivity {

    private Button addNormWagle, addTodayWagle;
    private TextView goback;


    private void init() {
        addNormWagle = findViewById(R.id.bt_addwaglehome_normwaglebtn);
        addTodayWagle = findViewById(R.id.bt_addwaglehome_todaywaglebtn);
        goback = findViewById(R.id.tv_addwaglehome_goback);

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
                    startActivity(new Intent(AddWagleActivity.this, AddNormWagleActivity.class));
                    break;
                case R.id.bt_addwaglehome_todaywaglebtn:
                    startActivity(new Intent(AddWagleActivity.this, AddTodayWagleActivity.class));
                    break;
                case R.id.tv_addwaglehome_goback:
                    finish();
            }
        }
    };


}