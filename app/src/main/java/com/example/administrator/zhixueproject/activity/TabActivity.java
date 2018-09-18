package com.example.administrator.zhixueproject.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.adapter.TabAdapter;
import com.example.administrator.zhixueproject.utils.EnumTAB;
import com.example.administrator.zhixueproject.view.MyViewPager;

public class TabActivity extends BaseActivity implements View.OnClickListener{

    private MyViewPager myViewPager;
    //侧滑菜单
    public static DrawerLayout mDrawerLayout;
    private TabAdapter tabAdapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initView();
        leftMenu();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        myViewPager=(MyViewPager)findViewById(R.id.tab_vp);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        final EnumTAB[] enumArr = EnumTAB.values();
        Drawable drawable;
        for (int i = 0; i < enumArr.length; i++) {
            enumArr[i].setRadioButton((RadioButton) findViewById(enumArr[i].getId()));
            enumArr[i].getRadioButton().setOnClickListener(this);
            enumArr[i].getRadioButton().setText(enumArr[i].getTitle());
            drawable = getResources().getDrawable(enumArr[i].getDrawable());
            drawable.setBounds(0, 0, 30, 30);
            enumArr[i].getRadioButton().setCompoundDrawables(null, drawable, null, null);
        }
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(tabAdapter);
        myViewPager.setNoScroll(true);//禁止不能滑动
        myViewPager.setOffscreenPageLimit(5);
        myViewPager.setCurrentItem(0);
    }


    /**
     * 设置侧边栏
     */
    private void leftMenu(){
        // 设置遮盖主要内容的布颜色
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        //关闭手势滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            public void onDrawerStateChanged(int arg0) {
            }

            public void onDrawerSlide(View arg0, float arg1) {
            }

            public void onDrawerOpened(View arg0) {
            }

            public void onDrawerClosed(View arg0) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_tab_1:
                myViewPager.setCurrentItem(0);
                 break;
            case R.id.rb_tab_2:
                myViewPager.setCurrentItem(1);
                break;
            case R.id.rb_tab_3:
                myViewPager.setCurrentItem(2);
                break;
            case R.id.rb_tab_4:
                myViewPager.setCurrentItem(3);
                break;
            case R.id.rb_tab_5:
                myViewPager.setCurrentItem(4);
                break;
                default:
                    break;
        }
    }

    /**
     * 打开侧边栏
     */
    public static void openLeft(){
        mDrawerLayout.openDrawer(Gravity.LEFT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
    }
}
