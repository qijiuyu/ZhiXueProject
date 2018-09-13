package com.example.administrator.zhixueproject.application;

import android.app.Application;

import com.example.administrator.zhixueproject.utils.ActivitysLifecycle;
import com.example.administrator.zhixueproject.utils.SPUtil;
import com.google.gson.Gson;

public class MyApplication extends Application {

    public static MyApplication application;
    public static Gson gson;
    public static SPUtil spUtil;
    public void onCreate() {
        super.onCreate();
        application=this;
        gson = new Gson();
        spUtil = SPUtil.getInstance(this);

        registerActivityLifecycleCallbacks(ActivitysLifecycle.getInstance());
    }
}
