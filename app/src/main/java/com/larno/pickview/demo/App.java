package com.larno.pickview.demo;

import android.app.Application;

/**
 * Created by sks on 2016/1/4.
 */
public class App extends Application {
    public static App app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
