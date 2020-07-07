package com.androidlec.wagle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.androidlec.wagle.fragments.HomeFragment;
import com.androidlec.wagle.fragments.MyPageFragment;
import com.androidlec.wagle.fragments.PlanFragment;
import com.androidlec.wagle.fragments.WaggleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment = new HomeFragment();
    private WaggleFragment waggleFragment = new WaggleFragment();
    private PlanFragment planFragment = new PlanFragment();
    private MyPageFragment myPageFragment = new MyPageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrame, homeFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

    }

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