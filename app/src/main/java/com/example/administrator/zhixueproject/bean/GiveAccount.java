package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GiveAccount extends BaseBean {

    private GiveAccountBean data;

    public GiveAccountBean getData() {
        return data;
    }

    public void setData(GiveAccountBean data) {
        this.data = data;
    }

    public static class GiveAccountBean implements Serializable{

        private List<GiveList> giveAccountList=new ArrayList<>();

        public List<GiveList> getGiveAccountList() {
            return giveAccountList;
        }

        public void setGiveAccountList(List<GiveList> giveAccountList) {
            this.giveAccountList = giveAccountList;
        }
    }


    public static class GiveList implements Serializable{
        private String giveToName;
        private double sumCost;
        private String createDate;

        public String getGiveToName() {
            return giveToName;
        }

        public void setGiveToName(String giveToName) {
            this.giveToName = giveToName;
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
