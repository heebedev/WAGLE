package com.androidlec.wagle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androidlec.wagle.CS.Model.User;
import com.androidlec.wagle.activity.menu.MyInfoActivity;
import com.androidlec.wagle.activity.menu.MyMoimActivity;
import com.androidlec.wagle.CS.Network.MINetworkTask;
import com.androidlec.wagle.activity.menu.MyInfoActivity;
import com.androidlec.wagle.fragments.HomeFragment;
import com.androidlec.wagle.fragments.MyPageFragment;
import com.androidlec.wagle.fragments.PlanFragment;
import com.androidlec.wagle.fragments.WaggleFragment;
import com.androidlec.wagle.network_sh.NetworkTask_ckGrade;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    //grade check
    String urlAddr;

    // Fragment 초기화
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment = new HomeFragment();
    private WaggleFragment waggleFragment = new WaggleFragment();
    private PlanFragment planFragment = new PlanFragment();
    private MyPageFragment myPageFragment = new MyPageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 초기 Fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrame, homeFragment).commitAllowingStateLoss();

        // 상단 툴바
        Toolbar toolbar = findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        setActionBar();

        // 하단 네비게이션바
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        //bottomNavigationView.setItemIconTintList(null);

        //GradeCheck
        ckGrade();

    }

    private void setActionBar() {
        ImageView include_ab_iv = findViewById(R.id.include_ab_iv);
        TextView include_ab_tv = findViewById(R.id.include_ab_tv);

        String[] data = getMoimData();
        String imageUri = UserInfo.MOIM_BASE_URL + data[0];

        Glide.with(this)
                .load(imageUri)
                .apply(new RequestOptions().circleCrop())
                .placeholder(R.drawable.ic_outline_emptyimage)
                .into(include_ab_iv);

        include_ab_tv.setText(data[1]);

    }

    private String[] getMoimData() {
        String[] result = new String[2];
        String urlAddr = "http://192.168.0.79:8080/wagle/csGetMoimWAGLE.jsp?";

        urlAddr = urlAddr + "mSeqno=" + UserInfo.MOIMSEQNO;

        try {
            MINetworkTask miNetworkTask = new MINetworkTask(HomeActivity.this, urlAddr);
            result = miNetworkTask.execute().get(); // doInBackground 의 리턴값
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        if(UserInfo.WAGLEMAGRADE.equals("O")) {
            menuInflater.inflate(R.menu.toolbar_menu, menu);
        } else {
            menuInflater.inflate(R.menu.toolbar_menu_general, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;

        switch (item.getItemId()){
            case R.id.toolbar_menu_home:
                intent = new Intent(HomeActivity.this, MainMoimListActivity.class);
                break;
            case R.id.toolbar_menu_myInfo:
                MyInfoActivity.previousXML = "edit";
                intent = new Intent(HomeActivity.this, MyInfoActivity.class);
                break;
            case R.id.toolbar_menu_myMoim:
                if (!UserInfo.WAGLEMAGRADE.equals("O")) {
                    return false;
                }
                intent = new Intent(HomeActivity.this, MyMoimActivity.class);
                break;
            case R.id.toolbar_menu_logout:
                break;
        }

        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    // 네비게이션 클릭시 화면 이동 리스너
    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.mainFrame, homeFragment).commitAllowingStateLoss();
                    break;
                case R.id.navigation_wagle:
                    transaction.replace(R.id.mainFrame, waggleFragment).commitAllowingStateLoss();
                    break;
                case R.id.navigation_plan:
                    transaction.replace(R.id.mainFrame, planFragment).commitAllowingStateLoss();
                    break;
                case R.id.navigation_myPage:
                    transaction.replace(R.id.mainFrame, myPageFragment).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    };

    //Grade Check
    private void ckGrade() {
        urlAddr = "http://192.168.0.138:8080/test/wagle_magradecheck.jsp?useqno=" + UserInfo.USEQNO + "&mseqno=" + UserInfo.MOIMSEQNO ;
        UserInfo.WAGLEMAGRADE = getOSData();
    }

    private String getOSData() {
        String result = "W";
        try {
            NetworkTask_ckGrade networkTask = new NetworkTask_ckGrade(HomeActivity.this, urlAddr);
            Object obj = networkTask.execute().get();
            result = obj.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }  // connectGetData

    // Fragment 이동 (home -> wagle)
    public void fragmentMove() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrame, waggleFragment).commitAllowingStateLoss();
    }

}