package com.androidlec.wagle.activity.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidlec.wagle.R;
import com.androidlec.wagle.activity.menu.MyInfoActivity;
import com.androidlec.wagle.networkTask.JH_IntNetworkTask;
import com.androidlec.wagle.networkTask.JH_VoidNetworkTask;

public class SignUpActivity extends AppCompatActivity {

    final static String TAG = "진행과정";
    String urlAddr;
    String id, pw, pwOk;

    EditText et_signupId, et_signupPw, et_signupPwOk;
    Button signupBtn;
    TextView backLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_signupId = findViewById(R.id.signup_et_id);
        et_signupPw = findViewById(R.id.signup_et_password);
        et_signupPwOk = findViewById(R.id.signup_et_passwordOk);

        signupBtn = findViewById(R.id.signup_btn_signup);
        signupBtn.setOnClickListener(onClickListener);

        backLogin = findViewById(R.id.signup_tv_login);
        backLogin.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.signup_tv_login:
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    break;
                case R.id.signup_btn_signup:
                    id = et_signupId.getText().toString();
                    pw = et_signupPw.getText().toString();
                    pwOk = et_signupPwOk.getText().toString();
                    blankChk();
                    break;
            }

        }

    };


    private void blankChk() {

        // 회원가입 이메일 포맷체크, 비밀번호 6자리체크
        if (!Patterns.EMAIL_ADDRESS.matcher(id).matches()) {
            // --------------- 대화상자 띄우기 ---------------------------------------------------------
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("올바른 형식의 이메일 주소를 설정해주세요.")
                    .setCancelable(false)
                    .setNegativeButton("확인", null)
                    .show();
            // -------------------------------------------------------------------------------------
            et_signupId.setError("이메일 확인!");
            et_signupId.setFocusable(true);
        } else if (pw.length() < 6) {
            // --------------- 대화상자 띄우기 ---------------------------------------------------------
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("비밀번호를 6자 이상으로 설정해주세요.")
                    .setCancelable(false)
                    .setNegativeButton("확인", null)
                    .show();
            // -------------------------------------------------------------------------------------
            et_signupPw.setError("비밀번호 확인!");
            et_signupPw.setFocusable(true);
        } else {
            pwOkChk(); // 패스워드 일치 확인.
        }
    }


    private void pwOkChk() {
        if (pw.equals(pwOk)) {
            urlAddrDivider("idDoubleChk", id, pw); // 아이디 중복 체크.
            // connectRegData(); // 회원가입 DB 연결.
        } else {
            // --------------- 대화상자 띄우기 ---------------------------------------------------------
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("비밀번호가 일치하지 않습니다.")
                    .setCancelable(false)
                    .setNegativeButton("확인", null)
                    .show();
            // -------------------------------------------------------------------------------------
        }
    }


    private void IdDoubleChk(int cntId) {
        switch (cntId) {
            case 1:
                Toast.makeText(SignUpActivity.this, "사용할 수 없는 이메일입니다.", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                urlAddrDivider("signup", id, pw); // 회원가입.
                break;
            default:
                Toast.makeText(SignUpActivity.this, "인터넷 환경을 확인해주세요.", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void urlAddrDivider(String function, String id, String pw) {
        switch (function) {
            case "idDoubleChk":
                urlAddr = "http://192.168.0.178:8080/wagle/idDoubleChk.jsp?";
                urlAddr = urlAddr + "id=" + id;
                connectDB("idDoubleChk");
                break;
            case "signup":
                urlAddr = "http://192.168.0.178:8080/wagle/signup.jsp?";
                urlAddr = urlAddr + "id=" + id + "&pw=" + pw;
                connectDB("signup");
                break;
        }
    }

    private void connectDB(String function) {
        try {
            switch (function) {
                case "idDoubleChk":
                    JH_IntNetworkTask idDoubleChkNetworkTask = new JH_IntNetworkTask(SignUpActivity.this, urlAddr);
                    int cntId = idDoubleChkNetworkTask.execute().get();
                    IdDoubleChk(cntId);
                    break;
                case "signup":
                    JH_VoidNetworkTask signupNetworkTask = new JH_VoidNetworkTask(SignUpActivity.this, urlAddr);
                    signupNetworkTask.execute().get();
                    Toast.makeText(SignUpActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "회원가입 완료.");
                    Intent intent = new Intent(SignUpActivity.this, MyInfoActivity.class);
                    intent.putExtra("uId", id);
                    intent.putExtra("LoginType", "wagle");
                    startActivity(intent);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}//----