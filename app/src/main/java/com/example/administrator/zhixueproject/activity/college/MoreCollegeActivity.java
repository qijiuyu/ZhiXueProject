package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.CollegeItemAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.CollegeList;
import com.example.administrator.zhixueproject.bean.Colleges;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.view.DividerItemDecoration;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 加入过的更多学院
 * Created by Administrator on 2018/9/23.
 */

public class MoreCollegeActivity extends BaseActivity  implements MyRefreshLayoutListener {
    private ListView listView;
    private MyRefreshLayout mRefreshLayout;
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
        mRefreshLayout = (MyRefreshLayout)findViewById(R.id.amc_collete_list);
        listView = (ListView)findViewById(R.id.rv_college_list);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
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
                         mRefreshLayout.refreshComplete();
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

    @Override
    public void onRefresh(View view) {
        mRefreshLayout.refreshComplete();
    }

    @Override
    public void onLoadMore(View view) {
        mRefreshLayout.loadMoreComplete();
    }


    /**
     * 获取加入过的更多学院
     */
    private void getData(){
        showProgress(getString(R.string.loading));
        final UserBean userBean= MyApplication.userInfo.getData().getUser();
        HttpMethod1.getMoreCollege(userBean.getUserId()+"",mHandler);
    }
}
