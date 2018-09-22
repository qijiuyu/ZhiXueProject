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
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.SPUtil;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 修改手机号
 *
 * @author petergee
 * @date 2018/9/21
 */
public class ModifyPhoneActivity extends BaseActivity implements View.OnClickListener {

    private EditText etTel;
    private EditText etCode;
    private TextView tvGetCode;
    //计数器
    private Timer mTimer;
    private int time = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);
        initView();
        checkTime();
    }

    private void initView() {
        etTel = (EditText) findViewById(R.id.et_tel);
        etCode = (EditText) findViewById(R.id.et_code);
        tvGetCode = (TextView) findViewById(R.id.tv_get_code);
        tvGetCode.setOnClickListener(this);
        TextView tvNext = (TextView) findViewById(R.id.tv_next);
        tvNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String mobile = etTel.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_get_code:
                if (TextUtils.isEmpty(mobile)) {
                    showMsg(getString(R.string.login_phone));
                } else if (mobile.length() < 11) {
                    showMsg(getString(R.string.login_right_phone));
                } else {
                    // 发短信
                    showProgress(getString(R.string.get_code));
                    HttpMethod1.getSmsCode(mobile, "1", mHandler);
                }
                break;
            case R.id.tv_next:
                String code = etCode.getText().toString().trim();
                String tel = etTel.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    showMsg(getString(R.string.print_certify_code));
                    return;
                }
                if (isInput()) {
                    // 修改个人信息
                    showProgress(getString(R.string.loading));
                    HttpMethod2.modifyUserInfo("", tel, "", code, "", mHandler);
                }
                break;
            default:
                break;
        }

    }

    /**
     * 输入判断
     *
     * @return
     */
    public boolean isInput() {
        if (TextUtils.isEmpty(etTel.getText().toString().trim())) {
            showMsg(getString(R.string.login_phone));
            return false;
        }
        if ((etCode.getText().toString().trim().length() != 11)) {
            showMsg(getString(R.string.login_right_phone));
            return false;
        }
        return true;
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseBean baseBean = null;
            switch (msg.what) {
                //获取验证码
                case HandlerConstant1.GET_SMS_CODE_SUCCESS:
                    clearTask();
                    baseBean = (BaseBean) msg.obj;
                    if (null == baseBean) {
                        return;
                    }
                    if (baseBean.isStatus()) {
                        //先保存计时时间
                        MyApplication.spUtil.addString(SPUtil.SMS_CODE_TIME, String.valueOf((System.currentTimeMillis() + 60000)));
                        time = 60;
                        startTime();
                    } else {
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
                // 修改资料成功
                case HandlerConstant2.MODIFY_USER_INFO_SUCCESS:
                    if (baseBean.status) {
                        showMsg(getString(R.string.modify_phone_success));
                    } else {
                        showMsg(baseBean.errorMsg);
                    }
                    break;
                case HandlerConstant2.REQUST_ERROR:
                    clearTask();
                    showMsg(getString(R.string.net_error));
                    break;
                default:
                    break;
            }
        }
    };

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
        String stopTime = MyApplication.spUtil.getString(SPUtil.SMS_CODE_TIME);
        if (!TextUtils.isEmpty(stopTime)) {
            int t = (int) ((Double.parseDouble(stopTime) - System.currentTimeMillis()) / 1000);
            if (t > 0) {
                time = t;
                startTime();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
        }
    }
}