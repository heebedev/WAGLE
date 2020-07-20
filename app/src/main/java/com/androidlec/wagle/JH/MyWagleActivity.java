package com.androidlec.wagle.JH;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidlec.wagle.R;

public class MyWagleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wagle);

        init();
        getData();

    }

    private void init() {
        // 독후감 파트.
        Button btn_bookreportAdd = findViewById(R.id.mywagle_btn_bookreportAdd);
        ProgressBar progressBar = findViewById(R.id.mywagle_pb_progress);
        Button btn_suggestionAdd = findViewById(R.id.mywagle_btn_suggestionAdd);
        ListView listView = findViewById(R.id.mywagle_lv_bookreport);
        btn_bookreportAdd.setOnClickListener(onClickListener);
        btn_suggestionAdd.setOnClickListener(onClickListener);

        // 갤러리 파트.
        Button btn_galleryAdd = findViewById(R.id.mywagle_btn_galleryAdd);
        ImageView iv_gallery1 = findViewById(R.id.mywagle_iv_gallery1);
        ImageView iv_gallery2 = findViewById(R.id.mywagle_iv_gallery2);
        ImageView iv_gallery3 = findViewById(R.id.mywagle_iv_gallery3);
        TextView tv_galleryPlus = findViewById(R.id.mywagle_tv_galleryPlus);
        btn_galleryAdd.setOnClickListener(onClickListener);
        tv_galleryPlus.setOnClickListener(onClickListener);

        // 정산 파트.
        Button btn_paymentAdd = findViewById(R.id.mywagle_btn_paymentAdd);
        btn_paymentAdd.setOnClickListener(onClickListener);
    }


    private void getData(){

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;

            switch (view.getId()){
                case R.id.mywagle_btn_bookreportAdd:
                    break;
                case R.id.mywagle_btn_suggestionAdd:
                    break;
                case R.id.mywagle_btn_galleryAdd:
                    break;
                case R.id.mywagle_tv_galleryPlus:
                    break;
                case R.id.mywagle_btn_paymentAdd:
                    intent = new Intent(MyWagleActivity.this, PaymentActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };



}//----
