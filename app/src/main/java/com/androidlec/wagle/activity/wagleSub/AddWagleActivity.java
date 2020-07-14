package com.androidlec.wagle.activity.wagleSub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.androidlec.wagle.R;

import java.text.DateFormat;
import java.util.Calendar;

public class AddWagleActivity extends AppCompatActivity {

    EditText wagleName, wagleStartD, wagleEndD, wagleDueD, waglePlace, wagleDetail, wagleAgreeRefund;
    TextView wagleAddBookInfo, wagleRegister;


    private void init() {
        wagleName = findViewById(R.id.et_addwagle_wagleName);
        wagleStartD = findViewById(R.id.et_addwagle_wagleStartDate);
        wagleEndD = findViewById(R.id.et_addwagle_wagleEndDate);
        wagleDueD = findViewById(R.id.et_addwagle_wagleDueDate);
        waglePlace = findViewById(R.id.et_addwagle_waglePlace);
        wagleDetail = findViewById(R.id.et_addwagle_wagleDetail);
        wagleAgreeRefund = findViewById(R.id.et_addwagle_wagleAgreeRefund);

        wagleAddBookInfo = findViewById(R.id.tv_addwagle_wagleAddBookInfo);
        wagleRegister = findViewById(R.id.tv_addwagle_wagleRegister);

        //날짜 입력 클릭 리스너
        wagleStartD.setOnClickListener(pickAdateClickListener);
        wagleEndD.setOnClickListener(pickAdateClickListener);
        wagleDueD.setOnClickListener(pickAdateClickListener);

        //장소 입력 클릭 리스너
        waglePlace.setOnClickListener(pickAplaceClickListener);

        //책등록, 와글 등록 클릭 리스너

        wagleAddBookInfo.setOnClickListener(rgstClickListener);
        wagleRegister.setOnClickListener(rgstClickListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wagle);

        init();

    }

    EditText.OnClickListener pickAdateClickListener = new View.OnClickListener() {

        //날짜 입력 클래스 선언
        DateFormat fmDateandTime = DateFormat.getDateTimeInstance();
        Calendar SelectDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SelectDate.set(Calendar.YEAR, year);
                SelectDate.set(Calendar.MONTH, month);
                SelectDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.et_addwagle_wagleStartDate:
                    new DatePickerDialog(AddWagleActivity.this, d,
                            SelectDate.get(Calendar.YEAR),
                            SelectDate.get(Calendar.MONTH),
                            SelectDate.get(Calendar.DAY_OF_MONTH)).show();
                    wagleStartD.setText(SelectDate.get(Calendar.YEAR) + "." + SelectDate.get(Calendar.MONTH) + "." + SelectDate.get(Calendar.DAY_OF_MONTH));
                    break;
                case R.id.et_addwagle_wagleEndDate:
                    new DatePickerDialog(AddWagleActivity.this, d,
                            SelectDate.get(Calendar.YEAR),
                            SelectDate.get(Calendar.MONTH),
                            SelectDate.get(Calendar.DAY_OF_MONTH)).show();
                    wagleEndD.setText(SelectDate.get(Calendar.YEAR) + "." + SelectDate.get(Calendar.MONTH) + "." + SelectDate.get(Calendar.DAY_OF_MONTH));
                    break;
                case R.id.et_addwagle_wagleDueDate:
                    new DatePickerDialog(AddWagleActivity.this, d,
                            SelectDate.get(Calendar.YEAR),
                            SelectDate.get(Calendar.MONTH),
                            SelectDate.get(Calendar.DAY_OF_MONTH)).show();
                    wagleDueD.setText(SelectDate.get(Calendar.YEAR) + "." + SelectDate.get(Calendar.MONTH) + "." + SelectDate.get(Calendar.DAY_OF_MONTH));
                    break;
            }
        }
    };  // 날짜 입력 클릭 리스너

    EditText.OnClickListener pickAplaceClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    };  // 장소 입력 클릭 리스너

    TextView.OnClickListener rgstClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



        }
    }; // 책,와글 등록 클릭 리스너
}