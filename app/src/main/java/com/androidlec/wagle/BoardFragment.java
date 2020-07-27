package com.androidlec.wagle;

import android.annotation.SuppressLint;
import android.content.Intent;
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
        for (int i = 0; i < boardTitleLists.size(); i++) {
            String boardSeq = boardTitleLists.get(i).getbSeqno();
            boardLists = getBoardList(boardSeq);
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
            btn_header.setTag(i+"");
            btn_header.setOnClickListener(addOnClickListener);

            ll_header.addView(tv_header);
            ll_header.addView(btn_header);
            ll_main.addView(ll_header);
////////////////////// Head LinearLayout /////////////////////
////////////////////// 4 Buttons /////////////////////
            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, convertPixelsToDp(36));
            btnParams.setMargins(convertPixelsToDp(15), convertPixelsToDp(8), convertPixelsToDp(15), 0);

            for (int j = 0; j < 4; j++) {
                Button btn = new Button(getActivity());
                btn.setTag(i+","+j);
                btn.setLayoutParams(btnParams);
                btn.setPadding(convertPixelsToDp(10),0,0,0);
                btn.setBackgroundResource(R.drawable.jhj_wagle_btn_loundary);
                btn.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                if(boardLists.size() > j){
                    btn.setText(boardLists.get(j).getPcTitle());
                    btn.setOnClickListener(onClickListener);
                }
                ll_main.addView(btn);
            }
////////////////////// 4 Buttons /////////////////////
////////////////////// More Button /////////////////////
            TextView tv_more = new TextView(getActivity());
            LinearLayout.LayoutParams tvMoreParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvMoreParams.setMargins(0, convertPixelsToDp(10), 0, 0);
            tv_more.setLayoutParams(tvMoreParams);
            tv_more.setGravity(Gravity.CENTER);
            tv_more.setText("더보기");
            tv_more.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv_more.setTag(i+"");
            tv_more.setOnClickListener(moreOnClickListener);
            ll_main.addView(tv_more);
////////////////////// More Button /////////////////////
////////////////////// Main LinearLayout /////////////////////
        }

        FrameLayout mainFrame = v.findViewById(R.id.mainFrame);
        mainFrame.addView(ll_main);

    }

    View.OnClickListener addOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag = (String) v.getTag();
            int seq = Integer.parseInt(tag);
            Intent intent = new Intent(getActivity(), AddBoardActivity.class);
            intent.putExtra("boardSeq", boardTitleLists.get(seq).getbSeqno());
            intent.putExtra("boardTitle", boardTitleLists.get(seq).getbName());
            startActivity(intent);
        }
    };

    View.OnClickListener moreOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag = (String) v.getTag();
            int seq = Integer.parseInt(tag);
            Intent intent = new Intent(getActivity(), BoardListActivity.class);
            intent.putExtra("boardSeq", boardTitleLists.get(seq).getbSeqno());
            intent.putExtra("boardTitle", boardTitleLists.get(seq).getbName());
            startActivity(intent);
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag = (String) v.getTag();
            String[] seq = tag.split(",");
            int titleSeq = Integer.parseInt(seq[0]);
            int boardSeq = Integer.parseInt(seq[1]);
            ArrayList<BoardList> mBoardList = getBoardList(boardTitleLists.get(titleSeq).getbSeqno());
            if(mBoardList.size() != 0){
                Intent intent = new Intent(getActivity(), BoardActivity.class);
                intent.putExtra("boardTitle", boardTitleLists.get(titleSeq).getbName());
                intent.putExtra("title", mBoardList.get(boardSeq).getPcTitle());
                intent.putExtra("contents", mBoardList.get(boardSeq).getPcContent());
                startActivity(intent);
            }
        }
    };

    private ArrayList<BoardList> getBoardList(String boardSeq) {
        ArrayList<BoardList> result = null;
        String urlAddr = "http://192.168.0.79:8080/wagle/csGetBoardListWAGLE.jsp?";

        urlAddr = urlAddr + "Moim_wmSeqno=" + UserInfo.MOIMSEQNO + "&bSeqno=" + boardSeq;

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

        try {
            BDTNetworkTask bdtNetworkTask = new BDTNetworkTask(getActivity(), urlAddr);
            boardTitleLists = bdtNetworkTask.execute().get(); // doInBackground 의 리턴값
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}