package com.example.administrator.zhixueproject.activity.live;

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
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.activity.college.SelectTeacherActivity;
import com.example.administrator.zhixueproject.bean.Teacher;
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import com.example.administrator.zhixueproject.fragment.college.TopicListFragment;
import com.example.administrator.zhixueproject.view.SwitchButton;

/**
 * 发布直播
 */
public class AddLiveActivity extends BaseActivity implements View.OnClickListener{

    private EditText etTitle,etContent;
    private TextView tvTopicName,tvTeacher,tvTime,tvCost;
    private SwitchButton switchButton;
    //侧滑菜单
    public static DrawerLayout mDrawerLayout;
    //话题对象
    private TopicListBean topicListBean;
    //讲师对象
    private Teacher teacher;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_live);
        initView();
        rightMenu();
        //注册广播
        registerReceiver();
    }


    /**
     * 初始化
     */
    private void initView(){
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.release_live));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        etTitle=(EditText)findViewById(R.id.et_title);
        tvTopicName=(TextView)findViewById(R.id.tv_topic);
        tvTeacher=(TextView)findViewById(R.id.tv_lecturer);
        tvTime=(TextView)findViewById(R.id.tv_live_time);
        tvCost=(TextView)findViewById(R.id.tv_cost);
        switchButton=(SwitchButton)findViewById(R.id.sb_stick);
        etContent=(EditText)findViewById(R.id.et_live_detail);
        findViewById(R.id.rl_topic).setOnClickListener(this);
        findViewById(R.id.rl_lecturer).setOnClickListener(this);
        findViewById(R.id.rl_live_time).setOnClickListener(this);
        findViewById(R.id.rl_cost).setOnClickListener(this);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //所属话题
            case R.id.rl_topic:
                 mDrawerLayout.openDrawer(Gravity.RIGHT);
                 break;
             //选择讲师
            case R.id.rl_lecturer:
                 Intent intent=new Intent(mContext,SelectTeacherActivity.class);
                 startActivityForResult(intent,1);
                 overridePendingTransition(R.anim.activity_bottom_in, R.anim.alpha);
                 break;
            //直播时间
            case R.id.rl_live_time:
                 break;
             //是否付费
            case R.id.rl_cost:
                 break;
           //发布
            case R.id.tv_confirm:
                 break;
            case R.id.lin_back:
                 finish();
                 break;
             default:
                 break;
        }
    }


    /**
     * 设置侧边栏
     */
    private void rightMenu() {
        // 设置遮盖主要内容的布颜色
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        //关闭手势滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }


    /**
     * 注册广播
     */
    private void registerReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(TopicListFragment.ACTION_TOPIC_TITLE);
        // 注册广播监听
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(action.equals(TopicListFragment.ACTION_TOPIC_TITLE)){
                topicListBean= (TopicListBean) intent.getSerializableExtra("topicListBean");
                if(null==topicListBean){
                    return;
                }
                tvTopicName.setText(topicListBean.getTopicName());
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            teacher= (Teacher) data.getSerializableExtra("teacher");
            if(teacher==null){
                return;
            }
            tvTeacher.setText(teacher.getUserName());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
