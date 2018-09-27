package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;

/**
 * 购买vip
 */
public class AddVipActivity extends BaseActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medal);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.add_medal));
    }

}
