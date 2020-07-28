package com.androidlec.wagle.JH;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
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
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.activity.wagleSub.AddBJMActivity;
import com.androidlec.wagle.activity.wagleSub.AddDHGActivity;
import com.androidlec.wagle.adapter.DHGListAdapter;
import com.androidlec.wagle.dto.BookInfo;
import com.androidlec.wagle.dto.SgstRptList;
import com.androidlec.wagle.jhj.Jhj_BookReport_DTO;
import com.androidlec.wagle.jhj.Jhj_FTPConnect;
import com.androidlec.wagle.jhj.Jhj_Gallery_DTO;
import com.androidlec.wagle.jhj.Jhj_MySql_Insert_Delete_Update_NetworkTask;
import com.androidlec.wagle.jhj.Jhj_MySql_Select_NetworkTask;
import com.androidlec.wagle.networkTask.JH_IntNetworkTask;
import com.androidlec.wagle.networkTask.JH_ObjectNetworkTask_Payment;
import com.androidlec.wagle.networkTask.JH_ObjectNetworkTask_Progress;
import com.androidlec.wagle.networkTask.JH_VoidNetworkTask;
import com.androidlec.wagle.network_sh.NetworkTask_BookInfo;
import com.androidlec.wagle.network_sh.NetworkTask_QuestionReportList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyWagleActivity extends AppCompatActivity {

    // JSP 연결 IP
    private final static String JH_IP = "192.168.0.178";


    private String urlAddr;
    private ListView lv_itemlist;
    private String item;
    private int price, paymentcnt;
    private PaymentAdapter adapter;
    private DHGListAdapter bookadapter;
    private ArrayList<Payment> lists;
    private ArrayList<Progress> progressdata;
    private ArrayList<ImageView> imageViews;
    private static ArrayList<Jhj_Gallery_DTO> Gdata;
    private int index = 0;
    private int wcSeqno = Integer.parseInt(UserInfo.WAGLESEQNO);

    // 와글 이름
    private TextView wagleName;

    private TextView bkname, bkwriter, bkmaxpate, bkIntro, bkData;
    private ImageView bookimage;

    // 독후감
    private TextView btn_bookreportAdd, tv_viewBJM;
    private TextView btn_suggestionAdd;
    private ListView dhglist;
    private BookInfo bookInfo;
    private View ic_bookinfo;
    private ArrayList<Jhj_BookReport_DTO> booklist;
    //발제문
    private static ArrayList<SgstRptList> questionListData;

    // 프로그레스바 파트.
    private RelativeLayout rl_images;
    private ProgressBar pb_book;
    private TextView btn_move;
    private EditText et_wpReadPage;

    // 갤러리 파트.
    private Button btn_galleryAdd;
    private TextView tv_galleryPlus;

    // 정산 파트.
    private TextView tv_PPP;
    private LinearLayout btn_paymentAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wagle);

        getProfileReadPage();
        getData();
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 리스트 가져오기.
        urlDivider("paymentList", 0, null,0);
        getTotal();
        Gallery_Setting();
    }


    private void init() {
        //와글 이름 설정
        wagleName = findViewById(R.id.tv_mywagle_wagleName);
        wagleName.setText(UserInfo.WAGLENAME);


        // 독후감 파트.
        btn_bookreportAdd = findViewById(R.id.mywagle_btn_bookreportAdd);
        btn_suggestionAdd = findViewById(R.id.mywagle_btn_suggestionAdd);
        et_wpReadPage = findViewById(R.id.mywagle_et_wpReadPage);

        //발제문
        tv_viewBJM = findViewById(R.id.tv_mywagle_readbjm);
        tv_viewBJM.setOnClickListener(onClickListener);

        btn_move = findViewById(R.id.mywagle_btn_move);


        if(UserInfo.WAGLETYPE.equals("정규")) {
            btn_bookreportAdd.setOnClickListener(onClickListener);
            btn_suggestionAdd.setOnClickListener(onClickListener);
            btn_move.setOnClickListener(onClickListener);

            bkname = findViewById(R.id.bookinfo_tv_bookname);
            bkwriter = findViewById(R.id.bookinfo_tv_bookwriter);
            bkmaxpate = findViewById(R.id.bookinfo_tv_bookmaxpage);
            bkIntro = findViewById(R.id.bookinfo_tv_bookinfo);
            bkData = findViewById(R.id.bookinfo_tv_bookdata);
            bookimage = findViewById(R.id.bookinfo_iv_bookImage);

            //책 정보 확인
            if(bookInfo != null) {
                ic_bookinfo = findViewById(R.id.ic_mywagle_bookinfo);
                ic_bookinfo.setVisibility(View.VISIBLE);
                btn_suggestionAdd.setText("발제문 추가");

                if(UserInfo.WAGLEMAKERSEQ.equals(Integer.toString(UserInfo.USEQNO))) {
                    btn_suggestionAdd.setVisibility(View.VISIBLE);

                    if (questionListData.size() > 0) {
                        btn_suggestionAdd.setText("발제문 수정");
                    }
                }

                bkname.setText(bookInfo.getTitle());
                bkwriter.setText(bookInfo.getWriter());
                bkmaxpate.setText(Integer.toString(bookInfo.getMaxpage()));
                bkIntro.setText(bookInfo.getIntro());
                bkData.setText(bookInfo.getData());

                if (bookInfo.getImgName().length() > 0)
                    Glide.with(this)
                            .load(UserInfo.BOOK_BASE_URL + bookInfo.getImgName())
                            .apply(new RequestOptions().centerCrop())
                            .into(bookimage);

            }

        } else {
            btn_suggestionAdd.setVisibility(View.GONE);
            btn_bookreportAdd.setVisibility(View.GONE);
            btn_move.setVisibility(View.GONE);
            tv_viewBJM.setVisibility(View.GONE);
            LinearLayout ll = findViewById(R.id.mywagle_ll_readingstatus);
            ll.setVisibility(View.GONE);
        }

        //독후감 목록
        dhglist = findViewById(R.id.mywagle_lv_bookreport);
        connectGetBookData();

        dhglist.setOnItemClickListener(bookReportClick);


        // 프로그레스바 파트.
        initProgressBar();

        // 갤러리 파트.
        Button btn_galleryAdd = findViewById(R.id.mywagle_btn_galleryAdd);
        TextView tv_galleryPlus = findViewById(R.id.mywagle_tv_galleryPlus);
        btn_galleryAdd.setOnClickListener(onClickListener);
        tv_galleryPlus.setOnClickListener(onClickListener);

        // 정산 파트.
        btn_paymentAdd = findViewById(R.id.mywagle_btn_paymentMsg);

        // 정산 해놓은거 있으면 영수증, 아니면 버튼 띄워줌.
        switch (paymentCnt()) {
            case 1:
                btn_paymentAdd.setVisibility(View.VISIBLE);
                break;
            case 2:
            default:
                btn_paymentAdd.setVisibility(View.INVISIBLE);
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
                    startActivity(new Intent(MyWagleActivity.this, AddDHGActivity.class));
                    break;
                case R.id.mywagle_btn_suggestionAdd:
                    if (UserInfo.MOIMMYGRADE.equals("W")) {
                        return;
                    }
                    intent = new Intent(MyWagleActivity.this, AddBJMActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mywagle_btn_move:
                    recordPage();
                    Intent intent1 = getIntent();
                    finish();
                    startActivity(intent1);
                    break;
                case R.id.mywagle_btn_galleryAdd:
                    break;
                case R.id.mywagle_tv_galleryPlus:
                    break;
                case R.id.mywagle_btn_paymentMsg:
                    // --------------- 대화상자 띄우기 -------------------------------------------------
                    EditText editText = new EditText(getApplicationContext());
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED);

                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MyWagleActivity.this);
                    builder.setTitle("계좌번호 등록");
                    builder.setMessage("계좌번호를 입력해 주세요.");
                    builder.setView(editText);
                    builder.setPositiveButton("확인", (dialog, which) -> {
                        String account = editText.getText().toString().trim();
                        sendMessage(account);
                    });
                    builder.setNegativeButton("취소", null);
                    builder.show();
                    // -----------------------------------------------------------------------------
                    break;
                case R.id.payment_btn_addItem:
                    popupAddItem();
                    break;
                case R.id.tv_mywagle_readbjm :
                    viewBJM();
                    break;
            }
        }
    };


    private void sendMessage(String account) {
        // 템플릿 ID
        String templateId = "33365";

        // 템플릿에 입력된 Argument에 채워질 값
        Map<String, String> templateArgs = new HashMap<>();
        templateArgs.put("WAGLE_NAME", UserInfo.WAGLENAME);
        templateArgs.put("PAYMENT", tv_PPP.getText().toString().trim());
        templateArgs.put("ACCOUNT", account);

        // 커스텀 템플릿으로 카카오링크 보내기
        KakaoLinkService.getInstance()
                .sendCustom(this, templateId, templateArgs, null, new ResponseCallback<KakaoLinkResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "카카오링크 보내기 실패: " + errorResult);
                    }

                    @Override
                    public void onSuccess(KakaoLinkResponse result) {
                        Log.i("KAKAO_API", "카카오링크 보내기 성공");

                        // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                        Log.w("KAKAO_API", "warning messages: " + result.getWarningMsg());
                        Log.w("KAKAO_API", "argument messages: " + result.getArgumentMsg());
                    }
                });
    }


    private void initProgressBar(){

        RelativeLayout rl_images = findViewById(R.id.mywagle_rl_images);
        ProgressBar pb_book = findViewById(R.id.mywagle_pb_book);


        int deviceWidth = getApplication().getResources().getDisplayMetrics().widthPixels; // 디바이스 최대 크기를 구한다.
        pb_book.setMax(deviceWidth); // 사용할 프로그레스바의 최대크기를 디바이스 최대크기로 지정한다.

        int size = progressdata.size(); // 와글 총 인원 수.

        int wbMaxPage= getwbMaxPage(); // 필요 할당량 (ex 책의 최대 페이지)
        imageViews = new ArrayList<ImageView>();

        for(int i = 0; i < size; i++) {

            ImageView iv = new ImageView(getApplicationContext());
            imageViews.add(iv); // Initialize a new ImageView widget
            imageViews.get(i).setId(progressdata.get(i).getuSeqno());

            if(progressdata.get(i).getuLoginType().equals("wagle")){
                Glide.with(this)
                        .load("http://192.168.0.82:8080/wagle/userImgs/" + progressdata.get(i).getuImageName())
                        .apply(new RequestOptions().circleCrop())
                        .override(30,30)
                        .placeholder(R.drawable.ic_outline_emptyimage)
                        .into(imageViews.get(i));
            } else {
                Glide.with(this)
                        .load(progressdata.get(i).getuImageName())
                        .apply(new RequestOptions().circleCrop())
                        .placeholder(R.drawable.ic_outline_emptyimage)
                        .into(imageViews.get(i));
            }

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); // Create layout parameters for ImageView
            layoutParams.addRule(RelativeLayout.ABOVE, pb_book.getId()); // Add rule to layout parameters // Add the ImageView below to Button
            imageViews.get(i).setLayoutParams(layoutParams); // Add layout parameters to ImageView
            rl_images.addView(imageViews.get(i)); // Finally, add the ImageView to layout

            imageViews.get(i).requestLayout();
            imageViews.get(i).getLayoutParams().height = dpToPx(30, rl_images); // Apply the new height for ImageView programmatically
            imageViews.get(i).getLayoutParams().width = dpToPx(30, rl_images);
            imageViews.get(i).setScaleType(ImageView.ScaleType.FIT_XY); // Set the scale type for ImageView image scaling



            float wpReadPage = progressdata.get(i).getWpReadPage();// 유저의 읽은 페이지 수만큼 이미지 이동.

            if(wpReadPage == 0) wpReadPage = (float) 0.1;

            float movePage = wbMaxPage / wpReadPage; // 필요 할당량 에서 움직일 만큼의 비율을 구한다. (책의 총 페이지 / 읽은 책의 양)

            float moveProgressBar = deviceWidth / movePage; // 비율 구한것을 화면 기기에 넣는다.

            if(wpReadPage >= wbMaxPage){
                imageViews.get(i).setX(deviceWidth - imageViews.get(i).getWidth()); // 맨 오른쪽 으로 이동
            }else{
                imageViews.get(i).setX(moveProgressBar);
            }
            if(progressdata.get(i).getuSeqno() == UserInfo.USEQNO){ // 프로그레스바는 내가 읽은 부분까지 채워줌.
                index = i;
                pb_book.incrementProgressBy((int) moveProgressBar); // 프로그레스바 비율에따른 이동
                et_wpReadPage.setText(Integer.toString(progressdata.get(i).getWpReadPage()));
            }
        }

    }


    private void recordPage() {
        int wpSeqno = progressdata.get(index).getWpSeqno();
        String page = et_wpReadPage.getText().toString();
        urlAddr = "http://" + JH_IP + ":8080/wagle/recordPage.jsp?";
        urlAddr = urlAddr + "wpSeqno=" + wpSeqno + "&wpReadPage=" + page;
        try {
            JH_VoidNetworkTask networkTask7 = new JH_VoidNetworkTask(MyWagleActivity.this, urlAddr);
            networkTask7.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static int dpToPx(int dp, RelativeLayout context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }


    private void getData(){

        String centIP = "192.168.0.138";
        String url = "http://" + centIP + ":8080/test/wagle_bookinfoGet.jsp?wcSeqno=" + UserInfo.WAGLESEQNO;

        bookInfo = getBookinfo(url);

        //발제문 정보 가져오기
        String urlAddr = "http://192.168.0.82:8080/wagle/wagle_questionlist.jsp?wcseqno=" + UserInfo.WAGLESEQNO;
        connectGetbjmData(urlAddr);

    }
  
    private void getProfileReadPage() {
        Log.v("로그 체크 : ", "getProfileReadPage()");
        urlAddr = "http://192.168.0.178:8080/wagle/getProfileReadPage.jsp?";
        urlAddr = urlAddr + "wcSeqno=" + wcSeqno;
        try {
            JH_ObjectNetworkTask_Progress networkTask6 = new JH_ObjectNetworkTask_Progress(MyWagleActivity.this, urlAddr);
            Object obj = networkTask6.execute().get();
            progressdata = (ArrayList<Progress>) obj;
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private int getwbMaxPage() {
        int wbMaxPage = 0;
        urlAddr = "http://" + JH_IP + ":8080/wagle/getTotalPage.jsp?";
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
        int ppp = total/getWagleUsers();
        tv_PPP = findViewById(R.id.payment_tv_PricePerPerson);
        tv_PPP.setText(ppp + "원");
    }

    private int getWagleUsers(){

        int memNo=0;
        urlAddr = "http://" + JH_IP + ":8080/wagle/getWagleUsers.jsp?";


        urlAddr = urlAddr + "wcSeqno=" + wcSeqno;
        try {
            JH_IntNetworkTask networkTask8 = new JH_IntNetworkTask(MyWagleActivity.this, urlAddr);
            memNo = networkTask8.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return memNo;
    }


    private int paymentCnt(){
        String wcSeqno = UserInfo.WAGLESEQNO;
        paymentcnt = 3;
        urlAddr = "http://" + JH_IP + ":8080/wagle/paymentCnt.jsp?";
        urlAddr = urlAddr + "wcSeqno=" + wcSeqno;
        connectDB("paymentCnt");
        return paymentcnt;
    }


    private void urlDivider(String function, int wpSeqno, String wpItem, int wpPrice){
        String wcSeqno = UserInfo.WAGLESEQNO;
        switch(function){
            case "wpItemAdd":
                urlAddr = "http://" + JH_IP + ":8080/wagle/wpItemAdd.jsp?";
                urlAddr = urlAddr + "wcSeqno=" + wcSeqno + "&wpItem=" + item + "&wpPrice=" + price;
                connectDB("wpItemAdd");
                break;
            case "paymentList":
                urlAddr = "http://" + JH_IP + ":8080/wagle/paymentList.jsp?";
                urlAddr = urlAddr + "wcSeqno=" + wcSeqno;
                connectDB("paymentList");
                break;
            case "deleteItem":
                urlAddr = "http://" + JH_IP + ":8080/wagle/deleteItem.jsp?";
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


    // ----------------- 롱클릭 이벤트 --------------------------------------------------------------------------------------------------
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

    //    책 정보 가져오기 ------------------------------------------------------------
    private BookInfo getBookinfo(String urlAddr) {
        BookInfo result = null;
        try {
            NetworkTask_BookInfo networkTask = new NetworkTask_BookInfo(MyWagleActivity.this, urlAddr);
            Object obj = networkTask.execute().get();

            result = (BookInfo) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //bjm 목록 가져오기
    private void connectGetbjmData(String urlAddr) {

        try {
            NetworkTask_QuestionReportList networkTask = new NetworkTask_QuestionReportList(MyWagleActivity.this, urlAddr);
            Object obj = networkTask.execute().get();
            questionListData = (ArrayList<SgstRptList>) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }  // connectGetData


    private void viewBJM() {

        if (questionListData.size() > 0) {
            final LinearLayout linear = (LinearLayout) View.inflate(MyWagleActivity.this, R.layout.custom_bjmview_sh, null);


            LinearLayout ll = linear.findViewById(R.id.bjmview_ll_bjmlayout);

            for (int i = 0; i < questionListData.size(); i++) {
                //질문 입력 EditText 추가
                TextView textView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                p.setMargins(30,10,30,10);
                textView.setLayoutParams(p);
                textView.setTextSize(14);
                textView.setBackgroundResource(R.drawable.white_rounded_background);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(5,5,5,5);
                textView.setText(questionListData.get(i).getsContent());

                ll.addView(textView);
            }


            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MyWagleActivity.this);
            builder.setTitle("")
                    .setView(linear)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            androidx.appcompat.app.AlertDialog dialog = builder.create();
            dialog.show();

        } else {
            Toast.makeText(MyWagleActivity.this, "등록된 발제문이 없습니다.", Toast.LENGTH_SHORT).show();
        }

    }

    private void connectGetBookData() {
        //독후감 목록
        String centIP = "192.168.0.138";
        String bookAddr = "http://" + centIP + ":8080/test/mywagle_BookReport_Select.jsp?wcSeqno=" + UserInfo.WAGLESEQNO;

        String data = null;

        try {
            Jhj_MySql_Select_NetworkTask networkTask = new Jhj_MySql_Select_NetworkTask(MyWagleActivity.this, bookAddr);
            // execute() java 파일안의 메소드 한번에 동작시키기, 메소드를 사용하면 HttpURLConnection 이 제대로 작동하지않는다.
            Object obj = networkTask.execute().get();
            data = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        booklist = BookReport_Parser(data);


        bookadapter = new DHGListAdapter(MyWagleActivity.this, R.layout.jhj_post_notice_list_item, booklist);
        dhglist.setAdapter(bookadapter);

    }

    protected ArrayList<Jhj_BookReport_DTO> BookReport_Parser(String jsonStr) {
        ArrayList<Jhj_BookReport_DTO> dtos = new ArrayList<Jhj_BookReport_DTO>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("bookreport"));
            dtos.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String brSeqno = jsonObject1.getString("brSeqno");
                String wcSeqno = UserInfo.WAGLESEQNO;
                String wcName = UserInfo.WAGLENAME;
                String uName = jsonObject1.getString("uName");


                dtos.add(new Jhj_BookReport_DTO(brSeqno, wcSeqno, wcName, uName));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }


    ListView.OnItemClickListener bookReportClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(MyWagleActivity.this,AddDHGActivity.class);
            startActivity(intent);
        }

    };

    // -------------------------------------------------------------------------------------
    // 갤러리 시작
    // -------------------------------------------------------------------------------------

    // 갤러리 세팅
    protected void Gallery_Setting() {
        // --------------------------------------------------------------
        // 갤러리 정보 6개 가져오기
        // --------------------------------------------------------------
        String IP = "192.168.0.82";
        String urlAddr = "http://" + IP + ":8080/wagle/Post_Gallery_Select.jsp?moimSeqno=" + UserInfo.MOIMSEQNO;
        String Gallery_JsonString = Post_Select_All(urlAddr);
        Gdata = Gallery_parser(Gallery_JsonString);
        // --------------------------------------------------------------
        // --------------------------------------------------------------



        // --------------------------------------------------------------
        // 갤러리 정보 3개 보여주기
        // --------------------------------------------------------------
        ImageView[] gallery_Frag_Btn = new ImageView[3];
        Integer[] gallery_Frag_Btn_Id = {
                R.id.mywagle_iv_gallery1, R.id.mywagle_iv_gallery2, R.id.mywagle_iv_gallery3
        };

        String imgUrl = "http://" + IP + ":8080/wagle/moimImgs/gallery/";

        for (int i = 0 ; i < 3; i++) {
            gallery_Frag_Btn[i] = findViewById(gallery_Frag_Btn_Id[i]);

            //         Context                 URL              ImageView
            //Glide.with(getActivity()).load(imgUrl + Gdata.get(i).getImageName()).into(gallery_Frag_Btn[i]);
            Glide.with(MyWagleActivity.this)
                    .load(imgUrl + Gdata.get(i).getImageName())
                    .placeholder(R.drawable.ic_baseline_crop_din_24)
                    .apply(new RequestOptions().centerCrop())
                    .into(gallery_Frag_Btn[i]);
        }

        // --------------------------------------------------------------
        //
        // --------------------------------------------------------------
    }


    protected String Post_Select_All(String urlAddr) {
        String data = null;

        try {
            Jhj_MySql_Select_NetworkTask networkTask = new Jhj_MySql_Select_NetworkTask(MyWagleActivity.this, urlAddr);
            // execute() java 파일안의 메소드 한번에 동작시키기, 메소드를 사용하면 HttpURLConnection 이 제대로 작동하지않는다.
            Object obj = networkTask.execute().get();
            data = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }



    // 갤러리 JsonData Dtos 에 저장하기
    protected ArrayList<Jhj_Gallery_DTO> Gallery_parser(String jsonStr) {
        ArrayList<Jhj_Gallery_DTO> dtos = new ArrayList<Jhj_Gallery_DTO>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("gallery"));
            dtos.clear();

            for (int i = 0 ; i < jsonArray.length() ; i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String seqno = jsonObject1.getString("seqno");
                String imgName = jsonObject1.getString("imagename");
                String user_uSeqno = jsonObject1.getString("user_useqno");

                dtos.add(new Jhj_Gallery_DTO(seqno, imgName, user_uSeqno));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }

    // 갤러리 FTP File Uplaod
    protected void GalleryFTPUpload(Uri file, String fileName) {
        try {
            String fileDirectroy = "/moimImgs/gallery";

            // FTP 접속
            String IP = "192.168.0.82";
            Jhj_FTPConnect connectFTP = new Jhj_FTPConnect(MyWagleActivity.this, IP, "host", "qwer1234", 25, file, fileName, fileDirectroy);
            connectFTP.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 갤러리 FTP FileUpload 시, MySql 저장
    protected void connectionInsertData(String urlAddr) {
        // Jsp 서버 전송
        try {
            Jhj_MySql_Insert_Delete_Update_NetworkTask insNetworkTask = new Jhj_MySql_Insert_Delete_Update_NetworkTask(MyWagleActivity.this, urlAddr);
            insNetworkTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------------------
    // 갤러리 끝
    // -------------------------------------------------------------------------------------




}//----