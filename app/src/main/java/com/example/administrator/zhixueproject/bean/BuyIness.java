package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuyIness extends BaseBean {

    private BusInessBean data;

    public BusInessBean getData() {
        return data;
    }

    public void setData(BusInessBean data) {
        this.data = data;
    }

    public static class BusInessBean implements Serializable{

        private List<BusInessList> list=new ArrayList<>();

        public List<BusInessList> getList() {
            return list;
        }

        public void setList(List<BusInessList> list) {
            this.list = list;
        }
    }


    public static class BusInessList implements Serializable{
        private long buytopicBuyer;
        private String buytopicBuyerCollegeid;
        private long buytopicCreattime;
        private int buytopicDelyn;
        private long buytopicEndtime;
        private long buytopicId;
        private int buytopicMonths;
        private long buytopicSellor;
        private String buytopicSellorCollegeid;
        private int buytopicStatus;
        private long buytopicTopic;
        private long buytopicTopicNew;
        private String buytopicUpdatetime;
        private String buytopicUpdator;
        private String collegeName;
        private String newWriterId;
        private String topicImg;
        private String topicName;
        //1:上架
        private int buytopicUp;
        private String newWriterName;

        public long getBuytopicBuyer() {
            return buytopicBuyer;
        }

        public void setBuytopicBuyer(long buytopicBuyer) {
            this.buytopicBuyer = buytopicBuyer;
        }

        public String getBuytopicBuyerCollegeid() {
            return buytopicBuyerCollegeid;
        }

        public void setBuytopicBuyerCollegeid(String buytopicBuyerCollegeid) {
            this.buytopicBuyerCollegeid = buytopicBuyerCollegeid;
        }

        public long getBuytopicCreattime() {
            return buytopicCreattime;
        }

        public void setBuytopicCreattime(long buytopicCreattime) {
            this.buytopicCreattime = buytopicCreattime;
        }

        public int getBuytopicDelyn() {
            return buytopicDelyn;
        }

        public void setBuytopicDelyn(int buytopicDelyn) {
            this.buytopicDelyn = buytopicDelyn;
        }

        public long getBuytopicEndtime() {
            return buytopicEndtime;
        }

        public void setBuytopicEndtime(long buytopicEndtime) {
            this.buytopicEndtime = buytopicEndtime;
        }

        public long getBuytopicId() {
            return buytopicId;
        }

        public void setBuytopicId(long buytopicId) {
            this.buytopicId = buytopicId;
        }

        public int getBuytopicMonths() {
            return buytopicMonths;
        }

        public void setBuytopicMonths(int buytopicMonths) {
            this.buytopicMonths = buytopicMonths;
        }

        public long getBuytopicSellor() {
            return buytopicSellor;
        }

        public void setBuytopicSellor(long buytopicSellor) {
            this.buytopicSellor = buytopicSellor;
        }

        public String getBuytopicSellorCollegeid() {
            return buytopicSellorCollegeid;
        }

        public void setBuytopicSellorCollegeid(String buytopicSellorCollegeid) {
            this.buytopicSellorCollegeid = buytopicSellorCollegeid;
        }

        public int getBuytopicStatus() {
            return buytopicStatus;
        }

        public void setBuytopicStatus(int buytopicStatus) {
            this.buytopicStatus = buytopicStatus;
        }

        public long getBuytopicTopic() {
            return buytopicTopic;
        }

        public void setBuytopicTopic(long buytopicTopic) {
            this.buytopicTopic = buytopicTopic;
        }

        public long getBuytopicTopicNew() {
            return buytopicTopicNew;
        }

        public void setBuytopicTopicNew(long buytopicTopicNew) {
            this.buytopicTopicNew = buytopicTopicNew;
        }

        public String getBuytopicUpdatetime() {
            return buytopicUpdatetime;
        }

        public void setBuytopicUpdatetime(String buytopicUpdatetime) {
            this.buytopicUpdatetime = buytopicUpdatetime;
        }

        public String getBuytopicUpdator() {
            return buytopicUpdator;
        }

        public void setBuytopicUpdator(String buytopicUpdator) {
            this.buytopicUpdator = buytopicUpdator;
        }

        public String getCollegeName() {
            return collegeName;
        }

        public void setCollegeName(String collegeName) {
            this.collegeName = collegeName;
        }

        public String getNewWriterId() {
            return newWriterId;
        }

        public void setNewWriterId(String newWriterId) {
            this.newWriterId = newWriterId;
        }

        public String getTopicImg() {
            return topicImg;
        }

        public void setTopicImg(String topicImg) {
            this.topicImg = topicImg;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public int getBuytopicUp() {
            return buytopicUp;
        }

        public void setBuytopicUp(int buytopicUp) {
            this.buytopicUp = buytopicUp;
        }

        public String getNewWriterName() {
            return newWriterName;
        }

        public void setNewWriterName(String newWriterName) {
            this.newWriterName = newWriterName;
        }
    }
}
