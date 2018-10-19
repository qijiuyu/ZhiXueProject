package com.example.administrator.zhixueproject.http.method;

import android.os.Handler;

import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.BuyIness;
import com.example.administrator.zhixueproject.bean.CollegeList;
import com.example.administrator.zhixueproject.bean.ColleteVips;
import com.example.administrator.zhixueproject.bean.EntryGroup;
import com.example.administrator.zhixueproject.bean.FeedBack;
import com.example.administrator.zhixueproject.bean.GiveAccount;
import com.example.administrator.zhixueproject.bean.GiveScalAccount;
import com.example.administrator.zhixueproject.bean.Home;
import com.example.administrator.zhixueproject.bean.Medal;
import com.example.administrator.zhixueproject.bean.MemBerLevel;
import com.example.administrator.zhixueproject.bean.Notice;
import com.example.administrator.zhixueproject.bean.Post;
import com.example.administrator.zhixueproject.bean.QuestionAccount;
import com.example.administrator.zhixueproject.bean.Report;
import com.example.administrator.zhixueproject.bean.TeacherBean;
import com.example.administrator.zhixueproject.bean.RecentEarning;
import com.example.administrator.zhixueproject.bean.TopicAccount;
import com.example.administrator.zhixueproject.bean.UploadFile;
import com.example.administrator.zhixueproject.bean.UserInfo;
import com.example.administrator.zhixueproject.bean.VipDetails;
import com.example.administrator.zhixueproject.bean.WithDraw;
import com.example.administrator.zhixueproject.bean.WithDrawInfo;
import com.example.administrator.zhixueproject.bean.live.Live;
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
     * 微信登陆
     * @param handler
     */
    public static void wxLogin(String opendId,String isRegister,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("opendId",opendId);
        map.put("isRegister",isRegister);
        Http.getRetrofit().create(HttpApi1.class).wxLogin(map).enqueue(new Callback<UserInfo>() {
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
    public static void getHomeInfo(final Handler handler) {
        Map<String, String> map = new HashMap<>();
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
    public static void getUserInfo(final Handler handler) {
        Map<String, String> map = new HashMap<>();
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
    public static void getMoreCollege(final Handler handler) {
        Map<String, String> map = new HashMap<>();
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
    public static void editCollege(String collegeName,String collegeUser,String collegeAccBankinfo,String collegeAccBank,String collegeBackimg,int scale,int collegeType,String collegePrice,int collegeDelYn,String collegeInfo,final Handler handler) {
        Map<String, String> map = new HashMap<>();
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
    public static void settingMemberLevel(final Handler handler) {
        Map<String, String> map = new HashMap<>();
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


    /**
     * 自动登陆保持回话
     * @param handler
     */
    public static void autoLogin(String mobile,String pwd,String sign,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile",mobile);
        map.put("pwd",pwd);
        map.put("sign",sign);
        Http.getRetrofit().create(HttpApi1.class).autoLogin(map).enqueue(new Callback<UserInfo>() {
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                try {
                    sendMessage(handler, HandlerConstant1.AUTO_LOGIN_SUCCESS, response.body());
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
     * 保存会员等级
     * @param handler
     */
    public static void saveVipGrade(String userCollegegradeId,String userCollegegradeName,String userCollegegradePoints,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("userCollegegradeId",userCollegegradeId);
        map.put("userCollegegradeName",userCollegegradeName);
        map.put("userCollegegradePoints",userCollegegradePoints);
        Http.getRetrofit().create(HttpApi1.class).saveVipGrade(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.SAVE_VIP_GRADE_SUCCESS, response.body());
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
     * 获取勋章列表
     * @param timestamp
     * @param page
     * @param handler
     */
    public static void getMedalList(String timestamp,int page,int limit,final int index,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("timestamp",timestamp);
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getMedalList(map).enqueue(new Callback<Medal>() {
            public void onResponse(Call<Medal> call, Response<Medal> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<Medal> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 编辑或保存勋章
     * @param handler
     */
    public static void saveMedal(long medalTypeId,String medalTypeName,String medalTypeInfo,String medalTypeMig,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        if(medalTypeId!=0){
            map.put("medalTypeId",medalTypeId+"");
        }
        map.put("medalTypeName",medalTypeName);
        map.put("medalTypeInfo",medalTypeInfo);
        map.put("medalTypeMig",medalTypeMig);
        Http.getRetrofit().create(HttpApi1.class).saveMedal(map).enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    sendMessage(handler, HandlerConstant1.SAVE_MEDAL_SUCCESS, response.body().string());
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
     * 删除勋章
     * @param medalTypeId
     * @param handler
     */
    public static void delMedal(long medalTypeId,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("medalTypeId",medalTypeId+"");
        Http.getRetrofit().create(HttpApi1.class).delMedal(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.DEL_MEDAL_SUCCESS, response.body());
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
     * 购买学院vip
     */
    public static void buyVip(long gradeId,int gradeType,String gradeLimit,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("gradeId",gradeId+"");
        map.put("gradeType",gradeType+"");
        map.put("gradeLimit",gradeLimit);
        Http.getRetrofit().create(HttpApi1.class).buyVip(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.BUY_VIPS_SUCCESS, response.body());
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
     * 查询VIP申请明细
     * @param timestamp
     * @param page
     * @param limit
     * @param handler
     */
    public static void getVipDetails(String timestamp,int page,int limit,final int index,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("timestamp",timestamp);
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getVipDetails(map).enqueue(new Callback<VipDetails>() {
            public void onResponse(Call<VipDetails> call, Response<VipDetails> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<VipDetails> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 友商购进列表
     * @param timestamp
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void buyInessIn(String timestamp,int page,int limit,final int index,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("timestamp",timestamp);
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).buyInessIn(map).enqueue(new Callback<BuyIness>() {
            public void onResponse(Call<BuyIness> call, Response<BuyIness> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<BuyIness> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 删除/取消代理友商
     * @param buyTopicId
     * @param handler
     */
    public static void delBuyIness(long buyTopicId,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("buyTopicId",buyTopicId+"");
        Http.getRetrofit().create(HttpApi1.class).delBuyIness(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.DEL_BUY_INESS_SUCCESS, response.body());
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
     * 友商售出列表
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void buyInessOut(int page,int limit,final int index,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).buyInessOut(map).enqueue(new Callback<BuyIness>() {
            public void onResponse(Call<BuyIness> call, Response<BuyIness> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<BuyIness> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 获取近期收益
     * @param startDate
     * @param endDate
     * @param handler
     */
    public static void getAccount(String startDate,String endDate,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        Http.getRetrofit().create(HttpApi1.class).getAccount(map).enqueue(new Callback<RecentEarning>() {
            public void onResponse(Call<RecentEarning> call, Response<RecentEarning> response) {
                try {
                    sendMessage(handler, HandlerConstant1.GET_ACCOUNT_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<RecentEarning> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 获取话题收益明细
     * @param startDate
     * @param endDate
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getTopicAccount(String startDate, String endDate, int page, int limit, final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getTopicAccount(map).enqueue(new Callback<TopicAccount>() {
            public void onResponse(Call<TopicAccount> call, Response<TopicAccount> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<TopicAccount> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 获取讲师列表
     * @param key
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getTeacherList(String key,int page,int limit,final int index,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("key",key);
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getTeacherList(map).enqueue(new Callback<TeacherBean>() {
            public void onResponse(Call<TeacherBean> call, Response<TeacherBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<TeacherBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 添加友商售出
     * @param topicId
     * @param newWriterId
     * @param Months
     * @param handler
     */
    public static void addCooPerate(long topicId,long newWriterId,String Months,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("topicId",topicId+"");
        map.put("newWriterId",newWriterId+"");
        map.put("Months",Months);
        Http.getRetrofit().create(HttpApi1.class).addCooPerate(map).enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    sendMessage(handler, HandlerConstant1.ADD_COOPERATE_SUCCESS, response.body().string());
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
     * 入群收益明细
     * @param startDate
     * @param endDate
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getEntryGroupAccount(String startDate, String endDate, int page, int limit,final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getEntryGroupAccount(map).enqueue(new Callback<EntryGroup>() {
            public void onResponse(Call<EntryGroup> call, Response<EntryGroup> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<EntryGroup> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 帖子收益明细
     * @param startDate
     * @param endDate
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getPostAccount(String startDate, String endDate, int page, int limit,final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getPostAccount(map).enqueue(new Callback<Post>() {
            public void onResponse(Call<Post> call, Response<Post> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<Post> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 打赏收益明细
     * @param startDate
     * @param endDate
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getGiveAccount(String startDate, String endDate, int page, int limit,final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getGiveAccount(map).enqueue(new Callback<GiveAccount>() {
            public void onResponse(Call<GiveAccount> call, Response<GiveAccount> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<GiveAccount> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }



    /**
     * 打赏分成收益明细
     * @param startDate
     * @param endDate
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getGiveScalAccount(String startDate, String endDate, int page, int limit, final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getGiveScalAccount(map).enqueue(new Callback<GiveScalAccount>() {
            public void onResponse(Call<GiveScalAccount> call, Response<GiveScalAccount> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<GiveScalAccount> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 有偿提问收益
     * @param startDate
     * @param endDate
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getQuestionAccount(String startDate, String endDate, int page, int limit,final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getQuestionAccount(map).enqueue(new Callback<QuestionAccount>() {
            public void onResponse(Call<QuestionAccount> call, Response<QuestionAccount> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<QuestionAccount> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 提现明细列表
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getWithDraw(int page, int limit, final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getWithDraw(map).enqueue(new Callback<WithDraw>() {
            public void onResponse(Call<WithDraw> call, Response<WithDraw> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<WithDraw> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 获取提现页面相关信息
     * @param handler
     */
    public static void getWithDrawInfo(final Handler handler) {
        Map<String, String> map = new HashMap<>();
        Http.getRetrofit().create(HttpApi1.class).getWithDrawInfo(map).enqueue(new Callback<WithDrawInfo>() {
            public void onResponse(Call<WithDrawInfo> call, Response<WithDrawInfo> response) {
                try {
                    sendMessage(handler, HandlerConstant1.GET_WITHDRAW_INFO_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<WithDrawInfo> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 申请提现
     * @param cashValue
     * @param handler
     */
    public static void addWithDraw(String cashValue,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("cashValue",cashValue);
        Http.getRetrofit().create(HttpApi1.class).addWithDraw(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.ADD_WITHDRAW_SUCCESS, response.body());
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
     * 获取公告列表
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getNoticeList(int page, int limit,final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getNoticeList(map).enqueue(new Callback<Notice>() {
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<Notice> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 删除公告
     * @param noticeId
     * @param handler
     */
    public static void deleteNotice(long noticeId,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("noticeId",noticeId+"");
        Http.getRetrofit().create(HttpApi1.class).deleteNotice(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.DELETE_NOTICE_SUCCESS, response.body());
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
     * 添加公告
     * @param noticeTitle
     * @param noticeInfo
     * @param handler
     */
    public static void addNotice(String noticeTitle,String noticeInfo,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("noticeTitle",noticeTitle);
        map.put("noticeInfo",noticeInfo);
        Http.getRetrofit().create(HttpApi1.class).addNotice(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.ADD_NOTICE_SUCCESS, response.body());
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
     * 修改公告
     * @param noticeId
     * @param noticeTitle
     * @param noticeInfo
     * @param handler
     */
    public static void updateNotice(long noticeId,String noticeTitle,String noticeInfo,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("noticeId",noticeId+"");
        map.put("noticeTitle",noticeTitle);
        map.put("noticeInfo",noticeInfo);
        Http.getRetrofit().create(HttpApi1.class).updateNotice(map).enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    sendMessage(handler, HandlerConstant1.UPDATE_NOTICE_SUCCESS, response.body().string());
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
     * 获取反馈列表
     * @param key
     * @param page
     * @param limit
     * @param handler
     */
    public static void getFeedBack(String key,int page,int limit,final int index,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("key",key);
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getFeedBack(map).enqueue(new Callback<FeedBack>() {
            public void onResponse(Call<FeedBack> call, Response<FeedBack> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<FeedBack> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 添加意见反馈
     * @param adviceContent
     * @param handler
     */
    public static void addFeedBack(String adviceContent,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("adviceContent",adviceContent);
        Http.getRetrofit().create(HttpApi1.class).addFeedBack(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.ADD_FEEDBACK_SUCCESS, response.body());
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
     * 添加意见设置已读
     * @param adviceId
     * @param handler
     */
    public static void feedbackIsRead(int adviceId,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("adviceId",adviceId+"");
        Http.getRetrofit().create(HttpApi1.class).feedbackIsRead(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.SET_FEEDBACK_ISREAD_SUCCESS, response.body());
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
     * 查询举报列表
     * @param complaintType
     * @param orderBy
     * @param page
     * @param limit
     * @param handler
     */
    public static void getReportList(int complaintType,String orderBy,int page,int limit,final int index,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("complaintType",complaintType+"");
        map.put("orderBy",orderBy);
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getReportList(map).enqueue(new Callback<Report>() {
            public void onResponse(Call<Report> call, Response<Report> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<Report> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 查询直播列表
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getLiveList(int page,int limit,final int index,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi1.class).getLiveList(map).enqueue(new Callback<Live>() {
            public void onResponse(Call<Live> call, Response<Live> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<Live> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 添加直播预告
     * @param postName
     * @param postTopicId
     * @param postWriterId
     * @param postLivetime
     * @param postIsFree
     * @param postPrice
     * @param postIsTop
     * @param postInfo
     * @param handler
     */
    public static void addLive(String postName,long postTopicId,long postWriterId,String postLivetime,int postIsFree,String postPrice,int postIsTop,String postInfo,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("postName",postName);
        map.put("postTopicId",postTopicId+"");
        map.put("postWriterId",postWriterId+"");
        map.put("postLivetime",postLivetime);
        map.put("postIsFree",postIsFree+"");
        if(postIsFree==2){
            map.put("postPrice",postPrice);
        }
        map.put("postIsTop",postIsTop+"");
        map.put("postInfo",postInfo);
        Http.getRetrofit().create(HttpApi1.class).addLive(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.ADD_LIVE_SUCCESS, response.body());
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
     * 删除直播预告
     * @param postId
     * @param handler
     */
    public static void deleteLive(long postId,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("postId",postId+"");
        Http.getRetrofit().create(HttpApi1.class).deleteLive(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant1.DELETE_LIVE_SUCEESSS, response.body());
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
