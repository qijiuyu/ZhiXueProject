package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;

/**
 * 学院管理页面
 */
public class CollegeManageActivity extends BaseActivity implements View.OnClickListener{
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_manage);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.institution_manage));
        findViewById(R.id.rl_member_level_setting).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //会员等级设置
            case R.id.rl_member_level_setting:
                 setClass(MemberLevelActivity.class);
                 break;
            case R.id.lin_back:
                 finish();
                 break;
             default:
                 break;
        }
    }
}
