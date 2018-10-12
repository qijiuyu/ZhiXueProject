package com.example.administrator.zhixueproject.activity.college;

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
 * 添加反馈内容
 */
public class AddFeedBackActivity extends BaseActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.feedback));
        final EditText etContent=(EditText)findViewById(R.id.et_feedback_content);
        //提交
        findViewById(R.id.tv_commit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String content=etContent.getText().toString().trim();
                if(TextUtils.isEmpty(content)){
                    showMsg("请输入反馈内容！");
                }else{
                    showProgress(getString(R.string.loding));
                    HttpMethod1.addFeedBack(content,mHandler);
                }
            }
        });

        //返回
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddFeedBackActivity.this.finish();
            }
        });
    }



    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                case HandlerConstant1.ADD_FEEDBACK_SUCCESS:
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
