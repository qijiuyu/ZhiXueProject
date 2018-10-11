package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GiveScalAccount extends BaseBean {

    private GiveScalBean data;

    public GiveScalBean getData() {
        return data;
    }

    public void setData(GiveScalBean data) {
        this.data = data;
    }

    public static class GiveScalBean implements Serializable{

        private List<GiveScalList> GiveScalAccountList=new ArrayList<>();

        public List<GiveScalList> getGiveScalAccountList() {
            return GiveScalAccountList;
        }

        public void setGiveScalAccountList(List<GiveScalList> giveScalAccountList) {
            GiveScalAccountList = giveScalAccountList;
        }
    }


    public static class GiveScalList implements Serializable{
        private String giveToName;
        private double sumScalGive;
        private String createDate;

        public String getGiveToName() {
            return giveToName;
        }

        public void setGiveToName(String giveToName) {
            this.giveToName = giveToName;
        }

        public double getSumScalGive() {
            return sumScalGive;
        }

        public void setSumScalGive(double sumScalGive) {
            this.sumScalGive = sumScalGive;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
