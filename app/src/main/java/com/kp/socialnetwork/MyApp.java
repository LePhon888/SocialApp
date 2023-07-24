package com.kp.socialnetwork;

import android.app.Application;

public class MyApp extends Application {
    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    //Context = MyApp.getInstance();

    public static synchronized MyApp getInstance() {
        return instance;
    }

}
