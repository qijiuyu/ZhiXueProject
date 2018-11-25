package com.example.administrator.zhixueproject.bean;

/**
 * 学院
 */
public class Colleges {
    //学院logo
    private String collegeLogo;
    //学院名称
    private String collegeName;
    //学院欢迎语
    private String collegeBanner;
    //学院id
    private int collegeId;
    //2：可以退出，   1：可以编辑
    private int type=1;

    public String getCollegeLogo() {
        return collegeLogo;
    }

    public void setCollegeLogo(String collegeLogo) {
        this.collegeLogo = collegeLogo;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeBanner() {
        return collegeBanner;
    }

    public void setCollegeBanner(String collegeBanner) {
        this.collegeBanner = collegeBanner;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
