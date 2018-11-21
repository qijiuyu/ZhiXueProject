package com.example.administrator.zhixueproject.application;

import android.app.Application;
import android.text.TextUtils;

import com.example.administrator.zhixueproject.bean.Home;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.bean.UserInfo;
import com.example.administrator.zhixueproject.http.HttpConstant;
import com.example.administrator.zhixueproject.utils.ActivitysLifecycle;
import com.example.administrator.zhixueproject.utils.SPUtil;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MyApplication extends Application {

    public static MyApplication application;
    public static Gson gson;
    public static SPUtil spUtil;
    public static UserInfo userInfo;
    public static Home.HomeBean homeBean;
    public static IWXAPI api;
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

        api = WXAPIFactory.createWXAPI(this, HttpConstant.WX_APPID, true);
        api.registerApp(HttpConstant.WX_APPID);

        registerActivityLifecycleCallbacks(ActivitysLifecycle.getInstance());
    }



}
