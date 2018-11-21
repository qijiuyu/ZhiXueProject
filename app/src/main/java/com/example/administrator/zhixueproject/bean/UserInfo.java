package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息
 */
public class UserInfo extends BaseBean {

    private UserInfoBean data;

    private int type;

    public UserInfoBean getData() {
        return data;
    }

    public void setData(UserInfoBean data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class UserInfoBean implements Serializable{
        private List<Colleges> colleges=new ArrayList<>();

        private UserBean user;

        public boolean status;

        private String token;

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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
