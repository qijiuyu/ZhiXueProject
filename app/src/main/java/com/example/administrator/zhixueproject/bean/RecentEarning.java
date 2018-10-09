package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;

public class RecentEarning extends BaseBean {

    private DataList data;

    public DataList getData() {
        return data;
    }

    public void setData(DataList data) {
        this.data = data;
    }

    public static class DataList implements Serializable{
        private Account account;

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }
    }

    public static class Account implements Serializable{

        private String sumTopic="0.0";
        private String sumPost="0.0";
        private String  SumYouChangGive="0.0";
        private double sumScalGive;
        private String sumAcc="0.0";
        private int sumGive;
        private int sumCost;
        private double collegeIncomes;
        private int sumGift;
        private double collegeBalance;

        public String getSumTopic() {
            return sumTopic;
        }

        public void setSumTopic(String sumTopic) {
            this.sumTopic = sumTopic;
        }

        public String getSumPost() {
            return sumPost;
        }

        public void setSumPost(String sumPost) {
            this.sumPost = sumPost;
        }

        public String getSumYouChangGive() {
            return SumYouChangGive;
        }

        public void setSumYouChangGive(String sumYouChangGive) {
            SumYouChangGive = sumYouChangGive;
        }

        public double getSumScalGive() {
            return sumScalGive;
        }

        public void setSumScalGive(double sumScalGive) {
            this.sumScalGive = sumScalGive;
        }

        public String getSumAcc() {
            return sumAcc;
        }

        public void setSumAcc(String sumAcc) {
            this.sumAcc = sumAcc;
        }

        public int getSumGive() {
            return sumGive;
        }

        public void setSumGive(int sumGive) {
            this.sumGive = sumGive;
        }

        public int getSumCost() {
            return sumCost;
        }

        public void setSumCost(int sumCost) {
            this.sumCost = sumCost;
        }

        public double getCollegeIncomes() {
            return collegeIncomes;
        }

        public void setCollegeIncomes(double collegeIncomes) {
            this.collegeIncomes = collegeIncomes;
        }

        public int getSumGift() {
            return sumGift;
        }

        public void setSumGift(int sumGift) {
            this.sumGift = sumGift;
        }

        public double getCollegeBalance() {
            return collegeBalance;
        }

        public void setCollegeBalance(double collegeBalance) {
            this.collegeBalance = collegeBalance;
        }
    }
}
