package com.androidlec.wagle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidlec.wagle.CS.Model.WagleList;
import com.androidlec.wagle.dto.BookInfo;
import com.androidlec.wagle.networkTask.JH_IntNetworkTask;
import com.androidlec.wagle.networkTask.JH_VoidNetworkTask;
import com.androidlec.wagle.network_sh.NetworkTask_BookInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class ViewDetailWagleActivity extends AppCompatActivity {

    // JSP 연결 IP
    private final static String JH_IP = "192.168.0.178";

    private TextView et_title, et_startDate, et_endDate, et_dueDate, et_location, et_fee, et_wagleDetail, et_wagleAgreeRefund, tv_joinIn;

    private TextView tv_bookInfo, tv_num6Name;

    private CheckBox cb_agreement;
    String title;
    Intent intent;

    private Boolean cbClick = false;

    //BookInfo
    private TextView bk_title, bk_writer, bk_maxpage, bk_intro, bk_data;
    private ImageView bk_bookImage;
    private BookInfo bookinfo;

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
        tv_bookInfo = findViewById(R.id.vdw_cs_tv_bookInfo);
        tv_num6Name = findViewById(R.id.vdw_cs_tv_num6name);

        tv_joinIn.setOnClickListener(onClickListener);
        tv_bookInfo.setOnClickListener(onClickListener);

        intent = getIntent();

    }

    private void getData() {
        WagleList data = intent.getParcelableExtra("data");

        setData(data);

        String centIP = "192.168.0.138";
        String url = "http://" + centIP + ":8080/test/wagle_bookinfoGet.jsp?wcSeqno=" + intent.getStringExtra("wcSeqno");
        bookinfo = getBookinfo(url);
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

        if (data.getWcType().equals("투데이")) {
            et_wagleAgreeRefund.setVisibility(View.GONE);
            tv_bookInfo.setVisibility(View.GONE);
            tv_num6Name.setVisibility(View.GONE);

        }
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
            case R.id.vdw_cs_tv_bookInfo:
                if (bookinfo != null) {
                    final LinearLayout linear = (LinearLayout) View.inflate(ViewDetailWagleActivity.this, R.layout.custom_bookinfo_sh, null);

                    bk_title = linear.findViewById(R.id.bookinfo_tv_bookname);
                    bk_writer = linear.findViewById(R.id.bookinfo_tv_bookwriter);
                    bk_maxpage = linear.findViewById(R.id.bookinfo_tv_bookmaxpage);
                    bk_intro = linear.findViewById(R.id.bookinfo_tv_bookinfo);
                    bk_data = linear.findViewById(R.id.bookinfo_tv_bookdata);
                    bk_bookImage = linear.findViewById(R.id.bookinfo_iv_bookImage);

                    bk_title.setText(bookinfo.getTitle());
                    bk_writer.setText(bookinfo.getWriter());
                    bk_maxpage.setText(Integer.toString(bookinfo.getMaxpage()));
                    bk_intro.setText(bookinfo.getIntro());
                    bk_data.setText(bookinfo.getData());

                    if (bookinfo.getImgName().length() > 0) {
                        Glide.with(this)
                                .load(UserInfo.BOOK_BASE_URL + bookinfo.getImgName())
                                .apply(new RequestOptions().centerCrop())
                                .placeholder(R.drawable.ic_outline_emptyimage)
                                .into(bk_bookImage);
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewDetailWagleActivity.this);
                    builder.setTitle("")
                            .setView(linear)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();


                } else {
                    Toast.makeText(ViewDetailWagleActivity.this, "등록된 도서 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                }
                break;


        }
    };


    private void joinInWagle() {
        String wcSeqno = intent.getStringExtra("wcSeqno");
        String uSeqno = String.valueOf(UserInfo.USEQNO);
        String urlAddr = "http://" + JH_IP + ":8080/wagle/joinInWagle.jsp?";
        urlAddr = urlAddr + "Moim_mSeqno=" + UserInfo.MOIMSEQNO + "&wcSeqno=" + wcSeqno + "&uSeqno=" + uSeqno;
        try {
            JH_VoidNetworkTask networkTask = new JH_VoidNetworkTask(ViewDetailWagleActivity.this, urlAddr);
            networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        String insert_urlAddr = "http://" + JH_IP + ":8080/wagle/insertWagleProgress.jsp?";
        insert_urlAddr = insert_urlAddr + "wcSeqno=" + wcSeqno + "&uSeqno=" + uSeqno;
        try {
            JH_VoidNetworkTask networkTask2 = new JH_VoidNetworkTask(ViewDetailWagleActivity.this, insert_urlAddr);
            networkTask2.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }

        finish();
    }


    private BookInfo getBookinfo(String urlAddr) {
        BookInfo result = null;
        try {
            NetworkTask_BookInfo networkTask = new NetworkTask_BookInfo(ViewDetailWagleActivity.this, urlAddr);
            Object obj = networkTask.execute().get();

            result = (BookInfo) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}//---