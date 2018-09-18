package com.example.administrator.zhixueproject.fragment.college;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.TabActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;
import com.example.administrator.zhixueproject.view.CircleImageView;
import com.example.administrator.zhixueproject.view.PagerSlidingTabStrip;

/**
 * 学院fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class CollegeFragment extends BaseFragment implements View.OnClickListener{

    private PagerSlidingTabStrip tabs;
    private CircleImageView imgHead;
    private DisplayMetrics dm;
    private ViewPager pager;
    private CollegeInfoFragment collegeInfoFragment=new CollegeInfoFragment();
    private BuyVipFragment buyVipFragment=new BuyVipFragment();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    View view=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_college, container, false);
        pager=(ViewPager)view.findViewById(R.id.pager);
        imgHead=(CircleImageView)view.findViewById(R.id.img_fc_head);
        imgHead.setOnClickListener(this);
        tabs = (PagerSlidingTabStrip)view.findViewById(R.id.tabs);
        dm = getResources().getDisplayMetrics();
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        pager.setOffscreenPageLimit(2);
        tabs.setViewPager(pager);
        setTabsValue();
        return view;
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
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_fc_head:
                TabActivity.openLeft();
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



}
