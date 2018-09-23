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
public class UpdatePhoneActivity extends BaseActivity implements View.OnClickListener {

    private EditText etEmail;
    private EditText etCode;
    private TextView tvGetCode;
    //计数器
    private Timer mTimer;
    private int time = 0;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone);
        initView();
        checkTime();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.modify_phone));
        etEmail = (EditText) findViewById(R.id.et_email);
        etCode = (EditText) findViewById(R.id.et_email_code);
        tvGetCode = (TextView) findViewById(R.id.tv_get_code);
        tvGetCode.setOnClickListener(this);
        findViewById(R.id.tv_aup_next).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final String email = etEmail.getText().toString().trim();
        switch (view.getId()) {
            //获取验证码
            case R.id.tv_get_code:
                if (TextUtils.isEmpty(email)) {
                    showMsg(getString(R.string.input_mailbox));
                } else {
                    showProgress(getString(R.string.get_code));
                    HttpMethod1.getEmailCode(email, "1", mHandler);
                }
                break;
            //下一步
            case R.id.tv_aup_next:
                String code = etCode.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    showMsg(getString(R.string.input_mailbox));
                }else if(TextUtils.isEmpty(code)) {
                    showMsg(getString(R.string.print_certify_code));
                }else{
                    showProgress(getString(R.string.loading));
                    HttpMethod2.modifyUserInfo(null, null, email, code, null, mHandler);
                }
                break;
            case R.id.lin_back:
                 finish();
                 break;
            default:
                break;
        }

    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseBean baseBean = null;
            switch (msg.what) {
                //获取验证码
                case HandlerConstant1.GET_EMAIL_CODE_SUCCESS:
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
                     clearTask();
                    if (baseBean.status) {
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