package com.androidlec.wagle.activity.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.androidlec.wagle.R;
import com.androidlec.wagle.jhj.Jhj_MyMoim_Admin_List_Adapter;
import com.androidlec.wagle.jhj.Jhj_MyMoim_CustomDialog;
import com.androidlec.wagle.jhj.Jhj_MyMoim_DTO;
import com.androidlec.wagle.jhj.Jhj_MyMoim_User_List_Adapter;
import com.androidlec.wagle.jhj.Jhj_MySql_Select_NetworkTask;
import com.androidlec.wagle.jhj.Jhj_Notice_DTO;
import com.androidlec.wagle.jhj.Jhj_Post_Notice_List;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyMoimActivity extends AppCompatActivity {

    // JSP 연결 IP
    private final static String IP = "192.168.0.82";

    ArrayList<Jhj_MyMoim_DTO> jsonData = null;
    ArrayList<Jhj_MyMoim_DTO> adminData = null;
    ArrayList<Jhj_MyMoim_DTO> workerData = null;

    // moim Data .. 0 -> moimSeqno, 1 -> moimName, 2 -> moimImage
    String[] moimData;

    // 지워야할것
    int userSeqno = 1;
    int moimSeqno = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_moim);

        moimData = new String[3];
        jsonData = new ArrayList<Jhj_MyMoim_DTO>();
        adminData = new ArrayList<Jhj_MyMoim_DTO>();
        workerData = new ArrayList<Jhj_MyMoim_DTO>();

        // 버튼 이벤트 등록
        findViewById(R.id.bt_mymoiminfo_Admin_Sub).setOnClickListener(mymoim_info_Admin_OnClickListener);
        findViewById(R.id.bt_mymoiminfo_Admin_Add).setOnClickListener(mymoim_info_Admin_OnClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        jsonData.clear();
        adminData.clear();
        workerData.clear();

        // Data 받을 URL
        String urlAddr = "http://" + IP + ":8080/wagle/Moim_MyMoim_SelectAll.jsp?moimseqno=" + moimSeqno;
        // Json Data 받기
        String MyMoim_JsonString = Post_Select_All(urlAddr);
        // Json KeyName
        String[] keyName = {"maSeqno", "maGrade", "muSeqno", "uName", "uEmail", "uImageName"};
        // JsonData Bean 형태로 저장
        jsonData = JsonData_Parser(MyMoim_JsonString, "moimadminister", keyName);

        // 모임 아이콘 이미지 보여주기
        ImageView MyMoim_ImageIcon = findViewById(R.id.iv_mymoiminfo_moimimage);
        //         Context                 URL              ImageView
        Glide.with(MyMoimActivity.this).load("http://" + IP + ":8080/wagle/moimImgs/" + moimData[2]).into(MyMoim_ImageIcon);

        // 모임 타이틀 이름 보여주기
        TextView My_Moim_Title_Text = findViewById(R.id.tv_mymoiminfo_moimname);
        My_Moim_Title_Text.setText(moimData[1]);

        // -----------------------------------------------------------------------------------------
        // Admin 리사이클러 뷰
        // -----------------------------------------------------------------------------------------

        for (int i = 0 ; i < jsonData.size() ; i++) {
            if (jsonData.get(i).getMaGrade().equals("S")) {
                adminData.add(jsonData.get(i));
            }
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recycler_mymoim_list = findViewById(R.id.recyclerview_mymoiminfo_list) ;
        // 리사이클러뷰 가로로 보여주기
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyMoimActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recycler_mymoim_list.setLayoutManager(layoutManager) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        Jhj_MyMoim_Admin_List_Adapter admin_adapter = new Jhj_MyMoim_Admin_List_Adapter(adminData) ;
        recycler_mymoim_list.setAdapter(admin_adapter) ;


        // -----------------------------------------------------------------------------------------
        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // User 리스트 뷰
        // -----------------------------------------------------------------------------------------

        for (int i = 0 ; i < jsonData.size() ; i++) {
            if (jsonData.get(i).getMaGrade().equals("W")) {
                workerData.add(jsonData.get(i));
            }
        }

        Jhj_MyMoim_User_List_Adapter user_adapter = new Jhj_MyMoim_User_List_Adapter(MyMoimActivity.this, R.layout.jhj_mymoiminfo_user_list_item, workerData);

        ListView listview_mymoim_list = findViewById(R.id.lv_mymoim_boardlist);
        listview_mymoim_list.setAdapter(user_adapter);

        // -----------------------------------------------------------------------------------------
        // -----------------------------------------------------------------------------------------


    }

    // ---------------------------------------------------------------------------------------------
    // 모임 데이터 가져오기
    // ---------------------------------------------------------------------------------------------

    // JSP 파일 URL로 받아 JSON Data 받아오는 메소드
    protected String Post_Select_All(String urlAddr) {
        String data = null;

        try {
            Jhj_MySql_Select_NetworkTask networkTask = new Jhj_MySql_Select_NetworkTask(MyMoimActivity.this, urlAddr);
            // execute() java 파일안의 메소드 한번에 동작시키기, 메소드를 사용하면 HttpURLConnection 이 제대로 작동하지않는다.
            Object obj = networkTask.execute().get();
            data = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    // 갤러리 JsonData Dtos 에 저장하기
    protected ArrayList<Jhj_MyMoim_DTO> JsonData_Parser(String jsonStr, String keyName, String[] attrName) {
        ArrayList<Jhj_MyMoim_DTO> dtos = new ArrayList<Jhj_MyMoim_DTO>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            moimData[0] = jsonObject.getString("moimSeqno");
            moimData[1] = jsonObject.getString("moimName");
            moimData[2] = jsonObject.getString("moimImage");

            JSONArray jsonArray = new JSONArray(jsonObject.getString(keyName));
            dtos.clear();

            String[] attrValue = new String[attrName.length];
            for (int i = 0 ; i < jsonArray.length() ; i++) {
                // JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                for (int j = 0 ; j < attrName.length ; j++) {
                    attrValue[j] = jsonObject1.getString(attrName[j]);
                }

                dtos.add(new Jhj_MyMoim_DTO(attrValue[0], attrValue[1], attrValue[2], attrValue[3], attrValue[4], attrValue[5]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    Button.OnClickListener mymoim_info_Admin_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_mymoiminfo_Admin_Add :
                    mymoiminfo_CustomDialog(workerData);
                    break;
                case R.id.bt_mymoiminfo_Admin_Sub :
                    mymoiminfo_CustomDialog(adminData);
                    break;
            }
        }
    };

    public void mymoiminfo_CustomDialog(ArrayList<Jhj_MyMoim_DTO> data) {
        Jhj_MyMoim_CustomDialog customDialog = new Jhj_MyMoim_CustomDialog(MyMoimActivity.this, data, new Jhj_MyMoim_CustomDialog.CustomDialogClickListener() {
            @Override
            public void onCancleClick() {
                onResume();
            }
        });
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setCancelable(true);
        customDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        customDialog.show();
    }
}