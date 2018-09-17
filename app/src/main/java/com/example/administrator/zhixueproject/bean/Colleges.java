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
}
