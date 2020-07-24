package com.androidlec.wagle;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidlec.wagle.CS.Model.BoardList;
import com.androidlec.wagle.CS.Model.BoardTitleList;
import com.androidlec.wagle.CS.Network.BDNetworkTask;
import com.androidlec.wagle.CS.Network.BDTNetworkTask;

import java.util.ArrayList;

public class BoardFragment extends Fragment {

    ArrayList<BoardTitleList> boardTitleLists;
    ArrayList<BoardList> boardLists;

    public BoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_board, container, false);

        getData();
        initView(v);

        return v;
    }

    @SuppressLint("NewApi")
    private void initView(View v) {
////////////////////// Main LinearLayout /////////////////////
        LinearLayout ll_main = new LinearLayout(getActivity());
        LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll_main.setLayoutParams(mainParams);
        ll_main.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < 2; i++) {
            String boardSeq = boardTitleLists.get(i).getbSeqno();
            ArrayList<BoardList> boardLists = getBoardList(boardSeq);
////////////////////// Head LinearLayout /////////////////////
            LinearLayout ll_header = new LinearLayout(getActivity());
            LinearLayout.LayoutParams headerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll_header.setLayoutParams(headerParams);

            TextView tv_header = new TextView(getActivity());
            LinearLayout.LayoutParams tvHeaderParams = new LinearLayout.LayoutParams(convertPixelsToDp(300), ViewGroup.LayoutParams.WRAP_CONTENT);
            tvHeaderParams.setMargins(convertPixelsToDp(16), convertPixelsToDp(16), 0, 0);
            tv_header.setLayoutParams(tvHeaderParams);
            tv_header.setTextAppearance(R.style.TextStyleBold);
            tv_header.setTextColor(getActivity().getResources().getColor(R.color.mainColor));
            tv_header.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv_header.setText(boardTitleLists.get(i).getbName());

            Button btn_header = new Button(getActivity());
            LinearLayout.LayoutParams btnHeaderParams = new LinearLayout.LayoutParams(convertPixelsToDp(30), convertPixelsToDp(30));
            btnHeaderParams.setMargins(convertPixelsToDp(48), convertPixelsToDp(15), 0, 0);
            btn_header.setLayoutParams(btnHeaderParams);
            btn_header.setBackgroundResource(R.drawable.ic_add_circle_24);

            ll_header.addView(tv_header);
            ll_header.addView(btn_header);
            ll_main.addView(ll_header);
////////////////////// Head LinearLayout /////////////////////
////////////////////// 4 Buttons /////////////////////
            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, convertPixelsToDp(36));
            btnParams.setMargins(convertPixelsToDp(15), convertPixelsToDp(8), convertPixelsToDp(15), 0);

            Button btn1 = new Button(getActivity());
            btn1.setLayoutParams(btnParams);
            btn1.setPadding(convertPixelsToDp(10),0,0,0);
            btn1.setBackgroundResource(R.drawable.jhj_wagle_btn_loundary);
            btn1.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
            btn1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            btn1.setText(boardLists.get(0).getPcTitle());
            ll_main.addView(btn1);

            Button btn2 = new Button(getActivity());
            btn2.setLayoutParams(btnParams);
            btn2.setPadding(convertPixelsToDp(10),0,0,0);
            btn2.setBackgroundResource(R.drawable.jhj_wagle_btn_loundary);
            btn2.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
            btn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            btn2.setText(boardLists.get(1).getPcTitle());
            ll_main.addView(btn2);

            Button btn3 = new Button(getActivity());
            btn3.setLayoutParams(btnParams);
            btn3.setPadding(convertPixelsToDp(10),0,0,0);
            btn3.setBackgroundResource(R.drawable.jhj_wagle_btn_loundary);
            btn3.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
            btn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            btn3.setText(boardLists.get(2).getPcTitle());
            ll_main.addView(btn3);

            Button btn4 = new Button(getActivity());
            btn4.setLayoutParams(btnParams);
            btn4.setPadding(convertPixelsToDp(10),0,0,0);
            btn4.setBackgroundResource(R.drawable.jhj_wagle_btn_loundary);
            btn4.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
            btn4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            //btn4.setText(boardLists.get(3).getPcTitle());
            ll_main.addView(btn4);
////////////////////// 4 Buttons /////////////////////
////////////////////// More Button /////////////////////
            TextView tv_more = new TextView(getActivity());
            LinearLayout.LayoutParams tvMoreParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvMoreParams.setMargins(0, convertPixelsToDp(10), 0, 0);
            tv_more.setLayoutParams(tvMoreParams);
            tv_more.setGravity(Gravity.CENTER);
            tv_more.setText("더보기");
            tv_more.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            ll_main.addView(tv_more);
////////////////////// More Button /////////////////////
////////////////////// Main LinearLayout /////////////////////
        }

        FrameLayout mainFrame = v.findViewById(R.id.mainFrame);
        mainFrame.addView(ll_main);

    }

    private ArrayList<BoardList> getBoardList(String boardSeq) {
        ArrayList<BoardList> result = null;
        String urlAddr = "http://192.168.0.79:8080/wagle/csGetBoardListWAGLE.jsp?";

        urlAddr = urlAddr + "Moim_wmSeqno=" + UserInfo.MOIMSEQNO + "&bSeqno=" + boardSeq;
        Log.e("Chance", urlAddr);

        try {
            BDNetworkTask bdNetworkTask = new BDNetworkTask(getActivity(), urlAddr);
            result = bdNetworkTask.execute().get(); // doInBackground 의 리턴값
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private int convertPixelsToDp(float px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getActivity().getResources().getDisplayMetrics());
    }


    private void getData() {
        String urlAddr = "http://192.168.0.79:8080/wagle/csGetBoardTitleListWAGLE.jsp?";

        urlAddr = urlAddr + "Moim_wmSeqno=" + UserInfo.MOIMSEQNO;
        Log.e("Chance", urlAddr);

        try {
            BDTNetworkTask bdtNetworkTask = new BDTNetworkTask(getActivity(), urlAddr);
            boardTitleLists = bdtNetworkTask.execute().get(); // doInBackground 의 리턴값
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}