package com.androidlec.wagle.activity.wagleSub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidlec.wagle.CS.Permission;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.jhj.Jhj_FTPConnect;
import com.androidlec.wagle.network_sh.NetworkTask_CRUD;
import com.androidlec.wagle.network_sh.NetworkTask_GetInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddBookActivity extends Activity {

    private EditText bookName, bookWriter, bookPage, bookIntro, bookData;
    private TextView cancleBtn, registerBtn;
    private LinearLayout addBookImage;
    private ImageView bookImage;

    final static String jspIP = "192.168.0.138";
    final static String IP = "192.168.0.82";

    // 카메라 관련
    private static final int PERMISSION_REQUST_CODE = 100;
    private static final int IMAGE_PICK_CAMERA_CODE = 101;
    private static final int IMAGE_PICK_GALLERY_CODE = 102;
    private Uri image_uri;
    private Permission permission;

    String bkname, bkwriter, bkpage, bkintro, bkdata;


    private void init() {
        bookName = findViewById(R.id.addbookinfo_et_bookname);
        bookWriter = findViewById(R.id.addbookinfo_et_bookwriter);
        bookPage = findViewById(R.id.addbookinfo_et_bookmaxpage);
        bookIntro = findViewById(R.id.addbookinfo_et_bookinfo);
        bookData = findViewById(R.id.addbookinfo_et_bookdata);

        cancleBtn = findViewById(R.id.addbookinfo_tv_cancleBtn);
        registerBtn = findViewById(R.id.addbookinfo_tv_registerBtn);

        addBookImage = findViewById(R.id.addbookinfo_ll_addbookimage);
        bookImage = findViewById(R.id.addbookinfo_iv_bookImage);

        addBookImage.setOnClickListener(addBookClickListener);
        cancleBtn.setOnClickListener(onClickListener);
        registerBtn.setOnClickListener(onClickListener);

        permission = new Permission(this);
    }



    private void showImagePicDialog() {
        String[] options = {"카메라에서 촬영", "갤러리에서 선택"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddBookActivity.this);
        builder.setTitle("이미지 등록");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (!permission.checkCameraPermission()) {
                        permission.requestCameraPermission();
                    } else {
                        pickFromCamera();
                    }
                } else if (which == 1) {
                    if (!permission.checkStoragePermission()) {
                        permission.requestStoragePermission();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    } // 이미지 선택 다이얼로그

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    } // 카메라에서 이미지선택

    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    } // 갤러리에서 이미지선택

    private void uploadProfileCoverPhoto(Uri uri) {
        Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.ic_baseline_crop_din_24)
                .into(bookImage);
    } // 이미지 보이기

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Permission.CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        pickFromCamera();
                    } else {
                        Toast.makeText(AddBookActivity.this, "카메라 권한을 동의해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case Permission.STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(AddBookActivity.this, "저장공간 권한을 동의해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    } // 권한에 대한 응답이 있을때 작동하는 함수




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_PICK_GALLERY_CODE:
                    image_uri = data.getData();
                case IMAGE_PICK_CAMERA_CODE:
                    uploadProfileCoverPhoto(image_uri);
                    break;
            }
        }

        // Check which request we're responding to
        if (requestCode == 1001) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    image_uri = data.getData();

                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(image_uri);
                    Bitmap img = BitmapFactory.decodeStream(in);

                    in.close();
                    // 이미지 표시
                    bookImage.setImageBitmap(img);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        init();
    }


    //이미지 등록을 위한 클릭 리스너
    LinearLayout.OnClickListener addBookClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showImagePicDialog();
            Toast.makeText(AddBookActivity.this, "이미지가 등록되었습니다.", Toast.LENGTH_SHORT).show();
        }
    };

    TextView.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addbookinfo_tv_registerBtn :

                    if(checkContent()) {

                        // 현재시간을 msec 으로 구한다.
                        long now = System.currentTimeMillis();
                        // 현재시간을 date 변수에 저장한다.
                        Date date = new Date(now);
                        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
                        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                        // nowDate 변수에 값을 저장한다.
                        String formatDate = sdfNow.format(date);

                        String imgName = bkname + formatDate + ".jpg";

                        connectionFTP(imgName);

                        String urlAddr = "http://" + jspIP + ":8080/test/wagle_bookinsert.jsp?title=" + bkname + "&writer=" + bkwriter + "&maxpage=" + bkpage + "&intro=" + bkintro + "&data=" + bkdata + "&imgName=" + imgName;
                        connectionInsertData(urlAddr);


                        urlAddr = "http://" + jspIP + ":8080/test/wagle_bookseq.jsp?imgName=" + imgName;
                        UserInfo.WAGLEBOOKSEQ = connectionGetData(urlAddr);


                        finish();

                    }

                    break;
                case R.id.addbookinfo_tv_cancleBtn :
                    finish();
                    break;
            }
        }
    };

    private void connectionFTP(String imgName) {
        try {
            // FTP 접속
            Jhj_FTPConnect connectFTP = new Jhj_FTPConnect(AddBookActivity.this, IP, "host", "qwer1234", 25, image_uri, imgName, "/bookImgs");
            connectFTP.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkContent() {
        boolean result = false;

        bkname = bookName.getText().toString().trim();
        bkwriter = bookWriter.getText().toString().trim();
        bkpage = bookPage.getText().toString().trim();
        bkintro = bookIntro.getText().toString().trim();
        bkdata = bookData.getText().toString().trim();

        if (bkname.length() == 0) {
            Toast.makeText(AddBookActivity.this, "책 이름을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else if (bkwriter.length() == 0) {
            Toast.makeText(AddBookActivity.this, "저자를 입력해주세요", Toast.LENGTH_SHORT).show();
        } else if (bkpage.length() == 0 || Integer.parseInt(bkpage) > 10000) {
            Toast.makeText(AddBookActivity.this, "페이지 수를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }


    private void connectionInsertData(String url) {
        try {
            NetworkTask_CRUD networkTask = new NetworkTask_CRUD(AddBookActivity.this, url);
            Object obj = networkTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String connectionGetData(String url) {
        String result = "0";

        try {
            NetworkTask_GetInfo networkTask = new NetworkTask_GetInfo(AddBookActivity.this, url, "bookseq");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


}