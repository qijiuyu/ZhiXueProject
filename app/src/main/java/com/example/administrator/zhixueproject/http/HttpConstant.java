package com.example.administrator.zhixueproject.http;

public class HttpConstant {

    public static final String IP="http://kooboss.imwork.net/risenb-client-web/";

    //微信的appid
    public static final String WX_APPID="wxf2413139ede45239";
    public static final String WX_APPSECRET="8083f3007ecb2540b65538946e3d5724";

    //获取短信验证码
    public static final String GET_SMS_CODE="user/getSmsCode.do";

    //注册
    public static final String REGISTER="user/userRegister.do";

    //登陆
    public static final String LOGIN="user/login.do";

    //微信登陆
    public static final String WX_LOGIN="user/weChatLogin.do";

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

    //修改密码
    public static final String UPDATE_PWD2="user/updatePwd.do";

    //编辑学院
    public static final String EDIT_COLLEGE="college/editCollege.do";

    //会员等级设置
    public static final String MEMBER_LEVEL_SETTING="college/updateVipGrade.do";

    //判断验证码是否正确
    public static final String CHECK_SMS_CODE="user/checkCode.do";

    //自动登录(会话保持)
    public static final String AUTO_LOGIN="user/autoLogin.do";

    //保存会员等级
    public static final String SAVE_VIP_GRADE="college/saveVipGrade.do";

    //获取勋章列表
    public static final String GET_MEDAL_LIST="medalType/getMedalType.do";

    //编辑或保存勋章
    public static final String SAVE_MEDAL="medalType/saveMedalType.do";

    //删除勋章
    public static final String DEL_MEDAL="medalType/delMedalType.do";

    //购买vip
    public static final String BUY_VIP="college/buyCollegeGrade.do";

    //查询vip明细
    public static final String GET_VIP_DETAILS="college/getVipDetail.do";

    //友商购进列表(
    public static final String BUY_INESS_IN="buyTopic/getBuyInList.do";

    //删除/取消代理友商
    public static final String DEL_BUY_INESS="buyTopic/delBuyTopic.do";

    //友商售出列表
    public static final String BUY_INESS_OUT="buyTopic/getBuyOutList.do";

    //获取讲师列表
    public static final String GET_TEACHER_LIST="attendance/getTeacherList.do";

    //添加友商售出
    public static final String ADD_COOPERTE="buyTopic/addBuyTopic.do";

    // 话题上下架
    public static final String IS_UP_OR_DOWN = "topic/isUpOrDown.do";

    // 话题排序
    public static final String UPDATE_SORT = "topic/updateSort.do";

    // 添加话题
    public static final String ADD_TOPIC = "topic/addTopic.do";

    //修改话题
    public static final String UPDATE_TOPIC = "topic/updateTopic.do";
    //获取近期收益
    public static final String GET_ACCOUNT="account/getAccount.do";

    //获取话题收益明细
    public static final String GET_TOPIC_ACCOUNT="account/getTopicAccount.do";

    // 获取帖子列表
    public static final String GET_POST_LIST = "post/getPostList.do";

    //入群收益明细
    public static final String GET_ENTRY_GROUP_ACCOUNT="account/getCollegeAccount.do";

    //帖子明细
    public static final String GET_POST_ACCOUNT="account/getPostAccount.do";

    //打赏收益
    public static final String GET_GIVE_ACCOUNT="account/getGiveAccount.do";

    //打赏分成收益
    public static final String GET_GIVE_SCAL_ACCOUNT="account/getGiveScalAccount.do";

    //有偿提问收益
    public static final String GET_QUESTION_ACCOUNT="account/getYouChangAccount.do";

    //获取提现明细列表
    public static final String GET_WITHDRAW="cashRecord/getCashRecordList.do";

    //发布帖子
    public static final String ADD_POST = "post/addPost.do";

    //修改帖子
    public static final String UPDATE_POST = "post/updatePost.do";

    // 添加活动
    public static final String ADD_ACTIVITY = "activity/addActivity.do";

    // 修改活动
    public static final String UPDATE_ACTIVITY = "activity/updateActivity.do";

    //获取提现页面相关信息
    public static final String GET_WITHDRAW_INFO="cashRecord/getCashInfo.do";

    //申请提现
    public static final String ADD_WITHDRAW="cashRecord/addCashRecord.do";

    //获取公告列表
    public static final String GET_NOTICE_LIST="notice/getNoticeList.do";

    //删除公告
    public static final String DELETE_NOTICE_BYID="notice/delNotice.do";

    //添加公告
    public static final String ADD_NOTICE="notice/addNotice.do";

    //修改公告
    public static final String UPDATE_NOTICE="notice/updateNotice.do";

    //获取反馈列表
    public static final String GET_FEEDBACK_LIST="advice/getAdviceList.do";

    //添加意见反馈
    public static final String ADD_FEEDBACK="advice/addAdvice.do";

    // 添加投票
    public static final String ADD_VOTE = "vote/addVote.do";

    // 评论帖子
    public static final String COMMENT_POST = "post/commentPost.do";

    // 回复楼层
    public static final String COMMENT_REPLY = "post/commentReply.do";

    // 获取帖子详情
    public static final String GET_POST_DETAIL = "post/getPostDetail.do";

    //意见反馈设置已读
    public static final String FEEDBACK_IS_READ="advice/isRead.do";

    //获取有偿提问帖子详情
    public static final String GET_YOUCHANG_DETAIL = "post/getYouChangDetail.do";

    //查询举报列表
    public static final String GET_REPORT_LIST="complaint/getComplaint.do";
}
