package com.androidlec.wagle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidlec.wagle.CS.Model.WagleList;
import com.androidlec.wagle.networkTask.JH_VoidNetworkTask;

public class ViewDetailWagleActivity extends AppCompatActivity {

    private TextView et_title, et_startDate, et_endDate, et_dueDate, et_location, et_fee, et_wagleDetail, et_wagleAgreeRefund, tv_joinIn;
    String title;

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
        title = data.getWcName();
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
        switch (v.getId()) {
            case R.id.vdw_cs_tv_joinIn:
                Toast.makeText(this, title + " 와글에 가입되었습니다.", Toast.LENGTH_SHORT).show();
                joinInWagle();
                break;
        }
    };


    private void joinInWagle() {
        String wcSeqno = "1"; // 받아와야함. 임시절대값.
        String uSeqno = String.valueOf(UserInfo.USEQNO);
        String urlAddr = "http://192.168.0.178:8080/wagle/joinInWagle.jsp?";
        urlAddr = urlAddr + "wcSeqno=" + wcSeqno + "&uSeqno=" + uSeqno;
        try {
            JH_VoidNetworkTask networkTask = new JH_VoidNetworkTask(ViewDetailWagleActivity.this, urlAddr);
            networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        finish();
    }

}//---