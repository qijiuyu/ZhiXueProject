package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.college.CollegeItemAdapter;
import com.example.administrator.zhixueproject.bean.CollegeList;
import com.example.administrator.zhixueproject.bean.Colleges;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import java.util.ArrayList;
import java.util.List;

/**
 * 加入过的更多学院
 * Created by Administrator on 2018/9/23.
 */

public class MoreCollegeActivity extends BaseActivity{
    private ListView listView;
    private List<Colleges> list=new ArrayList<>();
    private CollegeItemAdapter collegeItemAdapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_college);
        initView();
        getData();
    }

    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.joined_college));
        listView = (ListView)findViewById(R.id.rv_college_list);
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MoreCollegeActivity.this.finish();
            }
        });
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                case HandlerConstant1.GET_MORE_COLLEGE_SUCCESS:
                     CollegeList collegeList= (CollegeList) msg.obj;
                     if(null==collegeList){
                         return;
                     }
                     if(collegeList.isStatus()){
                         list.addAll(collegeList.getData().getData());
                         collegeItemAdapter=new CollegeItemAdapter(mContext,list);
                         listView.setAdapter(collegeItemAdapter);
                     }else{
                         showMsg(collegeList.getErrorMsg());
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


    /**
     * 获取加入过的更多学院
     */
    private void getData(){
        showProgress(getString(R.string.loading));
        HttpMethod1.getMoreCollege(mHandler);
    }
}
