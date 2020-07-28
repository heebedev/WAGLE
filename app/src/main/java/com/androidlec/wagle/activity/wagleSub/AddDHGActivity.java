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
import com.androidlec.wagle.jhj.Jhj_BookReport_DTO;
import com.androidlec.wagle.jhj.Jhj_MySql_Select_NetworkTask;
import com.androidlec.wagle.jhj.Jhj_Suggestion_DTO;
import com.androidlec.wagle.network_sh.NetworkTask_CRUD;
import com.androidlec.wagle.network_sh.NetworkTask_QuestionReportList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddDHGActivity extends Activity {

    private final static String TAG = "AddDHGActivity";

    private static String centIP = "192.168.0.82";

    //질문리스트뷰
    private static QuestionListAdapter adapter;
    private static ListView questionList;

    // 저장, 취소 버튼
    private static Button registDhg;
    private static Button cancelDhg;

    // ListView 숨겨진 EditText
    private static EditText report;

    private static ArrayList<Jhj_BookReport_DTO> ListData;
    public static String check;

    // 초기화
    private void init() {
        // 초기선언
        questionList = findViewById(R.id.lv_dhglist_questionList);

        // 저장, 취소 버튼
        registDhg = findViewById(R.id.bt_dhgadd_dgmRegister);
        cancelDhg = findViewById(R.id.bt_dhgadd_dhgCancel);

        DHG_Update_Data();
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
                    ArrayList<Jhj_BookReport_DTO> EditData = adapter.EditData;
                    String urlAddr = "";

                    if (check.equals("1")) {
                        // 저장한 URL
                        urlAddr = "http://" + centIP + ":8080/wagle/wagle_BookReport_Update.jsp?brCount=" + ListData.size() + "&uSeqno=" + UserInfo.USEQNO + "&moimSeqno=" + UserInfo.MOIMSEQNO + "&num=" + EditData.size();

                        for (int i = 0 ; i < ListData.size() ; i++) {
                            urlAddr = urlAddr + "&brSeqno" + i + "=" + ListData.get(i).getBrSeqno();
                        }

                        // URL 에 EditText 넣기
                        for (int i = 0; i < EditData.size(); i++) {
                            urlAddr = urlAddr + "&sSeqno" + i + "=" + ListData.get(i).getsSeqno() + "&bContent" + i + "=" + EditData.get(i).getBrContent();
                        }
                    } else {
                        // 저장한 URL
                        urlAddr = "http://" + centIP + ":8080/wagle/wagle_BookReport_Insert.jsp?uSeqno=" + UserInfo.USEQNO + "&moimSeqno=" + UserInfo.MOIMSEQNO + "&num=" + EditData.size();

                        // URL 에 EditText 넣기
                        for (int i = 0; i < EditData.size(); i++) {
                            urlAddr = urlAddr + "&sSeqno" + i + "=" + ListData.get(i).getsSeqno() + "&bContent" + i + "=" + EditData.get(i).getBrContent();
                        }
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

    // 저장된 데이터 불러오기
    protected void DHG_Update_Data() {
        // Data 불러오는 URL
        String urlAddr = "http://" + centIP + ":8080/wagle/wagle_BookReport_Select.jsp?wcSeqno=" + UserInfo.WAGLESEQNO;
        String JsonData = DHG_Select(urlAddr);
        String[] attrName = {"brSeqno", "User_uSeqno", "brContent", "sSeqno", "WagleCreate_wcSeqno", "sType", "sContent"};
        ListData = JsonData_DHG_Parser(JsonData, "bookreport", attrName);

        adapter = new QuestionListAdapter(AddDHGActivity.this, R.layout.custom_dhglist_sh, ListData);
        questionList.setAdapter(adapter);
    }

    // JSP 파일 URL로 받아 JSON Data 받아오는 메소드
    protected String DHG_Select(String urlAddr) {
        String data = null;

        try {
            Jhj_MySql_Select_NetworkTask networkTask = new Jhj_MySql_Select_NetworkTask(AddDHGActivity.this, urlAddr);
            // execute() java 파일안의 메소드 한번에 동작시키기, 메소드를 사용하면 HttpURLConnection 이 제대로 작동하지않는다.
            Object obj = networkTask.execute().get();
            data = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    // JsonData Dtos 에 저장하기
    protected ArrayList<Jhj_BookReport_DTO> JsonData_DHG_Parser(String jsonStr, String keyName, String[] attrName) {
        ArrayList<Jhj_BookReport_DTO> dtos = new ArrayList<Jhj_BookReport_DTO>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            check = jsonObject.getString("check");
            JSONArray jsonArray = new JSONArray(jsonObject.getString(keyName));
            dtos.clear();

            String[] attrValue = new String[attrName.length];
            for (int i = 0 ; i < jsonArray.length() ; i++) {
                // JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                for (int j = 0 ; j < attrName.length ; j++) {
                    attrValue[j] = jsonObject1.getString(attrName[j]);
                }

                dtos.add(new Jhj_BookReport_DTO(attrValue[0], attrValue[1], attrValue[2], attrValue[3], attrValue[4], attrValue[5], attrValue[6]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }

}
