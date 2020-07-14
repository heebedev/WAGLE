package com.androidlec.wagle;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.androidlec.wagle.jhj.Jhj_FTPConnect;
import com.androidlec.wagle.jhj.Jhj_Make_Moim_Spinner_Adapter;
import com.androidlec.wagle.jhj.Jhj_MySql_Insert_NetworkTask;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MakeMoimActivity extends Activity {

    final static String TAG = "MakeMoimActivity";
    final static String IP = "192.168.0.82";

    Uri file = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_moim);

        // ------------------------------------------------------------------
        // 버튼, 버튼이벤트 등록
        // ------------------------------------------------------------------

        TextView make_Moim_Text_ImgSelect = findViewById(R.id.tvaddMoimImage);
        TextView make_Moim_Text_Btn_Cancle = findViewById(R.id.tvMakemoimCancle);
        TextView make_Moim_Text_Btn_Next = findViewById(R.id.tvMakemoimNext);

        TextView make_Moim_Text_Btn_Previous = findViewById(R.id.make_moim_viewFliper_Previous);
        TextView make_Moim_Text_Btn_Complete = findViewById(R.id.make_moim_viewFliper_Complete);

        make_Moim_Text_ImgSelect.setOnClickListener(make_moim_Text_OnClickListener);
        make_Moim_Text_Btn_Cancle.setOnClickListener(make_moim_Text_OnClickListener);
        make_Moim_Text_Btn_Next.setOnClickListener(make_moim_Text_OnClickListener);

        make_Moim_Text_Btn_Previous.setOnClickListener(make_moim_Text_OnClickListener);
        make_Moim_Text_Btn_Complete.setOnClickListener(make_moim_Text_OnClickListener);

        // ------------------------------------------------------------------
        // 버튼, 버튼이벤트 등록 끝
        // ------------------------------------------------------------------


        // ------------------------------------------------------------------
        // 스피너, 스피너 아이템 생성
        // ------------------------------------------------------------------

        Spinner make_moim_Spinner = findViewById(R.id.make_moim_viewFliper_Spinner);
        String[] data = {"독서", "영화", "기타"};
        Jhj_Make_Moim_Spinner_Adapter adapter = new Jhj_Make_Moim_Spinner_Adapter(MakeMoimActivity.this, R.layout.jhj_make_moim_spinner_layout, data);
        make_moim_Spinner.setAdapter(adapter);

        // ------------------------------------------------------------------
        // 스피너, 스피너 아이템 끝
        // ------------------------------------------------------------------
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check which request we're responding to
        if (requestCode == 1001) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    file = data.getData();

                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(file);
                    Bitmap img = BitmapFactory.decodeStream(in);

                    in.close();
                    // 이미지 표시
                    ImageView make_Moim_ImageView = findViewById(R.id.iv_makemoim_moimimage);
                    make_Moim_ImageView.setImageBitmap(img);

                    // ViewFliper 이미지 표시
                    ImageView make_Moim_ViewFliper_Image = findViewById(R.id.make_moim_viewFliper_ImageView);
                    make_Moim_ViewFliper_Image.setImageBitmap(img);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    TextView.OnClickListener make_moim_Text_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewFlipper make_moim_ViewFliper = findViewById(R.id.make_moim_viewFliper);

            switch (v.getId()) {
                // Image 선택 버튼
                case R.id.tvaddMoimImage :
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 1001);
                    break;
                // 취소 버튼
                case R.id.tvMakemoimCancle :
                    finish();
                    break;
                // 다음 화면 이동
                case R.id.tvMakemoimNext :
                    EditText make_Moim_Edit_Name = findViewById(R.id.etMoimName);

                    // 모임 이름 체크
                    if (make_Moim_Edit_Name.getText().toString().replace(" ", "").equals("")) {
                        make_Moim_Edit_Name.requestFocus();
                        return;
                    }

                    TextView make_Moim_ViewFliper_Title = findViewById(R.id.make_moim_viewFliper_Text_Title);
                    make_Moim_ViewFliper_Title.setText(make_Moim_Edit_Name.getText().toString());

                    // 다음 화면으로 이동
                    make_moim_ViewFliper.showNext();
                    break;
                // 전 화면으로 이동
                case R.id.make_moim_viewFliper_Previous :
                    make_moim_ViewFliper.showPrevious();
                    break;
                // 모임 개설
                case R.id.make_moim_viewFliper_Complete :
                    moimInsert();
                    startActivity(new Intent(MakeMoimActivity.this, MainMoimListActivity.class));
                    break;
            }
        }
    };

    // ------------------------------------------------------------------
    // DB Inert
    // ------------------------------------------------------------------
    public void moimInsert() {

        // 입력받은 데이터 준비.
        EditText make_Moim_Edit_Name = findViewById(R.id.etMoimName);
        EditText make_Moim_ViewFliper_Intro = findViewById(R.id.make_moim_viewFliper_Intro);
        Spinner make_moim_Spinner = findViewById(R.id.make_moim_viewFliper_Spinner);

        String name = make_Moim_Edit_Name.getText().toString();
        String intro = make_Moim_ViewFliper_Intro.getText().toString();
        String spinnerSeleted = make_moim_Spinner.getSelectedItem().toString();
        // ----------------

        // 현재시간을 msec 으로 구한다.
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        // nowDate 변수에 값을 저장한다.
        String formatDate = sdfNow.format(date);

        // 지워야할것
        int seqno = UserInfo.uSeqno;

        String imgName = name + formatDate + ".jpg";

        connectionFTP(imgName);

        // Get 방식 URL 세팅
        String urlAddr = "http://" + IP + ":8080/wagle/moim_insert.jsp?userSeqno=" + seqno + "&title=" + name + "&subject=" + spinnerSeleted + "&intro=" + intro + "&imgName=" + imgName;
        connectionInsertData(urlAddr);
    }

    private void connectionFTP(String imgName) {
        try {
            // FTP 접속
            Jhj_FTPConnect connectFTP = new Jhj_FTPConnect(MakeMoimActivity.this, IP, "host", "qwer1234", 25, file, imgName);
            connectFTP.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connectionInsertData(String urlAddr) {
        // Jsp 서버 전송
        try {
            Jhj_MySql_Insert_NetworkTask insNetworkTask = new Jhj_MySql_Insert_NetworkTask(MakeMoimActivity.this, urlAddr);
            insNetworkTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ------------------------------------------------------------------
    // DB Inert End
    // ------------------------------------------------------------------

}