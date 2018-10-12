package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FeedBack extends BaseBean {

    private FeedBackData data;

    public FeedBackData getData() {
        return data;
    }

    public void setData(FeedBackData data) {
        this.data = data;
    }

    public static class FeedBackData implements Serializable{

        private List<FeedBackList> adviceList=new ArrayList<>();

        public List<FeedBackList> getAdviceList() {
            return adviceList;
        }

        public void setAdviceList(List<FeedBackList> adviceList) {
            this.adviceList = adviceList;
        }
    }


    public static class FeedBackList implements Serializable{
        private String adviceContent;
        private int adviceId;
        private String adviceCreationTime;
        private int adviceReadyn;
        private String userPhone;
        private String userEmail;
        private String userName;
        private int adviceType;

        public String getAdviceContent() {
            return adviceContent;
        }

        public void setAdviceContent(String adviceContent) {
            this.adviceContent = adviceContent;
        }

        public int getAdviceId() {
            return adviceId;
        }

        public void setAdviceId(int adviceId) {
            this.adviceId = adviceId;
        }

        public String getAdviceCreationTime() {
            return adviceCreationTime;
        }

        public void setAdviceCreationTime(String adviceCreationTime) {
            this.adviceCreationTime = adviceCreationTime;
        }

        public int getAdviceReadyn() {
            return adviceReadyn;
        }

        public void setAdviceReadyn(int adviceReadyn) {
            this.adviceReadyn = adviceReadyn;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getAdviceType() {
            return adviceType;
        }

        public void setAdviceType(int adviceType) {
            this.adviceType = adviceType;
        }
    }
}
