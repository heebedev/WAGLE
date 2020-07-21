package com.androidlec.wagle.activity.wagleSub;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;


import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.adapter.QuestionListAdapter;
import com.androidlec.wagle.dto.SgstRptList;
import com.androidlec.wagle.network_sh.NetworkTask_CRUD;
import com.androidlec.wagle.network_sh.NetworkTask_QuestionReportList;

import java.util.ArrayList;

public class AddDHGActivity extends Activity {


    private String urlAddr, centIP;

    //질문리스트뷰
    private ArrayList<SgstRptList> questionListData;
    private QuestionListAdapter adapter;
    private ListView questionList;

    private int preseq = -1;
    private EditText preet;

    private Button registDhg;
    private Button cancelDhg;

    private EditText report;



    private void init() {

        questionList = findViewById(R.id.lv_dhglist_questionList);
        registDhg = findViewById(R.id.bt_dhgadd_dgmRegister);
        cancelDhg = findViewById(R.id.bt_dhgadd_dhgCancel);

        centIP = "192.168.0.138";
        urlAddr = "http://" + centIP + ":8080/test/wagle_questionlist.jsp?useqno=" + UserInfo.USEQNO + "&wcseqno=" + UserInfo.WAGLESEQNO;

        connectGetData();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dhg);

        init();

        questionList.setOnItemClickListener(questionClickListener);

        registDhg.setOnClickListener(dhgRegCanClickListener);
        cancelDhg.setOnClickListener(dhgRegCanClickListener);

    }

    ListView.OnItemClickListener questionClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int seqno = 0;

            if(position != 0) {
                if (preseq != -1) {
                    //                SgstRptList sgstRptList = new SgstRptList(preseq, preet.getText().toString());
                    //                reportAddData.add(sgstRptList);
                    seqno = (int) questionList.getItemAtPosition(position);
                    report = view.findViewById(R.id.et_dhglist_report);
                    report.setVisibility(View.VISIBLE);

                    preseq = seqno;
                    preet = report;

                } else {
                    seqno = (int) questionList.getItemAtPosition(position);
                    report = view.findViewById(R.id.et_dhglist_report);
                    report.setVisibility(View.VISIBLE);

                    preseq = seqno;
                    preet = report;
                }
            }

        }

    };


    Button.OnClickListener dhgRegCanClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_dhgadd_dgmRegister :
//                    String rcont;
//
//
//                    Log.e("status", "size : " + questionListData.size());
//                    urlAddr = "http://" + centIP + ":8080/test/wagle_updatereport.jsp?useqno=" + UserInfo.USEQNO + "&size=" + questionListData.size() + "&seqcontent=";
//
//
//                    for (int i = 0; i < questionListData.size(); i++) {
//
//                        Log.e("status", "위치 : " + (i-questionList.getFirstVisiblePosition()));
//
//                       report =  (EditText) questionList.getChildAt(i-questionList.getFirstVisiblePosition()).findViewById(R.id.et_dhglist_report);
//
//
//                        if ( i == 0) {
//                            rcont = "head";
//                            urlAddr += "sseqno" + questionListData.get(i).getSrSeqno() + "=" + rcont;
//                        } else {
//                            rcont = report.getText().toString();
//                            urlAddr += "sseqno" + questionListData.get(i).getSrSeqno() + "=" + rcont;
//                        }
//
//                    }
//
//                    connectSetData();

                    break;
                case R.id.bt_dhgadd_dhgCancel :
                    new AlertDialog.Builder(AddDHGActivity.this)
                            .setTitle("정말 취소하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //startActivity(new Intent(AddBJMActivity.this, HomeActivity.class));

                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
        }
    };

    private void connectGetData() {
        try {
            NetworkTask_QuestionReportList networkTask = new NetworkTask_QuestionReportList(AddDHGActivity.this, urlAddr);
            Object obj = networkTask.execute().get();
            questionListData = (ArrayList<SgstRptList>) obj;
            adapter = new QuestionListAdapter(AddDHGActivity.this, R.layout.custom_dhglist_sh, questionListData);
            questionList.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }  // connectGetData

    private boolean connectSetData() {
        boolean result = false;
        try {
            NetworkTask_CRUD networkTask = new NetworkTask_CRUD(AddDHGActivity.this, urlAddr);
            networkTask.execute();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }  // connectGetData

}