package com.example.administrator.zhixueproject.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtil {

    private SharedPreferences shar;
    private Editor editor;
    public final static String USERMESSAGE = "zhixue";

    //是否首次打开APP
    public static final String IS_FIRST_OPEN="is_first_open";
    //验证码计时器
    public static final String SMS_CODE_TIME="sms_code_time";
    //登陆手机号码
    public static final String LOGIN_MOBILE="login_mobile";
    //用户信息
    public static final String USER_INFO="user_info";
    //回话id
    public static final String SESSION_ID="session_Id";
    //登录的token
    public static final String TOKEN="token";
    private static SPUtil sharUtil = null;
    private SPUtil(Context context, String sharname) {
        shar = context.getSharedPreferences(sharname, Context.MODE_PRIVATE + Context.MODE_APPEND);
        editor = shar.edit();
    }

    public static SPUtil getInstance(Context context) {
        if (null == sharUtil) {
            sharUtil = new SPUtil(context, USERMESSAGE);
        }
        return sharUtil;
    }


    //添加String信息
    public void addString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    //添加int信息
    public void addInt(String key, Integer value) {
        editor.putInt(key, value);
        editor.commit();
    }

    //添加boolean信息
    public void addBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    //添加float信息
    public void addFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    //添加long信息
    public void addLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }


    public void removeMessage(String delKey) {
        editor.remove(delKey);
        editor.commit();
    }

    public void removeAll() {
        editor.clear();
        editor.commit();
    }

    public String getString(String key) {
        return shar.getString(key, "");
    }

    public Integer getInteger(String key) {
        return shar.getInt(key, 0);
    }

    public boolean getBoolean(String key) {
        return shar.getBoolean(key, false);
    }

    public float getFloat(String key) {
        return shar.getFloat(key, 0);
    }

    public long getLong(String key) {
        return shar.getLong(key, 0);
    }

}
