package com.androidlec.wagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.androidlec.wagle.CS.Model.BoardList;
import com.androidlec.wagle.CS.Network.BDNetworkTask;
import com.androidlec.wagle.CS.Network.CSNetworkTask;
import com.androidlec.wagle.CS.Network.NetworkTask;

import java.util.ArrayList;

public class AddBoardActivity extends AppCompatActivity {

    private TextView tv_boardTitle, tv_cancel, tv_ok;
    private EditText et_title, et_contents;

    private String seq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board);

        init();

    }

    private void init() {
        tv_boardTitle = findViewById(R.id.addBoard_tv_boardTitle);
        tv_cancel = findViewById(R.id.addBoard_tv_cancel);
        tv_ok = findViewById(R.id.addBoard_tv_ok);
        et_title = findViewById(R.id.addBoard_et_title);
        et_contents = findViewById(R.id.addBoard_et_contents);

        Intent intent = getIntent();
        String boardTitle = intent.getStringExtra("boardTitle");
        seq = intent.getStringExtra("boardSeq");
        tv_boardTitle.setText(boardTitle);

        tv_cancel.setOnClickListener(onClickListener);
        tv_ok.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.addBoard_tv_cancel:
                    finish();
                    break;
                case R.id.addBoard_tv_ok:
                    insertPost();
                    break;
            }
        }
    };

    private void insertPost() {
        String urlAddr = "http://192.168.0.79:8080/wagle/csInputPostAndGetSeqnoWAGLE.jsp?";

        urlAddr = urlAddr + "User_uSeqno=" + UserInfo.USEQNO + "&Moim_mSeqno=" + UserInfo.MOIMSEQNO + "&Board_bSeqno=" + seq;

        Log.e("Chance", "1 - " + urlAddr);
        try {
            CSNetworkTask csNetworkTask = new CSNetworkTask(AddBoardActivity.this, urlAddr);
            String result = csNetworkTask.execute().get(); // doInBackground 의 리턴값
            insertPostContent(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertPostContent(String newSeq) {
        String title = et_title.getText().toString().trim();
        String contents = et_contents.getText().toString().trim();

        String urlAddr = "http://192.168.0.79:8080/wagle/csInputPostContentWAGLE.jsp?";

        urlAddr = urlAddr + "Post_pSeqno=" + newSeq + "&pcTitle=" + title + "&pcContent=" + contents;

        Log.e("Chance", "2 - " + urlAddr);
        try {
            NetworkTask networkTask = new NetworkTask(AddBoardActivity.this, urlAddr);
            networkTask.execute(); // doInBackground 의 리턴값
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}