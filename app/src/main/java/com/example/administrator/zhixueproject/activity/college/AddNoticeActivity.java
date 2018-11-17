package com.example.administrator.zhixueproject.activity.college;

import android.content.Intent;
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
import com.example.administrator.zhixueproject.bean.Notice;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;

import org.json.JSONObject;

/**
 * 添加公告
 */
public class AddNoticeActivity extends BaseActivity implements View.OnClickListener{

    private EditText etTitle,etContent;
    private Notice.NoticeList noticeList;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.announcement_edit));
        etTitle=(EditText)findViewById(R.id.et_announce_title);
        etContent=(EditText)findViewById(R.id.et_announce_info);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);

        //编辑
        noticeList= (Notice.NoticeList) getIntent().getSerializableExtra("noticeList");
        if(null!=noticeList){
            etTitle.setText(noticeList.getNoticeTitle());
            etContent.setText(noticeList.getNoticeInfo());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_commit:
                 final String title=etTitle.getText().toString().trim();
                 final String content=etContent.getText().toString().trim();
                 if(TextUtils.isEmpty(title)){
                     showMsg("请输入标题！");
                 }else if(TextUtils.isEmpty(content)){
                     showMsg("请输入内容！");
                 }else{
                     showProgress(getString(R.string.loding));
                     if(null==noticeList){
                         HttpMethod1.addNotice(title,content,mHandler);
                     }else{
                         HttpMethod1.updateNotice(noticeList.getNoticeId(),title,content,mHandler);
                     }
                 }
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
                //添加成功
                case HandlerConstant1.ADD_NOTICE_SUCCESS:
                     BaseBean baseBean= (BaseBean) msg.obj;
                     if(null==baseBean){
                         return;
                     }
                     if(baseBean.isStatus()){
                         Intent intent=new Intent(mContext,NoticeListActivity.class);
                         setResult(1,intent);
                         finish();
                     }else{
                         showMsg(baseBean.getErrorMsg());
                     }
                     break;
                 //修改成功
                case HandlerConstant1.UPDATE_NOTICE_SUCCESS:
                     final String message= (String) msg.obj;
                     if(TextUtils.isEmpty(message)){
                         return;
                     }
                     try {
                         final JSONObject jsonObject=new JSONObject(message);
                         if(jsonObject.getBoolean("status")){
                             final JSONObject jsonObject1=new JSONObject(jsonObject.getString("data"));
                             final Notice.NoticeList noticeList= MyApplication.gson.fromJson(jsonObject1.getString("notice"),Notice.NoticeList.class);
                             if(null==noticeList){
                                 return;
                             }
                             Intent intent=new Intent(mContext,NoticeListActivity.class);
                             intent.putExtra("noticeList",noticeList);
                             setResult(2,intent);
                             finish();
                         }else{
                             showMsg(jsonObject.getString("errorMsg"));
                         }
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
}
