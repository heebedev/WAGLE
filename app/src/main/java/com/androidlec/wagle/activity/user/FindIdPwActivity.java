package com.androidlec.wagle.activity.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidlec.wagle.MainMoimListActivity;
import com.androidlec.wagle.R;
import com.androidlec.wagle.adapter.MoimListAdapter;
import com.androidlec.wagle.dto.Moimlist;
import com.androidlec.wagle.network_sh.NetworkTask_FindIDPW;
import com.androidlec.wagle.network_sh.NetworkTask_MoimList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FindIdPwActivity extends Activity {

    private TextView findid, findpw, result;
    private EditText id, name, birth;
    private String urlAddr;
    private String centIP;
    private String findData;
    private Button canc;

    private Intent intent;



    private void init() {

        findid = findViewById(R.id.tvbt_findid);
        findpw = findViewById(R.id.tvbt_findpw);
        result = findViewById(R.id.tv_findidpw_result);
        id = findViewById(R.id.et_findidpw_id);
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

        findid.setOnClickListener(onClickListener);
        findpw.setOnClickListener(onClickListener);
        canc.setOnClickListener(onClickListener);
    }



    ///버튼 클릭시 function;
    TextView.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvbt_findid :
                    if (name.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (birth.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        urlAddr = "http://" + centIP + ":8080/test/wagle_findidpw.jsp?find=id&name=" + name.getText().toString().trim() + "&birth=" + birth.getText().toString().trim();
                        connectGetData();
                        if (findData.length() != 0) {
                            result.setVisibility(View.VISIBLE);
                            result.setText("당신의 아이디는 " + findData + " 입니다.");
                        } else {
                            result.setVisibility(View.VISIBLE);
                            result.setText("회원정보가 존재하지 않습니다.");
                        }
                    }
                    break;
                case R.id.tvbt_findpw :
                    if (name.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (birth.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (id.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        urlAddr = "http://" + centIP + ":8080/test/wagle_findidpw.jsp?find=pw&id=" + id.getText().toString().trim() + "&name=" + name.getText().toString().trim() + "&birth=" + birth.getText().toString().trim();
                        Log.v("Status", urlAddr);
                        connectGetData();
                        if (findData.length() != 0) {
                            final LinearLayout linear = (LinearLayout) View.inflate(FindIdPwActivity.this, R.layout.custom_newpw_sh, null);

                            new AlertDialog.Builder(FindIdPwActivity.this)
                                    .setTitle("비밀번호 변경")
                                    .setView(linear)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            EditText newPw = linear.findViewById(R.id.newpw_pw);
                                            EditText newPwcheck = linear.findViewById(R.id.newpw_pwcheck);

                                            if (newPw.getText().toString().trim().equals(newPwcheck.getText().toString().trim())) {
                                                urlAddr = "http://" + centIP + ":8080/test/wagle_changePw.jsp?id="+findData+"&pw="+newPw.getText().toString().trim();
                                                Toast.makeText(FindIdPwActivity.this, "비밀번호가 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                intent = new Intent(FindIdPwActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(FindIdPwActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    })
                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            intent = new Intent(FindIdPwActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .show();
                        } else {
                            result.setVisibility(View.VISIBLE);
                            result.setText("회원정보가 존재하지 않습니다.");
                        }

                    }
                    break;
                case R.id.bt_findidpw_back :
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