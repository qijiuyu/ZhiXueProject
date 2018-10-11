package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Post extends BaseBean {

    private PostBean data;

    public PostBean getData() {
        return data;
    }

    public void setData(PostBean data) {
        this.data = data;
    }

    public static class PostBean implements Serializable{

        private List<PostList> postAccountList=new ArrayList<>();

        public List<PostList> getPostAccountList() {
            return postAccountList;
        }

        public void setPostAccountList(List<PostList> postAccountList) {
            this.postAccountList = postAccountList;
        }
    }


    public static class PostList implements Serializable{
        private String topicName;
        private String postName;
        private double sumCost;
        private String createDate;

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
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
