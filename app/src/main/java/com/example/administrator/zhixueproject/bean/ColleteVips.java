package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 学院vip数据
 */
public class ColleteVips extends BaseBean {

    private ColleteVipsBean data;

    public ColleteVipsBean getData() {
        return data;
    }

    public void setData(ColleteVipsBean data) {
        this.data = data;
    }

    public static class ColleteVipsBean implements Serializable{
        private List<collegeGradeListBean> collegeGradeList;

        public List<collegeGradeListBean> getCollegeGradeList() {
            return collegeGradeList;
        }

        public void setCollegeGradeList(List<collegeGradeListBean> collegeGradeList) {
            this.collegeGradeList = collegeGradeList;
        }

        public static class collegeGradeListBean implements Serializable{
            private int collegeDelyn;

            private int collegeGradeId;

            private String collegeGradeImg;

            private int collegeGradeMprice;

            private String collegeGradeName;

            private int collegeGradeYprice;

            private int collegeLimitStu;

            private int collegeLimitTopic;

            private int collegeLiveNum;

            private int collegeLivePostYn;

            private long updatetime;

            private int updator;

            public int getCollegeDelyn() {
                return collegeDelyn;
            }

            public void setCollegeDelyn(int collegeDelyn) {
                this.collegeDelyn = collegeDelyn;
            }

            public int getCollegeGradeId() {
                return collegeGradeId;
            }

            public void setCollegeGradeId(int collegeGradeId) {
                this.collegeGradeId = collegeGradeId;
            }

            public String getCollegeGradeImg() {
                return collegeGradeImg;
            }

            public void setCollegeGradeImg(String collegeGradeImg) {
                this.collegeGradeImg = collegeGradeImg;
            }

            public int getCollegeGradeMprice() {
                return collegeGradeMprice;
            }

            public void setCollegeGradeMprice(int collegeGradeMprice) {
                this.collegeGradeMprice = collegeGradeMprice;
            }

            public String getCollegeGradeName() {
                return collegeGradeName;
            }

            public void setCollegeGradeName(String collegeGradeName) {
                this.collegeGradeName = collegeGradeName;
            }

            public int getCollegeGradeYprice() {
                return collegeGradeYprice;
            }

            public void setCollegeGradeYprice(int collegeGradeYprice) {
                this.collegeGradeYprice = collegeGradeYprice;
            }

            public int getCollegeLimitStu() {
                return collegeLimitStu;
            }

            public void setCollegeLimitStu(int collegeLimitStu) {
                this.collegeLimitStu = collegeLimitStu;
            }

            public int getCollegeLimitTopic() {
                return collegeLimitTopic;
            }

            public void setCollegeLimitTopic(int collegeLimitTopic) {
                this.collegeLimitTopic = collegeLimitTopic;
            }

            public int getCollegeLiveNum() {
                return collegeLiveNum;
            }

            public void setCollegeLiveNum(int collegeLiveNum) {
                this.collegeLiveNum = collegeLiveNum;
            }

            public int getCollegeLivePostYn() {
                return collegeLivePostYn;
            }

            public void setCollegeLivePostYn(int collegeLivePostYn) {
                this.collegeLivePostYn = collegeLivePostYn;
            }

            public long getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(long updatetime) {
                this.updatetime = updatetime;
            }

            public int getUpdator() {
                return updator;
            }

            public void setUpdator(int updator) {
                this.updator = updator;
            }
        }
    }

}
