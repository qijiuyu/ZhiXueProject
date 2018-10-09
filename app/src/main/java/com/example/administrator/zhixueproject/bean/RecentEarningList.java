package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;

public class RecentEarningList implements Serializable {

    private String typeName;
    private String money;
    private int icon;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
