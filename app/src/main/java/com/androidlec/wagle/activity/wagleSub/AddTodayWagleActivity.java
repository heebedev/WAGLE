package com.androidlec.wagle.activity.wagleSub;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidlec.wagle.CS.Network.CSNetworkTask;
import com.androidlec.wagle.CS.Network.WCNetworkTask;
import com.androidlec.wagle.CS.Activities.FindLocationActivity;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;

import net.daum.android.map.MapActivity;

import java.text.DecimalFormat;
import java.util.Calendar;

public class AddTodayWagleActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;

    /////////////////////////// 정규 와글//////////////////////////////
    private EditText wagleName, wagleDetail;
    private TextView wagleRegister, wagleDate, wagleDueD, waglePlace;

    // 화폐단위표시
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String result = "";

    private TextView calendarStatus;

    //location permission and values
    private static final int LOCATION_REQUEST_CODE = 100;
    private String[] locationPermissions;

    private void init() {

        /////////////////////////// 정규 와글//////////////////////////////
        wagleName = findViewById(R.id.et_addwagletoday_wagleName);
        wagleDate = findViewById(R.id.et_addwagletoday_wagleDate);

        wagleDueD = findViewById(R.id.et_addwagletoday_wagleDueDate);

        waglePlace = findViewById(R.id.et_addwagletoday_waglePlace);
        wagleDetail = findViewById(R.id.et_addwagletoday_wagleDetail);

        wagleRegister = findViewById(R.id.tv_addwagletoday_wagleRegister);


        //날짜 입력 클릭 리스너
        wagleDate.setOnClickListener(pickAdateClickListener);
        wagleDueD.setOnClickListener(pickAdateClickListener);

        //장소 입력 클릭 리스너
        waglePlace.setOnClickListener(pickAplaceClickListener);

        //책등록, 와글 등록 클릭 리스너
        wagleRegister.setOnClickListener(rgstClickListener);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wagle_today);

        //init permissions array
        locationPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

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
                case R.id.et_addwagletoday_wagleDate:
                    calendarStatus = wagleDate;
                    new DatePickerDialog(AddTodayWagleActivity.this, d,
                            SelectDate.get(Calendar.YEAR),
                            SelectDate.get(Calendar.MONTH),
                            SelectDate.get(Calendar.DAY_OF_MONTH)).show();
                    break;
                case R.id.et_addwagletoday_wagleDueDate:
                    calendarStatus = wagleDueD;
                    new DatePickerDialog(AddTodayWagleActivity.this, d,
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

            if(checkLocationPermission()){
                startActivityForResult(new Intent(getApplicationContext(), FindLocationActivity.class), REQUEST_CODE);
            } else {
                requestLocationPermission();
            }
        }
    };  // 장소 입력 클릭 리스너

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                (PackageManager.PERMISSION_GRANTED);
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, locationPermissions, LOCATION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (locationAccepted) {
                    startActivity(new Intent(AddTodayWagleActivity.this, FindLocationActivity.class));
                    finish();
                } else {
                    //permission denied
                    Toast.makeText(this, "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    View.OnClickListener rgstClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                    case R.id.tv_addwagletoday_wagleRegister:
                    InputWagleCreateData();
                    break;
            }
        }
    }; // 책,와글 등록 클릭 리스너

    private void InputWagleCreateData() {
        if (TextUtils.isEmpty(wagleName.getText().toString().trim())) {
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            wagleName.setSelection(0);
        } else if (wagleDate.getText().toString().trim().equals("모임 일자")) {
            Toast.makeText(this, "모임 일자를 선택해주세요.", Toast.LENGTH_SHORT).show();
            wagleDate.performClick();
        } else if (wagleDueD.getText().toString().trim().equals("신청 마감일")) {
            Toast.makeText(this, "신청 마감일을 선택해주세요.", Toast.LENGTH_SHORT).show();
            wagleDueD.performClick();
        } else {
            inputWagleCreateData();
        }
    }

    private void inputWagleCreateData() {
        String wcName = wagleName.getText().toString().trim();
        String wcType = "투데이";
        String wcStartDate = wagleDate.getText().toString().trim();
        String wcEndDate = wagleDate.getText().toString().trim();
        String wcDueDate = wagleDueD.getText().toString().trim();
        String wcLocate;
        if (waglePlace.getText().toString().trim().equals("와글 장소를 등록해주세요.")) {
            wcLocate = "";
        } else {
            wcLocate = waglePlace.getText().toString().trim();
        }
        String wcEntryFee = "0";
        String wcWagleDetail = wagleDetail.getText().toString().trim();
        String wcWagleAgreeRefund = "today";


        createWaglenetwork(wcName, wcType, wcStartDate, wcEndDate, wcDueDate, wcLocate, wcEntryFee, wcWagleDetail, wcWagleAgreeRefund);

    }

    private void createWaglenetwork(String wcName, String wcType, String wcStartDate, String wcEndDate, String wcDueDate, String wcLocate, String wcEntryFee, String wcWagleDetail, String wcWagleAgreeRefund) {
        String urlAddr = "http://192.168.0.79:8080/wagle/csInputWagleCreateWAGLE.jsp?";
        urlAddr = urlAddr + "Moim_wmSeqno=" + UserInfo.MOIMSEQNO + "&User_uSeqno=" + UserInfo.USEQNO + "&WagleBook_wbSeqno=" + 1 +
                "&wcName=" + wcName + "&wcType=" + wcType + "&wcStartDate=" + wcStartDate +
                "&wcEndDate=" + wcEndDate + "&wcDueDate=" + wcDueDate +
                "&wcLocate=" + wcLocate + "&wcEntryFee=" + wcEntryFee +
                "&wcWagleDetail=" + wcWagleDetail + "&wcWagleAgreeRefund=" + wcWagleAgreeRefund;

//        urlAddr = urlAddr.replace(" ", "%20");
//        urlAddr = urlAddr.replace("\n", "%20");

        try {
            WCNetworkTask wcNetworkTask = new WCNetworkTask(AddTodayWagleActivity.this, urlAddr);
            String seq = wcNetworkTask.execute().get();
            inputWagleUserNetwork(seq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inputWagleUserNetwork(String seq) {
        String urlAddr = "http://192.168.0.79:8080/wagle/csInputWagleUserWAGLE.jsp?";
        urlAddr = urlAddr + "User_uSeqno=" + UserInfo.USEQNO + "&wcSeqno=" + seq;

        try {
            CSNetworkTask networkTask = new CSNetworkTask(AddTodayWagleActivity.this, urlAddr);
            networkTask.execute();
            setResult(Activity.RESULT_OK);
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


}