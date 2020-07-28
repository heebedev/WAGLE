package com.androidlec.wagle.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidlec.wagle.CS.Activities.BoardFragment;
import com.androidlec.wagle.CS.Model.WagleList;
import com.androidlec.wagle.JH.MyWagleActivity;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.CS.Activities.ViewDetailWagleActivity;
import com.androidlec.wagle.activity.wagleSub.AddDHGActivity;
import com.androidlec.wagle.activity.wagleSub.AddTodayWagleActivity;
import com.androidlec.wagle.activity.wagleSub.AddWagleActivity;
import com.androidlec.wagle.jhj.Jhj_BookReport_DTO;
import com.androidlec.wagle.jhj.Jhj_FTPConnect;
import com.androidlec.wagle.jhj.Jhj_Gallery_DTO;
import com.androidlec.wagle.jhj.Jhj_MySql_Insert_Delete_Update_NetworkTask;
import com.androidlec.wagle.jhj.Jhj_MySql_Select_NetworkTask;
import com.androidlec.wagle.jhj.Jhj_Notice_DTO;
import com.androidlec.wagle.jhj.Jhj_Post_Gallery_List;
import com.androidlec.wagle.jhj.Jhj_HomeAndMyPage_Plus_List;
import com.androidlec.wagle.jhj.Jhj_Post_Write_Notice;
import com.androidlec.wagle.networkTask.JH_IntNetworkTask;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    // 지워야할것.
    private static String seqno = Integer.toString(UserInfo.USEQNO);

    // Post_Notice_Json Data (Json 파싱)
    private static ArrayList<Jhj_Notice_DTO> Ndata;
    private static ArrayList<WagleList> Wdata;
    private static ArrayList<Jhj_Gallery_DTO> Gdata;
    private static ArrayList<Jhj_BookReport_DTO> Bdata;

    // Layout (findViewById 를 사용하기위해) 선언
    private static ViewGroup rootView;
    private static String IP = "192.168.0.82";


    private static final String TAG = "HomeFragment";

    //

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        Button NoticeBtnAdd = rootView.findViewById(R.id.fragment_home_Notice_Add);
        Button WagleBtnAdd = rootView.findViewById(R.id.fragment_home_Wagle_Add);
        Button GalleryBtnAdd = rootView.findViewById(R.id.fragment_home_Gallery_Add);

        // 일반인일때 공지사항 버튼 안보이기
        if (UserInfo.MOIMMYGRADE.equals("W")) {
            NoticeBtnAdd.setVisibility(View.INVISIBLE);
        }

        // 버튼 이벤트 등록
        // 추가 버튼
        NoticeBtnAdd.setOnClickListener(add_home_fragment_OnClickListener);
        WagleBtnAdd.setOnClickListener(add_home_fragment_OnClickListener);
        GalleryBtnAdd.setOnClickListener(add_home_fragment_OnClickListener);

        // 더보기 버튼
        rootView.findViewById(R.id.fragment_home_Notice_Plus).setOnClickListener(plus_home_fragment_OnClickListener);
        rootView.findViewById(R.id.fragment_home_Wagle_Plus).setOnClickListener(plus_home_fragment_OnClickListener);
        rootView.findViewById(R.id.fragment_home_Gallery_Plus).setOnClickListener(plus_home_fragment_OnClickListener);
        rootView.findViewById(R.id.fragment_home_BookReport_Plus).setOnClickListener(plus_home_fragment_OnClickListener);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // 공지사항 세팅
        Notice_Setting();
        // 진행중인 와글 세팅
        Wagle_Setting();
        // 갤러리 세팅
        Gallery_Setting();
        // 독후감 세팅
        BookReport_Setting();

        // 초기 Fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BoardFragment boardFragment = new BoardFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_home_boardContainer, boardFragment).commitAllowingStateLoss();

    }

    protected String Post_Select_All(String urlAddr) {
        String data = null;

        try {
            Jhj_MySql_Select_NetworkTask networkTask = new Jhj_MySql_Select_NetworkTask(getActivity(), urlAddr);
            // execute() java 파일안의 메소드 한번에 동작시키기, 메소드를 사용하면 HttpURLConnection 이 제대로 작동하지않는다.
            Object obj = networkTask.execute().get();
            data = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check which request we're responding to
        if (requestCode == 1002) {
            // Make sure the request was successful
            if (resultCode == getActivity().RESULT_OK) {
                Uri file = data.getData();

                // 현재시간을 msec 으로 구한다.
                long now = System.currentTimeMillis();
                // 현재시간을 date 변수에 저장한다.
                Date date = new Date(now);
                // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                // nowDate 변수에 값을 저장한다.
                String formatDate = sdfNow.format(date);

                // 파일이름 , 파일 경로
                String fileName = UserInfo.UNAME + formatDate + ".jpg";

                // FTP File Upload
                GalleryFTPUpload(file, fileName);

                // Get 방식 URL 세팅
                String urlAddr = "http://" + IP + ":8080/wagle/Post_Gallery_Insert.jsp?userSeqno=" + seqno + "&type=G&fileName=" + fileName + "&MoimSeqno=" + UserInfo.MOIMSEQNO;
                Log.v("qwerasdf", urlAddr);
                connectionInsertData(urlAddr);
            }
        }
    }

    // --------------------------------------------------------------
    // + 버튼 이벤트
    // --------------------------------------------------------------

    // 추가 버튼 이벤트
    Button.OnClickListener add_home_fragment_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.fragment_home_Notice_Add :
                    intent = new Intent(getActivity(), Jhj_Post_Write_Notice.class);
                    intent.putExtra("Type", "W");
                    startActivity(intent);
                    break;
                case R.id.fragment_home_Gallery_Add :
                    intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 1002);
                    break;
                case R.id.fragment_home_Wagle_Add :
                    if (UserInfo.MOIMMYGRADE.equals("O") || UserInfo.MOIMMYGRADE.equals("S")) {
                        intent = new Intent(getActivity(), AddWagleActivity.class);
                    } else {
                        intent = new Intent(getActivity(), AddTodayWagleActivity.class);
                    }
                    startActivity(intent);
                    break;
            }
        }
    };

    // 더보기 버튼 이벤트
    Button.OnClickListener plus_home_fragment_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            switch (v.getId()) {
                case R.id.fragment_home_Notice_Plus :
                    intent = new Intent(getActivity(), Jhj_HomeAndMyPage_Plus_List.class);
                    intent.putExtra("Type", "Notice");
                    break;
                case R.id.fragment_home_Gallery_Plus :
                    intent = new Intent(getActivity(), Jhj_Post_Gallery_List.class);
                    break;
                case R.id.fragment_home_Wagle_Plus :
                    getActivity().findViewById(R.id.navigation_wagle).performClick();
                    return;
                case R.id.fragment_home_BookReport_Plus :
                    intent = new Intent(getActivity(), Jhj_HomeAndMyPage_Plus_List.class);
                    intent.putExtra("Type", "BookReport");
                    break;
            }
            startActivity(intent);
        }
    };

    // --------------------------------------------------------------
    // --------------------------------------------------------------

    // -------------------------------------------------------------------------------------
    // 공지사항 메소드
    // -------------------------------------------------------------------------------------

    protected void Notice_Setting() {
        // --------------------------------------------------------------
        // 공지사항 정보 4개 가져오기
        // --------------------------------------------------------------
        String urlAddr = "http://" + IP + ":8080/wagle/Post_Notice_Select.jsp?moimSeqno=" + UserInfo.MOIMSEQNO;
        String Noitce_JsonString = Post_Select_All(urlAddr);
        Ndata = Notice_parser(Noitce_JsonString);
        // --------------------------------------------------------------
        // --------------------------------------------------------------

        // --------------------------------------------------------------
        // 공지사항 정보 4개 보여주기
        // --------------------------------------------------------------
        Button[] notice_Frag_Btn = new Button[4];
        Integer[] notice_Frag_Btn_Id = {
                R.id.fragment_home_Notice1, R.id.fragment_home_Notice2, R.id.fragment_home_Notice3, R.id.fragment_home_Notice4
        };

        for (int i = 0 ; i < Ndata.size() ; i++) {
            notice_Frag_Btn[i] = rootView.findViewById(notice_Frag_Btn_Id[i]);
            notice_Frag_Btn[i].setOnClickListener(notice_Frag_OnClickListener);
            notice_Frag_Btn[i].setText(Ndata.get(i).getNoticeTitle());
        }
        // --------------------------------------------------------------
        //
        // --------------------------------------------------------------
    }

    protected ArrayList<Jhj_Notice_DTO> Notice_parser(String jsonStr) {
        ArrayList<Jhj_Notice_DTO> dtos = new ArrayList<Jhj_Notice_DTO>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            for (int i = 0 ; i < 4 ; i++) {
                Jhj_Notice_DTO dto = new Jhj_Notice_DTO(jsonObject.getString("PostSeqno" + i),
                        jsonObject.getString("PostTitle" + i),
                        jsonObject.getString("PostContent" + i),
                        jsonObject.getString("PostUserSeqno" + i));

                dtos.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }

    Button.OnClickListener notice_Frag_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), Jhj_Post_Write_Notice.class);

            // Type -> NW = Notice Write , NR = Notice Read
            switch (v.getId()) {
                case R.id.fragment_home_Notice1 :
                    intent.putExtra("Title", Ndata.get(0).getNoticeTitle());
                    intent.putExtra("Content", Ndata.get(0).getNoticeContent());
                    if (Ndata.get(0).getPostUserSeqno().equals(seqno)) {
                        intent.putExtra("Type", "NW");
                        intent.putExtra("Seqno", Ndata.get(0).getNoticeSeqno());
                    } else {
                        intent.putExtra("Type", "NR");
                    }
                    break;
                case R.id.fragment_home_Notice2 :
                    intent.putExtra("Title", Ndata.get(1).getNoticeTitle());
                    intent.putExtra("Content", Ndata.get(1).getNoticeContent());
                    if (Ndata.get(1).getPostUserSeqno().equals(seqno)) {
                        intent.putExtra("Type", "NW");
                        intent.putExtra("Seqno", Ndata.get(1).getNoticeSeqno());
                    } else {
                        intent.putExtra("Type", "NR");
                    }
                    break;
                case R.id.fragment_home_Notice3 :
                    intent.putExtra("Title", Ndata.get(2).getNoticeTitle());
                    intent.putExtra("Content", Ndata.get(2).getNoticeContent());
                    if (Ndata.get(2).getPostUserSeqno().equals(seqno)) {
                        intent.putExtra("Type", "NW");
                        intent.putExtra("Seqno", Ndata.get(2).getNoticeSeqno());
                    } else {
                        intent.putExtra("Type", "NR");
                    }
                    break;
                case R.id.fragment_home_Notice4 :
                    intent.putExtra("Title", Ndata.get(3).getNoticeTitle());
                    intent.putExtra("Content", Ndata.get(3).getNoticeContent());
                    if (Ndata.get(3).getPostUserSeqno().equals(seqno)) {
                        intent.putExtra("Type", "NW");
                        intent.putExtra("Seqno", Ndata.get(3).getNoticeSeqno());
                    } else {
                        intent.putExtra("Type", "NR");
                    }
                    break;
            }
            startActivity(intent);
        }
    };

    // -------------------------------------------------------------------------------------
    // 공지사항 메소드 끝
    // -------------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------------
    // 진행중인 와글 시작
    // -------------------------------------------------------------------------------------

    protected void Wagle_Setting() {
        String urlAddr = "http://" + IP + ":8080/wagle/Post_Wagle_Select.jsp?moimSeqno=" + UserInfo.MOIMSEQNO;
        String Wagle_JsonString = Post_Select_All(urlAddr);
        Wdata = Wagle_Parser(Wagle_JsonString);

        // 와글 정보 4개 보여주기
        Button[] wagle_Frag_Btn = new Button[4];
        Integer[] wagle_Frag_Btn_Id = {
                R.id.fragment_home_Wagle1, R.id.fragment_home_Wagle2, R.id.fragment_home_Wagle3, R.id.fragment_home_Wagle4
        };

        Date today = Calendar.getInstance().getTime();
        String todayStr = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(today);

        for (int i = 0 ; i < Wdata.size() ; i++) {
            wagle_Frag_Btn[i] = rootView.findViewById(wagle_Frag_Btn_Id[i]);
            wagle_Frag_Btn[i].setOnClickListener(wagle_Frag_OnClickListener);

            // 와글 이 종료되면 표시해주기
            String dueDate = Wdata.get(i).getWcDueDate().replaceAll("\\.", "");
            if(Integer.parseInt(todayStr) > Integer.parseInt(dueDate)) {
                wagle_Frag_Btn[i].setTextColor(getResources().getColor(R.color.generalTextLight));
                wagle_Frag_Btn[i].setPaintFlags(wagle_Frag_Btn[i].getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                wagle_Frag_Btn[i].setText(Wdata.get(i).getWcName());
            } else {
                wagle_Frag_Btn[i].setText(Wdata.get(i).getWcName());
            }
        }
    }

    protected ArrayList<WagleList> Wagle_Parser(String jsonStr) {
        ArrayList<WagleList> dtos = new ArrayList<WagleList>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("wagle_list"));
            dtos.clear();

            for (int i = 0 ; i < jsonArray.length() ; i++) {
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

    Button.OnClickListener wagle_Frag_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fragment_home_Wagle1 :
                    chkWagleCheck(0);
                    break;
                case R.id.fragment_home_Wagle2 :
                    chkWagleCheck(1);
                    break;
                case R.id.fragment_home_Wagle3 :
                    chkWagleCheck(2);
                    break;
                case R.id.fragment_home_Wagle4 :
                    chkWagleCheck(3);
                    break;
            }
        }
    };

    protected void chkWagleCheck(int position) {
        Intent intent;
        switch (chkJoinIn(Wdata.get(position).getWcSeqno())){
            case 1: // 와글 신청이 되었을 때.
                intent = new Intent(getActivity(), MyWagleActivity.class);
                UserInfo.WAGLESEQNO = Wdata.get(position).getWcSeqno();
                UserInfo.WAGLENAME = Wdata.get(position).getWcName();
                UserInfo.WAGLETYPE = Wdata.get(position).getWcType();
                startActivity(intent);
                break;
            case 2: // 와글 신청이 안되었을 때.
                intent = new Intent(getActivity(), ViewDetailWagleActivity.class);
                intent.putExtra("data", Wdata.get(position));
                intent.putExtra("wcSeqno", Wdata.get(position).getWcSeqno());
                startActivity(intent);
                break;
            case 0: // 데이터베이스 연결이 안되었을 때.
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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
            JH_IntNetworkTask networkTask = new JH_IntNetworkTask(getContext(), urlAddr);
            chk = networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return chk;
    }

    // -------------------------------------------------------------------------------------
    // 진행중인 와글 끝
    // -------------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------------
    // 갤러리 시작
    // -------------------------------------------------------------------------------------

    // 갤러리 세팅
    protected void Gallery_Setting() {
        // --------------------------------------------------------------
        // 갤러리 정보 6개 가져오기
        // --------------------------------------------------------------
        String urlAddr = "http://" + IP + ":8080/wagle/Post_Gallery_Select.jsp?moimSeqno=" + UserInfo.MOIMSEQNO;
        String Gallery_JsonString = Post_Select_All(urlAddr);
        Gdata = Gallery_parser(Gallery_JsonString);
        // --------------------------------------------------------------
        // --------------------------------------------------------------

        // --------------------------------------------------------------
        // 갤러리 정보 6개 보여주기
        // --------------------------------------------------------------
        ImageView[] gallery_Frag_Btn = new ImageView[6];
        Integer[] gallery_Frag_Btn_Id = {
                R.id.fragment_home_Gallery1, R.id.fragment_home_Gallery2, R.id.fragment_home_Gallery3,
                R.id.fragment_home_Gallery4, R.id.fragment_home_Gallery5, R.id.fragment_home_Gallery6
        };

        String imgUrl = "http://" + IP + ":8080/wagle/moimImgs/gallery/";

        for (int i = 0 ; i < Gdata.size() ; i++) {
            gallery_Frag_Btn[i] = rootView.findViewById(gallery_Frag_Btn_Id[i]);
            Glide.with(getActivity())
                    .load(imgUrl + Gdata.get(i).getImageName())
                    .placeholder(R.drawable.ic_baseline_crop_din_24)
                    .into(gallery_Frag_Btn[i]);
        }

        // --------------------------------------------------------------
        //
        // --------------------------------------------------------------
    }

    // 갤러리 JsonData Dtos 에 저장하기
    protected ArrayList<Jhj_Gallery_DTO> Gallery_parser(String jsonStr) {
        ArrayList<Jhj_Gallery_DTO> dtos = new ArrayList<Jhj_Gallery_DTO>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("gallery"));
            dtos.clear();

            for (int i = 0 ; i < jsonArray.length() ; i++) {
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

    // 갤러리 FTP File Uplaod
    protected void GalleryFTPUpload(Uri file, String fileName) {
        try {
            String fileDirectroy = "/moimImgs/gallery";

            // FTP 접속
            Jhj_FTPConnect connectFTP = new Jhj_FTPConnect(getActivity(), IP, "host", "qwer1234", 25, file, fileName, fileDirectroy);
            connectFTP.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 갤러리 FTP FileUpload 시, MySql 저장
    protected void connectionInsertData(String urlAddr) {
        // Jsp 서버 전송
        try {
            Jhj_MySql_Insert_Delete_Update_NetworkTask insNetworkTask = new Jhj_MySql_Insert_Delete_Update_NetworkTask(getActivity(), urlAddr);
            insNetworkTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------------------
    // 갤러리 끝
    // -------------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------------
    // 독후감 시작
    // -------------------------------------------------------------------------------------

    protected void BookReport_Setting() {
        String urlAddr = "http://" + IP + ":8080/wagle/Post_BookReport_Select.jsp?moimSeqno=" + UserInfo.MOIMSEQNO;
        String BookReport_JsonString = Post_Select_All(urlAddr);
        Bdata = BookReport_Parser(BookReport_JsonString);

        // 독후감 xml 가져오기
        Button[] BookReport_Frag_Btn = new Button[4];
        Integer[] BookReport_Frag_Btn_Id = {
                R.id.fragment_home_BookReport1, R.id.fragment_home_BookReport2, R.id.fragment_home_BookReport3, R.id.fragment_home_BookReport4
        };

        // 독후감 값 설정하기
        for (int i = 0 ; i < Bdata.size() ; i++) {
            BookReport_Frag_Btn[i] = rootView.findViewById(BookReport_Frag_Btn_Id[i]);
            BookReport_Frag_Btn[i].setOnClickListener(bookReport_Frag_OnClickListener);
            BookReport_Frag_Btn[i].setText(Bdata.get(i).getWcName() + " - " + Bdata.get(i).getuName());
        }
    }

    protected ArrayList<Jhj_BookReport_DTO> BookReport_Parser(String jsonStr) {
        ArrayList<Jhj_BookReport_DTO> dtos = new ArrayList<Jhj_BookReport_DTO>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("bookreport"));
            dtos.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String brSeqno = jsonObject1.getString("brSeqno");
                String wcSeqno = jsonObject1.getString("wcSeqno");
                String wcName = jsonObject1.getString("wcName");
                String uName = jsonObject1.getString("uName");

                dtos.add(new Jhj_BookReport_DTO(brSeqno, wcSeqno, wcName, uName));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }

    Button.OnClickListener bookReport_Frag_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fragment_home_BookReport1 :
                    BookReportMove(0);
                    break;
                case R.id.fragment_home_BookReport2 :
                    BookReportMove(1);
                    break;
                case R.id.fragment_home_BookReport3 :
                    BookReportMove(2);
                    break;
                case R.id.fragment_home_BookReport4 :
                    BookReportMove(3);
                    break;
            }
        }
    };

    protected void BookReportMove(int position) {
        UserInfo.WAGLESEQNO = Bdata.get(position).getWcSeqno();

        Intent intent = new Intent(getActivity(), AddDHGActivity.class);
        startActivity(intent);
    }

    // -------------------------------------------------------------------------------------
    // 독후감 끝
    // -------------------------------------------------------------------------------------

}