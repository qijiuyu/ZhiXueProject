package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntryGroup extends BaseBean {

    private EntryGroupData data;

    public EntryGroupData getData() {
        return data;
    }

    public void setData(EntryGroupData data) {
        this.data = data;
    }

    public static class EntryGroupData implements Serializable{

        private List<EntryGroupList> collegeAccountList=new ArrayList<>();

        public List<EntryGroupList> getCollegeAccountList() {
            return collegeAccountList;
        }

        public void setCollegeAccountList(List<EntryGroupList> collegeAccountList) {
            this.collegeAccountList = collegeAccountList;
        }
    }


    public static class EntryGroupList implements Serializable{
        private String userName;
        private double sumCost;
        private String createDate;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public double getSumCost() {
            return sumCost;
        }

        public void setSumCost(double sumCost) {
            this.sumCost = sumCost;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
