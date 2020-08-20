package com.example.fluper.util;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

public class AppController extends Application {


    static AppController mInstance;
    static AppCompatActivity mSupportActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }


    public static void setActivity(AppCompatActivity supportActivity) {
        mSupportActivity = supportActivity;
    }

    public static synchronized AppCompatActivity getActivity() {
        return mSupportActivity;
    }

}
