package com.wzhy.simplecustomviews;

import android.app.Application;
import android.content.Context;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Administrator on 2017-9-28 0028.
 */

public class MyApp extends Application {

    private static MyApp mApp;

    public static MyApp getApp(){
        return mApp;
    }

    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }
}
