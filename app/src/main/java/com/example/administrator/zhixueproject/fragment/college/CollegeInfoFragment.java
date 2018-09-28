package com.example.administrator.zhixueproject.fragment.college;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.college.EditCollegeActivity;
import com.example.administrator.zhixueproject.bean.Home;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.DateUtil;
import com.example.administrator.zhixueproject.view.OvalImageViews;

/**
 * 学院基本信息fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class CollegeInfoFragment extends BaseFragment implements View.OnClickListener{

    private OvalImageViews imgBJ;
    private ImageView imgEdit,imgGrade;
    private TextView tvName,tvTime,tvContent;
    public static Home.HomeBean homeBean;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collete_info, container, false);
        imgBJ=(OvalImageViews)view.findViewById(R.id.iv_college);
        imgEdit=(ImageView)view.findViewById(R.id.iv_edit);
        tvName=(TextView)view.findViewById(R.id.tv_college_name);
        imgGrade=(ImageView)view.findViewById(R.id.iv_grade);
        tvTime=(TextView)view.findViewById(R.id.tv_expire_time);
        tvContent=(TextView)view.findViewById(R.id.tv_content);
        view.findViewById(R.id.iv_edit).setOnClickListener(this);
        return view;
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HandlerConstant1.GET_HOME_INFO_SUCCESS:
                     final Home home= (Home) msg.obj;
                     if(null==home){
                         return;
                     }
                     if(home.isStatus()){
                         homeBean=home.getData().getCollege();
                         if(null==homeBean){
                             return;
                         }
                         Glide.with(mActivity).load(homeBean.getCollegeBackimg()).override(337,192).centerCrop().into(imgBJ);
                         tvName.setText(homeBean.getCollegeName());
                         tvTime.setText(DateUtil.getDay(homeBean.getCollegeCreationTime())+"到期");
                         tvContent.setText(homeBean.getCollegeInfo());
                     }else{
                         showMsg(home.getErrorMsg());
                     }
                     break;
                 default:
                     break;
            }

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //编辑学院
            case R.id.iv_edit:
                Intent intent=new Intent(mActivity,EditCollegeActivity.class);
                intent.putExtra("homeBean",homeBean);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    /**
     * 查询首页信息
     */
    private void getHomeInfo(){
        HttpMethod1.getHomeInfo(mHandler);
    }


    @Override
    public void onResume() {
        super.onResume();
        getHomeInfo();
    }
}
