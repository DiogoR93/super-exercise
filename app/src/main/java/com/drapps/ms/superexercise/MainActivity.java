package com.drapps.ms.superexercise;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.drapps.ms.superexercise.apicalls.ApiService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ApiService api;
    MainApplication app;
    AHBottomNavigation bottomNav;
    ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        setUpNavigation();
        setUpService();

    }

    private void setUpNavigation() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("1", R.drawable.ic_list_white_24dp, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("2", R.drawable.ic_list_white_24dp, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("3", R.drawable.ic_list_white_24dp, R.color.white);

// Add items
        bottomNav.addItem(item1);
        bottomNav.addItem(item2);
        bottomNav.addItem(item3);

        bottomNav.setAccentColor(Color.parseColor("#FFFFFF"));
        bottomNav.setInactiveColor(Color.parseColor("#FFFFFF"));
        bottomNav.setDefaultBackgroundColor(Color.parseColor("#273E47"));

        bottomNav.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager.setCurrentItem(position);
                return true;
            }
        });
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentExercie1());
        adapter.addFragment(new Fragment());
        viewPager.setAdapter(adapter);

    }

    public void setUpService(){
        app = MainApplication.getAppligation(this);
        api = app.getApiService();
    }

    public ApiService getApi() {
        return api;
    }
}
