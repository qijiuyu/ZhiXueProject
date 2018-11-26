package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.math.BigDecimal;

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

        private BigDecimal sumTopic=new BigDecimal(0);
        private BigDecimal sumPost=new BigDecimal(0);
        private BigDecimal  SumYouChangGive=new BigDecimal(0);
        private BigDecimal sumScalGive=new BigDecimal(0);
        private BigDecimal sumAcc=new BigDecimal(0);
        private BigDecimal sumGive=new BigDecimal(0);
        private BigDecimal sumCost=new BigDecimal(0);
        private BigDecimal collegeIncomes=new BigDecimal(0);
        private BigDecimal sumGift=new BigDecimal(0);
        private BigDecimal collegeBalance=new BigDecimal(0);

        public BigDecimal getSumTopic() {
            return sumTopic;
        }

        public void setSumTopic(BigDecimal sumTopic) {
            this.sumTopic = sumTopic;
        }

        public BigDecimal getSumPost() {
            return sumPost;
        }

        public void setSumPost(BigDecimal sumPost) {
            this.sumPost = sumPost;
        }

        public BigDecimal getSumYouChangGive() {
            return SumYouChangGive;
        }

        public void setSumYouChangGive(BigDecimal sumYouChangGive) {
            SumYouChangGive = sumYouChangGive;
        }

        public BigDecimal getSumScalGive() {
            return sumScalGive;
        }

        public void setSumScalGive(BigDecimal sumScalGive) {
            this.sumScalGive = sumScalGive;
        }

        public BigDecimal getSumAcc() {
            return sumAcc;
        }

        public void setSumAcc(BigDecimal sumAcc) {
            this.sumAcc = sumAcc;
        }

        public BigDecimal getSumGive() {
            return sumGive;
        }

        public void setSumGive(BigDecimal sumGive) {
            this.sumGive = sumGive;
        }

        public BigDecimal getSumCost() {
            return sumCost;
        }

        public void setSumCost(BigDecimal sumCost) {
            this.sumCost = sumCost;
        }

        public BigDecimal getCollegeIncomes() {
            return collegeIncomes;
        }

        public void setCollegeIncomes(BigDecimal collegeIncomes) {
            this.collegeIncomes = collegeIncomes;
        }

        public BigDecimal getSumGift() {
            return sumGift;
        }

        public void setSumGift(BigDecimal sumGift) {
            this.sumGift = sumGift;
        }

        public BigDecimal getCollegeBalance() {
            return collegeBalance;
        }

        public void setCollegeBalance(BigDecimal collegeBalance) {
            this.collegeBalance = collegeBalance;
        }
    }
}
