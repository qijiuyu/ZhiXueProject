package com.example.administrator.zhixueproject.bean.live;

import com.example.administrator.zhixueproject.bean.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Live extends BaseBean {

    private LiveData data;

    public LiveData getData() {
        return data;
    }

    public void setData(LiveData data) {
        this.data = data;
    }

    public static class LiveData implements Serializable{

        private List<LiveList> postLiveList=new ArrayList<>();

        public List<LiveList> getPostLiveList() {
            return postLiveList;
        }

        public void setPostLiveList(List<LiveList> postLiveList) {
            this.postLiveList = postLiveList;
        }
    }


    public static class LiveList implements Serializable{
        private String userImg;
        private long postLivetime;
        private String postInfo;
        private String postName;
        private String topicName;
        private long postId;
        private String userName;
        private long postTopicId;
        private int postIsLive;
        private int postIsTop;
        private long postWriterId;
        private int postIsFree;
        private double postPrice;

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public long getPostLivetime() {
            return postLivetime;
        }

        public void setPostLivetime(long postLivetime) {
            this.postLivetime = postLivetime;
        }

        public String getPostInfo() {
            return postInfo;
        }

        public void setPostInfo(String postInfo) {
            this.postInfo = postInfo;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public long getPostId() {
            return postId;
        }

        public void setPostId(long postId) {
            this.postId = postId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public long getPostTopicId() {
            return postTopicId;
        }

        public void setPostTopicId(long postTopicId) {
            this.postTopicId = postTopicId;
        }

        public int getPostIsLive() {
            return postIsLive;
        }

        public void setPostIsLive(int postIsLive) {
            this.postIsLive = postIsLive;
        }

        public int getPostIsTop() {
            return postIsTop;
        }

        public void setPostIsTop(int postIsTop) {
            this.postIsTop = postIsTop;
        }

        public long getPostWriterId() {
            return postWriterId;
        }

        public void setPostWriterId(long postWriterId) {
            this.postWriterId = postWriterId;
        }

        public int getPostIsFree() {
            return postIsFree;
        }

        public void setPostIsFree(int postIsFree) {
            this.postIsFree = postIsFree;
        }

        public double getPostPrice() {
            return postPrice;
        }

        public void setPostPrice(double postPrice) {
            this.postPrice = postPrice;
        }
    }
}
