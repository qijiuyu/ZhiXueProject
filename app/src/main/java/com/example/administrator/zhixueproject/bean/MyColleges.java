package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyColleges extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private List<Colleges> colleges=new ArrayList<>();

        public List<Colleges> getColleges() {
            return colleges;
        }

        public void setColleges(List<Colleges> colleges) {
            this.colleges = colleges;
        }
    }
}
