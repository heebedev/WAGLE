package com.androidlec.wagle.jhj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidlec.wagle.MakeMoimActivity;
import com.androidlec.wagle.R;

public class Jhj_Post_Write_Notice extends AppCompatActivity {

    final static String IP = "192.168.0.82";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jhj__post__write_notice);

        findViewById(R.id.post_write_Btn_Submit).setOnClickListener(jhj_post_write_Notice_OnClickListener);
        findViewById(R.id.post_write_Btn_Cancle).setOnClickListener(jhj_post_write_Notice_OnClickListener);

    }

    Button.OnClickListener jhj_post_write_Notice_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // 지워야할것
            String seqno = "1";

            // 에디트 텍스트값 받아오기
            EditText title_Edit_Post_Write = findViewById(R.id.post_write_Edit_Title);
            EditText content_Edit_Post_Write = findViewById(R.id.post_write_Edit_Content);

            // 에디트 텍스트값 저장하기
            String title = title_Edit_Post_Write.getText().toString();
            String content = content_Edit_Post_Write.getText().toString();

            // Type 데이터 String에 담기
            Intent intent = getIntent();
            String type = intent.getStringExtra("postType");

            // Get 방식 URL 세팅
            String urlAddr = "http://" + IP + ":8080/wagle/Post_Insert.jsp?userSeqno=" + seqno + "&title=" + title + "&content=" + content + "&type=" + type;
            connectionInsertData(urlAddr);
        }
    };

    private void connectionInsertData(String urlAddr) {
        // Jsp 서버 전송
        try {
            Jhj_MySql_Insert_NetworkTask insNetworkTask = new Jhj_MySql_Insert_NetworkTask(Jhj_Post_Write_Notice.this, urlAddr);
            insNetworkTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}