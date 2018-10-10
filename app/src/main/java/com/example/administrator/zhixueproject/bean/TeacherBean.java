package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/9/29.
 */

public class TeacherBean extends BaseBean {

    private TeacherListBean data;

    public TeacherListBean getData() {
        return data;
    }

    public void setData(TeacherListBean data) {
        this.data = data;
    }

    public static class TeacherListBean implements Serializable{

        public List<Teacher> teacherList;

        public List<Teacher> getTeacherList() {
            return teacherList;
        }

        public void setTeacherList(List<Teacher> teacherList) {
            this.teacherList = teacherList;
        }
    }
}
