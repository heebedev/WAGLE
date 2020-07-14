package com.androidlec.wagle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.androidlec.wagle.adapter.MoimListAdapter;
import com.androidlec.wagle.dto.Moimlist;
import com.androidlec.wagle.network_sh.NetworkTask_MoimList;

import java.util.ArrayList;

public class MainMoimListActivity extends Activity {

    private String urlAddr, centIP;

    //사용자정보
    UserInfo userinfo;

    //모임리스트뷰
    private ArrayList<Moimlist> moimlistdata;
    private MoimListAdapter adapter;
    private ListView moimList;

    //모임 더하기 버튼
    private TextView addMoim;

    private void init() {
        moimList = findViewById(R.id.lv_mainMoim_moimlist);
        addMoim = findViewById(R.id.tv_mainMoim_addmoim);

        centIP = "192.168.0.138";
        urlAddr = "http://" + centIP + ":8080/test/wagle_my_moim_list.jsp?userseqno=" + userinfo.uSeqno;

        connectGetData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_moim_list);
        //초기화

        init();


    }

    private void connectGetData() {
        try {
            NetworkTask_MoimList networkTask = new NetworkTask_MoimList(MainMoimListActivity.this, urlAddr);
            Object obj = networkTask.execute().get();
            moimlistdata = (ArrayList<Moimlist>) obj;
            adapter = new MoimListAdapter(MainMoimListActivity.this, R.layout.custom_moimlist_sh, moimlistdata);
            moimList.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }  // connectGetData
}