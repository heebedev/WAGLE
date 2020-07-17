package com.androidlec.wagle.activity.wagleSub;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidlec.wagle.CS.Network.WCNetworkTask;
import com.androidlec.wagle.FindLocationActivity;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;

import java.text.DecimalFormat;
import java.util.Calendar;

public class AddNormWagleActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;

    /////////////////////////// 정규 와글//////////////////////////////
    private EditText wagleName, wagleStartD, wagleEndD, wagleFee, wagleDueD, waglePlace, wagleDetail, wagleAgreeRefund;
    private TextView wagleAddBookInfo, wagleRegister;

    // 화폐단위표시
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String result = "";

    private EditText calendarStatus;

    private void init() {

        /////////////////////////// 정규 와글//////////////////////////////
        wagleName = findViewById(R.id.et_addwagle_wagleName);
        wagleStartD = findViewById(R.id.et_addwagle_wagleStartDate);
        wagleEndD = findViewById(R.id.et_addwagle_wagleEndDate);
        wagleDueD = findViewById(R.id.et_addwagle_wagleDueDate);
        wagleFee = findViewById(R.id.et_addwagle_wagleFee);
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

        // 화폐단위표시
        wagleFee.addTextChangedListener(watcher);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wagle_norm);

        init();

    }

    //날짜 입력 클래스 선언
    Calendar SelectDate = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String newMonth, newDay;
            if (month + 1 < 10) {
                newMonth = "" + 0 + (month + 1);
            } else {
                newMonth = "" + (month + 1);
            }
            if (dayOfMonth < 10) {
                newDay = "" + 0 + dayOfMonth;
            } else {
                newDay = "" + dayOfMonth;
            }
            calendarStatus.setText(year + "." + newMonth + "." + newDay);
        }
    };

    View.OnClickListener pickAdateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.et_addwagle_wagleStartDate:
                    calendarStatus = wagleStartD;
                    new DatePickerDialog(AddNormWagleActivity.this, d,
                            SelectDate.get(Calendar.YEAR),
                            SelectDate.get(Calendar.MONTH),
                            SelectDate.get(Calendar.DAY_OF_MONTH)).show();
                    break;
                case R.id.et_addwagle_wagleEndDate:
                    calendarStatus = wagleEndD;
                    new DatePickerDialog(AddNormWagleActivity.this, d,
                            SelectDate.get(Calendar.YEAR),
                            SelectDate.get(Calendar.MONTH),
                            SelectDate.get(Calendar.DAY_OF_MONTH)).show();
                    break;
                case R.id.et_addwagle_wagleDueDate:
                    calendarStatus = wagleDueD;
                    new DatePickerDialog(AddNormWagleActivity.this, d,
                            SelectDate.get(Calendar.YEAR),
                            SelectDate.get(Calendar.MONTH),
                            SelectDate.get(Calendar.DAY_OF_MONTH)).show();
                    break;
            }
        }
    };  // 날짜 입력 클릭 리스너

    View.OnClickListener pickAplaceClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityForResult(new Intent(getApplicationContext(), FindLocationActivity.class), REQUEST_CODE);
        }
    };  // 장소 입력 클릭 리스너

    View.OnClickListener rgstClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_addwagle_wagleAddBookInfo:
                    break;
                case R.id.tv_addwagle_wagleRegister:
                    InputWagleCreateData();
                    break;
            }
        }
    }; // 책,와글 등록 클릭 리스너

    private void InputWagleCreateData() {
        if (TextUtils.isEmpty(wagleName.getText().toString().trim())) {
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            wagleName.setSelection(0);
        } else if (wagleStartD.getText().toString().trim().equals("시작 일자")) {
            Toast.makeText(this, "시작 일자를 선택해주세요.", Toast.LENGTH_SHORT).show();
            wagleStartD.performClick();
        } else if (wagleEndD.getText().toString().trim().equals("종료 일자")) {
            Toast.makeText(this, "종료 일자를 선택해주세요.", Toast.LENGTH_SHORT).show();
            wagleEndD.performClick();
        } else if (wagleDueD.getText().toString().trim().equals("등록 마감일")) {
            Toast.makeText(this, "등록 마감일을 선택해주세요.", Toast.LENGTH_SHORT).show();
            wagleDueD.performClick();
        } else {
            inputWagleCreateData();
        }
    }

    private void inputWagleCreateData() {
        String wcName = wagleName.getText().toString().trim();
        String wcType = "정규";
        String wcStartDate = wagleStartD.getText().toString().trim();
        String wcEndDate = wagleEndD.getText().toString().trim();
        String wcDueDate = wagleDueD.getText().toString().trim();
        String wcLocate;
        if (waglePlace.getText().toString().trim().equals("등록하기")) {
            wcLocate = "";
        } else {
            wcLocate = waglePlace.getText().toString().trim();
        }
        String wcEntryFee = wagleFee.getText().toString().trim();
        String wcWagleDetail = wagleDetail.getText().toString().trim();
        String wcWagleAgreeRefund = wagleAgreeRefund.getText().toString().trim();


        String urlAddr = "http://192.168.0.79:8080/wagle/csInputWagleCreateWAGLE.jsp?";
        urlAddr = urlAddr + "Moim_wmSeqno=" + UserInfo.MOIMSEQNO + "&User_uSeqno=" + UserInfo.USEQNO + "&WagleBook_wbSeqno=" + 1 +
                "&wcName=" + wcName + "&wcType=" + wcType + "&wcStartDate=" + wcStartDate +
                "&wcEndDate=" + wcEndDate + "&wcDueDate=" + wcDueDate +
                "&wcLocate=" + wcLocate + "&wcEntryFee=" + wcEntryFee +
                "&wcWagleDetail=" + wcWagleDetail + "&wcWagleAgreeRefund=" + wcWagleAgreeRefund;

//        urlAddr = urlAddr.replace(" ", "%20");
//        urlAddr = urlAddr.replace("\n", "%20");

        try {
            WCNetworkTask wcNetworkTask = new WCNetworkTask(AddNormWagleActivity.this, urlAddr);
            wcNetworkTask.execute().get();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String locationName = data.getStringExtra("locationName");
                String locationAddress = data.getStringExtra("locationAddress");

                if (locationName.equals(locationAddress)) {
                    waglePlace.setText(locationName);
                } else {
                    waglePlace.setText(locationName + "\n" + locationAddress);
                }
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(convertPxToDp(10), convertPxToDp(5), convertPxToDp(10), 0);
                waglePlace.setLayoutParams(lp);
                waglePlace.setPadding(10, 10, 10, 10);
            }
        }

    }

    public int convertPxToDp(float px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result)) {
                result = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",", "")));
                wagleFee.setText(result);
                wagleFee.setSelection(result.length());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}