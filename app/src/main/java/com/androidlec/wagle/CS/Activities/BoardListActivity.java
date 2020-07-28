package com.androidlec.wagle.CS.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidlec.wagle.CS.Adapter.BoardListAdapter;
import com.androidlec.wagle.CS.Model.BoardList;
import com.androidlec.wagle.CS.Network.BDNetworkTask;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;

import java.util.ArrayList;

public class BoardListActivity extends AppCompatActivity {

    private TextView tv_boardTitle, tv_noBoardList;
    private RecyclerView rv_main;

    private BoardListAdapter adapter;
    private ArrayList<BoardList> boardLists;

    public static String BOARD_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);

        init();

    }

    private void init() {
        tv_boardTitle = findViewById(R.id.boardList_tv_boardTitle);
        tv_noBoardList = findViewById(R.id.boardList_tv_noBoardList);
        rv_main = findViewById(R.id.boardList_rv_main);

        Intent intent = getIntent();
        String boardTitle = intent.getStringExtra("boardTitle");
        String boardSeq = intent.getStringExtra("boardSeq");
        tv_boardTitle.setText(boardTitle);
        BOARD_TITLE = boardTitle;

        boardLists = getRecyclerViewData(boardSeq);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();
    }

    private void setAdapter() {
        if (boardLists.size() == 0) {
            tv_noBoardList.setVisibility(View.VISIBLE);
            rv_main.setVisibility(View.GONE);
        } else {
            tv_noBoardList.setVisibility(View.GONE);
            rv_main.setVisibility(View.VISIBLE);
            adapter = new BoardListAdapter(BoardListActivity.this, boardLists);
            rv_main.setAdapter(adapter);
        }
    }

    private ArrayList<BoardList> getRecyclerViewData(String seq) {
        ArrayList<BoardList> result = null;
        String urlAddr = "http://192.168.0.79:8080/wagle/csGetBoardListWAGLE.jsp?";

        urlAddr = urlAddr + "Moim_wmSeqno=" + UserInfo.MOIMSEQNO + "&bSeqno=" + seq;

        try {
            BDNetworkTask bdNetworkTask = new BDNetworkTask(BoardListActivity.this, urlAddr);
            result = bdNetworkTask.execute().get(); // doInBackground 의 리턴값
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}