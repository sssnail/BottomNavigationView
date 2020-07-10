package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.fragment.DashboardFragment;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends FragmentActivity {
    //记录当前正在使用的fragment
    private Fragment isFragment;
    private HomeFragment homeFragment;
    private DashboardFragment dashboardFragment;
    private NotificationsFragment notificationsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);//设置icon颜色
        navView.setOnNavigationItemReselectedListener(null);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        //用itemSelect监听则Homefra中内容无显示
//        initFragment(savedInstanceState);
//        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (homeFragment == null) {
                        homeFragment = new HomeFragment();
                    }
                    switchContent(isFragment, homeFragment);
                    return true;
                case R.id.navigation_dashboard:
                    if (dashboardFragment == null) {
                        dashboardFragment = new DashboardFragment();
                    }
                    switchContent(isFragment, dashboardFragment);
                    return true;
                case R.id.navigation_notifications:
                    if (notificationsFragment == null) {
                        notificationsFragment = new NotificationsFragment();
                    }
                    switchContent(isFragment, notificationsFragment);
                    return true;

            }
            return false;
        }

    };


    public void switchContent(Fragment from, Fragment to) {
        if (isFragment != to) {
            isFragment = to;
            FragmentManager fm = getSupportFragmentManager();
            //添加渐隐渐现的动画
            FragmentTransaction ft = fm.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                ft.hide(from).add(R.id.nav_host_fragment, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    public void initFragment(Bundle savedInstanceState) {
        //判断activity是否重建，如果不是，则不需要重新建立fragment.
        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if (homeFragment == null) {
                homeFragment = new HomeFragment();
            }
            isFragment = homeFragment;
            ft.replace(R.id.nav_host_fragment, homeFragment).commit();
        }
    }

}
