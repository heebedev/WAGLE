package com.androidlec.wagle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidlec.wagle.CS.Model.WagleList;
import com.androidlec.wagle.networkTask.JH_VoidNetworkTask;

public class ViewDetailWagleActivity extends AppCompatActivity {

    private TextView et_title, et_startDate, et_endDate, et_dueDate, et_location, et_fee, et_wagleDetail, et_wagleAgreeRefund, tv_joinIn;
    private CheckBox cb_agreement;
    String title;
    Intent intent;

    private Boolean cbClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_wagle);

        init();
        getData();

    }

    private void init() {
        et_title = findViewById(R.id.vdw_cs_et_title);
        et_startDate = findViewById(R.id.vdw_cs_tv_startDate);
        et_endDate = findViewById(R.id.vdw_cs_tv_endDate);
        et_dueDate = findViewById(R.id.vdw_cs_tv_dueDate);
        et_location = findViewById(R.id.vdw_cs_et_location);
        et_fee = findViewById(R.id.vdw_cs_et_fee);
        et_wagleDetail = findViewById(R.id.vdw_cs_tv_wagleDetail);
        et_wagleAgreeRefund = findViewById(R.id.vdw_cs_et_wagleAgreeRefund);
        tv_joinIn = findViewById(R.id.vdw_cs_tv_joinIn);
        cb_agreement = findViewById(R.id.vdw_cs_cb_agreement);

        tv_joinIn.setOnClickListener(onClickListener);

        intent = getIntent();

    }

    private void getData() {
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
                if (!cbClick) {
                    cb_agreement.setVisibility(View.VISIBLE);
                    cbClick = true;
                } else {
                    if (cb_agreement.isChecked()) {
                        Toast.makeText(this, title + " 와글에 가입되었습니다.", Toast.LENGTH_SHORT).show();
                        joinInWagle();
                    } else {
                        Toast.makeText(this, "동의 사항 및 환불규정을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }
    };


    private void joinInWagle() {
        String wcSeqno = intent.getStringExtra("wcSeqno");
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