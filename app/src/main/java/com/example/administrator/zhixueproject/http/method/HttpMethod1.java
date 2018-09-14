package com.example.administrator.zhixueproject.http.method;

import android.os.Handler;

import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.api.HttpApi1;
import com.example.administrator.zhixueproject.http.base.BaseRequst;
import com.example.administrator.zhixueproject.http.base.Http;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpMethod1  extends BaseRequst {

    /**
     * 获取短信验证码
     * @param mobile
     * @param handler
     */
    public static void getSmsCode(String mobile,String type,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile",mobile);
        map.put("type",type);
        Http.getRetrofit().create(HttpApi1.class).getSmsCode(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.GET_SMS_CODE_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<BaseBean> call, Throwable t) {
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 注册
     * @param handler
     */
    public static void register(String mobile,String pwd,String code,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile",mobile);
        map.put("pwd",pwd);
        map.put("code",code);
        Http.getRetrofit().create(HttpApi1.class).register(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.REGISTER_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<BaseBean> call, Throwable t) {
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }
}
