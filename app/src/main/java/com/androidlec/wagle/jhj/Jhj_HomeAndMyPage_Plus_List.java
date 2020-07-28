package com.androidlec.wagle.jhj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.androidlec.wagle.CS.Activities.ViewDetailWagleActivity;
import com.androidlec.wagle.CS.Model.WagleList;
import com.androidlec.wagle.JH.MyWagleActivity;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.activity.wagleSub.AddDHGActivity;
import com.androidlec.wagle.networkTask.JH_IntNetworkTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Jhj_HomeAndMyPage_Plus_List extends AppCompatActivity {

    private final static String TAG = "Jhj_HomeAndPage_Plus_List";

    // 공지사항 데이터
    private static ArrayList<Jhj_Notice_DTO> NData;
    // 독후감 데이터
    private static ArrayList<Jhj_BookReport_DTO> BRData;
    // 안쓴 독후감 데이터
    private static ArrayList<Jhj_Suggestion_DTO> SData;
    // 와글 데이터
    private static ArrayList<WagleList> WData;

    // 아이템 클릭 체크
    private static int itemClickCheck = 0;

    // JSP 연결 IP
    private final static String IP = "192.168.0.82";

    // 리사이클러뷰 사용 준비
    private static Jhj_Post_Notice_List_Adapter Nadapter;
    private static Jhj_MyPage_Wagle_List_Adapter Wadapter;
    private static Jhj_Post_DHG_List_Adapter BRadapter;
    private static Jhj_MyPage_DHG_List_Adapter NBRadapter;

    private static RecyclerView recyclerView;

    private static TextView plusTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jhj__post__notice__list);
        plusTitle = findViewById(R.id.Recycler_tv_Post_Title);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().getStringExtra("Type").equals("Notice")) {
            Notice_Setting();
        } else if (getIntent().getStringExtra("Type").equals("Wagle")) {
            Wagle_Setting();
        } else {
            DHG_Setting();
        }
    }

    protected void RecyclerList() {
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = findViewById(R.id.Recycler_List_Post_Notice) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(Jhj_HomeAndMyPage_Plus_List.this)) ;

        if (getIntent().getStringExtra("Type").equals("Notice")) {
            // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
            Nadapter = new Jhj_Post_Notice_List_Adapter(NData);

            // 공지사항 자세히 보기
            Nadapter.setOnItemClickListener(new Jhj_Post_Notice_List_Adapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(View v, int position) {
                    if (itemClickCheck == 1) {
                        return;
                    }

                    if (position != RecyclerView.NO_POSITION) {
                        // 글쓰기로 인탠트 보내기
                        Intent intent = new Intent(Jhj_HomeAndMyPage_Plus_List.this, Jhj_Post_Write_Notice.class);
                        intent.putExtra("Title", NData.get(position).getNoticeTitle());
                        intent.putExtra("Content", NData.get(position).getNoticeContent());
                        if (NData.get(position).getPostUserSeqno().equals(UserInfo.USEQNO)) {
                            intent.putExtra("Type", "NW");
                            intent.putExtra("Seqno", NData.get(position).getNoticeSeqno());
                        } else {
                            intent.putExtra("Type", "NR");
                        }
                        startActivity(intent);
                    }
                }
            });

            // 공지사항 삭제할지 선택하기
            Nadapter.setOnItemLongClickListener(new Jhj_Post_Notice_List_Adapter.OnItemLongClickListener() {
                @Override
                public void onItemLongClickListener(View v, int position) {
                    itemClickCheck = 1;

                    Dialog(position);

                    itemClickCheck = 0;
                }
            });

            recyclerView.setAdapter(Nadapter);
        } else if (getIntent().getStringExtra("Type").equals("Wagle")) {
            // 와글 어댑터 세팅
            Wadapter = new Jhj_MyPage_Wagle_List_Adapter(WData);

            // 와글 버튼 이벤트
            Wadapter.setOnItemClickListener(new Jhj_MyPage_Wagle_List_Adapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(View v, int position) {
                    if (position != RecyclerView.NO_POSITION) {
                        chkWagleCheck(position);
                    }
                }
            });

            recyclerView.setAdapter(Wadapter);
        } else {
            // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
            if (getIntent().getStringExtra("Type").equals("NoBookReport")) {
                NBRadapter = new Jhj_MyPage_DHG_List_Adapter(SData);

                // 리사이클러뷰 아이템 클릭 이벤트
                NBRadapter.setOnItemClickListener(new Jhj_MyPage_DHG_List_Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View v, int position) {
                        if (position != RecyclerView.NO_POSITION) {
                            UserInfo.WAGLESEQNO = SData.get(position).getWcSeqno();

                            Intent intent = new Intent(Jhj_HomeAndMyPage_Plus_List.this, AddDHGActivity.class);
                            startActivity(intent);
                        }
                    }
                });

                recyclerView.setAdapter(NBRadapter);
            } else {
                BRadapter = new Jhj_Post_DHG_List_Adapter(BRData);

                // 리사이클러뷰 아이템 클릭 이벤트
                BRadapter.setOnItemClickListener(new Jhj_Post_DHG_List_Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View v, int position) {
                        if (position != RecyclerView.NO_POSITION) {
                            UserInfo.WAGLESEQNO = BRData.get(position).getWcSeqno();

                            Intent intent = new Intent(Jhj_HomeAndMyPage_Plus_List.this, AddDHGActivity.class);
                            startActivity(intent);
                        }
                    }
                });

                recyclerView.setAdapter(BRadapter);
            }
        }

    }

    // ---------------------------------------------------------------------------------------------
    // 공지사항 시작
    // ---------------------------------------------------------------------------------------------

    protected void Notice_Setting() {
        // Data 받을 URL
        String urlAddr = "http://" + IP + ":8080/wagle/Post_Notice_SelectAll.jsp?moimSeqno=" + UserInfo.MOIMSEQNO;
        // Json Data 받기
        String Notice_JsonString = Post_Select_All(urlAddr);
        // Json KeyName
        String[] keyName = {"pcSeqno", "pcTitle", "pcContent", "User_uSeqno"};
        // JsonData Bean 형태로 저장
        NData = JsonData_Notice_Parser(Notice_JsonString, "notice", keyName);

        RecyclerList();
        plusTitle.setText("공지사항");
    }

    // JSP 파일 URL로 받아 JSON Data 받아오는 메소드
    protected String Post_Select_All(String urlAddr) {
        String data = null;

        try {
            Jhj_MySql_Select_NetworkTask networkTask = new Jhj_MySql_Select_NetworkTask(Jhj_HomeAndMyPage_Plus_List.this, urlAddr);
            // execute() java 파일안의 메소드 한번에 동작시키기, 메소드를 사용하면 HttpURLConnection 이 제대로 작동하지않는다.
            Object obj = networkTask.execute().get();
            data = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    // 공지사항 JsonData Dtos 에 저장하기
    protected ArrayList<Jhj_Notice_DTO> JsonData_Notice_Parser(String jsonStr, String keyName, String[] attrName) {
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

    // Dialog 띄워서 삭제 물어보기
    public void Dialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog 제목");
        builder.setMessage("AlertDialog 내용");
        builder.setCancelable(false);
        builder.setPositiveButton("취소", null);
        builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String urlAddr = "http://" + IP + ":8080/wagle/Post_Notice_Delete.jsp?seqno=" + NData.get(position).getNoticeSeqno();
                connectionDeleteData(urlAddr);

                // data ArrayList 안에있는 데이터 삭제후 새로고침.
                NData.remove(position);
                Nadapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }

    // PostNotice를 삭제 시키기 위한 메소드
    private void connectionDeleteData(String urlAddr) {
        // Jsp 서버 전송
        try {
            Jhj_MySql_Insert_Delete_Update_NetworkTask networkTask = new Jhj_MySql_Insert_Delete_Update_NetworkTask(Jhj_HomeAndMyPage_Plus_List.this, urlAddr);
            networkTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------------------------------------------
    // 공지사항 끝
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // 와글 시작
    // ---------------------------------------------------------------------------------------------

    protected void Wagle_Setting() {
        // Data 받을 URL
        String urlAddr = "http://" + IP + ":8080/wagle/MyPage_Wagle_SelectAll.jsp?moimSeqno=" + UserInfo.MOIMSEQNO + "&userSeqno=" + UserInfo.USEQNO;
        // Json Data 받기
        String Wagle_JsonString = Post_Select_All(urlAddr);
        // JsonData Bean 형태로 저장
        WData = JsonData_Wagle_Parser(Wagle_JsonString, "noWagle");

        RecyclerList();
    }

    protected ArrayList<WagleList> JsonData_Wagle_Parser(String jsonStr, String keyName) {
        ArrayList<WagleList> dtos = new ArrayList<WagleList>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = new JSONArray(jsonObject.getString(keyName));
            dtos.clear();

            for (int i = 0 ; i < jsonArray.length() ; i++) {
                // JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String wcSeqno = jsonObject1.getString("wcSeqno");
                String Moim_wmSeqno = jsonObject1.getString("Moim_wmSeqno");
                String MoimUser_muSeqno = jsonObject1.getString("MoimUser_muSeqno");
                String WagleBook_wbSeqno = jsonObject1.getString("WagleBook_wbSeqno");
                String wcName = jsonObject1.getString("wcName");
                String wcType = jsonObject1.getString("wcType");
                String wcStartDate = jsonObject1.getString("wcStartDate");
                String wcEndDate = jsonObject1.getString("wcEndDate");
                String wcDueDate = jsonObject1.getString("wcDueDate");
                String wcLocate = jsonObject1.getString("wcLocate");
                String wcEntryFee = jsonObject1.getString("wcEntryFee");
                String wcWagleDetail = jsonObject1.getString("wcWagleDetail");
                String wcWagleAgreeRefund = jsonObject1.getString("wcWagleAgreeRefund");

                dtos.add(new WagleList(wcSeqno, Moim_wmSeqno, MoimUser_muSeqno, WagleBook_wbSeqno, wcName, wcType,
                        wcStartDate, wcEndDate, wcDueDate, wcLocate, wcEntryFee, wcWagleDetail, wcWagleAgreeRefund));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }


    protected void chkWagleCheck(int position) {
        Intent intent;
        switch (chkJoinIn(WData.get(position).getWcSeqno())){
            case 1: // 와글 신청이 되었을 때.
                intent = new Intent(Jhj_HomeAndMyPage_Plus_List.this, MyWagleActivity.class);
                UserInfo.WAGLESEQNO = WData.get(position).getWcSeqno();
                UserInfo.WAGLENAME = WData.get(position).getWcName();
                UserInfo.WAGLETYPE = WData.get(position).getWcType();
                startActivity(intent);
                break;
            case 2: // 와글 신청이 안되었을 때.
                intent = new Intent(Jhj_HomeAndMyPage_Plus_List.this, ViewDetailWagleActivity.class);
                intent.putExtra("data", WData.get(position));
                intent.putExtra("wcSeqno", WData.get(position).getWcSeqno());
                startActivity(intent);
                break;
            case 0: // 데이터베이스 연결이 안되었을 때.
                Toast.makeText(Jhj_HomeAndMyPage_Plus_List.this, "Error", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    protected int chkJoinIn(String wcSeqno){
        int chk = 3;
        String uSeqno = String.valueOf(UserInfo.USEQNO);
        //uSeqno = "1"; // 임시 절대값. 위에꺼 쓰면 됨.
        String urlAddr = "http://192.168.0.178:8080/wagle/joininChk.jsp?";
        urlAddr = urlAddr + "wcSeqno=" + wcSeqno + "&User_uSeqno=" + uSeqno;
        try {
            JH_IntNetworkTask networkTask = new JH_IntNetworkTask(Jhj_HomeAndMyPage_Plus_List.this, urlAddr);
            chk = networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return chk;
    }

    // ---------------------------------------------------------------------------------------------
    // 와글 끝
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // 독후감 시작
    // ---------------------------------------------------------------------------------------------

    protected void DHG_Setting() {
        // Data 받을 URL
        String urlAddr = "";
        if (getIntent().getStringExtra("Type").equals("NoBookReport")) {
            urlAddr = "http://" + IP + ":8080/wagle/MyPage_BookReport_SelectAll.jsp?moimSeqno=" + UserInfo.MOIMSEQNO;
        } else {
            urlAddr = "http://" + IP + ":8080/wagle/Post_BookReport_SelectAll.jsp?moimSeqno=" + UserInfo.MOIMSEQNO;
        }
        // Json Data 받기
        String BookReport_JsonString = Post_Select_All(urlAddr);
        // JsonData Bean 형태로 저장
        if (getIntent().getStringExtra("Type").equals("NoBookReport")) {
            // Json KeyName
            String[] keyName = {"sSeqno", "sContent", "wcSeqno"};
            SData = JsonData_noBookReport_Parser(BookReport_JsonString, "noBookReport", keyName);
        } else {
            String[] keyName = {"brSeqno", "wcSeqno", "wcName", "uName"};
            BRData = JsonData_BookReport_Parser(BookReport_JsonString, "bookreport", keyName);
        }

        plusTitle.setText("독후감");
        RecyclerList();
    }

    // 독후감 JsonData Dtos 에 저장하기
    protected ArrayList<Jhj_BookReport_DTO> JsonData_BookReport_Parser(String jsonStr, String keyName, String[] attrName) {
        ArrayList<Jhj_BookReport_DTO> dtos = new ArrayList<Jhj_BookReport_DTO>();

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

                dtos.add(new Jhj_BookReport_DTO(attrValue[0], attrValue[1], attrValue[2], attrValue[3]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }

    // 안쓴 독후감 JsonData Dtos 에 저장하기
    protected ArrayList<Jhj_Suggestion_DTO> JsonData_noBookReport_Parser(String jsonStr, String keyName, String[] attrName) {
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

                dtos.add(new Jhj_Suggestion_DTO(attrValue[0], attrValue[1], attrValue[2]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }

    // ---------------------------------------------------------------------------------------------
    // 독후감 끝
    // ---------------------------------------------------------------------------------------------


}