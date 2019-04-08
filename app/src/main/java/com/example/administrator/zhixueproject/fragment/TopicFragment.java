package com.example.administrator.zhixueproject.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.activity.TabActivity;
import com.example.administrator.zhixueproject.activity.college.CollegeManageActivity;
import com.example.administrator.zhixueproject.activity.topic.ActionManageActivity;
import com.example.administrator.zhixueproject.activity.topic.TopicListManageActivity;
import com.example.administrator.zhixueproject.activity.topic.VoteManageActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.view.CircleImageView;

/**
 * 话题管理fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class TopicFragment extends BaseActivity implements View.OnClickListener {

    //侧滑菜单
    public static DrawerLayout mDrawerLayout;
    private CircleImageView imgHead;
    private TextView tvHead;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_topic_manage);

        imgHead=(CircleImageView)findViewById(R.id.img_fc_head);
        imgHead.setOnClickListener(this);
        tvHead=(TextView)findViewById(R.id.tv_head);
        TextView tvTopicManage = (TextView) findViewById(R.id.tv_topic);
        tvTopicManage.setOnClickListener(this);
        TextView tvActivityManage = (TextView)findViewById(R.id.tv_action);
        tvActivityManage.setOnClickListener(this);
        TextView tvVoteManage = (TextView)findViewById(R.id.tv_vote);
        tvVoteManage.setOnClickListener(this);
       findViewById(R.id.iv_college).setOnClickListener(this);

        leftMenu();

        registerReceiver();
    }


    /**
     * 设置侧边栏
     */
    private void leftMenu() {
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        // 设置遮盖主要内容的布颜色
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View content = mDrawerLayout.getChildAt(0);
                int offset = (int) (drawerView.getWidth() * slideOffset);
                content.setTranslationX(offset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击头像
            case R.id.img_fc_head:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            //点击设置
            case R.id.iv_college:
                setClass(CollegeManageActivity.class);
                break;
            // 话题列表管理
            case R.id.tv_topic:
                setClass(TopicListManageActivity.class);
                break;
            // 活动管理
            case R.id.tv_action:
                setClass(ActionManageActivity.class);
                break;
            // 投票管理
            case R.id.tv_vote:
                setClass(VoteManageActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        final UserBean userBean= MyApplication.userInfo.getData().getUser();
        tvHead.setText(MyApplication.homeBean.getCollegeName());
        Glide.with(mContext).load(userBean.getUserImg()).override(30,30).error(R.mipmap.head_bg).into(imgHead);

        if(MyApplication.homeBean.getAttendType()!=1){
            findViewById(R.id.iv_college).setVisibility(View.GONE);
        }else{
            findViewById(R.id.iv_college).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 注册广播
     */
    private void registerReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(LeftFragment.GET_COLLEGE_DETAILS);
        // 注册广播监听
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(LeftFragment.GET_COLLEGE_DETAILS)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                tvHead.setText(MyApplication.homeBean.getCollegeName());
            }
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
