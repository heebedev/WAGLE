package com.androidlec.wagle.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import java.util.HashMap;
import java.util.Map;

public class MyPageFragment extends Fragment {

    Button btn_sendMessage;

    // Layout (findViewById 를 사용하기위해) 선언
    ViewGroup rootView;
    String IP = "192.168.0.82";

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

        ImageView rankingIcon = rootView.findViewById(R.id.fragment_my_page_RankingIcon);
        TextView myName = rootView.findViewById(R.id.fragment_my_page_Text_Name);
        TextView rankGrade = rootView.findViewById(R.id.fragment_my_page_Text_Rank_Grade);

        TextView wagleNum = rootView.findViewById(R.id.fragment_my_page_Text_WagleNum);
        TextView bookReportNum = rootView.findViewById(R.id.fragment_my_page_Text_BookReportNum);

        TextView rankNum1 = rootView.findViewById(R.id.fragment_my_page_Text_Rank_Num1);
        TextView rankNum2 = rootView.findViewById(R.id.fragment_my_page_Text_Rank_Num2);
        TextView rankNum3 = rootView.findViewById(R.id.fragment_my_page_Text_Rank_Num3);
        TextView rankNum4 = rootView.findViewById(R.id.fragment_my_page_Text_Rank_Num4);
        TextView rankNum5 = rootView.findViewById(R.id.fragment_my_page_Text_Rank_Num5);

        ListView wagleListView = rootView.findViewById(R.id.fragment_my_page_Wagle_ListVIew);
        ListView bookReportListView = rootView.findViewById(R.id.fragment_my_page_BookReport_ListVIew);
    }

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

}