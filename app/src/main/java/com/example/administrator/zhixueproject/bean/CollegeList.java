package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 加入过的更多学院
 * Created by Administrator on 2018/9/23.
 */

public class CollegeList extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private List<Colleges> data;

        public List<Colleges> getData() {
            return data;
        }

        public void setData(List<Colleges> data) {
            this.data = data;
        }
    }
}
