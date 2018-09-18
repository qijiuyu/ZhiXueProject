package com.example.administrator.zhixueproject.utils;

import android.widget.RadioButton;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.fragment.LiveFragment;
import com.example.administrator.zhixueproject.fragment.PersonalManagerFragment;
import com.example.administrator.zhixueproject.fragment.TopicFragment;
import com.example.administrator.zhixueproject.fragment.college.CollegeFragment;
import com.example.administrator.zhixueproject.fragment.InvitationFragment;

public enum EnumTAB {
    TAB1(R.id.rb_tab_1, R.drawable.tab_1, "学院", new CollegeFragment()),
    TAB2(R.id.rb_tab_2, R.drawable.tab_2, "帖子", new InvitationFragment()),
    TAB3(R.id.rb_tab_3, R.drawable.tab_3, "直播预告", new LiveFragment()),
    TAB4(R.id.rb_tab_4, R.drawable.tab_4, "话题管理", new TopicFragment()),
    TAB5(R.id.rb_tab_5, R.drawable.tab_5, "人员管理", new PersonalManagerFragment());

    private int id;
    private int drawable;
    private String title;
    private BaseFragment fragment;
    private RadioButton radioButton;

    EnumTAB(int id, int drawable, String title, BaseFragment fragment) {
        this.id = id;
        this.drawable = drawable;
        this.title = title;
        this.fragment = fragment;
    }

    public int getId() {
        return id;
    }

    public int getDrawable() {
        return drawable;
    }

    public String getTitle() {
        return title;
    }

    public BaseFragment getFragment() {
        return fragment;
    }

    public void setRadioButton(RadioButton radioButton) {
        this.radioButton = radioButton;
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

}

