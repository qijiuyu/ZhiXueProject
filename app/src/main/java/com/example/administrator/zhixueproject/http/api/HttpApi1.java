package com.example.administrator.zhixueproject.http.api;

import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.ColleteVips;
import com.example.administrator.zhixueproject.bean.UserInfo;
import com.example.administrator.zhixueproject.http.HttpConstant;

import java.util.Map;

import okhttp3.ResponseBody;
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
    Call<BaseBean> getSmsCode(@FieldMap Map<String, String> map);


    /**
     * 注册
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.REGISTER)
    Call<BaseBean> register(@FieldMap Map<String, String> map);


    /**
     * 登陆
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.LOGIN)
    Call<UserInfo> login(@FieldMap Map<String, String> map);


    /**
     * 查询学院vip等级
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_COLLETE_VIPS)
    Call<ColleteVips> getCollegeVips(@FieldMap Map<String, String> map);
}
