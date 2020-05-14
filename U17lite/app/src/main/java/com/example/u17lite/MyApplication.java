package com.example.u17lite;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context context;
    public static int read_way = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {return context;}
}
