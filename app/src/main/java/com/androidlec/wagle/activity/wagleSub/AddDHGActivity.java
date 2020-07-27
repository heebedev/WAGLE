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

    private final static String TAG = "AddDHGActivity";

    private static String centIP = "192.168.0.82";

    //질문리스트뷰
    private static ArrayList<SgstRptList> questionListData;
    private static QuestionListAdapter adapter;
    private static ListView questionList;

    // 저장, 취소 버튼
    private static Button registDhg;
    private static Button cancelDhg;

    // ListView 숨겨진 EditText
    private static EditText report;

    // 초기화
    private void init() {
        // 초기선언
        questionList = findViewById(R.id.lv_dhglist_questionList);

        // 저장, 취소 버튼
        registDhg = findViewById(R.id.bt_dhgadd_dgmRegister);
        cancelDhg = findViewById(R.id.bt_dhgadd_dhgCancel);

        // Data 불러오는 URL
        String urlAddr = "http://" + centIP + ":8080/wagle/wagle_questionlist.jsp?wcseqno=" + UserInfo.WAGLESEQNO;

        connectGetData(urlAddr);
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

        // ListView 이벤트
        questionList.setOnItemClickListener(questionClickListener);

        // 버튼 이벤트
        registDhg.setOnClickListener(dhgRegCanClickListener);
        cancelDhg.setOnClickListener(dhgRegCanClickListener);
    }

    // ListView 클릭 이벤트
    ListView.OnItemClickListener questionClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (position != 0) {
                report = view.findViewById(R.id.et_dhglist_report);
                report.setVisibility(View.VISIBLE);
            }
        }
    };

    Button.OnClickListener dhgRegCanClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // 저장
                case R.id.bt_dhgadd_dgmRegister :
                    // EditText 가 저장된 Data
                    ArrayList<SgstRptList> EditData = adapter.EditData;

                    // 저장한 URL
                    String urlAddr = "http://" + centIP + ":8080/wagle/wagle_BookReport_Insert.jsp?uSeqno=" + UserInfo.USEQNO + "&moimSeqno=" + UserInfo.MOIMSEQNO + "&num=" + EditData.size();

                    // URL 에 EditText 넣기
                    for (int i = 0 ; i < EditData.size() ; i++) {
                        urlAddr = urlAddr + "&sSeqno" + i + "=" + questionListData.get(i).getsSeqno() + "&bContent" + i + "=" + EditData.get(i).getaContent();
                    }

                    // 보내기
                    connectSetData(urlAddr);

                    finish();
                    break;
                // 취소
                case R.id.bt_dhgadd_dhgCancel :
                    new AlertDialog.Builder(AddDHGActivity.this)
                            .setTitle("정말 취소하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            })
                            .show();
                    break;
            }
        }
    };

    // Json Data 저장 밑 List 데이터 뿌리기
    private void connectGetData(String urlAddr) {
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

    private boolean connectSetData(String urlAddr) {
        boolean result = false;
        try {
            NetworkTask_CRUD networkTask = new NetworkTask_CRUD(AddDHGActivity.this, urlAddr);
            networkTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }  // connectGetData

}
