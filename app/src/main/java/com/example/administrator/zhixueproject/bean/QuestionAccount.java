package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionAccount extends BaseBean {

    private QuestionBean data;

    public QuestionBean getData() {
        return data;
    }

    public void setData(QuestionBean data) {
        this.data = data;
    }

    public static class QuestionBean implements Serializable{

        private List<QuestionList> YouChangAccountList=new ArrayList<>();

        public List<QuestionList> getYouChangAccountList() {
            return YouChangAccountList;
        }

        public void setYouChangAccountList(List<QuestionList> youChangAccountList) {
            YouChangAccountList = youChangAccountList;
        }
    }


    public static class QuestionList implements Serializable{
        private String giveToName;
        private double youChangMoney;
        private String createDate;

        public String getGiveToName() {
            return giveToName;
        }

        public void setGiveToName(String giveToName) {
            this.giveToName = giveToName;
        }

        public double getYouChangMoney() {
            return youChangMoney;
        }

        public void setYouChangMoney(double youChangMoney) {
            this.youChangMoney = youChangMoney;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
