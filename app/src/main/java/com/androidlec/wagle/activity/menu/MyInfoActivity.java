package com.androidlec.wagle.activity.menu;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidlec.wagle.CS.Model.User;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.activity.user.LoginActivity;
import com.androidlec.wagle.networkTask.JH_ConnectFTP;
import com.androidlec.wagle.networkTask.JH_VoidNetworkTask;
import com.androidlec.wagle.networkTask.JH_ObjectNetworkTask_MyInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MyInfoActivity extends AppCompatActivity {

    EditText et_name, et_birthDate, et_emailAddress;
    ImageView iv_photo;
    Button startBtn, editBtn;
    TextView tv_changePw;

    public static String previousXML = ""; // 어떤 경로에서 왔는지.
    private int mWhich;
    private Uri image_uri;
    String urlAddr;

    ArrayList<User> userInfo;
    String uSeqno, uId, uPassword, uLoginType, uName, uImageName, uEmail, uBirthDate, uDate;


    // 카메라 관련
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int IMAGE_PICK_CAMERA_CODE = 101;
    private static final int IMAGE_PICK_GALLERY_CODE = 102;

    private void init(){
        iv_photo = findViewById(R.id.myInfo_iv_photo);

        et_name = findViewById(R.id.myInfo_et_name);
        et_birthDate = findViewById(R.id.myInfo_et_birthDate);
        et_emailAddress = findViewById(R.id.myInfo_et_EmailAddress);

        tv_changePw = findViewById(R.id.myInfo_tv_changePw);

        startBtn = findViewById(R.id.myInfo_btn_start);
        editBtn = findViewById(R.id.myInfo_btn_edit);
    }

    // 회원가입을 통해 들어왔는지 / 정보수정을 통해 들어왔는지.
    private void chkPreviousXML(){
        if(previousXML.equals("edit")){
            tv_changePw.setVisibility(View.VISIBLE);
            startBtn.setVisibility(View.INVISIBLE);
            editBtn.setVisibility(View.VISIBLE);
        } else {
            tv_changePw.setVisibility(View.INVISIBLE);
            startBtn.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.INVISIBLE);
            previousXML="";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        init();
        chkPreviousXML();

        iv_photo.setOnClickListener(onClickListener);
        et_birthDate.setOnClickListener(onClickListener);
        tv_changePw.setOnClickListener(onClickListener);
        startBtn.setOnClickListener(onClickListener);
        editBtn.setOnClickListener(onClickListener);

        // 와글 회원가입 시
        Intent intent = getIntent();
        uId = intent.getStringExtra("uId");
        String loginType = intent.getStringExtra("LoginType");
        et_emailAddress.setText(uId);

        // 소셜 회원가입 시
        String userProfile = intent.getStringExtra("UserProfile");
        String userName = intent.getStringExtra("UserName");
        String userBirth = intent.getStringExtra("UserBirth");
        String userEmail = intent.getStringExtra("UserEmail");

        // 소셜 회원가입을 통해 들어왔을 경우, 회원정보 불러오기.
        if(previousXML.equals("") || previousXML == null){
            if(loginType.equals("NAVER") || loginType.equals("GOOGLE") || loginType.equals("KAKAO")){
                Glide.with(this)
                        .load(userProfile)
                        .apply(new RequestOptions().circleCrop())
                        .placeholder(R.drawable.ic_outline_emptyimage)
                        .into(iv_photo);
                iv_photo.setClickable(false);
                et_name.setText(userName);
                et_birthDate.setText(userBirth);
                et_emailAddress.setText(userEmail);
            }
        }

        // 정보수정을 통해 들어왔을 경우, 회원정보 불러오기.
        if(previousXML.equals("edit")){
            tv_changePw.setVisibility(View.VISIBLE);
            getmyInfo();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.myInfo_iv_photo:
                    showImagePicDialog();
                    break;
                case R.id.myInfo_et_birthDate:
                    chooseBirthDate();
                    break;
                case R.id.myInfo_tv_changePw:
                    changePw();
                    break;
                case R.id.myInfo_btn_start:
                    inputNewData();
                    Toast.makeText(MyInfoActivity.this, "회원님의 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.myInfo_btn_edit:
                    Toast.makeText(MyInfoActivity.this, "회원님의 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    editMyInfo();
                    finish();
                    break;

            }
        }
    };

    // ------------------------------------------------- 정보수정을 통해 들어왔을 경우, 회원정보 불러오기.--------------------------------------------------------
    private void getmyInfo(){

        uSeqno = Integer.toString(UserInfo.USEQNO);
        urlAddr = "http://192.168.0.178:8080/wagle/getMyInfo.jsp?";
        urlAddr += "uSeqno=" + uSeqno;
        connectGetMyInfo();
    }

    private void connectGetMyInfo() {
        try {
            JH_ObjectNetworkTask_MyInfo objectNetworkTask_myInfo = new JH_ObjectNetworkTask_MyInfo(MyInfoActivity.this, urlAddr);
            Object obj = objectNetworkTask_myInfo.execute().get();
            userInfo = (ArrayList<User>) obj;

            uSeqno = userInfo.get(0).getuSeqno();
            uId = userInfo.get(0).getuId();
            uEmail = userInfo.get(0).getuEmail();
            uName = userInfo.get(0).getuName();
            uImageName = userInfo.get(0).getuImageName();
            uBirthDate = userInfo.get(0).getuBirthDate();
            uLoginType = userInfo.get(0).getuLoginType();
            uPassword = userInfo.get(0).getuPassword();
            uDate = userInfo.get(0).getuDate();

        }catch (Exception e){
            e.printStackTrace();
        }
        setText();
    }

    private void setText(){
        if(uLoginType.equals("wagle")){
            Glide.with(this)
                    .load("http://192.168.0.82:8080/wagle/userImgs/" + uImageName)
                    .apply(new RequestOptions().circleCrop())
                    .placeholder(R.drawable.ic_outline_emptyimage)
                    .into(iv_photo);
        } else {
            Glide.with(this)
                    .load(uImageName)
                    .apply(new RequestOptions().circleCrop())
                    .placeholder(R.drawable.ic_outline_emptyimage)
                    .into(iv_photo);
            iv_photo.setClickable(false);
        }
        et_name.setText(uName);
        et_birthDate.setText(uBirthDate);
        et_emailAddress.setText(uEmail);
    }
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------


    private void changePw() {
        final LinearLayout ll = (LinearLayout) View.inflate(MyInfoActivity.this, R.layout.custom_newpw_sh, null);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MyInfoActivity.this);
        builder.setTitle("비밀번호 변경")
                .setView(ll)
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

        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newPw = ll.findViewById(R.id.newpw_pw);
                EditText newPwcheck = ll.findViewById(R.id.newpw_pwcheck);
                TextView newPwCmt = ll.findViewById(R.id.newpw_comment);

                String newPwStr = newPw.getText().toString().trim();
                String newPwCkStr = newPwcheck.getText().toString().trim();

                if (newPwStr.equals(newPwCkStr) && newPwStr.length() >= 6) {
                    uPassword = newPw.getText().toString().trim();
                    String JH_IP = "192.168.0.178";
                    urlAddr = "http://" + JH_IP + ":8080/wagle/changePw.jsp?";
                    urlAddr = urlAddr + "uSeqno=" + uSeqno + "&uId=" + uId + "&uEmail=" + uEmail + "&uName=" + uName + "&uImageName=" + uImageName + "&uBirthDate=" + uBirthDate + "&uPassword=" + uPassword;
                    try {
                        JH_VoidNetworkTask myInfoNetworkTask = new JH_VoidNetworkTask(MyInfoActivity.this, urlAddr);
                        myInfoNetworkTask.execute().get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(MyInfoActivity.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_LONG).show();
                } else if (newPwStr.length() < 6) {
                    newPwCmt.setText("비밀번호는 6자리 이상입니다.");
                    newPwCmt.setVisibility(View.VISIBLE);
                } else {
                    newPwCmt.setText("비밀번호를 다시 확인해주세요.");
                    newPwCmt.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void chooseBirthDate() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());

        int year = Integer.parseInt(yearFormat.format(date));
        int month = Integer.parseInt(monthFormat.format(date)) - 1;
        int day = Integer.parseInt(dayFormat.format(date));

        // DatePickerDialog (달력 팝업 띄우기)
        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog, listener, year, month, day);
        dialog.getDatePicker().setCalendarViewShown(false);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String yearStr = String.valueOf(year);
            yearStr = yearStr.substring(2);
            String monthOfYearStr = String.valueOf(monthOfYear+1);
            if(monthOfYear<10){
                monthOfYearStr = "0" + monthOfYearStr;
            }
            String dayOfMonthStr = String.valueOf(dayOfMonth);
            et_birthDate.setText(yearStr+monthOfYearStr+dayOfMonthStr);
        }
    };


    public boolean checkPermission() {
        String temp = "";
        //카메라 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.CAMERA + " ";
        }
        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }
        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }
        if (!TextUtils.isEmpty(temp)) {
            // 권한 요청 다이얼로그
            ActivityCompat.requestPermissions(this, temp.trim().split(" "), PERMISSION_REQUEST_CODE);
        } else {
            // 모두 허용 상태
            return true;
        }
        return false;
    }


    //권한에 대한 응답이 있을때 작동하는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //권한을 허용 했을 경우
        if (requestCode == PERMISSION_REQUEST_CODE) {
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 동의
                    if (mWhich == 0) {
                        pickFromCamera();
                    } else {
                        pickFromGallery();
                    }
                } else {
                    Toast.makeText(this, "권한 요청을 동의해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void showImagePicDialog() {
        String[] options = {"카메라에서 촬영", "갤러리에서 선택"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoActivity.this);
        builder.setTitle("이미지 등록");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (checkPermission()) {
                    if (which == 0) {
                        pickFromCamera();
                        mWhich = 0;
                    } else {
                        pickFromGallery();
                        mWhich = 1;
                    }
                }
            }
        });
        builder.create().show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                assert data != null;
                image_uri = data.getData();
            }

            Glide.with(this)
                    .load(image_uri.toString())
//                    .apply(new RequestOptions().circleCrop())
                    .placeholder(R.drawable.ic_outline_emptyimage)
                    .into(iv_photo);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }


    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }


    private void inputNewData() {
        String name = et_name.getText().toString().trim();
        String birthDate = et_birthDate.getText().toString().trim();
        String emailAddress = et_emailAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(birthDate)) {
            Toast.makeText(this, "생년월일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(emailAddress)) {
            Toast.makeText(this, "이메일주소를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else if (image_uri == null) {
            connectDB(name, birthDate, emailAddress, "", uId);
        } else {
            JH_ConnectFTP mConnectFTP = new JH_ConnectFTP(MyInfoActivity.this, "192.168.0.82", "host", "qwer1234", 25, image_uri, uId);
            String fileName = "";
            try {
                fileName = mConnectFTP.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connectDB(name, birthDate, emailAddress, fileName, uId);
        }
    }


    private void connectDB(String name, String birthDate, String emailAddress, String fileName, String uId) {

        String JH_IP = "192.168.0.178";
        urlAddr = "http://" + JH_IP + ":8080/wagle/saveMyInfo.jsp?";
        urlAddr = urlAddr + "uId=" + uId + "&uEmail=" + emailAddress + "&uName=" + name + "&uImageName=" + fileName + "&uBirthDate=" + birthDate;
        try {
            JH_VoidNetworkTask myInfoNetworkTask = new JH_VoidNetworkTask(MyInfoActivity.this, urlAddr);
            myInfoNetworkTask.execute().get();
            startActivity(new Intent(MyInfoActivity.this, LoginActivity.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void editMyInfo(){

        uName = et_name.getText().toString().trim();
        uEmail = et_emailAddress.getText().toString().trim();
        uBirthDate = et_birthDate.getText().toString().trim();

        if (TextUtils.isEmpty(uName)) {
            Toast.makeText(this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(uBirthDate)) {
            Toast.makeText(this, "생년월일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(uEmail)) {
            Toast.makeText(this, "이메일주소를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else if (image_uri == null) {
            connectEditDB(uName, uEmail, uBirthDate, "");
        } else {
            JH_ConnectFTP mConnectFTP = new JH_ConnectFTP(MyInfoActivity.this, "192.168.0.82", "host", "qwer1234", 25, image_uri, uId);
            String fileName = "";
            try {
                fileName = mConnectFTP.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connectEditDB(uName, uEmail, uBirthDate, fileName);
        }
    }

    private void connectEditDB(String name, String email, String birthDate, String fileName){
        urlAddr = "http://192.168.0.178:8080/wagle/editMyInfo.jsp?";
        urlAddr = urlAddr + "uSeqno=" + uSeqno + "&uId=" + uId + "&uEmail=" + email + "&uName=" + name + "&uImageName=" + fileName + "&uBirthDate=" + birthDate + "&uLoginType=" + uLoginType + "&uPassword=" + uPassword;
        try {
            JH_VoidNetworkTask myInfoNetworkTask = new JH_VoidNetworkTask(MyInfoActivity.this, urlAddr);
            myInfoNetworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}//----