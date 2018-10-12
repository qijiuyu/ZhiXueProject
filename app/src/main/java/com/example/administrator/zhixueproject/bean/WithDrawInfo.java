package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;

public class WithDrawInfo extends BaseBean {

    private WithDrawInfoBean data;

    public WithDrawInfoBean getData() {
        return data;
    }

    public void setData(WithDrawInfoBean data) {
        this.data = data;
    }

    public static class WithDrawInfoBean implements Serializable{

        private WithDrawData cashInfo;

        public WithDrawData getCashInfo() {
            return cashInfo;
        }

        public void setCashInfo(WithDrawData cashInfo) {
            this.cashInfo = cashInfo;
        }
    }


    public static class WithDrawData implements Serializable{
        private String collegeAccBankinfo;
        private double collegeBalance;

        public String getCollegeAccBankinfo() {
            return collegeAccBankinfo;
        }

        public void setCollegeAccBankinfo(String collegeAccBankinfo) {
            this.collegeAccBankinfo = collegeAccBankinfo;
        }

        public double getCollegeBalance() {
            return collegeBalance;
        }

        public void setCollegeBalance(double collegeBalance) {
            this.collegeBalance = collegeBalance;
        }
    }
}
