package com.example.administrator.zhixueproject.activity.userinfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;

/**
 * 修改签名
 *
 * @author petergee
 * @date 2018/9/21
 */
public class EditSignActivity extends BaseActivity{

    private EditText etSign;
    private TextView tvLength;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.edit_sign));
        etSign=(EditText)findViewById(R.id.et_sign);
        final UserBean userBean=MyApplication.userInfo.getData().getUser();
        etSign.setText(userBean.getUserIntro());
        etSign.setSelection(etSign.getText().toString().trim().length());
        tvLength=(TextView)findViewById(R.id.tv_sign_length);
        etSign.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            public void afterTextChanged(Editable s) {
                final int length=s.toString().length();
                tvLength.setText(length+"/20");
            }
        });
        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String strSign=etSign.getText().toString().trim();
                if(TextUtils.isEmpty(strSign)){
                    showMsg(getString(R.string.sign_empty));
                }else{
                    showProgress(getString(R.string.loading));
                    HttpMethod2.modifyUserInfo(null, null, null, null, strSign,null, mHandler);
                }
            }
        });
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditSignActivity.this.finish();
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
                         MyApplication.userInfo.getData().getUser().setUserIntro(etSign.getText().toString().trim());
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