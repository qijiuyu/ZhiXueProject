package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VipDetails extends BaseBean {

    private VipDetailsBean data;

    public VipDetailsBean getData() {
        return data;
    }

    public void setData(VipDetailsBean data) {
        this.data = data;
    }

    public static class VipDetailsBean implements Serializable{
        private List<VipDtailsList> vipDetailList=new ArrayList<>();

        public List<VipDtailsList> getVipDetailList() {
            return vipDetailList;
        }

        public void setVipDetailList(List<VipDtailsList> vipDetailList) {
            this.vipDetailList = vipDetailList;
        }
    }

    public static class VipDtailsList implements Serializable{

        private String vipGradeName;
        private int time;
        private int status;
        private int gradeType;
        private String createTime;

        public String getVipGradeName() {
            return vipGradeName;
        }

        public void setVipGradeName(String vipGradeName) {
            this.vipGradeName = vipGradeName;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getGradeType() {
            return gradeType;
        }

        public void setGradeType(int gradeType) {
            this.gradeType = gradeType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
