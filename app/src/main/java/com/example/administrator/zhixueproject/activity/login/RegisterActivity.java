package com.example.administrator.zhixueproject.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText etMobile,etCode,etPwd,etConPwd;
    private TextView tvCode;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        etMobile=(EditText)findViewById(R.id.et_register_phone);
        etCode=(EditText)findViewById(R.id.et_code);
        etPwd=(EditText)findViewById(R.id.et_pwd);
        etConPwd=(EditText)findViewById(R.id.et_confirm_pwd);
        tvCode=(TextView)findViewById(R.id.tv_get_code);
        tvCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String mobile;
        switch (v.getId()){
            //获取短信验证码
            case R.id.tv_get_code:
                 mobile=etMobile.getText().toString().trim();
                 if(TextUtils.isEmpty(mobile)){
                     showMsg(getString(R.string.login_phone));
                 }else{
                     
                 }
                 break;
        }

    }
}
