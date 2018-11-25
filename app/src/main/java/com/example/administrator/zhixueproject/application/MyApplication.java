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

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    public static MyApplication application;
    public static Gson gson;
    public static SPUtil spUtil;
    public static UserInfo userInfo;
    public static List<Colleges> listColleges=new ArrayList<>();
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

        //获取学院列表
        final String  colleges=spUtil.getString(SPUtil.COLLEGE_LIST);
        if(!TextUtils.isEmpty(strUserInfo)){
            listColleges=gson.fromJson(colleges, new TypeToken<List<Colleges>>(){}.getType());
        }

        //获取学院信息
        final String strHome=spUtil.getString(SPUtil.HOME_INFO);
        if(!TextUtils.isEmpty(strHome)){
            homeBean=gson.fromJson(strHome,Home.HomeBean.class);
        }

        api = WXAPIFactory.createWXAPI(this, HttpConstant.WX_APPID, true);
        api.registerApp(HttpConstant.WX_APPID);

        registerActivityLifecycleCallbacks(ActivitysLifecycle.getInstance());
    }



}
