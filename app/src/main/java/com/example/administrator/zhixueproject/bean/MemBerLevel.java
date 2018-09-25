package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 会员等级
 */
public class MemBerLevel extends BaseBean {

    private MemberLevelBean data;

    public MemberLevelBean getData() {
        return data;
    }

    public void setData(MemberLevelBean data) {
        this.data = data;
    }

    public static class MemberLevelBean implements Serializable{

        private List<MemberLevelList> userCollegeList;

        public List<MemberLevelList> getUserCollegeList() {
            return userCollegeList;
        }

        public void setUserCollegeList(List<MemberLevelList> userCollegeList) {
            this.userCollegeList = userCollegeList;
        }
    }

    public static class MemberLevelList implements Serializable{
        private int userCollegegradeId;
        private String userCollegegradeName;
        private int userCollegegradePoints;

        public int getUserCollegegradeId() {
            return userCollegegradeId;
        }

        public void setUserCollegegradeId(int userCollegegradeId) {
            this.userCollegegradeId = userCollegegradeId;
        }

        public String getUserCollegegradeName() {
            return userCollegegradeName;
        }

        public void setUserCollegegradeName(String userCollegegradeName) {
            this.userCollegegradeName = userCollegegradeName;
        }

        public int getUserCollegegradePoints() {
            return userCollegegradePoints;
        }

        public void setUserCollegegradePoints(int userCollegegradePoints) {
            this.userCollegegradePoints = userCollegegradePoints;
        }
    }
}
