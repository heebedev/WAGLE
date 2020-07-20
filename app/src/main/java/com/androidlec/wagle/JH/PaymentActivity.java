package com.androidlec.wagle.JH;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidlec.wagle.R;
import com.androidlec.wagle.networkTask.JH_ObjectNetworkTask_Payment;
import com.androidlec.wagle.networkTask.JH_VoidNetworkTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    final static String TAG = "Log check : ";
    String urlAddr;
    ArrayList<Payment> lists;
    PaymentAdapter adapter;

    FloatingActionButton addItemBtn;
    ListView lv_itemlist;

    String item;
    int price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        lv_itemlist = findViewById(R.id.payment_lv_itemlist);

        addItemBtn = findViewById(R.id.payment_btn_addItem);
        addItemBtn.setOnClickListener(onClickListener);

        // 리스트 가져오기.
        urlDivider("paymentList", 0, null,0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");
        // 리스트 가져오기.
        urlDivider("paymentList", 0, null,0);
        getTotal();

    }


    private void getTotal(){
        int total = 0;
        for (int i = 0 ; i < lists.size() ; i++) {
            total += lists.get(i).getPrice();
        }
        TextView tv_total = findViewById(R.id.payment_tv_total);
        tv_total.setText(total + "원");

        int memNo = 10;
        int ppp = total/memNo;
        TextView tv_PPP = findViewById(R.id.payment_tv_PricePerPerson);
        tv_PPP.setText(ppp + "원");
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // --------------- 대화상자 띄우기 ---------------------------------------------------------
            final LinearLayout linearLayout = (LinearLayout) View.inflate(PaymentActivity.this, R.layout.customdialog_payment_additem, null);
            new AlertDialog.Builder(PaymentActivity.this)
                    .setView(linearLayout) // 불러줌.
                    .setCancelable(true)
                    .setPositiveButton("추가하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 다이어로그 안에 있는 xml.
                            EditText et_item = linearLayout.findViewById(R.id.payment_et_item);
                            EditText et_price = linearLayout.findViewById(R.id.payment_et_price);
                            item = et_item.getText().toString().trim();
                            price = Integer.parseInt(et_price.getText().toString().trim());

                            urlDivider("wpItemAdd",0, item, price); // wpItemAdd()
                            Toast.makeText(PaymentActivity.this, "추가 되었습니다.", Toast.LENGTH_SHORT).show();

                            PaymentActivity.this.onResume();
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(PaymentActivity.this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setCancelable(false)
                    .show();
            // -------------------------------------------------------------------------------------

        }
    };


    private void urlDivider(String function, int wpSeqno, String wpItem, int wpPrice){
        String wcSeqno = "1"; // 임의로 절대값 넣음.
        switch(function){
            case "wpItemAdd":
                urlAddr = "http://192.168.0.178:8080/wagle/wpItemAdd.jsp?";
                urlAddr = urlAddr + "wcSeqno=" + wcSeqno + "&wpItem=" + item + "&wpPrice=" + price;
                connectDB("wpItemAdd");
                break;
            case "paymentList":
                urlAddr = "http://192.168.0.178:8080/wagle/paymentList.jsp?";
                urlAddr = urlAddr + "wcSeqno=" + wcSeqno;
                connectDB("paymentList");
                break;
            case "deleteItem":
                urlAddr = "http://192.168.0.178:8080/wagle/deleteItem.jsp?";
                urlAddr = urlAddr + "wpSeqno=" + wpSeqno;
                connectDB("deleteItem");
                break;

        }
    }


    private void connectDB(String function){
        try {
            switch (function){
                case "wpItemAdd":
                case "deleteItem":
                    JH_VoidNetworkTask voidNetworkTask = new JH_VoidNetworkTask(PaymentActivity.this, urlAddr);
                    voidNetworkTask.execute().get();
                    break;
                case "paymentList":
                    JH_ObjectNetworkTask_Payment networkTask = new JH_ObjectNetworkTask_Payment(PaymentActivity.this, urlAddr);
                    Object obj = networkTask.execute().get(); // 오브젝트로 받아야함.
                    lists = (ArrayList<Payment>) obj; // cast.
                    adapter = new PaymentAdapter(PaymentActivity.this, R.layout.customlayout_payment_listview, lists); // making adapter.
                    lv_itemlist.setAdapter(adapter);
//                    lv_itemlist.setOnItemClickListener(onItemClickListener);
                    lv_itemlist.setOnItemLongClickListener(onItemLongClickListener);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // ----------------- 꾹~ 롱클릭 이벤트 --------------------------------------------------------------------------------------------------
    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
//            click = 1;

            // --------------- 대화상자 띄우기 ---------------------------------------------------------
            new AlertDialog.Builder(PaymentActivity.this)
                    .setTitle("항목을 삭제하시겠습니까?")
                    .setCancelable(false)
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            click = 0;
                        }
                    })
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            urlDivider("deleteItem", lists.get(position).getWpSeqno(), null, 0); // position(번째) 값 입력하면서, 데이터 삭제하기 함수 실행.
//                            click = 1;
                            Toast.makeText(PaymentActivity.this, "해당 항목이 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                            PaymentActivity.this.onResume();
                        }
                    })
                    .show();
            // -------------------------------------------------------------------------------------
            return false;
        }
    };


}//----