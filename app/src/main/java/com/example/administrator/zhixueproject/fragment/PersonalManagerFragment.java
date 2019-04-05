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
import com.example.administrator.zhixueproject.activity.memberManage.BlacklistActivity;
import com.example.administrator.zhixueproject.activity.memberManage.KickOutMemberActivity;
import com.example.administrator.zhixueproject.activity.memberManage.MemberApplyActivity;
import com.example.administrator.zhixueproject.activity.memberManage.MemberManagerActivity;
import com.example.administrator.zhixueproject.activity.memberManage.SignInManageActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.view.CircleImageView;

/**
 * 人员管理fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class PersonalManagerFragment extends BaseActivity implements View.OnClickListener {

    //侧滑菜单
    public static DrawerLayout mDrawerLayout;
    private CircleImageView imgHead;
    private TextView tvHead;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_personal_manager);
        initView();
        leftMenu();
        registerReceiver();
    }

    private void initView() {
        imgHead=(CircleImageView)findViewById(R.id.img_fc_head);
        imgHead.setOnClickListener(this);
        tvHead=(TextView)findViewById(R.id.tv_head);
        // 会员管理
        findViewById(R.id.iv_college).setOnClickListener(this);
        findViewById(R.id.rl_member_manager).setOnClickListener(this);
        findViewById(R.id.rl_member_application).setOnClickListener(this);
        findViewById(R.id.rl_kick_out_member).setOnClickListener(this);
        findViewById(R.id.rl_black_list).setOnClickListener(this);
        findViewById(R.id.rl_sign_in_manager).setOnClickListener(this);
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


    public void onClick(View view){
        switch (view.getId()){
            //点击头像
            case R.id.img_fc_head:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            //点击设置
            case R.id.iv_college:
                setClass(CollegeManageActivity.class);
                break;
            case R.id.rl_member_manager://C端会员管理
                setClass(MemberManagerActivity.class);
                break;
            case R.id.rl_member_application://会员申请
                setClass(MemberApplyActivity.class);
                break;
            case R.id.rl_kick_out_member://踢出的会员
                setClass(KickOutMemberActivity.class);
                break;
            case R.id.rl_black_list://黑名单
                setClass(BlacklistActivity.class);
                break;
            case R.id.rl_sign_in_manager://签到管理
                setClass(SignInManageActivity.class);
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
