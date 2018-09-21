package com.example.administrator.zhixueproject.http;

public class HttpConstant {

    public static final String IP="http://kooboss.imwork.net/risenb-client-web/";

    //获取短信验证码
    public static final String GET_SMS_CODE="user/getSmsCode.do";

    //注册
    public static final String REGISTER="user/userRegister.do";

    //登陆
    public static final String LOGIN="user/login.do";

    //查询学院VIP数据
    public static final String GET_COLLETE_VIPS="college/getCollegeGradeList.do";

    //查询话题列表
    public static final String GET_TOPIC_LIST="topic/getTopicList.do";

    //查询首页信息
    public static final String GET_HOME_INFO="user/home.do";

    //修改密码
    public static final String UPDATE_PWD="user/updatePwdByMobile.do";
}
