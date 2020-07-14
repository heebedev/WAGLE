package com.androidlec.wagle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewDetailWagleActivity extends AppCompatActivity {

    private TextView tv_title, tv_startDate, tv_endDate, tv_dueDate, tv_location, tv_fee, tv_wagleDetail, tv_wagleAgreeRefund, tv_joinIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_wagle);

        init();

    }

    private void init() {
        tv_title = findViewById(R.id.vdw_cs_tv_title);
        tv_startDate = findViewById(R.id.vdw_cs_tv_startDate);
        tv_endDate = findViewById(R.id.vdw_cs_tv_endDate);
        tv_dueDate = findViewById(R.id.vdw_cs_tv_dueDate);
        tv_location = findViewById(R.id.vdw_cs_tv_location);
        tv_fee = findViewById(R.id.vdw_cs_tv_fee);
        tv_wagleDetail = findViewById(R.id.vdw_cs_tv_wagleDetail);
        tv_wagleAgreeRefund = findViewById(R.id.vdw_cs_tv_wagleAgreeRefund);
        tv_joinIn = findViewById(R.id.vdw_cs_tv_joinIn);
    }



}