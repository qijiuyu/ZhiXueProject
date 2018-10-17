package com.example.administrator.zhixueproject.activity.college;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.fragment.college.CollegeFragment;
import com.example.administrator.zhixueproject.fragment.college.FloorReportFragment;
import com.example.administrator.zhixueproject.fragment.college.TopicReportFragment;
import com.example.administrator.zhixueproject.view.PagerSlidingTabStrip;

/**
 * 举报管理
 * Created by Administrator on 2018/10/17.
 */

public class ReportManagerActivity extends BaseActivity {

    private PagerSlidingTabStrip tabs;
    private DisplayMetrics dm;
    private ViewPager pager;
    private TopicReportFragment topicReportFragment=new TopicReportFragment();
    private FloorReportFragment floorReportFragment=new FloorReportFragment();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_manager);
        initView();
        setTabsValue();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        tabs = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        pager=(ViewPager)findViewById(R.id.pager);
        dm = getResources().getDisplayMetrics();
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(2);
        tabs.setViewPager(pager);
    }


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
                TypedValue.COMPLEX_UNIT_SP, 14, dm));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColorResource(R.color.color_40c5f1);
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        tabs.setTextColorResource(R.color.color_666666);
        tabs.setSelectedTextColorResource(R.color.color_333333);
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = { "帖子举报","楼层举报"};

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
                return topicReportFragment;
            }else{
                return floorReportFragment;
            }
        }

    }
}
