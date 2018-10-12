package com.example.administrator.zhixueproject.http.api;

import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.BuyIness;
import com.example.administrator.zhixueproject.bean.CollegeList;
import com.example.administrator.zhixueproject.bean.ColleteVips;
import com.example.administrator.zhixueproject.bean.EntryGroup;
import com.example.administrator.zhixueproject.bean.GiveAccount;
import com.example.administrator.zhixueproject.bean.GiveScalAccount;
import com.example.administrator.zhixueproject.bean.Home;
import com.example.administrator.zhixueproject.bean.Medal;
import com.example.administrator.zhixueproject.bean.MemBerLevel;
import com.example.administrator.zhixueproject.bean.Notice;
import com.example.administrator.zhixueproject.bean.Post;
import com.example.administrator.zhixueproject.bean.QuestionAccount;
import com.example.administrator.zhixueproject.bean.TeacherBean;
import com.example.administrator.zhixueproject.bean.RecentEarning;
import com.example.administrator.zhixueproject.bean.TopicAccount;
import com.example.administrator.zhixueproject.bean.UserInfo;
import com.example.administrator.zhixueproject.bean.VipDetails;
import com.example.administrator.zhixueproject.bean.WithDraw;
import com.example.administrator.zhixueproject.bean.WithDrawInfo;
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
     * 微信登陆
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.WX_LOGIN)
    Call<UserInfo> wxLogin(@FieldMap Map<String, String> map);


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


    /**
     * 判断验证码是否正确
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.CHECK_SMS_CODE)
    Call<BaseBean> checkSmsCode(@FieldMap Map<String, String> map);

    /**
     * 自动登陆保持回话
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.AUTO_LOGIN)
    Call<UserInfo> autoLogin(@FieldMap Map<String, String> map);


    /**
     * 保存会员等级
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.SAVE_VIP_GRADE)
    Call<BaseBean> saveVipGrade(@FieldMap Map<String, String> map);

    /**
     * 获取勋章列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_MEDAL_LIST)
    Call<Medal> getMedalList(@FieldMap Map<String, String> map);


    /**
     * 编辑或保存勋章
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.SAVE_MEDAL)
    Call<ResponseBody> saveMedal(@FieldMap Map<String, String> map);


    /**
     * 删除勋章
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.DEL_MEDAL)
    Call<BaseBean> delMedal(@FieldMap Map<String, String> map);


    /**
     * 购买学院vip
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.BUY_VIP)
    Call<BaseBean> buyVip(@FieldMap Map<String, String> map);


    /**
     * 查询VIP申请明细
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_VIP_DETAILS)
    Call<VipDetails> getVipDetails(@FieldMap Map<String, String> map);


    /**
     * 友商购进列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.BUY_INESS_IN)
    Call<BuyIness> buyInessIn(@FieldMap Map<String, String> map);


    /**
     * 删除/取消代理友商
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.DEL_BUY_INESS)
    Call<BaseBean> delBuyIness(@FieldMap Map<String, String> map);


    /**
     * 友商售出列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.BUY_INESS_OUT)
    Call<BuyIness> buyInessOut(@FieldMap Map<String, String> map);


    /**
     * 获取讲师列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_TEACHER_LIST)
    Call<TeacherBean> getTeacherList(@FieldMap Map<String, String> map);


    /**
     * 添加友商售出
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.ADD_COOPERTE)
    Call<ResponseBody> addCooPerate(@FieldMap Map<String, String> map);



    /**
     * 获取近期收益
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_ACCOUNT)
    Call<RecentEarning> getAccount(@FieldMap Map<String, String> map);


    /**
     * 获取话题收益明细
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_TOPIC_ACCOUNT)
    Call<TopicAccount> getTopicAccount(@FieldMap Map<String, String> map);

    /**
     * 入群收益明细
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_ENTRY_GROUP_ACCOUNT)
    Call<EntryGroup> getEntryGroupAccount(@FieldMap Map<String, String> map);


    /**
     * 帖子收益明细
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_POST_ACCOUNT)
    Call<Post> getPostAccount(@FieldMap Map<String, String> map);


    /**
     * 打赏收益明细
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_GIVE_ACCOUNT)
    Call<GiveAccount> getGiveAccount(@FieldMap Map<String, String> map);


    /**
     * 打赏分成收益明细
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_GIVE_SCAL_ACCOUNT)
    Call<GiveScalAccount> getGiveScalAccount(@FieldMap Map<String, String> map);


    /**
     * 有偿提问收益
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_QUESTION_ACCOUNT)
    Call<QuestionAccount> getQuestionAccount(@FieldMap Map<String, String> map);


    /**
     * 提现明细列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_WITHDRAW)
    Call<WithDraw> getWithDraw(@FieldMap Map<String, String> map);


    /**
     * 获取提现页面相关信息
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_WITHDRAW_INFO)
    Call<WithDrawInfo> getWithDrawInfo(@FieldMap Map<String, String> map);


    /**
     * 申请提现
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.ADD_WITHDRAW)
    Call<BaseBean> addWithDraw(@FieldMap Map<String, String> map);


    /**
     * 获取公告列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_NOTICE_LIST)
    Call<Notice> getNoticeList(@FieldMap Map<String, String> map);


    /**
     * 删除公告
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.DELETE_NOTICE_BYID)
    Call<BaseBean> deleteNotice(@FieldMap Map<String, String> map);


    /**
     * 添加公告
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.ADD_NOTICE)
    Call<BaseBean> addNotice(@FieldMap Map<String, String> map);


    /**
     * 修改公告
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.UPDATE_NOTICE)
    Call<ResponseBody> updateNotice(@FieldMap Map<String, String> map);
}
