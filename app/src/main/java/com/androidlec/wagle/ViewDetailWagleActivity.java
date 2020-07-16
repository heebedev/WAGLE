package com.androidlec.wagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidlec.wagle.CS.Model.WagleList;

public class ViewDetailWagleActivity extends AppCompatActivity {

    private TextView tv_title, tv_startDate, tv_endDate, tv_dueDate, tv_location, tv_fee, tv_wagleDetail, tv_wagleAgreeRefund, tv_joinIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_wagle);

        init();
        getData();

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

        tv_joinIn.setOnClickListener(onClickListener);

    }

    private void getData() {
        Intent intent = getIntent();
        WagleList data = intent.getParcelableExtra("data");

        setData(data);

    }

    private void setData(WagleList data) {
        tv_title.setText(data.getWcName());
        tv_startDate.setText(data.getWcStartDate());
        tv_endDate.setText(data.getWcEndDate());
        tv_dueDate.setText(data.getWcDueDate());
        tv_location.setText(data.getWcLocate());
        tv_fee.setText(data.getWcEntryFee());
        tv_wagleDetail.setText(data.getWcWagleDetail());
        tv_wagleAgreeRefund.setText(data.getWcWagleAgreeRefund());
    }

    View.OnClickListener onClickListener = v -> {
        switch (v.getId()){
            case R.id.vdw_cs_tv_joinIn:
                Toast.makeText(this, "가입하기", Toast.LENGTH_SHORT).show();
                break;
        }
    };

}