package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TopicAccount extends BaseBean {

    private TopicData data;

    public TopicData getData() {
        return data;
    }

    public void setData(TopicData data) {
        this.data = data;
    }

    public static class TopicData implements Serializable{
        private List<TopicAccountList> topicAccountList=new ArrayList<>();

        public List<TopicAccountList> getTopicAccountList() {
            return topicAccountList;
        }

        public void setTopicAccountList(List<TopicAccountList> topicAccountList) {
            this.topicAccountList = topicAccountList;
        }
    }


    public static class TopicAccountList implements Serializable{
        private String createDate;
        private String sumCost;
        private String topicName;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getSumCost() {
            return sumCost;
        }

        public void setSumCost(String sumCost) {
            this.sumCost = sumCost;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }
    }
}
