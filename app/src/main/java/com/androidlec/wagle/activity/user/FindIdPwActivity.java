package com.androidlec.wagle.activity.user;


import androidx.appcompat.app.AlertDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.androidlec.wagle.R;
import com.androidlec.wagle.network_sh.NetworkTask_FindIDPW;

public class FindIdPwActivity extends Activity {

    private TextView findemid, findpw, result;
    private EditText email, name, birth;
    private String urlAddr;
    private String centIP;
    private String findData;
    private Button canc;

    private Intent intent;


    private void init() {

        findemid = findViewById(R.id.tvbt_findemid);
        findpw = findViewById(R.id.tvbt_findpw);
        result = findViewById(R.id.tv_findidpw_result);
        email = findViewById(R.id.et_findidpw_email);
        name = findViewById(R.id.et_findidpw_name);
        birth = findViewById(R.id.et_findidpw_birth);
        canc = findViewById(R.id.bt_findidpw_back);

        centIP = "192.168.0.138";


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findidpw);

        init();

        findemid.setOnClickListener(onClickListener);
        findpw.setOnClickListener(onClickListener);
        canc.setOnClickListener(onClickListener);
    }


    ///버튼 클릭시 function;
    TextView.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvbt_findemid:
                    if (name.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (birth.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        urlAddr = "http://" + centIP + ":8080/test/wagle_findidpw.jsp?find=email&name=" + name.getText().toString().trim() + "&birth=" + birth.getText().toString().trim();
//                        connectGetData();

                        if (findData == null) {
                            result.setVisibility(View.VISIBLE);
                            result.setText("회원정보가 존재하지 않습니다.");
                        } else {
                            result.setVisibility(View.VISIBLE);
                            result.setText("당신의 아이디는 " + findData + " 입니다.");
                        }
                    }
                    break;
                case R.id.tvbt_findpw:
                    if (name.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (birth.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (email.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        urlAddr = "http://" + centIP + ":8080/test/wagle_findidpw.jsp?find=pw&email=" + email.getText().toString().trim() + "&name=" + name.getText().toString().trim() + "&birth=" + birth.getText().toString().trim();
                        connectGetData();
                        if (findData != null) {
                            final LinearLayout linear = (LinearLayout) View.inflate(FindIdPwActivity.this, R.layout.custom_newpw_sh, null);

                            AlertDialog.Builder builder = new AlertDialog.Builder(FindIdPwActivity.this);
                            builder.setTitle("비밀번호 변경")
                                    .setView(linear)
                                    .setPositiveButton("OK", null) //onClick오버라이딩할거니까 null로해줘요.
                                    .setNegativeButton("취소", null)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            AlertDialog dialog = builder.create();
                            dialog.show();

                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EditText newPw = linear.findViewById(R.id.newpw_pw);
                                    EditText newPwcheck = linear.findViewById(R.id.newpw_pwcheck);
                                    TextView newPwCmt = linear.findViewById(R.id.newpw_comment);

                                    String newPwStr = newPw.getText().toString().trim();
                                    String newPwCkStr = newPwcheck.getText().toString().trim();

                                    if (newPwStr.equals(newPwCkStr) && newPwStr.length() >= 6) {
                                        urlAddr = "http://" + centIP + ":8080/test/wagle_changePw.jsp?email=" + findData + "&pw=" + newPw.getText().toString().trim();
                                        connectGetData();
                                        Toast.makeText(FindIdPwActivity.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_LONG).show();
                                        intent = new Intent(FindIdPwActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else if (newPwStr.length() < 6) {
                                        newPwCmt.setText("비밀번호는 6자리 이상입니다.");
                                        newPwCmt.setVisibility(View.VISIBLE);
                                    } else {
                                        newPwCmt.setText("비밀번호를 다시 확인해주세요.");
                                        newPwCmt.setVisibility(View.VISIBLE);
                                    }
                                }
                            });

                            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intent = new Intent(FindIdPwActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            });


                        } else {
                            result.setVisibility(View.VISIBLE);
                            result.setText("회원정보가 존재하지 않습니다.");
                        }

                    }
                    break;
                case R.id.bt_findidpw_back:
                    intent = new Intent(FindIdPwActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };


    private void connectGetData() {
        try {
            NetworkTask_FindIDPW networkTask = new NetworkTask_FindIDPW(FindIdPwActivity.this, urlAddr);
            Object obj = networkTask.execute().get();
            findData = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }  // connectGetData


}