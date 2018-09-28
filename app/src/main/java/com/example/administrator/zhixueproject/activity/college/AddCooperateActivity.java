package com.example.administrator.zhixueproject.activity.college;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.fragment.college.TopicListFragment;

/**
 * 添加合作
 */
public class AddCooperateActivity extends BaseActivity implements View.OnClickListener{

    private EditText etName,etTimeNum;
    private TextView tvTopic,tvTeacherName;
    private TopicListFragment topicListFragment;
    //侧滑菜单
    public static DrawerLayout mDrawerLayout;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cooperate);
        initView();
        rightMenu();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.add_cooperation));
        etName=(EditText)findViewById(R.id.et_institution_name);
        tvTopic=(TextView)findViewById(R.id.tv_topic_content);
        tvTeacherName=(TextView)findViewById(R.id.tv_teacher_name);
        etTimeNum=(EditText)findViewById(R.id.et_purchase_period);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        findViewById(R.id.rl_add_topic).setOnClickListener(this);
        findViewById(R.id.rl_choose_teacher).setOnClickListener(this);
        findViewById(R.id.tv_setting_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //所选话题
            case R.id.rl_add_topic:
                 openRight();
                 break;
            //发布人
            case R.id.rl_choose_teacher:
                 break;
            //保存
            case R.id.tv_setting_save:
                 break;
             default:
                 break;
        }
    }


    /**
     * 打开侧边栏
     */
    public static void openRight() {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
    }

    /**
     * 设置侧边栏
     */
    private void rightMenu() {
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
}