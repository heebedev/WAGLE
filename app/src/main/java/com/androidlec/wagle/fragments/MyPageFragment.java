package com.androidlec.wagle.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androidlec.wagle.CS.Model.User;
import com.androidlec.wagle.CS.Model.WagleList;
import com.androidlec.wagle.JH.MyWagleActivity;
import com.androidlec.wagle.JH.Payment;
import com.androidlec.wagle.JH.PaymentAdapter;
import com.androidlec.wagle.JH.Progress;
import com.androidlec.wagle.JH.Rank;
import com.androidlec.wagle.JH.RankAdapter;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.ViewDetailWagleActivity;
import com.androidlec.wagle.jhj.Jhj_BookReport_DTO;
import com.androidlec.wagle.jhj.Jhj_MyPage_DTO;
import com.androidlec.wagle.jhj.Jhj_MySql_Select_NetworkTask;
import com.androidlec.wagle.jhj.Jhj_Notice_DTO;
import com.androidlec.wagle.jhj.Jhj_Suggestion_DTO;
import com.androidlec.wagle.jhj.Jhj_Wagle_DTO;
import com.androidlec.wagle.networkTask.JH_IntNetworkTask;
import com.androidlec.wagle.networkTask.JH_ObjectNetworkTask_Rank;
import com.bumptech.glide.Glide;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPageFragment extends Fragment {

    private final static String TAG = "MyPAgeFragment";

    private static Button btn_sendMessage;

    // Layout (findViewById 를 사용하기위해) 선언
    private static ViewGroup rootView;
    private static String IP = "192.168.0.82";

    private static Jhj_MyPage_DTO data;

    // 랭킹 탑5 구하기.
    private static ArrayList<Rank> ranks;
    private static RankAdapter rankAdapter;

    public MyPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_page, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        btn_sendMessage = rootView.findViewById(R.id.myPage_btn_sendMessage);
        btn_sendMessage.setOnClickListener(onClickListener);

        ImageView profileIcon = rootView.findViewById(R.id.fragment_my_page_ProfileIcon);
        ImageView Rank_Grade = rootView.findViewById(R.id.fragment_my_page_iv_Rank_Grade);

//        TextView rankGrade = rootView.findViewById(R.id.fragment_my_page_Text_Rank_Grade);

        TextView waglePlusBtn = rootView.findViewById(R.id.fragment_my_page_Wagle_Plus);

        TextView bookReportPlusBtn = rootView.findViewById(R.id.fragment_my_page_BookReport_Plus);


        // 프로필 사진 가져오기
        UserInfo.ULOGINTYPE = "wagle";
        if(UserInfo.ULOGINTYPE.equals("wagle")){
            Glide.with(this)
                    .load("http://192.168.0.82:8080/wagle/userImgs/" + UserInfo.UIMAGENAME)
                    .placeholder(R.drawable.ic_outline_emptyimage)
                    .into(profileIcon);
        } else {
            Glide.with(this)
                    .load(UserInfo.UIMAGENAME)
                    .placeholder(R.drawable.ic_outline_emptyimage)
                    .into(profileIcon);
        }

        // 페이지 정보 가져오기
        String urlAddr = "http://" + IP + ":8080/wagle/MyPage_Select.jsp?moimSeqno=" + UserInfo.MOIMSEQNO + "&userSeqno=" + UserInfo.USEQNO;
        Log.v(TAG, "urlAddr = " + urlAddr);
        String MyPage_JsonString = MyPage_Select_All(urlAddr);
        data = MyPage_parser(MyPage_JsonString);

        // 유저 이름 넣기
        TextView myName = rootView.findViewById(R.id.fragment_my_page_Text_Name);
        myName.setText(UserInfo.UNAME);


        // 참여한 총 와글 00개 / 00개 ( 00 % )
        TextView wagleNum = rootView.findViewById(R.id.fragment_my_page_Text_WagleNum);
        double result = Double.parseDouble(data.getWagleNum()) / Double.parseDouble(data.getTotalWagle());
        int percentage = (int) (result * 100);
        wagleNum.setText("참여한 총 와글 : " + data.getWagleNum() + " 개 / " + data.getTotalWagle() + "개 (" + percentage + "%)");
        // 참여한 총 독후감 00개 / 00개 ( 00 % )
        result = Double.parseDouble(data.getWagleBookReportNum()) / Double.parseDouble(data.getTotalBookReport());
        percentage = (int) (result * 100);
        TextView bookReportNum = rootView.findViewById(R.id.fragment_my_page_Text_BookReportNum);
        bookReportNum.setText("참여한 총 독후감 : " + data.getWagleBookReportNum() + " 개 / " + data.getTotalBookReport() + "개 (" + percentage + "%)");
        // 총점 계산
        TextView totalScore = rootView.findViewById(R.id.fragment_my_page_Text_TotalScore);
        int myscore = (Integer.parseInt(data.getWagleNum())*10) + (Integer.parseInt(data.getWagleBookReportNum())*5);
        totalScore.setText("총 " + myscore + " 점");

        // 랭크 이미지
        if(getRankGrade() <= 10){
            Rank_Grade.setImageResource(R.drawable.diamond);
        }else if(getRankGrade() <= 30){
            Rank_Grade.setImageResource(R.drawable.gold);
        }else if(getRankGrade() <= 60) {
            Rank_Grade.setImageResource(R.drawable.silver);
        }else {
            Rank_Grade.setImageResource(R.drawable.bronze);
        }

        // 랭킹 탑5
        getTop5();

        // 와글 버튼 텍스트, 이벤트 설정
        Button[] wagleBtn = new Button[4];
        Integer[] wagle_Frag_Btn_Id = {
                R.id.fragment_my_page_Wagle1, R.id.fragment_my_page_Wagle2, R.id.fragment_my_page_Wagle3, R.id.fragment_my_page_Wagle4
        };

        for (int i = 0 ; i < data.getWagle().size() ; i++) {
            wagleBtn[i] = rootView.findViewById(wagle_Frag_Btn_Id[i]);
            wagleBtn[i].setOnClickListener(wagle_MyPage_OnClickListener);
            wagleBtn[i].setText(data.getWagle().get(i).getWcName());
        }

        // 독후감 버튼 텍스트, 이벤트 설정
        Button[] suggestionBtn = new Button[4];
        Integer[] suggestion_Frag_Btn_Id = {
                R.id.fragment_my_page_BookReport1, R.id.fragment_my_page_BookReport2, R.id.fragment_my_page_BookReport3, R.id.fragment_my_page_BookReport4
        };

        for (int i = 0 ; i < data.getSuggestion().size(); i++) {
            suggestionBtn[i] = rootView.findViewById(suggestion_Frag_Btn_Id[i]);
            suggestionBtn[i].setOnClickListener(suggestion_MyPage_OnClickListener);
            suggestionBtn[i].setText(data.getSuggestion().get(i).getsContent());
        }
    }

    private void getTop5(){
        ListView lv_ranktop5 = rootView.findViewById(R.id.fragment_my_page_ListView_Rank);
        String Moim_mSeqno = UserInfo.MOIMSEQNO;
        String JH_IP = "192.168.0.178";
        String urlAddr = "http://" + JH_IP + ":8080/wagle/getTop5.jsp?";
        urlAddr = urlAddr + "Moim_mSeqno=" + Moim_mSeqno;
        try {
            JH_ObjectNetworkTask_Rank objectNetworkTask_rank = new JH_ObjectNetworkTask_Rank(getActivity(), urlAddr);
            Object obj = objectNetworkTask_rank.execute().get();
            ranks = (ArrayList<Rank>) obj;
            rankAdapter = new RankAdapter(getContext(), R.layout.customlayout_ranktop5_listview, ranks); // making adapter.
            lv_ranktop5.setAdapter(rankAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int getRankGrade(){
        int rankgrade = 0;
//        String muSeqno = UserInfo.MOIMUSERSEQNO;
        String muSeqno = "33"; // 일단 절대값 넣어둠!!!
        String JH_IP = "192.168.0.178";
        String urlAddr = "http://" + JH_IP + ":8080/wagle/getRankGrade.jsp?";
        urlAddr = urlAddr + "muSeqno=" + muSeqno;
        try {
            JH_IntNetworkTask networkTask4 = new JH_IntNetworkTask(getActivity(), urlAddr);
            rankgrade = networkTask4.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rankgrade;
    }





    // JsonData 가져오기
    protected String MyPage_Select_All(String urlAddr) {
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

    // Json 파싱
    protected Jhj_MyPage_DTO MyPage_parser(String jsonString) {
        Jhj_MyPage_DTO dtos = null;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            String wagleNum = jsonObject.getString("wagleNum");
            String wagleBookReportNum = jsonObject.getString("wagleBookReportNum");
            String wagleScore = jsonObject.getString("wagleScore");
            String totalBookReport = jsonObject.getString("totalBookReport");
            String totalWagle = jsonObject.getString("totalWagle");

            JSONArray jsonArray = new JSONArray(jsonObject.getString("noBookReport"));
            ArrayList<Jhj_Suggestion_DTO> book_Dtos = new ArrayList<Jhj_Suggestion_DTO>();
            for (int i = 0 ; i < jsonArray.length() ; i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String wcSeqno = jsonObject1.getString("sSeqno");
                String wcName = jsonObject1.getString("sContent");
                String wcDueDate = jsonObject1.getString("wcSeqno");

                book_Dtos.add(new Jhj_Suggestion_DTO(wcSeqno, wcName, wcDueDate));
            }

            JSONArray jsonArray2 = new JSONArray(jsonObject.getString("noWagle"));
            ArrayList<WagleList> wagle_Dtos = new ArrayList<WagleList>();
            for (int i = 0 ; i < jsonArray2.length() ; i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray2.get(i);

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

                wagle_Dtos.add(new WagleList(wcSeqno, Moim_wmSeqno, MoimUser_muSeqno, WagleBook_wbSeqno, wcName, wcType,
                        wcStartDate, wcEndDate, wcDueDate, wcLocate, wcEntryFee, wcWagleDetail, wcWagleAgreeRefund));
            }

            dtos = new Jhj_MyPage_DTO(wagleNum, wagleBookReportNum, wagleScore, totalWagle, totalBookReport, wagle_Dtos, book_Dtos);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }

    // -------------------------------------------------------------------------------------
    // 와글 시작
    // -------------------------------------------------------------------------------------

    // 와글 신청 이벤트
    Button.OnClickListener wagle_MyPage_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fragment_my_page_Wagle1 :
                    chkWagleCheck(0);
                    break;
                case R.id.fragment_my_page_Wagle2 :
                    chkWagleCheck(1);
                    break;
                case R.id.fragment_my_page_Wagle3 :
                    chkWagleCheck(2);
                    break;
                case R.id.fragment_my_page_Wagle4 :
                    chkWagleCheck(3);
                    break;
            }
        }
    };

    protected void chkWagleCheck(int position) {
        Intent intent;
        switch (chkJoinIn(data.getWagle().get(position).getWcSeqno())){
            case 1: // 와글 신청이 되었을 때.
                intent = new Intent(getActivity(), MyWagleActivity.class);
                UserInfo.WAGLESEQNO = data.getWagle().get(position).getWcSeqno();
                UserInfo.WAGLENAME = data.getWagle().get(position).getWcName();
                UserInfo.WAGLETYPE = data.getWagle().get(position).getWcType();
                startActivity(intent);
                break;
            case 2: // 와글 신청이 안되었을 때.
                intent = new Intent(getActivity(), ViewDetailWagleActivity.class);
                intent.putExtra("data", data.getWagle().get(position));
                intent.putExtra("wcSeqno", data.getWagle().get(position).getWcSeqno());
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
    // 와글 끝
    // -------------------------------------------------------------------------------------


    // -------------------------------------------------------------------------------------
    // 발제문 시작
    // -------------------------------------------------------------------------------------

    Button.OnClickListener suggestion_MyPage_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    // -------------------------------------------------------------------------------------
    // 발제문 끝
    // -------------------------------------------------------------------------------------

    // 카카오톡 이벤트
    View.OnClickListener onClickListener = v -> {
        kakaoLink();
    };

    private void kakaoLink() {
        // 템플릿 ID
        String templateId = "32851";

        // 템플릿에 입력된 Argument에 채워질 값
        Map<String, String> templateArgs = new HashMap<>();
        templateArgs.put("USER_NAME", UserInfo.UNAME);
        templateArgs.put("MOIM_NAME", UserInfo.MOIM_NAME);

        // 링크 콜백에서 함께 수신할 커스텀 파라미터를 설정합니다.
        Map<String, String> serverCallbackArgs = new HashMap<>();
        serverCallbackArgs.put("user_id", "${current_user_id}");
        serverCallbackArgs.put("product_id", "${shared_product_id}");

        // 커스텀 템플릿으로 카카오링크 보내기
        KakaoLinkService.getInstance()
                .sendCustom(getActivity(), templateId, templateArgs, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "카카오링크 보내기 실패: " + errorResult);
                    }

                    @Override
                    public void onSuccess(KakaoLinkResponse result) {
                        Log.i("KAKAO_API", "카카오링크 보내기 성공");

                        // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                        Log.w("KAKAO_API", "warning messages: " + result.getWarningMsg());
                        Log.w("KAKAO_API", "argument messages: " + result.getArgumentMsg());
                    }
                });
    }
    // 카카오톡 이벤트 끝

}