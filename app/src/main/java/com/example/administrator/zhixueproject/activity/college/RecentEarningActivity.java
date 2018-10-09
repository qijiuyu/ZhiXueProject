package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.college.RecentEarningAdapter;
import com.example.administrator.zhixueproject.bean.RecentEarning;
import com.example.administrator.zhixueproject.bean.RecentEarningList;
import com.example.administrator.zhixueproject.fragment.college.SelectTimeFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 近期收益
 */
public class RecentEarningActivity extends BaseActivity implements View.OnClickListener,SelectTimeFragment.OnInquireTimeListener{

    private TextView tvCount,tvTotalPrice,tvCollegeCount,tvBalance;
    private RecyclerView recyclerView;
    private String startTime="",endTime="";
    private Integer[] incomeIcon = new Integer[]{R.mipmap.income_group_icon, R.mipmap
            .income_topic_icon, R.mipmap.income_post_icon, R.mipmap.income_reward_icon, R.mipmap
            .income_reward_divide_icon, R.mipmap.income_question_icon};
    //选择时间的fragment
    private SelectTimeFragment selectTimeFragment;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_earning);
        initView();
        getData();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HttpMethod1.getTopicAccount(startTime,endTime,1,20,simpleDateFormat.format(new Date()),0,null);
    }


    /**
     * 初始化控件
     */
    private void initView(){
        tvCount=(TextView)findViewById(R.id.tv_gift_count);
        tvTotalPrice=(TextView)findViewById(R.id.tv_gift_prices);
        tvCollegeCount=(TextView)findViewById(R.id.tv_college_count);
        tvBalance=(TextView)findViewById(R.id.tv_college_prices);
        recyclerView=(RecyclerView)findViewById(R.id.rv_recent_income);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        findViewById(R.id.tv_search).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //按时间查询
            case R.id.tv_search:
                showFragment(true);
                break;
        }
    }


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                case HandlerConstant1.GET_ACCOUNT_SUCCESS:
                     final RecentEarning recentEarning= (RecentEarning) msg.obj;
                     if(null==recentEarning){
                         return;
                     }
                     if(recentEarning.isStatus()){
                         tvCount.setText(recentEarning.getData().getAccount().getSumGift()+"个");
                         tvTotalPrice.setText(recentEarning.getData().getAccount().getSumCost()+"元");
                         tvCollegeCount.setText(recentEarning.getData().getAccount().getCollegeIncomes()+"元");
                         tvBalance.setText(recentEarning.getData().getAccount().getCollegeBalance()+"元");
                         List<RecentEarningList> list=new ArrayList<>();
                         final String[] incomeType = getResources().getStringArray(R.array.income_type);
                         for (int i = 0; i < incomeType.length; i++) {
                              RecentEarningList recentEarningList=new RecentEarningList();
                              recentEarningList.setIcon(incomeIcon[i]);
                              switch (i){
                                  case 0:
                                      recentEarningList.setMoney(recentEarning.getData().getAccount().getSumAcc()+"元");
                                       break;
                                  case 1:
                                      recentEarningList.setMoney(recentEarning.getData().getAccount().getSumTopic()+"元");
                                      break;
                                  case 2:
                                      recentEarningList.setMoney(recentEarning.getData().getAccount().getSumPost()+"元");
                                      break;
                                  case 3:
                                      recentEarningList.setMoney(recentEarning.getData().getAccount().getSumGive()+"元");
                                      break;
                                  case 4:
                                      recentEarningList.setMoney(recentEarning.getData().getAccount().getSumScalGive()+"元");
                                      break;
                                  case 5:
                                      recentEarningList.setMoney(recentEarning.getData().getAccount().getSumYouChangGive()+"元");
                                      break;
                              }
                              recentEarningList.setTypeName(incomeType[i]);
                              list.add(recentEarningList);
                         }
                         RecentEarningAdapter recentEarningAdapter=new RecentEarningAdapter(R.layout.recent_earning_item,list);
                         recyclerView.setAdapter(recentEarningAdapter);
                     }else{
                         showMsg(recentEarning.getErrorMsg());
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
     * 查询数据
     */
    private void getData(){
        showProgress(getString(R.string.loding));
        HttpMethod1.getAccount(startTime,endTime,mHandler);
    }


    /**
     * 显示查询时间弹窗
     *
     * @param show
     */
    private void showFragment(boolean show) {
        if (selectTimeFragment == null) {
            selectTimeFragment = new SelectTimeFragment();
            selectTimeFragment.setOnInquireTimeListener(this);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (show) {
            transaction.add(R.id.fl_time, selectTimeFragment).commit();
        } else {
            transaction.remove(selectTimeFragment).commit();
        }
    }

    @Override
    public void closeInquireTimeListener(String startTime, String endTime) {
        this.startTime=startTime;
        this.endTime=endTime;
        showFragment(false);
        if(TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)){
            return;
        }
        getData();
    }
}
