package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 */
public class UserInfo extends BaseBean {

    private UserInfoBean data;

    public UserInfoBean getData() {
        return data;
    }

    public void setData(UserInfoBean data) {
        this.data = data;
    }

    public static class UserInfoBean implements Serializable{
        private List<Colleges> colleges;

        private UserBean user;

        public boolean status;

        public List<Colleges> getColleges() {
            return colleges;
        }

        public void setColleges(List<Colleges> colleges) {
            this.colleges = colleges;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
}
