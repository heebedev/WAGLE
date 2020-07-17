package com.androidlec.wagle.jhj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Jhj_Post_Notice_List extends AppCompatActivity {

    // 공지사항 데이터
    ArrayList<Jhj_Notice_DTO> jsonData;

    // 아이템 클릭 체크
    int itemClickCheck = 0;

    // JSP 연결 IP
    private final static String IP = "192.168.0.82";

    // 리사이클러뷰 사용 준비
    Jhj_Post_Notice_List_Adapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jhj__post__notice__list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Data 받을 URL
        String urlAddr = "http://" + IP + ":8080/wagle/Post_Notice_SelectAll.jsp?moimSeqno=" + UserInfo.MOIMSEQNO;
        // Json Data 받기
        String Notice_JsonString = Post_Select_All(urlAddr);
        // Json KeyName
        String[] keyName = {"pcSeqno", "pcTitle", "pcContent", "User_uSeqno"};
        // JsonData Bean 형태로 저장
        jsonData = JsonData_Parser(Notice_JsonString, "notice", keyName);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = findViewById(R.id.Recycler_List_Post_Notice) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(Jhj_Post_Notice_List.this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = new Jhj_Post_Notice_List_Adapter(jsonData);

        // 공지사항 자세히 보기
        adapter.setOnItemClickListener(new Jhj_Post_Notice_List_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                if (itemClickCheck == 1) {
                    return;
                }

                if (position != RecyclerView.NO_POSITION) {
                    // 글쓰기로 인탠트 보내기
                    Intent intent = new Intent(Jhj_Post_Notice_List.this, Jhj_Post_Write_Notice.class);
                    intent.putExtra("Title", jsonData.get(position).getNoticeTitle());
                    intent.putExtra("Content", jsonData.get(position).getNoticeContent());
                    if (jsonData.get(position).getPostUserSeqno().equals(UserInfo.USEQNO)) {
                        intent.putExtra("Type", "NW");
                        intent.putExtra("Seqno", jsonData.get(position).getNoticeSeqno());
                    } else {
                        intent.putExtra("Type", "NR");
                    }
                    startActivity(intent);
                }
            }
        });

        // 공지사항 삭제할지 선택하기
        adapter.setOnItemLongClickListener(new Jhj_Post_Notice_List_Adapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {
                itemClickCheck = 1;

                Dialog(position);

                itemClickCheck = 0;
            }
        });

        recyclerView.setAdapter(adapter);
    }

    // ---------------------------------------------------------------------------------------------
    // 공지사항 데이터 가져오기
    // ---------------------------------------------------------------------------------------------

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
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // 공지사항 삭제
    // ---------------------------------------------------------------------------------------------

    // Dialog 띄워서 삭제 물어보기
    public void Dialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog 제목");
        builder.setMessage("AlertDialog 내용");
        builder.setCancelable(false);
        builder.setPositiveButton("취소", null);
        builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String urlAddr = "http://" + IP + ":8080/wagle/Post_Notice_Delete.jsp?seqno=" + jsonData.get(position).getNoticeSeqno();
                        connectionDeleteData(urlAddr);

                        // data ArrayList 안에있는 데이터 삭제후 새로고침.
                        jsonData.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.show();
    }

    // PostNotice를 삭제 시키기 위한 메소드
    private void connectionDeleteData(String urlAddr) {
        // Jsp 서버 전송
        try {
            Jhj_MySql_Insert_Delete_Update_NetworkTask networkTask = new Jhj_MySql_Insert_Delete_Update_NetworkTask(Jhj_Post_Notice_List.this, urlAddr);
            networkTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

}