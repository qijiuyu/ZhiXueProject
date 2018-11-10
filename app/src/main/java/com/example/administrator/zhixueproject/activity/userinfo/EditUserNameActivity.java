package com.example.administrator.zhixueproject.activity.userinfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;

/**
 * 修改用户名
 * Created by Administrator on 2018/9/24.
 */

public class EditUserNameActivity extends BaseActivity {
    private EditText etName;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_username);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.edit_username));
        etName=(EditText)findViewById(R.id.et_username);
        final UserBean userBean=MyApplication.userInfo.getData().getUser();
        etName.setText(userBean.getUserName());
        etName.setSelection(etName.getText().toString().trim().length());
        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String userName=etName.getText().toString().trim();
                if(TextUtils.isEmpty(userName)){
                    showMsg("请输入用户名!");
                }else{
                    showProgress(getString(R.string.loading));
                    HttpMethod2.modifyUserInfo(userName, null, null, null, null,null, mHandler);
                }
            }
        });
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditUserNameActivity.this.finish();
            }
        });
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                case HandlerConstant2.MODIFY_USER_INFO_SUCCESS:
                    BaseBean baseBean= (BaseBean) msg.obj;
                    if(null==baseBean){
                        return;
                    }
                    if(baseBean.isStatus()){
                        MyApplication.userInfo.getData().getUser().setUserName(etName.getText().toString().trim());
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
