package com.drapps.ms.superexercise;

import android.app.Application;
import android.content.Context;

import com.drapps.ms.superexercise.apicalls.ApiService;

import io.realm.Realm;

/**
 * Created by Diogo on 13/10/2017.
 */

public class MainApplication extends Application {
    ApiService api;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

    public static MainApplication getAppligation(Context context){
        return (MainApplication) context.getApplicationContext();
    }



}
