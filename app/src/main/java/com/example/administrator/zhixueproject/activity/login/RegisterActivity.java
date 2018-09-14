package com.example.administrator.zhixueproject.activity.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.FileUtils;
import com.example.administrator.zhixueproject.utils.SPUtil;
import com.example.administrator.zhixueproject.utils.Utils;
import com.example.administrator.zhixueproject.view.ClickTextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText etMobile,etCode,etPwd,etConPwd;
    private ClickTextView tvCode;
    //计数器
    private Timer mTimer;
    private int time = 0;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        //判断验证码秒数是否超过一分钟
        checkTime();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        etMobile=(EditText)findViewById(R.id.et_register_phone);
        etCode=(EditText)findViewById(R.id.et_code);
        etPwd=(EditText)findViewById(R.id.et_pwd);
        etConPwd=(EditText)findViewById(R.id.et_confirm_pwd);
        tvCode=(ClickTextView)findViewById(R.id.tv_get_code);
        tvCode.setOnClickListener(this);
        findViewById(R.id.tv_register).setOnClickListener(this);
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseBean baseBean=null;
            switch (msg.what){
                //获取验证码
                case HandlerConstant1.GET_SMS_CODE_SUCCESS:
                     clearTask();
                     baseBean= (BaseBean) msg.obj;
                     if(null==baseBean){
                         return;
                     }
                     if(baseBean.isStatus()){
                         //先保存计时时间
                         MyApplication.spUtil.addString(SPUtil.SMS_CODE_TIME, String.valueOf((System.currentTimeMillis() + 60000)));
                         time = 60;
                         startTime();
                     }else{
                         showMsg(baseBean.getErrorMsg());
                     }
                     break;
                //动态改变验证码秒数
                case 0x001:
                    tvCode.setText(time + getString(R.string.login_code_later));
                    break;
                case 0x002:
                    if (null != mTimer) {
                        mTimer.cancel();
                    }
                    tvCode.setText(getString(R.string.get_code));
                    MyApplication.spUtil.removeMessage(SPUtil.SMS_CODE_TIME);
                    break;
                //注册
                case HandlerConstant1.REGISTER_SUCCESS:
                     clearTask();
                     baseBean= (BaseBean) msg.obj;
                     if(null==baseBean){
                        return;
                     }
                     if(baseBean.isStatus()){
                         MyApplication.spUtil.addString(SPUtil.LOGIN_MOBILE,etMobile.getText().toString().trim());
                         showMsg(getString(R.string.register_success));
                         finish();
                     }else{
                        showMsg(baseBean.getErrorMsg());
                     }
                     break;
                case HandlerConstant1.REQUST_ERROR:
                     clearTask();
                     showMsg(getString(R.string.net_error));
                     break;
                     default:
                         break;
            }
        }
    };


    @Override
    public void onClick(View v) {
        String mobile=etMobile.getText().toString().trim();
        switch (v.getId()){
            //获取短信验证码
            case R.id.tv_get_code:
                 if(TextUtils.isEmpty(mobile)){
                     showMsg(getString(R.string.login_phone));
                 }else{
                     showProgress(getString(R.string.get_code));
                     HttpMethod1.getSmsCode(mobile,"0",mHandler);
                 }
                 break;
            //注册
            case R.id.tv_register:
                 String smsCode=etCode.getText().toString().trim();
                 String pwd=etPwd.getText().toString().trim();
                 String conPwd=etConPwd.getText().toString().trim();
                if(TextUtils.isEmpty(mobile)){
                    showMsg(getString(R.string.login_phone));
                    return;
                }
                if(TextUtils.isEmpty(smsCode)){
                    showMsg(getString(R.string.login_right_code));
                    return;
                }
                if(TextUtils.isEmpty(pwd)){
                    showMsg(getString(R.string.login_pwd));
                    return;
                }
                if(pwd.length()<6){
                    showMsg(getString(R.string.login_long_pwd));
                    return;
                }
                if(!Utils.isPwd(pwd)){
                    showMsg(getString(R.string.login_pwd_letter));
                    return;
                }
                if(TextUtils.isEmpty(conPwd)){
                    showMsg(getString(R.string.login_confirm_pwd));
                    return;
                }
                if(!TextUtils.equals(pwd,conPwd)){
                    showMsg(getString(R.string.login_confirm_error));
                    return;
                }
                showProgress("注册中...");
                HttpMethod1.register(mobile,pwd,smsCode,mHandler);
                break;
                default:
                    break;
        }
    }


    /**
     * 动态改变验证码秒数
     */
    private void startTime() {
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (time <= 0) {
                    mHandler.sendEmptyMessage(0x002);
                } else {
                    --time;
                    mHandler.sendEmptyMessage(0x001);
                }
            }
        }, 0, 1000);
    }



    /**
     * 判断验证码秒数是否超过一分钟
     */
    private void checkTime() {
        String stoptime = MyApplication.spUtil.getString(SPUtil.SMS_CODE_TIME);
        if (!TextUtils.isEmpty(stoptime)) {
            int t = (int) ((Double.parseDouble(stoptime) - System.currentTimeMillis()) / 1000);
            if (t > 0) {
                time = t;
                startTime();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
        }
        super.onDestroy();
    }
}
