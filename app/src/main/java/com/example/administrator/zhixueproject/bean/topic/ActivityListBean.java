package com.example.administrator.zhixueproject.bean.topic;

import com.example.administrator.zhixueproject.bean.BaseBean;

import java.io.Serializable;



public class ActivityListBean extends BaseBean implements Serializable {

    /**
     * activityId : 1799
     * postJoinNum : 1
     * topicId : 340
     * activityName : 930活动测试
     * topicName : 109话题添加测试1631
     * startTime : 2017-08-29 10:50
     * endTime : 2017-09-30 10:50
     * postPicture : 图片地址
     */

    private int activityId;
    private int postJoinNum;
    private int topicId;
    private String activityName;
    private String topicName;
    private String startTime;
    private String endTime;
    private String postPicture;
    private String activityType;//活动类型
    private int postIsTop; //是否置顶(0否 1是)
    private String postWriterName;//发布人名字
    private String userName;



    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }


    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getPostJoinNum() {
        return postJoinNum;
    }

    public void setPostJoinNum(int postJoinNum) {
        this.postJoinNum = postJoinNum;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPostPicture() {
        return postPicture;
    }

    public void setPostPicture(String postPicture) {
        this.postPicture = postPicture;
    }

    public int getPostIsTop() {
        return postIsTop;
    }

    public void setPostIsTop(int postIsTop) {
        this.postIsTop = postIsTop;
    }

    public String getPostWriterName() {
        return postWriterName;
    }

    public void setPostWriterName(String postWriterName) {
        this.postWriterName = postWriterName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
