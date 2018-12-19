package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/18.
 */

public class ReportDetails extends BaseBean {

    private dataBean data;

    public dataBean getData() {
        return data;
    }

    public void setData(dataBean data) {
        this.data = data;
    }

    public static class dataBean implements Serializable{
        private postBean post;

        private List<listBean> complaintList=new ArrayList<>();

        public List<listBean> getComplaintList() {
            return complaintList;
        }

        public void setComplaintList(List<listBean> complaintList) {
            this.complaintList = complaintList;
        }

        public postBean getPost() {
            return post;
        }

        public void setPost(postBean post) {
            this.post = post;
        }
    }

    public static class postBean implements Serializable{
        private String postContent;

        public String getPostContent() {
            return postContent;
        }

        public void setPostContent(String postContent) {
            this.postContent = postContent;
        }
    }

    public static class listBean implements Serializable{
        private String userImg;
        private long complaintId;
        private String complaintInfo;
        private String userName;
        private int complaintInfoType;

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public long getComplaintId() {
            return complaintId;
        }

        public void setComplaintId(long complaintId) {
            this.complaintId = complaintId;
        }

        public String getComplaintInfo() {
            return complaintInfo;
        }

        public void setComplaintInfo(String complaintInfo) {
            this.complaintInfo = complaintInfo;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getComplaintInfoType() {
            return complaintInfoType;
        }

        public void setComplaintInfoType(int complaintInfoType) {
            this.complaintInfoType = complaintInfoType;
        }
    }
}
