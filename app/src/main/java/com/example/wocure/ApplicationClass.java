package com.example.wocure;

import android.app.Application;

public class ApplicationClass extends Application {
    public static ApplicationClass applicationClass;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationClass = this;
    }

    public static ApplicationClass getAppInstance() {
        return applicationClass;
    }
}
