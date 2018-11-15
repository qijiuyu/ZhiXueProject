package com.example.administrator.zhixueproject.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.adapter.TabAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.ActivitysLifecycle;
import com.example.administrator.zhixueproject.utils.Encrypt;
import com.example.administrator.zhixueproject.utils.EnumTAB;
import com.example.administrator.zhixueproject.utils.EnumUtils;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.SPUtil;
import com.example.administrator.zhixueproject.view.MyViewPager;

import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

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
            drawable.setBounds(0, 0, 58, 58);
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


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                //获取个人资料
                case HandlerConstant1.GET_USER_INFO_SUCCESS:
                    final String message=msg.obj.toString();
                    if(TextUtils.isEmpty(message)){
                        return;
                    }
                    try {
                        final JSONObject jsonObject=new JSONObject(message);
                        if(jsonObject.getBoolean("status")){
                            final UserBean userBean= MyApplication.gson.fromJson(jsonObject.getString("data"),UserBean.class);
                            if(null==userBean){
                                return;
                            }
                            MyApplication.userInfo.getData().setUser(userBean);
                            MyApplication.spUtil.addString(SPUtil.USER_INFO,MyApplication.gson.toJson(MyApplication.userInfo));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

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


    @Override
    protected void onResume() {
        super.onResume();
        final UserBean userBean= MyApplication.userInfo.getData().getUser();
        HttpMethod1.getUserInfo(mHandler);

        String a= "15011224467123456be07e7093fefeaaf95f54c2b350d7810";
        try {
            String b=Encrypt.getSHA(a);
            String c=Encrypt.stringToAscii(b);
            HttpMethod1.autoLogin(userBean.getUserPhone(),userBean.getUserPassword(),c,mHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
