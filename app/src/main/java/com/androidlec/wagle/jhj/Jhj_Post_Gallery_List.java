package com.androidlec.wagle.jhj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Jhj_Post_Gallery_List extends AppCompatActivity {

    // Adapter 에서 사용하기위해 선언
    public static Context Gallery_List_Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jhj__post__gallery__list);

        Gallery_List_Context = Jhj_Post_Gallery_List.this;
    }

    @Override
    protected void onResume() {
        super.onResume();

        String IP = "192.168.0.82";

        // Data 받을 URL
        String urlAddr = "http://" + IP + ":8080/wagle/Post_Gallery_SelectAll.jsp?moimSeqno=" + UserInfo.MOIMSEQNO;
        String Gallery_JsonString = Post_Select_All(urlAddr);
        ArrayList<Jhj_Gallery_DTO> Gdata = Gallery_parser(Gallery_JsonString);

        // 리사이클러뷰 사용 준비
        Jhj_Post_Gallery_List_Adapter adapter;
        RecyclerView recyclerView;

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = findViewById(R.id.Recycler_List_Post_Gallery) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(Jhj_Post_Gallery_List.this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = new Jhj_Post_Gallery_List_Adapter(Gdata, Jhj_Post_Gallery_List.this);
        recyclerView.setAdapter(adapter);
    }

    // JSP 파일 URL로 받아 JSON Data 받아오는 메소드
    protected String Post_Select_All(String urlAddr) {
        String data = null;

        try {
            Jhj_MySql_Select_NetworkTask networkTask = new Jhj_MySql_Select_NetworkTask(Jhj_Post_Gallery_List.this, urlAddr);
            // execute() java 파일안의 메소드 한번에 동작시키기, 메소드를 사용하면 HttpURLConnection 이 제대로 작동하지않는다.
            Object obj = networkTask.execute().get();
            data = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    // 갤러리 JsonData Dtos 에 저장하기
    protected ArrayList<Jhj_Gallery_DTO> Gallery_parser(String jsonStr) {
        ArrayList<Jhj_Gallery_DTO> dtos = new ArrayList<Jhj_Gallery_DTO>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("gallery"));
            dtos.clear();

            for (int i = 0 ; i < jsonArray.length() ; i++) {
                // JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String seqno = jsonObject1.getString("seqno");
                String imgName = jsonObject1.getString("imagename");
                String user_uSeqno = jsonObject1.getString("user_useqno");

                dtos.add(new Jhj_Gallery_DTO(seqno, imgName, user_uSeqno));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }

}