package com.androidlec.wagle;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androidlec.wagle.fragments.HomeFragment;
import com.androidlec.wagle.fragments.MyPageFragment;
import com.androidlec.wagle.fragments.PlanFragment;
import com.androidlec.wagle.fragments.WaggleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

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

        // 하단 네비게이션바
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_menu_home:
                startActivity(new Intent(HomeActivity.this, MainMoimListActivity.class));
                break;
            case R.id.toolbar_menu_myInfo:
                break;
            case R.id.toolbar_menu_myMoim:
                break;
            case R.id.toolbar_menu_settings:
                break;
        }
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

}