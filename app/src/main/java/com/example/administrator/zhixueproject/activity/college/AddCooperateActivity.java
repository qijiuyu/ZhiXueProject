package com.example.administrator.zhixueproject.activity.college;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BuyIness;
import com.example.administrator.zhixueproject.bean.Teacher;
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import com.example.administrator.zhixueproject.fragment.college.TopicListFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;

import org.json.JSONObject;

/**
 * 添加合作
 */
public class AddCooperateActivity extends BaseActivity implements View.OnClickListener{

    private EditText etName,etTimeNum;
    private TextView tvTopic,tvTeacherName;
    //侧滑菜单
    public static DrawerLayout mDrawerLayout;
    //话题对象
    private TopicListBean topicListBean;
    //讲师对象
    private Teacher teacher;
    private BuyIness.BusInessList busInessList;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cooperate);
        initView();
        rightMenu();
        //注册广播
        registerReceiver();
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
        findViewById(R.id.lin_back).setOnClickListener(this);
    }


    /**
     * 展示要编辑的数据
     */
    private void showUpdateData(){
        busInessList= (BuyIness.BusInessList) getIntent().getSerializableExtra("busInessList");
        if(null==busInessList){
            return;
        }
        etName.setText(busInessList.getCollegeName());
        topicListBean=new TopicListBean();
        topicListBean.setTopicId(busInessList.getBuytopicTopic());
        tvTopic.setText(busInessList.getTopicName());
        teacher=new Teacher();
        teacher.setTeacherId(Long.parseLong(busInessList.getNewWriterId()));
//        tvTeacherName.setText(busInessList);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //所选话题
            case R.id.rl_add_topic:
                 mDrawerLayout.openDrawer(Gravity.RIGHT);
                 break;
            //发布人
            case R.id.rl_choose_teacher:
                 Intent intent=new Intent(mContext,SelectTeacherActivity.class);
                 startActivityForResult(intent,1);
                 overridePendingTransition(R.anim.activity_bottom_in, R.anim.alpha);
                 break;
            //保存
            case R.id.tv_setting_save:
                 final String colleteName=etName.getText().toString().trim();
                 final String month=etTimeNum.getText().toString().trim();
                 if(TextUtils.isEmpty(colleteName)){
                     showMsg("请输入学院名称！");
                     return;
                 }
                 if(null==topicListBean){
                     showMsg("请添加所属话题！");
                     return;
                 }
                 if(null==teacher){
                     showMsg("请添加发布人！");
                     return;
                 }
                 if(TextUtils.isEmpty(month)){
                     showMsg("请输入购买日期！");
                     return;
                 }
                 showProgress(getString(R.string.loding));
                 HttpMethod1.addCooPerate(topicListBean.getTopicId(),teacher.getTeacherId(),month,mHandler);
                 break;
            case R.id.lin_back:
                 finish();
                 break;
             default:
                 break;
        }
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                case HandlerConstant1.ADD_COOPERATE_SUCCESS:
                    //BuyIness.BusInessList
                     final String message= (String) msg.obj;
                     if(TextUtils.isEmpty(message)){
                         return;
                     }
                     try {
                         final JSONObject jsonObject=new JSONObject(message);
                         if(!jsonObject.getBoolean("status")){
                             showMsg(jsonObject.getString("errorMsg"));
                             return;
                         }
                         final JSONObject jsonObject1=new JSONObject(jsonObject.getString("data"));
                         BuyIness.BusInessList busInessList= MyApplication.gson.fromJson(jsonObject1.getString("buyTopic"),BuyIness.BusInessList.class);

                     }catch (Exception e){
                         e.printStackTrace();
                     }
                     break;
                case HandlerConstant1.REQUST_ERROR:
                    showMsg(getString(R.string.net_error));
                    break;
                default:
                    break;
            }
        }
    };


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
                tvTopic.setText(topicListBean.getTopicName());
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
            tvTeacherName.setText(teacher.getUserName());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
