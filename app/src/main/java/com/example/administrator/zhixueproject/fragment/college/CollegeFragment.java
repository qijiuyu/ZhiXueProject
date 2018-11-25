package com.example.administrator.zhixueproject.fragment.college;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.activity.TabActivity;
import com.example.administrator.zhixueproject.activity.college.CollegeManageActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.fragment.LeftFragment;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.view.CircleImageView;
import com.example.administrator.zhixueproject.view.PagerSlidingTabStrip;

/**
 * 学院fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class CollegeFragment extends BaseActivity implements View.OnClickListener{

    //侧滑菜单
    public static DrawerLayout mDrawerLayout;
    private PagerSlidingTabStrip tabs;
    private CircleImageView imgHead;
    private DisplayMetrics dm;
    private ViewPager pager;
    private CollegeInfoFragment collegeInfoFragment=new CollegeInfoFragment();
    private BuyVipFragment buyVipFragment=new BuyVipFragment();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_college);
        pager=(ViewPager)findViewById(R.id.pager);
        imgHead=(CircleImageView)findViewById(R.id.img_fc_head);
        imgHead.setOnClickListener(this);
        findViewById(R.id.iv_college).setOnClickListener(this);
        tabs = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        findViewById(R.id.iv_college).setOnClickListener(this);
        tabs = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        dm = getResources().getDisplayMetrics();
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(2);
        tabs.setViewPager(pager);
        setTabsValue();

        leftMenu();

        //注册广播
        registerReceiver();
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
            }
        }
    };



    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, dm));
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, dm));
        // 设置Tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 17, dm));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColorResource(android.R.color.white);
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        tabs.setTextColorResource(android.R.color.white);
        tabs.setSelectedTextColorResource(android.R.color.white);
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
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
        switch (v.getId()){
            //点击头像
            case R.id.img_fc_head:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                 break;
            //点击设置
            case R.id.iv_college:
                 setClass(CollegeManageActivity.class);
                 break;
                 default:
                     break;
        }
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = { "基本信息","学院VIP购买"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return collegeInfoFragment;
            }else{
                return buyVipFragment;
            }
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        final UserBean userBean= MyApplication.userInfo.getData().getUser();
        Glide.with(mContext).load(userBean.getUserImg()).override(30,30).error(R.mipmap.head_bg).into(imgHead);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
