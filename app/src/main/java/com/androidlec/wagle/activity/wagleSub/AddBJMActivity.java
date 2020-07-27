package com.androidlec.wagle.activity.wagleSub;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.androidlec.wagle.HomeActivity;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.jhj.Jhj_HomeAndMyPage_Plus_List;
import com.androidlec.wagle.jhj.Jhj_MySql_Select_NetworkTask;
import com.androidlec.wagle.jhj.Jhj_Notice_DTO;
import com.androidlec.wagle.jhj.Jhj_Suggestion_DTO;
import com.androidlec.wagle.network_sh.NetworkTask_CRUD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddBJMActivity extends AppCompatActivity {

    private static String urlAddr;
    private static String centIP = "192.168.0.82";

    private static LinearLayout ll;
    private static EditText bjmHead;
    private static Button questionAddBtn, registerbjmBtn, cancelbjmBtn;
    private static int bjmQuestCount = 0;

    private static ArrayList<Jhj_Suggestion_DTO> data;

    private void init() {
        bjmHead = findViewById(R.id.et_bjmadd_head);
        questionAddBtn = findViewById(R.id.bt_bjmadd_questadd);
        registerbjmBtn = findViewById(R.id.bt_bjmadd_bjmRegister);
        cancelbjmBtn = findViewById(R.id.bt_bjmadd_bjmCancel);
        ll = findViewById(R.id.ll_bjmadd_layout);

        for (int i = 0 ; i < data.size(); i++) {
            if (i == 0) {
                bjmHead.setText(data.get(i).getsContent());
                continue;
            }
            EditTextCreate(data.get(i).getsContent());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bjm);

        // 키보드 자동으로 올라가기
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        data = new ArrayList<Jhj_Suggestion_DTO>();

        urlAddr = "http://" + centIP + ":8080/wagle/wagle_BJM_Select.jsp?wcSeqno=" + UserInfo.WAGLESEQNO;
        String BJMJson = BJM_Select(urlAddr);
        // Json KeyName
        String[] keyName = {"sSeqno", "WagleCreate_wcSeqno", "sType", "sContent"};
        // JsonData Bean 형태로 저장
        data = JsonData_BJM_Parser(BJMJson, "suggestion", keyName);

        //초기화
        init();

        //발제문 질문 항목 추가
        questionAddBtn.setOnClickListener(bjmAddClickListener);
        //발제문 등록, 취소
        registerbjmBtn.setOnClickListener(bjmRegCanClickListener);
        cancelbjmBtn.setOnClickListener(bjmRegCanClickListener);
        //발제문 서문 터치리스너 - 터치시 텍스트 정렬 변경
        bjmHead.setOnTouchListener(bjmKeyInListener);
    }

    Button.OnClickListener bjmAddClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void onClick(View v) {
            EditTextCreate("");
        }
    };

    protected void EditTextCreate(String text) {
        bjmQuestCount ++;

        //질문 입력 EditText 추가
        EditText edittext = new EditText(getApplicationContext());
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.setMargins(30,10,30,10);
        edittext.setLayoutParams(p);
        edittext.setText(text);
        edittext.setHint(bjmQuestCount + "번 질문");
        edittext.setId(bjmQuestCount);
        edittext.setTextSize(16);
        edittext.setBackgroundResource(R.drawable.white_rounded_background);
        edittext.setGravity(Gravity.CENTER);
        edittext.setPadding(10,10,10,10);

        ll.addView(edittext);

        //터치 시 정렬 변경 method
        edittext.setOnTouchListener(bjmKeyInListener);
    }

    //클릭시 텍스트 정렬 변경
    EditText.OnTouchListener bjmKeyInListener = new View.OnTouchListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    EditText text = findViewById(v.getId());
                    text.setHint("");
                    text.setGravity(Gravity.LEFT);
                    break;
            }
            return false;
        }

    };


    Button.OnClickListener bjmRegCanClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_bjmadd_bjmRegister :
                    // 데이터베이스 저장
                    if (data.size() > 0) {
                        urlAddr = "http://" + centIP + ":8080/wagle/wagle_BJM_Update.jsp?sCount="+ data.size() +"&uSeqno=" + UserInfo.USEQNO + "&moimSeqno=" + UserInfo.MOIMSEQNO + "&wseqno=" + UserInfo.WAGLESEQNO + "&count=" + bjmQuestCount + "&head=" + bjmHead.getText().toString();
                        for (int i = 0 ; i < data.size() ; i++) {
                            urlAddr = urlAddr + "&sSeqno" + i + "=" + data.get(i).getsSeqno();
                        }
                    } else {
                        urlAddr = "http://" + centIP + ":8080/wagle/wagle_bjmadd.jsp?uSeqno=" + UserInfo.USEQNO + "&moimSeqno=" + UserInfo.MOIMSEQNO + "&wseqno=" + UserInfo.WAGLESEQNO + "&count=" + bjmQuestCount + "&head=" + bjmHead.getText().toString();
                    }

                    for (int i = 1; i <= bjmQuestCount; i++) {
                        EditText text = findViewById(i);
                        String qst = text.getText().toString();
                        urlAddr = urlAddr + "&question"+i+"="+qst;
                    }

                    try {
                        NetworkTask_CRUD networkTask = new NetworkTask_CRUD(AddBJMActivity.this, urlAddr);
                        networkTask.execute();
                        //startActivity(new Intent(AddBJMActivity.this, HomeActivity.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    bjmQuestCount = 0;
                    finish();
                    break;
                case R.id.bt_bjmadd_bjmCancel :
                    // 취소
                    new AlertDialog.Builder(AddBJMActivity.this)
                            .setTitle("정말 취소하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    bjmQuestCount = 0;
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

            }


        }
    };

    // JSP 파일 URL로 받아 JSON Data 받아오는 메소드
    protected String BJM_Select(String urlAddr) {
        String data = null;

        try {
            Jhj_MySql_Select_NetworkTask networkTask = new Jhj_MySql_Select_NetworkTask(AddBJMActivity.this, urlAddr);
            // execute() java 파일안의 메소드 한번에 동작시키기, 메소드를 사용하면 HttpURLConnection 이 제대로 작동하지않는다.
            Object obj = networkTask.execute().get();
            data = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    // JsonData Dtos 에 저장하기
    protected ArrayList<Jhj_Suggestion_DTO> JsonData_BJM_Parser(String jsonStr, String keyName, String[] attrName) {
        ArrayList<Jhj_Suggestion_DTO> dtos = new ArrayList<Jhj_Suggestion_DTO>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = new JSONArray(jsonObject.getString(keyName));
            dtos.clear();

            String[] attrValue = new String[attrName.length];
            for (int i = 0 ; i < jsonArray.length() ; i++) {
                // JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                for (int j = 0 ; j < attrName.length ; j++) {
                    attrValue[j] = jsonObject1.getString(attrName[j]);
                }

                dtos.add(new Jhj_Suggestion_DTO(attrValue[0], attrValue[1], attrValue[2], attrValue[3]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }
}