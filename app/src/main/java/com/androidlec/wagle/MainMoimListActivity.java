package com.androidlec.wagle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.androidlec.wagle.adapter.MoimListAdapter;
import com.androidlec.wagle.dto.MoimList;
import com.androidlec.wagle.network_sh.NetworkTask_MoimList;
import com.androidlec.wagle.network_sh.NetworkTask_ckGrade;

import java.util.ArrayList;

public class MainMoimListActivity extends Activity {

    private String urlAddr, centIP;
    private TextView tv_noList;

    //모임리스트뷰
    private ArrayList<MoimList> moimlistdata;
    private MoimListAdapter adapter;
    private ListView moimList;

    //모임 더하기 버튼
    private Button addMoim;

    private void init() {
        moimList = findViewById(R.id.lv_mainMoim_moimlist);
        addMoim = findViewById(R.id.bt_mainMoim_addmoim);
        tv_noList = findViewById(R.id.tv_mainMoim_noList);

        centIP = "192.168.0.138";
        urlAddr = "http://" + centIP + ":8080/test/wagle_my_moim_list.jsp?userseqno=" + UserInfo.USEQNO;

        connectGetData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_moim_list);
        //초기화

        init();

        addMoim.setOnClickListener(addMoimClickListener);
        moimList.setOnItemClickListener(moimItemClickListener);


    }

    private void connectGetData() {
        try {
            NetworkTask_MoimList networkTask = new NetworkTask_MoimList(MainMoimListActivity.this, urlAddr);
            Object obj = networkTask.execute().get();

            moimlistdata = (ArrayList<MoimList>) obj;
            if (moimlistdata.size() == 0) {
                tv_noList.setVisibility(View.VISIBLE);
                moimList.setVisibility(View.GONE);
            } else {
                tv_noList.setVisibility(View.GONE);
                moimList.setVisibility(View.VISIBLE);
                adapter = new MoimListAdapter(MainMoimListActivity.this, R.layout.custom_moimlist_sh, moimlistdata);
                moimList.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }  // connectGetData

    Button.OnClickListener addMoimClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainMoimListActivity.this, MakeMoimActivity.class));
        }
    };

    final ListView.OnItemClickListener moimItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UserInfo.MOIMSEQNO = parent.getItemAtPosition(position).toString();
            UserInfo.MOIMSUBJECT = moimlistdata.get(position).getMoimSubject();
            startActivity(new Intent(MainMoimListActivity.this, HomeActivity.class));
        }
    };


}