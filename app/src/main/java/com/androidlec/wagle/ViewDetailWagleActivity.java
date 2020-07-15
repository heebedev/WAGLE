package com.androidlec.wagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidlec.wagle.CS.Model.WagleList;

public class ViewDetailWagleActivity extends AppCompatActivity {

    private TextView et_title, et_startDate, et_endDate, et_dueDate, et_location, et_fee, et_wagleDetail, et_wagleAgreeRefund, tv_joinIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_wagle);

        init();
        getData();

    }

    private void init() {
        et_title = findViewById(R.id.vdw_cs_et_title);
        et_startDate = findViewById(R.id.vdw_cs_et_startDate);
        et_endDate = findViewById(R.id.vdw_cs_et_endDate);
        et_dueDate = findViewById(R.id.vdw_cs_et_dueDate);
        et_location = findViewById(R.id.vdw_cs_et_location);
        et_fee = findViewById(R.id.vdw_cs_et_fee);
        et_wagleDetail = findViewById(R.id.vdw_cs_et_wagleDetail);
        et_wagleAgreeRefund = findViewById(R.id.vdw_cs_et_wagleAgreeRefund);
        tv_joinIn = findViewById(R.id.vdw_cs_tv_joinIn);

        tv_joinIn.setOnClickListener(onClickListener);

    }

    private void getData() {
        Intent intent = getIntent();
        WagleList data = intent.getParcelableExtra("data");

        setData(data);

    }

    private void setData(WagleList data) {
        et_title.setText(data.getWcName());
        et_startDate.setText(data.getWcStartDate());
        et_endDate.setText(data.getWcEndDate());
        et_dueDate.setText(data.getWcDueDate());
        et_location.setText(data.getWcLocate());
        et_fee.setText(data.getWcEntryFee());
        et_wagleDetail.setText(data.getWcWagleDetail());
        et_wagleAgreeRefund.setText(data.getWcWagleAgreeRefund());
    }

    View.OnClickListener onClickListener = v -> {
        switch (v.getId()){
            case R.id.vdw_cs_tv_joinIn:
                Toast.makeText(this, "가입하기", Toast.LENGTH_SHORT).show();
                break;
        }
    };

}