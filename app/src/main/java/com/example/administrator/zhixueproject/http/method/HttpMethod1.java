package com.example.administrator.zhixueproject.http.method;

import android.os.Handler;

import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.CollegeList;
import com.example.administrator.zhixueproject.bean.ColleteVips;
import com.example.administrator.zhixueproject.bean.Home;
import com.example.administrator.zhixueproject.bean.MemBerLevel;
import com.example.administrator.zhixueproject.bean.UploadFile;
import com.example.administrator.zhixueproject.bean.UserInfo;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.api.HttpApi1;
import com.example.administrator.zhixueproject.http.base.BaseRequst;
import com.example.administrator.zhixueproject.http.base.Http;
import com.example.administrator.zhixueproject.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.ResponseBody;
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


    /**
     * 登陆
     * @param handler
     */
    public static void login(String mobile,String pwd,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile",mobile);
        map.put("pwd",pwd);
        Http.getRetrofit().create(HttpApi1.class).login(map).enqueue(new Callback<UserInfo>() {
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                try {
                    sendMessage(handler, HandlerConstant1.LOGIN_SUCCESS, response.body());
                    //保存sessionId
                    saveSessionId(response.headers());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<UserInfo> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 查询学院vip等级
     * @param handler
     */
    public static void getCollegeVips(final Handler handler) {
        Map<String, String> map = new HashMap<>();
        Http.getRetrofit().create(HttpApi1.class).getCollegeVips(map).enqueue(new Callback<ColleteVips>() {
            public void onResponse(Call<ColleteVips> call, Response<ColleteVips> response) {
                try {
                    sendMessage(handler, HandlerConstant1.GET_COLLETE_VIPS_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<ColleteVips> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 查询首页信息
     * @param handler
     */
    public static void getHomeInfo(String c,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("c",c);
        Http.getRetrofit().create(HttpApi1.class).getHomeInfo(map).enqueue(new Callback<Home>() {
            public void onResponse(Call<Home> call, Response<Home> response) {
                try {
                    sendMessage(handler, HandlerConstant1.GET_HOME_INFO_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<Home> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 修改密码
     * @param handler
     */
    public static void updatePwd(String mobile,String newPwd,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile",mobile);
        map.put("newPwd",newPwd);
        Http.getRetrofit().create(HttpApi1.class).updatePwd(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.UPDATE_PWD_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<BaseBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 获取邮箱验证码
     * @param handler
     */
    public static void getEmailCode(String email,String type,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("email",email);
        map.put("type",type);
        Http.getRetrofit().create(HttpApi1.class).getEmailCode(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.GET_EMAIL_CODE_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<BaseBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 获取个人资料
     * @param handler
     */
    public static void getUserInfo(String c,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("c",c);
        Http.getRetrofit().create(HttpApi1.class).getUserInfo(map).enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    sendMessage(handler, HandlerConstant1.GET_USER_INFO_SUCCESS, response.body().string());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 上传头像
     */
    public static void uploadFile(String url, List<File> list, final Handler handler) {
        Http.upLoadFile(url, "files", list, null, new okhttp3.Callback() {
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                final String body = response.body().string();
                LogUtils.e(body+"____________________");
                final UploadFile uploadFile= MyApplication.gson.fromJson(body,UploadFile.class);
                sendMessage(handler, HandlerConstant1.UPLOAD_HEAD_SUCCESS, uploadFile);
            }

            public void onFailure(okhttp3.Call call, IOException e) {
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 获取加入过的更多学院
     * @param handler
     */
    public static void getMoreCollege(String c,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("c",c);
        Http.getRetrofit().create(HttpApi1.class).getMoreCollege(map).enqueue(new Callback<CollegeList>() {
            public void onResponse(Call<CollegeList> call, Response<CollegeList> response) {
                try {
                    sendMessage(handler, HandlerConstant1.GET_MORE_COLLEGE_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<CollegeList> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 修改密码
     * @param handler
     */
    public static void updatePwd2(String pwd,String newPwd,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("pwd",pwd);
        map.put("newPwd",newPwd);
        Http.getRetrofit().create(HttpApi1.class).updatePwd2(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.UPDATE_PWD2_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<BaseBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 编辑学院
     * @param c
     * @param collegeId
     * @param collegeName
     * @param collegeUser
     * @param collegeAccBankinfo
     * @param collegeAccBank
     * @param collegeBackimg
     * @param scale
     * @param collegeType
     * @param collegePrice
     * @param collegeDelYn
     * @param collegeInfo
     * @param handler
     */
    public static void editCollege(int c,int collegeId,String collegeName,String collegeUser,String collegeAccBankinfo,String collegeAccBank,String collegeBackimg,int scale,int collegeType,String collegePrice,int collegeDelYn,String collegeInfo,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("c",c+"");
        map.put("collegeId",collegeId+"");
        map.put("collegeName",collegeName);
        map.put("collegeUser",collegeUser);
        map.put("collegeAccBankinfo",collegeAccBankinfo);
        map.put("collegeAccBank",collegeAccBank);
        map.put("collegeBackimg",collegeBackimg);
        map.put("scale",scale+"");
        map.put("collegeType",collegeType+"");
        map.put("collegePrice",collegePrice);
        map.put("collegeDelYn",collegeDelYn+"");
        map.put("collegeInfo",collegeInfo);
        Http.getRetrofit().create(HttpApi1.class).editCollege(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.EDIT_COLLEGE_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<BaseBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 会员等级设置
     * @param handler
     */
    public static void settingMemberLevel(int c,int collegeId,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("c",c+"");
        map.put("collegeId",collegeId+"");
        Http.getRetrofit().create(HttpApi1.class).settingMemberLevel(map).enqueue(new Callback<MemBerLevel>() {
            public void onResponse(Call<MemBerLevel> call, Response<MemBerLevel> response) {
                try {
                    sendMessage(handler, HandlerConstant1.SETTING_MEMBER_LEVEL_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<MemBerLevel> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 判断验证码是否正确
     * @param handler
     */
    public static void checkSmsCode(String account,String code,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("account",account);
        map.put("code",code);
        Http.getRetrofit().create(HttpApi1.class).checkSmsCode(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.CHECK_SMS_CODE_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<BaseBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }

}
