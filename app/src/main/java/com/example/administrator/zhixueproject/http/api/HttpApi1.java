package com.example.administrator.zhixueproject.http.api;

import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.CollegeList;
import com.example.administrator.zhixueproject.bean.ColleteVips;
import com.example.administrator.zhixueproject.bean.Home;
import com.example.administrator.zhixueproject.bean.MemBerLevel;
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


    /**
     * 查询首页信息
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_HOME_INFO)
    Call<Home> getHomeInfo(@FieldMap Map<String, String> map);


    /**
     * 修改密码
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.UPDATE_PWD)
    Call<BaseBean> updatePwd(@FieldMap Map<String, String> map);


    /**
     * 获取邮箱验证码
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_EMAIL_CODE)
    Call<BaseBean> getEmailCode(@FieldMap Map<String, String> map);


    /**
     * 获取个人资料
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_USER_INFO)
    Call<ResponseBody> getUserInfo(@FieldMap Map<String, String> map);


    /**
     * 获取加入过的更多学院
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_MORE_COLLEGE)
    Call<CollegeList> getMoreCollege(@FieldMap Map<String, String> map);


    /**
     * 修改密码
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.UPDATE_PWD2)
    Call<BaseBean> updatePwd2(@FieldMap Map<String, String> map);


    /**
     * 编辑学院
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.EDIT_COLLEGE)
    Call<BaseBean> editCollege(@FieldMap Map<String, String> map);


    /**
     * 会员等级设置
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.MEMBER_LEVEL_SETTING)
    Call<MemBerLevel> settingMemberLevel(@FieldMap Map<String, String> map);
}
