package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WithDraw extends BaseBean {

    private WithDrawBean data;

    public WithDrawBean getData() {
        return data;
    }

    public void setData(WithDrawBean data) {
        this.data = data;
    }

    public static class WithDrawBean implements Serializable{
        private List<WithDrawList> cashRecordList=new ArrayList<>();

        public List<WithDrawList> getCashRecordList() {
            return cashRecordList;
        }

        public void setCashRecordList(List<WithDrawList> cashRecordList) {
            this.cashRecordList = cashRecordList;
        }
    }


    public static class WithDrawList implements Serializable{
        private String cashCreationtime;
        private double cashValue;
        private String updatetime;
        private int payStatus;

        public String getCashCreationtime() {
            return cashCreationtime;
        }

        public void setCashCreationtime(String cashCreationtime) {
            this.cashCreationtime = cashCreationtime;
        }

        public double getCashValue() {
            return cashValue;
        }

        public void setCashValue(double cashValue) {
            this.cashValue = cashValue;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }
    }
}
