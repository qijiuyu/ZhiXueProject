package com.example.administrator.zhixueproject.http.api;

import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.http.HttpConstant;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpApi1 {

    /**
     * 获取短信验证码
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_SMS_CODE)
    Call<BaseBean> sendCode(@FieldMap Map<String, String> map);
}
