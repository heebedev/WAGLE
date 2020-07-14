package com.androidlec.wagle.activity.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.widget.Toast;

import com.androidlec.wagle.HomeActivity;
import com.androidlec.wagle.R;
import com.androidlec.wagle.networkTask.JH_ConnectFTP;
import com.androidlec.wagle.networkTask.JH_VoidNetworkTask;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MyInfoActivity extends AppCompatActivity {

    EditText et_name, et_birthDate, et_emailAddress;
    ImageView iv_photo;
    Button startBtn;

    private int mWhich;
    private Uri image_uri;

    // 카메라 관련
    private static final int PERMISSION_REQUST_CODE = 100;
    private static final int IMAGE_PICK_CAMERA_CODE = 101;
    private static final int IMAGE_PICK_GALLERY_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        et_name      = findViewById(R.id.myInfo_et_name);
        et_birthDate = findViewById(R.id.myInfo_et_birthDate);
        et_emailAddress = findViewById(R.id.myInfo_et_EmailAddress);

        iv_photo     = findViewById(R.id.myInfo_iv_photo);
        startBtn     = findViewById(R.id.myInfo_btn_start);

        et_birthDate.setOnClickListener(onClickListener);
        iv_photo.setOnClickListener(onClickListener);
        startBtn.setOnClickListener(onClickListener);

        Intent intent = getIntent();
        et_emailAddress.setText(intent.getStringExtra("uId"));
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.myInfo_iv_photo:
                    showImagePicDialog();
                    break;
                case R.id.myInfo_et_birthDate:
                    chooseBirthDate();
                    break;
                case R.id.myInfo_btn_start:
//                    saveMyInfo();
                    startActivity(new Intent(MyInfoActivity.this, HomeActivity.class));
                    break;
            }
        }
    };


    private void chooseBirthDate(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());

        int year = Integer.parseInt(yearFormat.format(date)) - 25;
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
            et_birthDate.setText(year + "년 " + monthOfYear + "월 " + dayOfMonth + "일");
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
            ActivityCompat.requestPermissions(this, temp.trim().split(" "),PERMISSION_REQUST_CODE);
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
        if (requestCode == PERMISSION_REQUST_CODE) {
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 동의
                    if(mWhich == 0){
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
                if(checkPermission()){
                    if(which == 0){
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
        //String uId = LoginId.getLoginId(); 임시..
        String uId = "jong@naver.com";

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(birthDate)) {
            Toast.makeText(this, "생년월일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(emailAddress)) {
            Toast.makeText(this, "이메일주소를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else if (image_uri == null) {
            connectDB(name, birthDate, emailAddress, "", uId);
        } else {
            JH_ConnectFTP mConnectFTP = new JH_ConnectFTP(MyInfoActivity.this, "192.168.0.82", "host", "qwer1234", 25, image_uri);
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
        String urlAddr = "http://192.168.0.178:8080/wagle/saveMyInfo.jsp?";
        urlAddr = urlAddr + "name=" + name + "&birthDate=" + birthDate + "&emailAddress=" + emailAddress + "&fileName=" + fileName + "&uId=" + uId;
        try {
            JH_VoidNetworkTask myInfoNetworkTask = new JH_VoidNetworkTask(MyInfoActivity.this, urlAddr);
            myInfoNetworkTask.execute().get();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}//----