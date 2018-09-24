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

    // 修改个人资料
    public static final String MODIFY_USER_INFO ="user/update.do" ;

    //查询个人资料
    public static final String GET_USER_INFO="user/getInformation.do";

    //获取邮箱验证码
    public static final String GET_EMAIL_CODE="user/getEmailCode.do";

    //上传文件
    public static final String UPDATE_FILES="sys/uploadFiles.do";

    //用户加入过的更多学院
    public static final String GET_MORE_COLLEGE="user/getMoreUserCollege.do";
}
