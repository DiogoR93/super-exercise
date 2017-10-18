package com.drapps.ms.superexercise;

import android.graphics.Color;
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
    FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        setUpNavigation();
        setUpService();
        getRaw();
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

    }

    public void setUpService(){
        app = MainApplication.getAppligation(this);
        api = app.getApiService();
    }

    public void getRaw(){
        rx.Observable<ResponseBody> observable = api.getString();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody response) {
                        try {
                            createList(response.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    });
    }

    private void createList(String string) {
        try {
            Log.i("TESTE","ERROR");
            Scanner scanner = new Scanner(string);
            int i = 0;
            while (scanner.hasNext()) {
                i++;
                String line = scanner.nextLine();
                String[] lineSperated = line.split(",");
                Log.i("Line " + i, lineSperated[0] + " / " + lineSperated[11] + " - " + lineSperated[12]);

            }
            scanner.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
