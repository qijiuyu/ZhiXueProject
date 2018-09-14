package com.example.administrator.zhixueproject.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }


    private void initView(){
        TextView tvTitle=(TextView)findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.main_title));
        TextView tvRegister=(TextView)findViewById(R.id.tv_right);
        tvRegister.setText(getString(R.string.register));
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_right:
                 setClass(RegisterActivity.class);
                 break;
        }

    }
}
