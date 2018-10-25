package com.example.administrator.zhixueproject.bean.memberManage;

import com.example.administrator.zhixueproject.bean.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SignIn extends BaseBean {

    private SignInData data;

    public SignInData getData() {
        return data;
    }

    public void setData(SignInData data) {
        this.data = data;
    }

    public static class SignInData implements Serializable{

        private List<SignInList> totalNum=new ArrayList<>();

        public List<SignInList> getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(List<SignInList> totalNum) {
            this.totalNum = totalNum;
        }
    }


    public static class SignInList implements Serializable{
        private int num;
        private int day;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }
}
