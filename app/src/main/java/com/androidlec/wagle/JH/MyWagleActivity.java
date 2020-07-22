package com.androidlec.wagle.JH;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidlec.wagle.R;
import com.androidlec.wagle.networkTask.JH_IntNetworkTask;
import com.androidlec.wagle.networkTask.JH_ObjectNetworkTask_Payment;
import com.androidlec.wagle.networkTask.JH_ObjectNetworkTask_Progress;
import com.androidlec.wagle.networkTask.JH_VoidNetworkTask;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyWagleActivity extends AppCompatActivity {


    final static String TAG = "Log check : ";
    String urlAddr;
    ListView lv_itemlist;
    String item;
    int price, paymentcnt;
    PaymentAdapter adapter;
    ArrayList<Payment> lists;
    ArrayList<Progress> progressdata;
    ArrayList<ImageView> imageViews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wagle);

        getProfileReadPage();
        init();
        getData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 리스트 가져오기.
//        urlDivider("paymentList", 0, null,0);
//        getTotal();
    }


    private void init() {
        // 독후감 파트.
        Button btn_bookreportAdd = findViewById(R.id.mywagle_btn_bookreportAdd);
        Button btn_suggestionAdd = findViewById(R.id.mywagle_btn_suggestionAdd);
        ListView listView = findViewById(R.id.mywagle_lv_bookreport);
        btn_bookreportAdd.setOnClickListener(onClickListener);
        btn_suggestionAdd.setOnClickListener(onClickListener);

        // 프로그레스바 파트.
        initProgressBar();

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
        LinearLayout layout = findViewById(R.id.payment_ll_paymentActivity);

        switch (paymentCnt()) {
            case 2:
                btn_paymentAdd.setVisibility(View.VISIBLE);
                layout.setVisibility(View.INVISIBLE);
                break;
            case 1:
                btn_paymentAdd.setVisibility(View.INVISIBLE);
                layout.setVisibility(View.VISIBLE);
                break;
            default:
                btn_paymentAdd.setVisibility(View.INVISIBLE);
                layout.setVisibility(View.INVISIBLE);
                break;
        }

        lv_itemlist = findViewById(R.id.payment_lv_itemlist);
        FloatingActionButton addItemBtn = findViewById(R.id.payment_btn_addItem);

        btn_paymentAdd.setOnClickListener(onClickListener);
        addItemBtn.setOnClickListener(onClickListener);
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
                    break;
                case R.id.payment_btn_addItem:
                    popupAddItem();
                    break;
            }
        }
    };


    private void initProgressBar(){

        RelativeLayout rl_images = findViewById(R.id.mywagle_rl_images);
        ProgressBar pb_book = findViewById(R.id.mywagle_pb_book);
        Button btn_move = findViewById(R.id.mywagle_btn_move);
        EditText et_wpReadPage = findViewById(R.id.mywagle_et_wpReadPage);

        int deviceWidth = getApplication().getResources().getDisplayMetrics().widthPixels; // 디바이스 최대 크기를 구한다.
        pb_book.setMax(deviceWidth); // 사용할 프로그레스바의 최대크기를 디바이스 최대크기로 지정한다.

        imageViews = new ArrayList<ImageView>();
        for (int i = 0; i < progressdata.size(); i++) {

            ImageView iv = new ImageView(getApplicationContext());
            imageViews.add(iv); // Initialize a new ImageView widget

            Log.v(TAG, "-------"+progressdata.get(i).getuSeqno());

            imageViews.get(i).setId(progressdata.get(i).getuSeqno());

            if(progressdata.get(i).getuLoginType().equals("wagle")){
                Glide.with(this)
                        .load("http://192.168.0.82:8080/wagle/userImgs/" + progressdata.get(i).getuImageName())
//                    .apply(new RequestOptions().circleCrop())
                        .override(30,30)
                        .placeholder(R.drawable.ic_outline_emptyimage)
                        .into(imageViews.get(i));
            } else {
                Glide.with(this)
                        .load(progressdata.get(i).getuImageName())
//                    .apply(new RequestOptions().circleCrop())
                        .placeholder(R.drawable.ic_outline_emptyimage)
                        .into(imageViews.get(i));
            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                iv.setImageDrawable(getDrawable(R.drawable.img_0214)); // Set an image for ImageView
//            }

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); // Create layout parameters for ImageView
            layoutParams.addRule(RelativeLayout.ABOVE, pb_book.getId()); // Add rule to layout parameters // Add the ImageView below to Button
            imageViews.get(i).setLayoutParams(layoutParams); // Add layout parameters to ImageView
            rl_images.addView(imageViews.get(i)); // Finally, add the ImageView to layout
            imageViews.get(i).requestLayout();
            imageViews.get(i).getLayoutParams().height = dpToPx(30, rl_images); // Apply the new height for ImageView programmatically
            imageViews.get(i).getLayoutParams().width = dpToPx(30, rl_images);
            imageViews.get(i).setScaleType(ImageView.ScaleType.FIT_XY); // Set the scale type for ImageView image scaling

            // 유저의 읽은 페이지 수만큼 이미지 이동.
            int wpReadPage = progressdata.get(i).getWpReadPage();
            Log.v(TAG, String.valueOf(progressdata.get(i).getWpReadPage()));
            int wbMaxPage = getwbMaxPage(); // 필요 할당량 (ex 책의 최대 페이지)
            int movePage = wbMaxPage / wpReadPage; // 필요 할당량 에서 움직일 만큼의 비율을 구한다. (책의 총 페이지 / 읽은 책의 양)
            int moveProgressBar = deviceWidth / movePage; // 비율 구한것을 화면 기기에 넣는다.
                if(moveProgressBar >= wbMaxPage){
                    imageViews.get(i).setX(deviceWidth - imageViews.get(i).getWidth()); // 맨 오른쪽 으로 이동
                }else{
                    imageViews.get(i).setX(moveProgressBar);
                }
                pb_book.incrementProgressBy(moveProgressBar); // 프로그레스바 비율에따른 이동
        }

        btn_move.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deviceWidth = getApplication().getResources().getDisplayMetrics().widthPixels; // 디바이스 최대 크기를 구한다.
                pb_book.setMax(deviceWidth); // 사용할 프로그레스바의 최대크기를 디바이스 최대크기로 지정한다.
                int wpReadPage = Integer.parseInt(et_wpReadPage.getText().toString()); // 움직일 만큼 EditText로 입력받는다. (읽은 책의 양)
                int maxpage = getwbMaxPage(); // 필요 할당량 (ex 책의 최대 페이지)
                Log.v(TAG, String.valueOf(maxpage));
                int movePage = maxpage / wpReadPage; // 필요 할당량 에서 움직일 만큼의 비율을 구한다. (책의 총 페이지 / 읽은 책의 양)
                int moveProgressBar = deviceWidth / movePage; // 비율 구한것을 화면 기기에 넣는다.

//                if(moveProgressBar >= maxpage){
//                    imageCharacter2.setX(deviceWidth - imageCharacter2.getWidth()); // 맨 오른쪽 으로 이동
//                }else{
//                    imageCharacter2.setX(moveProgressBar);
//                }
//                pb_book.incrementProgressBy(moveProgressBar); // 프로그레스바 비율에따른 이동
            }
        });
    }


    public static int dpToPx(int dp, RelativeLayout context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }


    private void getData(){
        // 리스트 가져오기.
//        urlDivider("paymentList", 0, null,0);
    }





    private void getProfileReadPage() {
        int wcSeqno = 1; // 임시 절대값.
        urlAddr = "http://192.168.0.178:8080/wagle/getProfileReadPage.jsp?";
        urlAddr = urlAddr + "wcSeqno=" + wcSeqno;
        try {
            JH_ObjectNetworkTask_Progress networkTask6 = new JH_ObjectNetworkTask_Progress(MyWagleActivity.this, urlAddr);
            Object obj = networkTask6.execute().get();
            progressdata = (ArrayList<Progress>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private int getwbMaxPage() {
        int wbMaxPage = 0;
        int wcSeqno = 1; // 임시 절대값.
        urlAddr = "http://192.168.0.178:8080/wagle/getTotalPage.jsp?";
        urlAddr = urlAddr + "wcSeqno=" + wcSeqno;
        try {
            JH_IntNetworkTask networkTask4 = new JH_IntNetworkTask(MyWagleActivity.this, urlAddr);
            wbMaxPage = networkTask4.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return wbMaxPage;
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


    private int paymentCnt(){
        int wcSeqno = 1; // 임시 절대값.
        paymentcnt = 3;
        urlAddr = "http://192.168.0.178:8080/wagle/paymentCnt.jsp?";
        urlAddr = urlAddr + "wcSeqno=" + wcSeqno;
        connectDB("paymentCnt");
        return paymentcnt;
    }


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
                case "paymentCnt":
                    JH_IntNetworkTask networkTask3 = new JH_IntNetworkTask(MyWagleActivity.this, urlAddr);
                    paymentcnt = networkTask3.execute().get();
                    break;
                case "wpItemAdd":
                case "deleteItem":
                    JH_VoidNetworkTask networkTask2 = new JH_VoidNetworkTask(MyWagleActivity.this, urlAddr);
                    networkTask2.execute().get();
                    break;
                case "paymentList":
                    JH_ObjectNetworkTask_Payment networkTask1 = new JH_ObjectNetworkTask_Payment(MyWagleActivity.this, urlAddr);
                    Object obj = networkTask1.execute().get(); // 오브젝트로 받아야함.
                    lists = (ArrayList<Payment>) obj; // cast.
                    adapter = new PaymentAdapter(MyWagleActivity.this, R.layout.customlayout_payment_listview, lists); // making adapter.
                    lv_itemlist.setAdapter(adapter);
//                    lv_itemlist.setOnItemClickListener(onItemClickListener);
                    lv_itemlist.setOnItemLongClickListener(onItemLongClickListener);
                    if(paymentCnt() == 1) {
                        setListViewHeightBasedOnChildren(lv_itemlist); // 리스트뷰 길이 조절.
                    }
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //-------------- 리스트뷰 길이 조절 -----------------------------------------------------------------
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    //----------------------------------------------------------------------------------------------


    // ----------------- 꾹~ 롱클릭 이벤트 --------------------------------------------------------------------------------------------------
    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
//            click = 1;

            // --------------- 대화상자 띄우기 ---------------------------------------------------------
            new AlertDialog.Builder(MyWagleActivity.this)
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
                            Toast.makeText(MyWagleActivity.this, "해당 항목이 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                            MyWagleActivity.this.onResume();
                        }
                    })
                    .show();
            // -------------------------------------------------------------------------------------
            return false;
        }
    };


    private void popupAddItem(){
        // --------------- 대화상자 띄우기 ---------------------------------------------------------
        final LinearLayout linearLayout = (LinearLayout) View.inflate(MyWagleActivity.this, R.layout.customdialog_payment_additem, null);
        new AlertDialog.Builder(MyWagleActivity.this)
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
                        Toast.makeText(MyWagleActivity.this, "추가 되었습니다.", Toast.LENGTH_SHORT).show();

                        MyWagleActivity.this.onResume();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MyWagleActivity.this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false)
                .show();
        // -------------------------------------------------------------------------------------
    }




}//----
