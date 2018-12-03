package com.example.administrator.zhixueproject.bean.eventBus;


public class PostEvent extends BaseEvent {


    /**
     * 贴子楼层回复
     */
    public static final int REPLY_POST = 4001;

    /**
     * 作业楼层回复
     */
    public static final int REPLY_WORK = 4002;

    /**
     * 作业楼层对人的回复
     */
    public static final int REPLY_WORK_COMMENT = 4003;

    /**
     * 贴子楼层对人的回复
     */
    public static final int REPLY_POST_COMMENT = 4004;

    /**
     *
     * 贴子发布成功
     */
    public static final int RELEASE_SUCCESS = 4005;
    /**
     * 回复贴子成功
     */
    public static final int COMMENT_SUCCESS = 4006;
    /**
     * 发布投票成功
     */
    public static final int RELEASE_VOTE_SUCCESS = 4007;
    /**
     * 发布活动成功
     */
    public static final int RELEASE_ACTIVITY_SUCCESS = 4008;


}
