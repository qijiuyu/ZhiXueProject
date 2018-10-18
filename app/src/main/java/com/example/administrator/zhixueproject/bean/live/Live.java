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
        private String postLivetime;
        private String postInfo;
        private String postName;
        private String topicName;
        private long postId;
        private String userName;
        private long topicId;

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getPostLivetime() {
            return postLivetime;
        }

        public void setPostLivetime(String postLivetime) {
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

        public long getTopicId() {
            return topicId;
        }

        public void setTopicId(long topicId) {
            this.topicId = topicId;
        }
    }
}
