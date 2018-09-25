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
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;

/**
 * 修改密码
 * Created by Administrator on 2018/9/24.
 */

public class UpdatePwdActivity extends BaseActivity implements View.OnClickListener{
    private EditText etOldPwd,etNewPwd,etNewPwd2;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.pwd_modify));
        etOldPwd=(EditText)findViewById(R.id.et_old_pwd);
        etNewPwd=(EditText)findViewById(R.id.et_new_pwd);
        etNewPwd2=(EditText)findViewById(R.id.et_new_pwd2);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_commit:
                String newPwd = etNewPwd.getText().toString().trim();
                String newPwd2 = etNewPwd2.getText().toString().trim();
                String oldPwd = etOldPwd.getText().toString().trim();
                if (TextUtils.isEmpty(oldPwd)) {
                    showMsg("请输入旧密码");
                    return;
                }
                if (TextUtils.isEmpty(newPwd)) {
                    showMsg("请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(newPwd2)) {
                    showMsg("请输入确认密码");
                    return;
                }
                if (!newPwd.equals(newPwd2)) {
                    showMsg("两次密码不一致，请请输入正确");
                    return;
                }
                showProgress("密码修改中...");
                HttpMethod1.updatePwd2(oldPwd,newPwd,mHandler);
                 break;
            case R.id.lin_back:
                 finish();
                 break;
            default:
                break;
        }
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                case HandlerConstant1.UPDATE_PWD2_SUCCESS:
                    BaseBean baseBean= (BaseBean) msg.obj;
                    if(null==baseBean){
                        return;
                    }
                    if(baseBean.isStatus()){
                        finish();
                    }else{
                        showMsg(baseBean.getErrorMsg());
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
}
