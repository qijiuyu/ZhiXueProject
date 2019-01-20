package com.example.administrator.zhixueproject.application;

import android.app.Application;
import android.text.TextUtils;

import com.example.administrator.zhixueproject.bean.Colleges;
import com.example.administrator.zhixueproject.bean.Home;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.bean.UserInfo;
import com.example.administrator.zhixueproject.http.HttpConstant;
import com.example.administrator.zhixueproject.utils.ActivitysLifecycle;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

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

        //获取学院信息
        final String strHome=spUtil.getString(SPUtil.HOME_INFO);
        if(!TextUtils.isEmpty(strHome)){
            homeBean=gson.fromJson(strHome,Home.HomeBean.class);
        }

        api = WXAPIFactory.createWXAPI(this, HttpConstant.WX_APPID, true);
        api.registerApp(HttpConstant.WX_APPID);

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        registerActivityLifecycleCallbacks(ActivitysLifecycle.getInstance());

        initShare();
    }



    /**
     * 初始化友盟分享
     */
    private void initShare(){
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this,"561cae6ae0f55abd990035bf","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        //微信
        PlatformConfig.setWeixin("wxf2413139ede45239", "59fecf8eedfd5c4fc7e699c4424a7dfa");
    }

}
