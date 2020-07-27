package com.androidlec.wagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidlec.wagle.jhj.Jhj_MySql_Insert_Delete_Update_NetworkTask;
import com.androidlec.wagle.jhj.Jhj_Post_Write_Notice;

public class BoardActivity extends AppCompatActivity {

    TextView tv_boardTitle, tv_cancel;
    EditText et_title, et_contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        init();

    }

    private void init() {
        tv_cancel = findViewById(R.id.board_tv_cancel);
        tv_boardTitle = findViewById(R.id.board_tv_boardTitle);
        et_title = findViewById(R.id.board_et_title);
        et_contents = findViewById(R.id.board_et_contents);

        Intent intent = getIntent();
        String boardTitle = intent.getStringExtra("boardTitle");
        String title = intent.getStringExtra("title");
        String contents = intent.getStringExtra("contents");
        tv_boardTitle.setText(boardTitle);
        et_title.setText(title);
        et_contents.setText(contents);

        tv_cancel.setOnClickListener(v -> finish());

    }

}