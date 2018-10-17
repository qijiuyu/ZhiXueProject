package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.bean.FeedBack;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;

/**
 * 意见反馈详情
 */
public class FeedBackDetailsActivity extends BaseActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_details);
        initView();
    }
    
    
    private void initView(){
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.feedback));
        TextView tvName=(TextView)findViewById(R.id.tv_feedback_name);
        TextView tvTime=(TextView)findViewById(R.id.tv_feedback_time);
        TextView tvType=(TextView)findViewById(R.id.tv_feedback_type);
        TextView tvIsRed=(TextView)findViewById(R.id.tv_feedback_isRead);
        TextView tvPhone=(TextView)findViewById(R.id.tv_feedback_phone);
        TextView tvEmail=(TextView)findViewById(R.id.tv_feedback_email);
        TextView tvContent=(TextView)findViewById(R.id.tv_feedback_content);
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedBackDetailsActivity.this.finish();
            }
        });
        
        
        final FeedBack.FeedBackList feedBackList= (FeedBack.FeedBackList) getIntent().getSerializableExtra("feedBackList");
        if(null==feedBackList){
            return;
        }
        tvName.setText(feedBackList.getUserName());
        tvTime.setText(feedBackList.getAdviceCreationTime());
        int type = feedBackList.getAdviceType();//反馈来源（0：用户，1：学院）
        String[] feedback_type = mContext.getResources().getStringArray(R.array.feedback_type);
        tvType.setText(feedback_type[type]);
        int adviceReadyn = feedBackList.getAdviceReadyn();//是否已读（0：否，1：是）
        String[] adviceReadyn_type = mContext.getResources().getStringArray(R.array.adviceReadyn);
        tvIsRed.setText(adviceReadyn_type[adviceReadyn]);
        tvPhone.setText(feedBackList.getUserPhone());
        tvEmail.setText(feedBackList.getUserEmail());
        tvContent.setText(feedBackList.getAdviceContent());

        //添加意见设置已读
        HttpMethod1.feedbackIsRead(feedBackList.getAdviceId(),null);
    }
}
