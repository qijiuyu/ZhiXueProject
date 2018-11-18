package com.example.administrator.zhixueproject.activity.college;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.fragment.college.FloorReportFragment;
import com.example.administrator.zhixueproject.fragment.college.TopicReportFragment;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.view.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.List;

/**
 * 举报管理
 * Created by Administrator on 2018/10/17.
 */

public class ReportManagerActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout linearLayout;
    private TextView tvRight,tvAll,tvTime,tvNum;
    private ImageView imgRight;
    private PagerSlidingTabStrip tabs;
    private DisplayMetrics dm;
    private ViewPager pager;
    public static String key="";
    private TopicReportFragment topicReportFragment=new TopicReportFragment();
    private FloorReportFragment floorReportFragment=new FloorReportFragment();
    public final static String ACTION_SELECT_ORDERBY="net.zhixue.adminapp.ACTION_SELECT_ORDERBY";
    //多选广播
    public final static String ACTION_DUO_XUAN="net.zhixue.adminapp.ACTION_DUO_XUAN";
    //全部删除广播
    public final static String ACTION_QUAN_BU_SHAN_CHU="net.zhixue.adminapp.ACTION_QUAN_BU_SHAN_CHU";
    //全选广播
    public final static String ACTION_QUAN_XUAN="net.zhixue.adminapp.ACTION_QUAN_XUAN";
    //删除广播
    public final static String ACTION_SHAN_CHU="net.zhixue.adminapp.ACTION_SHAN_CHU";
    //取消广播
    public final static String ACTION_QU_XIAO="net.zhixue.adminapp.ACTION_QU_XIAO";
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
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText("举报管理");
        linearLayout=(LinearLayout)findViewById(R.id.lin);
        tvRight=(TextView)findViewById(R.id.tv_right);
        tvRight.setText(getString(R.string.whole));
        imgRight=(ImageView)findViewById(R.id.img_right);
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageDrawable(getResources().getDrawable(R.mipmap.down_arrow_white));
        tabs = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        pager=(ViewPager)findViewById(R.id.pager);
        dm = getResources().getDisplayMetrics();
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(2);
        tabs.setViewPager(pager);
        findViewById(R.id.lin_right).setOnClickListener(this);
        findViewById(R.id.tv_delete_multiSelect).setOnClickListener(this);
        findViewById(R.id.tv_delete_all).setOnClickListener(this);
        findViewById(R.id.ll_all_check).setOnClickListener(this);
        findViewById(R.id.ll_delete).setOnClickListener(this);
        findViewById(R.id.ll_cancel).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.lin_right:
                 if(linearLayout.getVisibility()==View.VISIBLE){
                    linearLayout.setVisibility(View.GONE);
                    imgRight.setImageDrawable(getResources().getDrawable(R.mipmap.down_arrow_white));
                 }else{
                    showSelectPop();
                 }
                 break;
            case R.id.tv_all:
                updateTextView(0);
                break;
            case R.id.tv_red:
                updateTextView(1);
                break;
            case R.id.tv_no_red:
                updateTextView(2);
                break;
            //多选删除
            case R.id.tv_delete_multiSelect:
                 findViewById(R.id.ll_bottom_choose).setVisibility(View.VISIBLE);
                 findViewById(R.id.ll_bottom_delete).setVisibility(View.GONE);
                 intent.setAction(ACTION_DUO_XUAN);
                 break;
            case R.id.tv_delete_all:
                 intent.setAction(ACTION_QUAN_BU_SHAN_CHU);
                 break;
            //全选
            case R.id.ll_all_check:
                 ImageView imageView=(ImageView)findViewById(R.id.iv_all_checked);
                 imageView.setImageDrawable(getResources().getDrawable(R.mipmap.checked_blue_report));
                 intent.setAction(ACTION_QUAN_XUAN);
                 break;
            //删除
            case R.id.ll_delete:
                 intent.setAction(ACTION_SHAN_CHU);
                 break;
            //取消
            case R.id.ll_cancel:
                 ImageView imageView2=(ImageView)findViewById(R.id.iv_all_checked);
                 imageView2.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked_gray_report));
                 findViewById(R.id.ll_bottom_choose).setVisibility(View.GONE);
                 findViewById(R.id.ll_bottom_delete).setVisibility(View.VISIBLE);
                 intent.setAction(ACTION_QU_XIAO);
                 break;
            case R.id.lin_back:
                 finish();
                 break;
        }
        sendBroadcast(intent);
    }


    /**
     * 弹出选择框
     */
    private void showSelectPop(){
        imgRight.setImageDrawable(getResources().getDrawable(R.mipmap.up_arrow_white));
        View view= LayoutInflater.from(mContext).inflate(R.layout.feedback_select_pop,null);
        tvAll=(TextView)view.findViewById(R.id.tv_all);
        tvTime=(TextView)view.findViewById(R.id.tv_red);
        tvNum=(TextView)view.findViewById(R.id.tv_no_red);
        tvAll.setText("排序");
        tvTime.setText("时间");
        tvNum.setText("人数");
        tvAll.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        tvNum.setOnClickListener(this);
        switch (key){
            case "":
                updateColor(0);
                break;
            case "1":
                updateColor(1);
                break;
            case "2":
                updateColor(2);
                break;
        }
        linearLayout.removeAllViews();
        linearLayout.addView(view);
        linearLayout.setVisibility(View.VISIBLE);
    }


    /**
     * 修改选中的颜色
     * @param index
     */
    private void updateColor(int index){
        List<TextView> list=new ArrayList<>();
        list.add(tvAll);
        list.add(tvTime);
        list.add(tvNum);
        for (int i=0;i<list.size();i++){
            if(i==index){
                list.get(i).setTextColor(getResources().getColor(R.color.color_fd703e));
                list.get(i).setBackgroundColor(getResources().getColor(R.color.color_f8f8f8));
            }else{
                list.get(i).setTextColor(getResources().getColor(R.color.color_666666));
                list.get(i).setBackgroundColor(getResources().getColor(android.R.color.white));
            }
        }
    }


    /**
     * 选择后关闭弹框并查询数据
     */
    private void updateTextView(int index){
        linearLayout.setVisibility(View.GONE);
        imgRight.setImageDrawable(getResources().getDrawable(R.mipmap.down_arrow_white));
        switch (index){
            case 0:
                tvRight.setText("排序");
                key="";
                break;
            case 1:
                tvRight.setText("时间");
                key="1";
                break;
            case 2:
                tvRight.setText("人数");
                key="2";
                break;
        }
        Intent intent=new Intent(ACTION_SELECT_ORDERBY);
        sendBroadcast(intent);
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
