package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;

/**
 * 首页数据
 */
public class Home extends BaseBean {

    private collegeBean data;

    public collegeBean getData() {
        return data;
    }

    public void setData(collegeBean data) {
        this.data = data;
    }

    public static class collegeBean implements Serializable{

        private HomeBean college;

        public HomeBean getCollege() {
            return college;
        }

        public void setCollege(HomeBean college) {
            this.college = college;
        }
    }
    public static class HomeBean implements Serializable{
        private String collegeAccBank;
        private String collegeAccBankinfo;
        private String collegeAccPwd;
        private String collegeBackimg;
        private double collegeBalance;
        private String collegeBanner;
        private String collegeCode;
        private long collegeCreationTime;
        private int collegeDelYn;
        private String collegeEmail;
        private int collegeGrade;
        private String collegeGradeImg;
        private long collegeGradetime;
        private int collegeId;
        private double collegeIncomes;
        private String collegeInfo;
        private String collegeLoginPwd;
        private String collegeLogo;
        private String collegeName;
        private String collegePhone;
        private int collegePrice;
        private int collegeType;
        private String collegeUser;
        private int collegeZxScale;
        private int scale;
        private int sort;
        private String updatetime;
        private String updator;
        private String userUrl;
        private String userUrlImgCode;

        public String getCollegeAccBank() {
            return collegeAccBank;
        }

        public void setCollegeAccBank(String collegeAccBank) {
            this.collegeAccBank = collegeAccBank;
        }

        public String getCollegeAccBankinfo() {
            return collegeAccBankinfo;
        }

        public void setCollegeAccBankinfo(String collegeAccBankinfo) {
            this.collegeAccBankinfo = collegeAccBankinfo;
        }

        public String getCollegeAccPwd() {
            return collegeAccPwd;
        }

        public void setCollegeAccPwd(String collegeAccPwd) {
            this.collegeAccPwd = collegeAccPwd;
        }

        public String getCollegeBackimg() {
            return collegeBackimg;
        }

        public void setCollegeBackimg(String collegeBackimg) {
            this.collegeBackimg = collegeBackimg;
        }

        public double getCollegeBalance() {
            return collegeBalance;
        }

        public void setCollegeBalance(double collegeBalance) {
            this.collegeBalance = collegeBalance;
        }

        public String getCollegeBanner() {
            return collegeBanner;
        }

        public void setCollegeBanner(String collegeBanner) {
            this.collegeBanner = collegeBanner;
        }

        public String getCollegeCode() {
            return collegeCode;
        }

        public void setCollegeCode(String collegeCode) {
            this.collegeCode = collegeCode;
        }

        public long getCollegeCreationTime() {
            return collegeCreationTime;
        }

        public void setCollegeCreationTime(long collegeCreationTime) {
            this.collegeCreationTime = collegeCreationTime;
        }

        public int getCollegeDelYn() {
            return collegeDelYn;
        }

        public void setCollegeDelYn(int collegeDelYn) {
            this.collegeDelYn = collegeDelYn;
        }

        public String getCollegeEmail() {
            return collegeEmail;
        }

        public void setCollegeEmail(String collegeEmail) {
            this.collegeEmail = collegeEmail;
        }

        public int getCollegeGrade() {
            return collegeGrade;
        }

        public void setCollegeGrade(int collegeGrade) {
            this.collegeGrade = collegeGrade;
        }

        public String getCollegeGradeImg() {
            return collegeGradeImg;
        }

        public void setCollegeGradeImg(String collegeGradeImg) {
            this.collegeGradeImg = collegeGradeImg;
        }

        public long getCollegeGradetime() {
            return collegeGradetime;
        }

        public void setCollegeGradetime(long collegeGradetime) {
            this.collegeGradetime = collegeGradetime;
        }

        public int getCollegeId() {
            return collegeId;
        }

        public void setCollegeId(int collegeId) {
            this.collegeId = collegeId;
        }

        public double getCollegeIncomes() {
            return collegeIncomes;
        }

        public void setCollegeIncomes(double collegeIncomes) {
            this.collegeIncomes = collegeIncomes;
        }

        public String getCollegeInfo() {
            return collegeInfo;
        }

        public void setCollegeInfo(String collegeInfo) {
            this.collegeInfo = collegeInfo;
        }

        public String getCollegeLoginPwd() {
            return collegeLoginPwd;
        }

        public void setCollegeLoginPwd(String collegeLoginPwd) {
            this.collegeLoginPwd = collegeLoginPwd;
        }

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

        public String getCollegePhone() {
            return collegePhone;
        }

        public void setCollegePhone(String collegePhone) {
            this.collegePhone = collegePhone;
        }

        public int getCollegePrice() {
            return collegePrice;
        }

        public void setCollegePrice(int collegePrice) {
            this.collegePrice = collegePrice;
        }

        public int getCollegeType() {
            return collegeType;
        }

        public void setCollegeType(int collegeType) {
            this.collegeType = collegeType;
        }

        public String getCollegeUser() {
            return collegeUser;
        }

        public void setCollegeUser(String collegeUser) {
            this.collegeUser = collegeUser;
        }

        public int getCollegeZxScale() {
            return collegeZxScale;
        }

        public void setCollegeZxScale(int collegeZxScale) {
            this.collegeZxScale = collegeZxScale;
        }

        public int getScale() {
            return scale;
        }

        public void setScale(int scale) {
            this.scale = scale;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getUpdator() {
            return updator;
        }

        public void setUpdator(String updator) {
            this.updator = updator;
        }

        public String getUserUrl() {
            return userUrl;
        }

        public void setUserUrl(String userUrl) {
            this.userUrl = userUrl;
        }

        public String getUserUrlImgCode() {
            return userUrlImgCode;
        }

        public void setUserUrlImgCode(String userUrlImgCode) {
            this.userUrlImgCode = userUrlImgCode;
        }
    }
}
