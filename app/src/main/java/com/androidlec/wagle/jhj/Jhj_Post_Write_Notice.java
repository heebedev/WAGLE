package com.androidlec.wagle.jhj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.androidlec.wagle.R;

public class Jhj_Post_Write_Notice extends AppCompatActivity {

    // 지워야할것
    String seqno = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jhj__post__write_notice);


        // --------------------------------------------------------------
        // Intent 값 가져와서 체크하기.
        // --------------------------------------------------------------

        EditText title_Edit_Post_Write = findViewById(R.id.post_write_Edit_Title);
        EditText content_Edit_Post_Write = findViewById(R.id.post_write_Edit_Content);

        LinearLayout NW_linearLayout = findViewById(R.id.post_Write_Linear_NW);
        LinearLayout NR_linearLayout = findViewById(R.id.post_Write_Linear_NR);

        Intent intent = getIntent();

        if (intent.getStringExtra("Type").equals("NR")) {
            title_Edit_Post_Write.setText(intent.getStringExtra("Title"));
            content_Edit_Post_Write.setText(intent.getStringExtra("Content"));

            disableEditText(title_Edit_Post_Write);
            disableEditText(content_Edit_Post_Write);

            NW_linearLayout.setVisibility(View.INVISIBLE);
            NR_linearLayout.setVisibility(View.VISIBLE);

        } else if (intent.getStringExtra("Type").equals("NW")) {
            title_Edit_Post_Write.setText(intent.getStringExtra("Title"));
            content_Edit_Post_Write.setText(intent.getStringExtra("Content"));
        } else {
            NW_linearLayout.setVisibility(View.VISIBLE);
            NR_linearLayout.setVisibility(View.INVISIBLE);
        }

        // --------------------------------------------------------------
        // 버튼 이벤트 등록
        // --------------------------------------------------------------

        findViewById(R.id.post_write_Btn_Submit).setOnClickListener(jhj_post_write_Notice_OnClickListener);
        findViewById(R.id.post_write_Btn_Cancle).setOnClickListener(jhj_post_write_Notice_OnClickListener);
        findViewById(R.id.post_write_Btn_Close).setOnClickListener(jhj_post_write_Notice_OnClickListener);

        // --------------------------------------------------------------
        // --------------------------------------------------------------
    }

    Button.OnClickListener jhj_post_write_Notice_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.post_write_Btn_Submit) {
                // 에디트 텍스트값 받아오기
                EditText title_Edit_Post_Write = findViewById(R.id.post_write_Edit_Title);
                EditText content_Edit_Post_Write = findViewById(R.id.post_write_Edit_Content);

                // 에디트 텍스트값 저장하기
                String title = title_Edit_Post_Write.getText().toString();
                String content = content_Edit_Post_Write.getText().toString();

                // Type 지정
                String type = "N";

                // JSP 서버 IP
                String IP = "192.168.0.82";

                String urlAddr = "";

                Intent intent = getIntent();
                if (intent.getStringExtra("Type").equals("NW")) {
                    String Seqno = intent.getStringExtra("Seqno");
                    urlAddr = "http://" + IP + ":8080/wagle/Post_Notice_Update.jsp?seqno=" + Seqno + "&userSeqno=" + seqno + "&title=" + title + "&content=" + content + "&type=" + type;
                } else {
                    // Get 방식 URL 세팅
                    urlAddr = "http://" + IP + ":8080/wagle/Post_Notice_Insert.jsp?userSeqno=" + seqno + "&title=" + title + "&content=" + content + "&type=" + type;
                }

                connectionInsertData(urlAddr);
            }

            finish();
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

    // EditText 비활성화 시키는 메소드 ----------------------------
    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }
    // ------------------------------------------------------
}