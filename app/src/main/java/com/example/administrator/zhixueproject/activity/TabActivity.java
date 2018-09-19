package com.example.administrator.zhixueproject.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.adapter.TabAdapter;
import com.example.administrator.zhixueproject.utils.ActivitysLifecycle;
import com.example.administrator.zhixueproject.utils.EnumTAB;
import com.example.administrator.zhixueproject.utils.EnumUtils;
import com.example.administrator.zhixueproject.view.MyViewPager;

public class TabActivity extends BaseActivity implements View.OnClickListener {

    private MyViewPager myViewPager;
    //侧滑菜单
    public static DrawerLayout mDrawerLayout;
    private TabAdapter tabAdapter;
    // 按两次退出
    protected long exitTime = 0;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initView();
        leftMenu();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        myViewPager = (MyViewPager) findViewById(R.id.tab_vp);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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
    private void leftMenu() {
        // 设置遮盖主要内容的布颜色
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        //关闭手势滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            public void onDrawerStateChanged(int arg0) {
            }

            public void onDrawerSlide(View drawerView, float slideOffset) {
                View content = mDrawerLayout.getChildAt(0);
                int offset = (int) (drawerView.getWidth() * slideOffset);
                content.setTranslationX(offset);
            }

            public void onDrawerOpened(View arg0) {
            }

            public void onDrawerClosed(View arg0) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        EnumTAB[] enumArr = EnumTAB.values();
        for (int i = 0; i < enumArr.length; i++) {
            if (enumArr[i].getId() == v.getId()) {
                setCurrentTabByTag(enumArr[i]);
                break;
            }
        }
    }

    public void setCurrentTabByTag(EnumTAB enumTab) {
        EnumTAB[] enumArr = EnumTAB.values();
        for (int i = 0; i < enumArr.length; i++) {
            enumArr[i].getRadioButton().setChecked(enumArr[i] == enumTab);
        }
        myViewPager.setCurrentItem(EnumUtils.getEnumUtils().getIdx(enumTab), false);
    }

    /**
     * 打开侧边栏
     */
    public static void openLeft() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
    }


    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN ) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(),"再按一次退出程序!",Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivitysLifecycle.getInstance().exit();
            }
            return false;
        }
        return super.dispatchKeyEvent(event);
    }

}
