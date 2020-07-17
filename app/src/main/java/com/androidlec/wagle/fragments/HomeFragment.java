package com.androidlec.wagle.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.androidlec.wagle.GalleryImageView;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.jhj.Jhj_FTPConnect;
import com.androidlec.wagle.jhj.Jhj_Gallery_DTO;
import com.androidlec.wagle.jhj.Jhj_MySql_Insert_Delete_Update_NetworkTask;
import com.androidlec.wagle.jhj.Jhj_MySql_Select_NetworkTask;
import com.androidlec.wagle.jhj.Jhj_Notice_DTO;
import com.androidlec.wagle.jhj.Jhj_Post_Gallery_List;
import com.androidlec.wagle.jhj.Jhj_Post_Notice_List;
import com.androidlec.wagle.jhj.Jhj_Post_Write_Notice;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // 지워야할것.
    String seqno = Integer.toString(UserInfo.USEQNO);

    // Post_Notice_Json Data (Json 파싱)
    ArrayList<Jhj_Notice_DTO> Ndata;
    ArrayList<Jhj_Gallery_DTO> Gdata;

    // Layout (findViewById 를 사용하기위해) 선언
    ViewGroup rootView;
    String IP = "192.168.0.82";


    private static final String TAG = "HomeFragment";

    //

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        // --------------------------------------------------------------
        // 버튼 이벤트 등록
        // --------------------------------------------------------------

        // 추가 버튼
        rootView.findViewById(R.id.fragment_home_Notice_Add).setOnClickListener(add_home_fragment_OnClickListener);
        rootView.findViewById(R.id.fragment_home_Gallery_Add).setOnClickListener(add_home_fragment_OnClickListener);

        // 더보기 버튼
        rootView.findViewById(R.id.fragment_home_Notice_Plus).setOnClickListener(plus_home_fragment_OnClickListener);
        rootView.findViewById(R.id.fragment_home_Gallery_Plus).setOnClickListener(plus_home_fragment_OnClickListener);

        // --------------------------------------------------------------
        // --------------------------------------------------------------

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // 공지사항 세팅
        Notice_Setting(rootView, IP);
        // 갤러리 세팅
        Gallery_Setting(rootView, IP);
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
                String fileName = "userName" + formatDate + ".jpg";

                // FTP File Upload
                GalleryFTPUpload(file, fileName);

                // Get 방식 URL 세팅
                String urlAddr = "http://" + IP + ":8080/wagle/Post_Gallery_Insert.jsp?userSeqno=" + seqno + "&type=G&fileName=" + fileName;
                connectionInsertData(urlAddr);
            }
        }
    }

    // --------------------------------------------------------------
    // + 버튼 이벤트
    // --------------------------------------------------------------

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

            }
        }
    };

    Button.OnClickListener plus_home_fragment_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            switch (v.getId()) {
                case R.id.fragment_home_Notice_Plus :
                    intent = new Intent(getActivity(), Jhj_Post_Notice_List.class);
                    break;
                case R.id.fragment_home_Gallery_Plus :
                    intent = new Intent(getActivity(), Jhj_Post_Gallery_List.class);
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

    protected void Notice_Setting(ViewGroup rootView, String IP) {
        // --------------------------------------------------------------
        // 공지사항 정보 4개 가져오기
        // --------------------------------------------------------------
        String urlAddr = "http://" + IP + ":8080/wagle/Post_Notice_Select.jsp";
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
    // 갤러리 시작
    // -------------------------------------------------------------------------------------

    // 갤러리 세팅
    protected void Gallery_Setting(ViewGroup rootView, String IP) {
        // --------------------------------------------------------------
        // 공지사항 정보 4개 가져오기
        // --------------------------------------------------------------
        String urlAddr = "http://" + IP + ":8080/wagle/Post_Gallery_Select.jsp";
        String Gallery_JsonString = Post_Select_All(urlAddr);
        Gdata = Gallery_parser(Gallery_JsonString);
        // --------------------------------------------------------------
        // --------------------------------------------------------------

        // --------------------------------------------------------------
        // 공지사항 정보 4개 보여주기
        // --------------------------------------------------------------
        GalleryImageView[] gallery_Frag_Btn = new GalleryImageView[6];
        Integer[] gallery_Frag_Btn_Id = {
                R.id.fragment_home_Gallery1, R.id.fragment_home_Gallery2, R.id.fragment_home_Gallery3,
                R.id.fragment_home_Gallery4, R.id.fragment_home_Gallery5, R.id.fragment_home_Gallery6
        };

        String imgUrl = "http://" + IP + ":8080/wagle/moimImgs/gallery/";

        for (int i = 0 ; i < Gdata.size() ; i++) {
            gallery_Frag_Btn[i] = rootView.findViewById(gallery_Frag_Btn_Id[i]);
            gallery_Frag_Btn[i].setOnClickListener(notice_Frag_OnClickListener);
            //         Context                 URL              ImageView
            Glide.with(getActivity()).load(imgUrl + Gdata.get(i).getImageName()).into(gallery_Frag_Btn[i]);
            Log.e("status",imgUrl + Gdata.get(i).getImageName());
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

}