package com.example.administrator.zhixueproject.activity.live;

import android.annotation.SuppressLint;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.activity.college.SelectTeacherActivity;
import com.example.administrator.zhixueproject.adapter.topic.CostsListAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.Teacher;
import com.example.administrator.zhixueproject.bean.live.Live;
import com.example.administrator.zhixueproject.bean.topic.CostsListBean;
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import com.example.administrator.zhixueproject.callback.CustomListener;
import com.example.administrator.zhixueproject.fragment.college.TopicListFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.DateUtil;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.view.CustomPopWindow;
import com.example.administrator.zhixueproject.view.SwitchButton;
import com.example.administrator.zhixueproject.view.time.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 发布直播
 */
public class AddLiveActivity extends BaseActivity implements View.OnClickListener,BaseQuickAdapter.OnItemClickListener{

    private LinearLayout linearLayout;
    private EditText etTitle,etContent,etCost;
    private TextView tvTopicName,tvTeacher,tvTime,tvCost,tvNum;
    private SwitchButton switchButton;
    //侧滑菜单
    public static DrawerLayout mDrawerLayout;
    //话题对象
    private TopicListBean topicListBean;
    //讲师对象
    private Teacher teacher;
    private TimePickerView pvCustomTime;
    private CustomPopWindow mCostPopWindow;
    private List<CostsListBean> list;
    private CostsListAdapter costsListAdapter;
    private int postIsTop=0;//是否置顶
    private int postIsFree=0;//是否付费
    private Live.LiveList liveList;
    private int type; // 1管理员，2老师
    private RelativeLayout relLecture;
    private ImageView ivRightLecture;
    private TextView tvLecture;
    private long teacherId=0;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_live);
        initView();
        showData();
        rightMenu();
        //注册广播
        registerReceiver();
        initCustomTimePicker();
    }


    /**
     * 初始化
     */
    private void initView(){
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.release_live));
        linearLayout=(LinearLayout)findViewById(R.id.ll_release_live);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        etTitle=(EditText)findViewById(R.id.et_title);
        tvTopicName=(TextView)findViewById(R.id.tv_topic);
        tvTeacher=(TextView)findViewById(R.id.tv_lecturer);
        tvTime=(TextView)findViewById(R.id.tv_live_time);
        tvCost=(TextView)findViewById(R.id.tv_cost);
        tvCost.setText("免费");
        postIsFree=1;
        switchButton=(SwitchButton)findViewById(R.id.sb_stick);
        etContent=(EditText)findViewById(R.id.et_live_detail);
        tvNum=(TextView)findViewById(R.id.tv_num);
        etContent.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void afterTextChanged(Editable s) {
                if(null==s){
                    return;
                }
                tvNum.setText(s.toString().length()+"/100");
            }
        });

        //是否置顶
        ((SwitchButton) findViewById(R.id.sb_stick)).setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    postIsTop=1;
                }else{
                    postIsTop=0;
                }
            }
        });
        findViewById(R.id.rl_topic).setOnClickListener(this);
        findViewById(R.id.rl_live_time).setOnClickListener(this);
        findViewById(R.id.rl_cost).setOnClickListener(this);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
        relLecture = (RelativeLayout) findViewById(R.id.rl_lecturer);
        relLecture.setOnClickListener(this);
        ivRightLecture = (ImageView) findViewById(R.id.iv_right_lecturer);
        tvLecture = (TextView) findViewById(R.id.tv_lecturer);

        type = MyApplication.homeBean.getAttendType();
        LogUtils.e("type getAttendType  => "+type);
        String userName = MyApplication.userInfo.getData().getUser().getUserName() + "";
        // id
        long userId =  MyApplication.userInfo.getData().getUser().getUserId();
        teacherId=userId;
        teacher=new Teacher();
        teacher.setTeacherId(userId);
        // set default value
        tvLecture.setText(userName);
        if (type == 2) {
            // 老师身份
            // 设置不能选择发布人
            relLecture.setClickable(false);
            ivRightLecture.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * 展示要编辑的数据
     */
    private void showData(){
        liveList= (Live.LiveList) getIntent().getSerializableExtra("liveList");
        if(null==liveList){
            return;
        }
        etTitle.setText(liveList.getPostName());
        topicListBean=new TopicListBean();
        topicListBean.setTopicId(liveList.getPostTopicId());
        tvTopicName.setText(liveList.getTopicName());
        teacher=new Teacher();
        teacher.setTeacherId(liveList.getPostWriterId());
        teacherId=liveList.getPostWriterId();
        tvTeacher.setText(liveList.getUserName());
        tvTime.setText(DateUtil.gethour(liveList.getPostLivetime()));
        postIsFree=liveList.getPostIsFree();
        if(postIsFree==1){
            tvCost.setText("免费");
        }else{
            tvCost.setText("¥"+liveList.getPostPrice());
        }

        postIsTop=liveList.getPostIsTop();
        ((SwitchButton) findViewById(R.id.sb_stick)).setChecked(true);
        etContent.setText(liveList.getPostInfo());
    }

    @Override
    public void onClick(View v) {
        lockKey(etTitle);
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
                 pvCustomTime.show();
                 break;
             //是否付费
            case R.id.rl_cost:
                 showCostPopWindow();
                 break;
           //发布
            case R.id.tv_confirm:
                 final String title=etTitle.getText().toString().trim();
                 final String time=tvTime.getText().toString().trim();
                 final String content=etContent.getText().toString().trim();
                 final String cost=tvCost.getText().toString().trim().replace("¥","");
                 if(TextUtils.isEmpty(title)){
                     showMsg("请输入标题！");
                     return;
                 }
                if(null==topicListBean){
                    showMsg("请选择所属话题！");
                    return;
                }
                if(null==teacher){
                    showMsg("请选择讲师！");
                    return;
                }
                if(TextUtils.isEmpty(time)){
                     showMsg("请选择直播时间！");
                     return;
                }
                if(postIsFree==0){
                    showMsg("请选择是否付费！");
                    return;
                }
                if(TextUtils.isEmpty(content)){
                    showMsg("请输入预告简介！");
                    return;
                }
                showProgress(getString(R.string.loding));
                 if(null==liveList){
                     HttpMethod1.addLive(title,topicListBean.getTopicId(),teacherId,time,postIsFree,cost,postIsTop,content,mHandler);
                 }else{
                     HttpMethod1.updateLive(title,liveList.getPostId(),topicListBean.getTopicId(),teacherId,time,postIsFree,cost,postIsTop,content,mHandler);
                 }
                 break;
            case R.id.tv_cancel:
                 mCostPopWindow.dissmiss();
                 break;
            case R.id.lin_back:
                 finish();
                 break;
             default:
                 break;
        }
    }



    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                case HandlerConstant1.ADD_LIVE_SUCCESS:
                     final BaseBean baseBean= (BaseBean) msg.obj;
                     if(null==baseBean){
                         return;
                     }
                     if(baseBean.isStatus()){
                         Intent intent=new Intent();
                         setResult(1,intent);
                         finish();
                     }else{
                         showMsg(baseBean.getErrorMsg());
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
            teacherId=teacher.getTeacherId();
            if(teacher==null){
                return;
            }
            tvTeacher.setText(teacher.getUserName());
        }
    }


    /**
     * 是否付费的弹框
     */
    private void showCostPopWindow() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_cost_menu, null);
        etCost=(EditText)contentView.findViewById(R.id.et_cost);
        mCostPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.7f)
                .enableOutsideTouchableDissmiss(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimUp)
                .create();
        mCostPopWindow.showAtLocation(linearLayout, Gravity.BOTTOM, 0, 0);
        //获取显示内容
        String[] costs = getResources().getStringArray(R.array.release_cost);
        list=new ArrayList<>();
        for (int i = 0; i < costs.length; i++) {
            CostsListBean bean = new CostsListBean();
            bean.setContent(costs[i]);
            list.add(bean);
        }
        RecyclerView rv_cost_list = (RecyclerView) contentView.findViewById(R.id.rv_cost_list);
        rv_cost_list.setLayoutManager(new LinearLayoutManager(mContext));
        costsListAdapter = new CostsListAdapter(R.layout.cost_list_item, list);
        rv_cost_list.setAdapter(costsListAdapter);
        costsListAdapter.setOnItemClickListener(this);
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(this);
        contentView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(etCost.getVisibility()==View.GONE){
                    postIsFree=1;
                    mCostPopWindow.dissmiss();
                    tvCost.setText("免费");
                }else{
                    postIsFree=2;
                    final String money=etCost.getText().toString().trim();
                    if(TextUtils.isEmpty(money)){
                        showMsg("请输入金额！");
                    }else{
                        mCostPopWindow.dissmiss();
                        tvCost.setText("¥"+money);
                    }
                }
            }
        });
    }


    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(false);
        }
        list.get(position).setChecked(true);
        costsListAdapter.notifyDataSetChanged();
        if(position==1){
            etCost.setVisibility(View.VISIBLE);
        }else{
            etCost.setVisibility(View.GONE);
        }
    }


    /**
     * 初始化时间选择
     */
    private void initCustomTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2001, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2049, 2, 28);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            public void onTimeSelect(Date date, View v) {
                if(DateUtil.IsToday(date.getTime())){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    tvTime.setText(format.format(date)+":00");
                    pvCustomTime.dismiss();
                }else{
                    showMsg("不能选择已过期的时间！");
                }
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        TextView v_bottom = (TextView) v.findViewById(R.id.v_bottom);
                        if (v_bottom != null) {
                            ViewGroup.MarginLayoutParams bottomParams = (ViewGroup.MarginLayoutParams) v_bottom.getLayoutParams();
                            v_bottom.setLayoutParams(bottomParams);
                        }
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年","月","日","时","分","")
                .isCenterLabel(false)
                .setDividerColor(getResources().getColor(R.color.color_dbdbdb))
                .setTextColorCenter(getResources().getColor(R.color.color_333333))
                .build();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //表示用户是老师
        if(MyApplication.homeBean.getAttendType()!=1){
            findViewById(R.id.rl_lecturer).setClickable(false);
            teacher=new Teacher();
            teacher.setTeacherId(MyApplication.userInfo.getData().getUser().getUserId());
            tvTeacher.setText(MyApplication.userInfo.getData().getUser().getUserName());
            tvCost.setText("免费");
            postIsFree=1;
            if(null!=liveList){
                postIsFree=liveList.getPostIsFree();
                if(postIsFree!=1){
                    tvCost.setText("¥"+liveList.getPostPrice());
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
