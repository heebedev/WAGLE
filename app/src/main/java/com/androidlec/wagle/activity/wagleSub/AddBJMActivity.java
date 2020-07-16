package com.androidlec.wagle.activity.wagleSub;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.androidlec.wagle.HomeActivity;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.network_sh.NetworkTask_CRUD;

public class AddBJMActivity extends AppCompatActivity {

    private String urlAddr, centIP;

    private LinearLayout ll;
    private EditText bjmHead;
    private Button questionAddBtn, registerbjmBtn, cancelbjmBtn;
    private int bjmQuestCount;

    private void init() {
        bjmHead = findViewById(R.id.et_bjmadd_head);
        questionAddBtn = findViewById(R.id.bt_bjmadd_questadd);
        registerbjmBtn = findViewById(R.id.bt_bjmadd_bjmRegister);
        cancelbjmBtn = findViewById(R.id.bt_bjmadd_bjmCancel);
        ll = findViewById(R.id.ll_bjmadd_layout);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bjm);

        //초기화
        init();

        //발제문 질문 항목 추가
        questionAddBtn.setOnClickListener(bjmAddClickListener);
        //발제문 등록 취소
        registerbjmBtn.setOnClickListener(bjmRegCanClickListener);
        //발제문 서문 터치리스너 - 터치시 텍스트 정렬 변경
        bjmHead.setOnTouchListener(bjmKeyInListener);
    }



    Button.OnClickListener bjmAddClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void onClick(View v) {
            bjmQuestCount ++;

            //질문 입력 EditText 추가
            EditText edittext = new EditText(getApplicationContext());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.setMargins(30,10,30,10);
            edittext.setLayoutParams(p);
            edittext.setHint(bjmQuestCount + "번 질문");
            edittext.setId(bjmQuestCount);
            edittext.setTextSize(16);
            edittext.setBackgroundResource(R.drawable.white_rounded_background);
            edittext.setGravity(Gravity.CENTER);
            edittext.setPadding(10,10,10,10);

            ll.addView(edittext);

            //터치 시 정렬 변경 method
            edittext.setOnTouchListener(bjmKeyInListener);

        }
    };

    //클릭시 텍스트 정렬 변경
    EditText.OnTouchListener bjmKeyInListener = new View.OnTouchListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    EditText text = findViewById(v.getId());
                    text.setHint("");
                    text.setGravity(Gravity.LEFT);
                    break;
            }
            return false;
        }

    };


    Button.OnClickListener bjmRegCanClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_bjmadd_bjmRegister :
                    centIP = "192.168.0.138";
                    urlAddr = "http://" + centIP + ":8080/test/wagle_bjmadd.jsp?wseqno=" + UserInfo.WAGLESEQNO + "&count=" + bjmQuestCount + "&head=" + bjmHead.getText().toString();

                    for (int i = 1; i <= bjmQuestCount; i++) {
                        EditText text = findViewById(i);
                        String qst = text.getText().toString();
                        urlAddr = urlAddr + "&question"+i+"="+qst;
                    }

                    try {
                        NetworkTask_CRUD networkTask = new NetworkTask_CRUD(AddBJMActivity.this, urlAddr);
                        startActivity(new Intent(AddBJMActivity.this, HomeActivity.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case R.id.bt_bjmadd_bjmCancel :

                    new AlertDialog.Builder(AddBJMActivity.this)
                            .setTitle("정말 취소하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //startActivity(new Intent(AddBJMActivity.this, HomeActivity.class));

                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

            }


        }
    };
}