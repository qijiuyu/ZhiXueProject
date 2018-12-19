package com.example.administrator.zhixueproject.fragment.college;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.college.EditCollegeActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.fragment.LeftFragment;
import com.example.administrator.zhixueproject.utils.DateUtil;
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
        return view;
    }


    /**
     * 显示学院数据
     */
    private void showData(){
        Glide.with(mActivity).load(MyApplication.homeBean.getCollegeBackimg()).override(337,192).centerCrop().into(imgBJ);
        tvName.setText(MyApplication.homeBean.getCollegeName());
        int grade=MyApplication.homeBean.getCollegeGrade();
        if(grade==7){
            grade=0;
        }
        tvGrade.setText("VIP"+grade);
        tvTime.setText(DateUtil.getDay(MyApplication.homeBean.getCollegeCreationTime())+"到期");
        tvContent.setText(MyApplication.homeBean.getCollegeInfo());
//
        if(MyApplication.homeBean.getAttendType()==1){
            imgEdit.setVisibility(View.VISIBLE);
        }else{
            imgEdit.setVisibility(View.GONE);
        }
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
