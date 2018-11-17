package com.example.administrator.zhixueproject.http.base;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.SPUtil;

import java.util.List;

import okhttp3.Headers;

/**
 * Created by lyn on 2017/5/9.
 */

public class BaseRequst {

    public static void sendMessage(Handler handler, int wat, Object obj) {
        if(null==handler){
            return;
        }
        Message message = Message.obtain();
        message.what = wat;
        message.obj = obj;
        handler.sendMessage(message);
    }

}
