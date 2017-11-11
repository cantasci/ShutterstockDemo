package com.cantasci.shutterstock;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.cantasci.shutterstock.utils.Prefs;

import net.danlew.android.joda.JodaTimeAndroid;

import timber.log.Timber;

public class ShutterStockApp extends Application {
    private static ShutterStockApp sInstance;


    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    public static ShutterStockApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Timber.plant(new Timber.DebugTree());
        JodaTimeAndroid.init(this);
        Prefs.createInstanceIfNeeded(sInstance);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
