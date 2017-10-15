package com.drapps.ms.superexercise;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Diogo on 13/10/2017.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
