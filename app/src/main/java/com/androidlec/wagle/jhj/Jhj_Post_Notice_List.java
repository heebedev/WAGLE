package com.androidlec.wagle.jhj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.androidlec.wagle.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Jhj_Post_Notice_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jhj__post__notice__list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String IP = "192.168.0.82";

        // Data 받을 URL
        String urlAddr = "http://" + IP + ":8080/wagle/Post_Notice_SelectAll.jsp";
        // Json Data 받기
        String Notice_JsonString = Post_Select_All(urlAddr);
        // Json KeyName
        String[] keyName = {"pcSeqno", "pcTitle", "pcContent", "User_uSeqno"};
        // JsonData Bean 형태로 저장
        ArrayList<Jhj_Notice_DTO> jsonData = JsonData_Parser(Notice_JsonString, "notice", keyName);


        // 리사이클러뷰 사용 준비
        Jhj_Post_Notice_List_Adapter adapter;
        RecyclerView recyclerView;

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = findViewById(R.id.Recycler_List_Post_Notice) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(Jhj_Post_Notice_List.this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = new Jhj_Post_Notice_List_Adapter(jsonData);
        recyclerView.setAdapter(adapter);
    }

    // JSP 파일 URL로 받아 JSON Data 받아오는 메소드
    protected String Post_Select_All(String urlAddr) {
        String data = null;

        try {
            Jhj_MySql_Select_NetworkTask networkTask = new Jhj_MySql_Select_NetworkTask(Jhj_Post_Notice_List.this, urlAddr);
            // execute() java 파일안의 메소드 한번에 동작시키기, 메소드를 사용하면 HttpURLConnection 이 제대로 작동하지않는다.
            Object obj = networkTask.execute().get();
            data = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    // 갤러리 JsonData Dtos 에 저장하기
    protected ArrayList<Jhj_Notice_DTO> JsonData_Parser(String jsonStr, String keyName, String[] attrName) {
        ArrayList<Jhj_Notice_DTO> dtos = new ArrayList<Jhj_Notice_DTO>();

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

                dtos.add(new Jhj_Notice_DTO(attrValue[0], attrValue[1], attrValue[2], attrValue[3]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }

}