package com.androidlec.wagle.activity.wagleSub;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.adapter.QuestionListAdapter;
import com.androidlec.wagle.dto.SgstRptList;
import com.androidlec.wagle.network_sh.NetworkTask_QuestionReportList;

import java.util.ArrayList;

public class AddDHGActivity extends Activity {


    private String urlAddr, centIP;

    //질문리스트뷰
    private ArrayList<SgstRptList> questionListData;
    private QuestionListAdapter adapter;
    private ListView questionList;

    private ArrayList<SgstRptList> reportAddData;
    private int preseq = 0;
    private EditText preet;



    private void init() {

        questionList = findViewById(R.id.lv_dhglist_questionList);

        centIP = "192.168.0.138";
        urlAddr = "http://" + centIP + ":8080/test/wagle_questionlist.jsp?useqno=" + UserInfo.USEQNO + "&wcseqno=" + UserInfo.WAGLESEQNO;

        connectGetData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dhg);

        init();

        questionList.setOnItemClickListener(questionClickListener);

    }


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


    ListView.OnItemClickListener questionClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (preseq != 0) {
                SgstRptList sgstRptList = new SgstRptList(preseq, preet.getText().toString());
                reportAddData.add(sgstRptList);
            } else {
                int seqno = (int) questionList.getItemAtPosition(position);
                EditText report = view.findViewById(R.id.et_dhglist_report);
                report.setVisibility(View.VISIBLE);

                preseq = seqno;
                preet = report;
            }

        }
    };

}