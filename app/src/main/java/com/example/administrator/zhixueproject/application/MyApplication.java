package com.example.administrator.zhixueproject.application;

import android.app.Application;
import android.text.TextUtils;

import com.example.administrator.zhixueproject.bean.UserInfo;
import com.example.administrator.zhixueproject.utils.ActivitysLifecycle;
import com.example.administrator.zhixueproject.utils.SPUtil;
import com.google.gson.Gson;

public class MyApplication extends Application {

    public static MyApplication application;
    public static Gson gson;
    public static SPUtil spUtil;
    public static UserInfo userInfo;
    public void onCreate() {
        super.onCreate();
        application=this;
        gson = new Gson();
        spUtil = SPUtil.getInstance(this);

        //获取用户信息
        final String strUserInfo=spUtil.getString(SPUtil.USER_INFO);
        if(!TextUtils.isEmpty(strUserInfo)){
            userInfo=gson.fromJson(strUserInfo,UserInfo.class);
        }
        registerActivityLifecycleCallbacks(ActivitysLifecycle.getInstance());
    }
}
