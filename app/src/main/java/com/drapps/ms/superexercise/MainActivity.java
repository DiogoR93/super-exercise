package com.drapps.ms.superexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpService();


        getRaw();
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
