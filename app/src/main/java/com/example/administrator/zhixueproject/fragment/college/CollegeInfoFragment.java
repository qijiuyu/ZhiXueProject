package com.example.administrator.zhixueproject.fragment.college;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.college.EditCollegeActivity;
import com.example.administrator.zhixueproject.adapter.college.CollegeNameAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.Home;
import com.example.administrator.zhixueproject.bean.MyColleges;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.fragment.LeftFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.DateUtil;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.SPUtil;
import com.example.administrator.zhixueproject.view.OvalImageViews;

/**
 * 学院基本信息fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class CollegeInfoFragment extends BaseFragment implements View.OnClickListener{

    private ImageView imgBJ,imgEdit;
    private TextView tvName,tvTime,tvContent,tvGrade;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver();
    }


    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collete_info, container, false);
        imgBJ=(ImageView)view.findViewById(R.id.iv_college);
        imgEdit=(ImageView)view.findViewById(R.id.iv_edit);
        tvName=(TextView)view.findViewById(R.id.tv_college_name);
        tvTime=(TextView)view.findViewById(R.id.tv_expire_time);
        tvContent=(TextView)view.findViewById(R.id.tv_content);
        tvGrade=(TextView)view.findViewById(R.id.tv_grade);
        view.findViewById(R.id.iv_edit).setOnClickListener(this);
        view.findViewById(R.id.img_refresh).setOnClickListener(this);
        return view;
    }


    private Handler mHandler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            clearTask();
            //获取我的学院
            if(msg.what== HandlerConstant1.GET_MY_COLLEGE_SUCCESS){
                final MyColleges myColleges= (MyColleges) msg.obj;
                if(null==myColleges){
                    return  false;
                }
                if(myColleges.isStatus()){
                    boolean isCollege=false;
                    for (int i=0;i<myColleges.getData().getColleges().size();i++){
                         if(myColleges.getData().getColleges().get(i).getCollegeId()==MyApplication.homeBean.getCollegeId()){
                             isCollege=true;
                             break;
                         }
                    }
                    if(!isCollege && myColleges.getData().getColleges().size()>0){
                        showProgress("数据加载中");
                        HttpMethod1.getCollegeDetails(myColleges.getData().getColleges().get(0).getCollegeId(),mHandler);
                    }
                }
            }
            //获取学院的详情数据
            if(msg.what== HandlerConstant1.GET_COLLEGE_DETAILS_SUCCESS){
                final Home home= (Home) msg.obj;
                if(null==home){
                    return  false;
                }
                if(home.isStatus()) {
                    MyApplication.homeBean = home.getData().getCollege();
                    MyApplication.spUtil.addString(SPUtil.HOME_INFO,MyApplication.gson.toJson(MyApplication.homeBean));
                    mActivity.sendBroadcast(new Intent(LeftFragment.GET_COLLEGE_DETAILS));
                }
            }
            return false;
        }
    });


    /**
     * 显示学院数据
     */
    private void showData(){
        if(null==MyApplication.homeBean){
            return;
        }
        Glide.with(mActivity).load(MyApplication.homeBean.getCollegeBackimg()).override(337,192).centerCrop().into(imgBJ);
        tvName.setText(MyApplication.homeBean.getCollegeName());
        int grade=MyApplication.homeBean.getCollegeGrade();
        if(grade==7){
            grade=0;
        }
        tvGrade.setText("VIP"+grade);
        if(MyApplication.homeBean.getCollegeGradetime()!=0){
            tvTime.setText(DateUtil.getDay(MyApplication.homeBean.getCollegeGradetime())+"到期");
        }
        if(!TextUtils.isEmpty(MyApplication.homeBean.getCollegeInfo())){
            tvContent.setText(Html.fromHtml(MyApplication.homeBean.getCollegeInfo()));
        }
        imgEdit.setVisibility(View.VISIBLE);
//        if(MyApplication.homeBean.getAttendType()==1){
//
//        }else{
//            imgEdit.setVisibility(View.GONE);
//        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //编辑学院
            case R.id.iv_edit:
                Intent intent=new Intent(mActivity,EditCollegeActivity.class);
                intent.putExtra("homeBean",MyApplication.homeBean);
                startActivity(intent);
                break;
            //刷新当前学院
            case R.id.img_refresh:
                 //查询加入过的学院列表
                 showProgress("数据加载中");
                 HttpMethod1.getMyCollege(mHandler);
                 break;
            default:
                break;
        }
    }


    /**
     * 注册广播
     */
    private void registerReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(LeftFragment.GET_COLLEGE_DETAILS);
        // 注册广播监听
        mActivity.registerReceiver(mBroadcastReceiver, myIntentFilter);
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(LeftFragment.GET_COLLEGE_DETAILS)) {
                showData();
            }
        }
    };


    public void onResume() {
        super.onResume();
        showData();
    }

    public void onDestroy() {
        mActivity.unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
