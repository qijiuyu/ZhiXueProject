package com.example.administrator.zhixueproject.activity.memberManage;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;

/** 会员设置
 * @author PeterGee
 * @date 2018/10/20
 */
public class MemberSettingActivity extends BaseActivity {
    public static final int RESULT_CODE=10;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_setting);
    }
}
