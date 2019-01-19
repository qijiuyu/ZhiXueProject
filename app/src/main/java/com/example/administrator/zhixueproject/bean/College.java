package com.example.administrator.zhixueproject.bean;

import com.example.administrator.zhixueproject.activity.BaseActivity;

import java.io.Serializable;
import java.util.List;

public class College extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private List<CollegeDatas> collegelist;

        public List<CollegeDatas> getCollegelist() {
            return collegelist;
        }

        public void setCollegelist(List<CollegeDatas> collegelist) {
            this.collegelist = collegelist;
        }
    }


    public static class CollegeDatas implements Serializable{
        private long collegeId;
        private String collegeName;

        public long getCollegeId() {
            return collegeId;
        }

        public void setCollegeId(long collegeId) {
            this.collegeId = collegeId;
        }

        public String getCollegeName() {
            return collegeName;
        }

        public void setCollegeName(String collegeName) {
            this.collegeName = collegeName;
        }
    }
}
