package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/29.
 */

public class Teacher implements Serializable {

    private String userName;
    private long teacherId;
    private String userImg;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
