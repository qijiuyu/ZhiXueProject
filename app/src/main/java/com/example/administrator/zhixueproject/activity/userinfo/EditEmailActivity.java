package com.example.administrator.zhixueproject.activity.userinfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.SPUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 修改邮箱
 * Created by Administrator on 2018/9/25.
 */

public class EditEmailActivity extends BaseActivity implements View.OnClickListener{

    private EditText etMobile,etCode;
    private TextView tvGetCode;
    //计数器
    private Timer mTimer;
    private int time = 0;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.modify_mailbox));
        etMobile=(EditText)findViewById(R.id.et_tel);
        tvGetCode=(TextView)findViewById(R.id.tv_get_code);
        etCode=(EditText)findViewById(R.id.et_code);
        tvGetCode.setOnClickListener(this);
        findViewById(R.id.tv_next).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
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
                    tvGetCode.setText(time + getString(R.string.login_code_later));
                    break;
                case 0x002:
                    if (null != mTimer) {
                        mTimer.cancel();
                    }
                    tvGetCode.setText(getString(R.string.get_code));
                    MyApplication.spUtil.removeMessage(SPUtil.SMS_CODE_TIME);
                    break;
                //下一步
                case HandlerConstant1.CHECK_SMS_CODE_SUCCESS:
                     clearTask();
                     baseBean= (BaseBean) msg.obj;
                     if(null==baseBean){
                        return;
                     }
                     if(baseBean.isStatus()){
                         MyApplication.spUtil.removeMessage(SPUtil.SMS_CODE_TIME);
                         setClass(BindingEmailActivity.class);
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
            //获取验证码
            case R.id.tv_get_code:
                 if(TextUtils.isEmpty(mobile)){
                     showMsg(getString(R.string.login_phone));
                     return;
                 }
                 showProgress(getString(R.string.get_code));
                 HttpMethod1.getSmsCode(mobile,"0",mHandler);
                 break;
            //下一步
            case R.id.tv_next:
                 final String code=etCode.getText().toString().trim();
                 if(TextUtils.isEmpty(mobile)){
                    showMsg(getString(R.string.login_phone));
                    return;
                 }
                 if(TextUtils.isEmpty(code)){
                     showMsg(getString(R.string.print_certify_code));
                     return;
                 }
                 showProgress(getString(R.string.loding));
                 HttpMethod1.checkSmsCode(mobile,code,mHandler);
                 break;
            case R.id.lin_back:
                 finish();
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
