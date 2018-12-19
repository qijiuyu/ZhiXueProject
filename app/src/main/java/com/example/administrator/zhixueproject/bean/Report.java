package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Report extends BaseBean {

    private ReportData data;

    public ReportData getData() {
        return data;
    }

    public void setData(ReportData data) {
        this.data = data;
    }

    public static class ReportData implements Serializable{

        private List<ReportList> complaintList=new ArrayList<>();

        public List<ReportList> getComplaintList() {
            return complaintList;
        }

        public void setComplaintList(List<ReportList> complaintList) {
            this.complaintList = complaintList;
        }
    }


    public static class ReportList implements Serializable{
        private long complaintToId;
        private String postName;
        private String postWriterId;
        private String complaintContent;
        private String topicImg;
        private int complaintCount;
        private String complaintCreationTime;
        private String complaintInfo;

        public long getComplaintToId() {
            return complaintToId;
        }

        public void setComplaintToId(long complaintToId) {
            this.complaintToId = complaintToId;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public String getPostWriterId() {
            return postWriterId;
        }

        public void setPostWriterId(String postWriterId) {
            this.postWriterId = postWriterId;
        }

        public String getComplaintContent() {
            return complaintContent;
        }

        public void setComplaintContent(String complaintContent) {
            this.complaintContent = complaintContent;
        }

        public String getTopicImg() {
            return topicImg;
        }

        public void setTopicImg(String topicImg) {
            this.topicImg = topicImg;
        }

        public int getComplaintCount() {
            return complaintCount;
        }

        public void setComplaintCount(int complaintCount) {
            this.complaintCount = complaintCount;
        }

        public String getComplaintCreationTime() {
            return complaintCreationTime;
        }

        public void setComplaintCreationTime(String complaintCreationTime) {
            this.complaintCreationTime = complaintCreationTime;
        }

        public String getComplaintInfo() {
            return complaintInfo;
        }

        public void setComplaintInfo(String complaintInfo) {
            this.complaintInfo = complaintInfo;
        }
    }
}
