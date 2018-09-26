package com.example.administrator.zhixueproject.activity.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.activity.TabActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.Colleges;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.bean.UserInfo;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.CodeUtils;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.SPUtil;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText etMobile,etPwd,etCode;
    private ImageView imgCode;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }


    /**
     * 初始化控件
     */
    //13911896806 123456
    private void initView(){
        TextView tvTitle=(TextView)findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.main_title));
        TextView tvRegister=(TextView)findViewById(R.id.tv_right);
        tvRegister.setText(getString(R.string.register));
        etMobile=(EditText)findViewById(R.id.et_telphone) ;
        etPwd=(EditText)findViewById(R.id.et_pwd);
        etCode=(EditText)findViewById(R.id.et_code);
        imgCode=(ImageView) findViewById(R.id.iv_get_code);
        imgCode.setImageBitmap(CodeUtils.getInstance().createBitmap());
        findViewById(R.id.tv_login).setOnClickListener(this);
        findViewById(R.id.iv_get_code).setOnClickListener(this);
        findViewById(R.id.tv_forget_pwd).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //注册
            case R.id.tv_right:
                 setClass(RegisterActivity.class);
                 break;
            //刷新验证码
            case R.id.iv_get_code:
                 imgCode.setImageBitmap(CodeUtils.getInstance().createBitmap());
                 break;
            //登陆
            case R.id.tv_login:
                 String mobile=etMobile.getText().toString().trim();
                 String pwd=etPwd.getText().toString().trim();
                 String code=etCode.getText().toString().trim();
                 String realCode = CodeUtils.getInstance().getCode();
                 LogUtils.e(realCode+"+++++++++++++++++");
                if (TextUtils.isEmpty(mobile)){
                    showMsg(getString(R.string.login_phone));
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    showMsg(getString(R.string.login_pwd));
                    return;
                }
                if (pwd.length() < 6) {
                    showMsg(getString(R.string.login_long_pwd));
                    return;
                }
                if(TextUtils.isEmpty(code)){
                    showMsg(getString(R.string.login_right_code));
                    return;
                }
                if(!TextUtils.equals(realCode,code)){
                    showMsg(getString(R.string.code_error));
                    return;
                }
                showProgress("登陆中...");
                HttpMethod1.login(mobile,pwd,mHandler);
                 break;
            //忘记密码
            case R.id.tv_forget_pwd:
                 setClass(SettingPwdActivity.class);
                 break;
            case R.id.lin_back:
                 finish();
                 break;
                 default:
                     break;
        }
    }


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                //登陆回执
                case HandlerConstant1.LOGIN_SUCCESS:
                     final String message= (String) msg.obj;
                     if(TextUtils.isEmpty(message)){
                         return;
                     }
                     try {
                         
                     }catch (Exception e){
                         e.printStackTrace();
                     }
                     break;
                case HandlerConstant1.REQUST_ERROR:
                     showMsg(getString(R.string.net_error));
                     break;
                     default:
                         break;
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        final String mobile=MyApplication.spUtil.getString(SPUtil.LOGIN_MOBILE);
        etMobile.setText(mobile);
    }
}
