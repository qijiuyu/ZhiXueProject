package com.example.administrator.zhixueproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.TabActivity;
import com.example.administrator.zhixueproject.activity.college.CollegeManageActivity;
import com.example.administrator.zhixueproject.activity.memberManage.BlacklistActivity;
import com.example.administrator.zhixueproject.activity.memberManage.KickOutMemberActivity;
import com.example.administrator.zhixueproject.activity.memberManage.MemberApplyActivity;
import com.example.administrator.zhixueproject.activity.memberManage.MemberManagerActivity;
import com.example.administrator.zhixueproject.activity.memberManage.SignInManageActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.view.CircleImageView;

/**
 * 人员管理fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class PersonalManagerFragment extends BaseFragment implements View.OnClickListener {

    View view=null;
    private CircleImageView imgHead;
    private TextView tvHead;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_personal_manager, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        imgHead=(CircleImageView)view.findViewById(R.id.img_fc_head);
        imgHead.setOnClickListener(this);
        tvHead=(TextView)view.findViewById(R.id.tv_head);
        tvHead.setText("人员管理");
        // 会员管理
        view.findViewById(R.id.iv_college).setOnClickListener(this);
        view.findViewById(R.id.rl_member_manager).setOnClickListener(this);
        view.findViewById(R.id.rl_member_application).setOnClickListener(this);
        view.findViewById(R.id.rl_kick_out_member).setOnClickListener(this);
        view.findViewById(R.id.rl_black_list).setOnClickListener(this);
        view.findViewById(R.id.rl_sign_in_manager).setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            //点击头像
            case R.id.img_fc_head:
                TabActivity.openLeft();
                break;
            //点击设置
            case R.id.iv_college:
                setClass(CollegeManageActivity.class);
                break;
            case R.id.rl_member_manager://C端会员管理
                setClass(MemberManagerActivity.class);
                break;
            case R.id.rl_member_application://会员申请
                setClass(MemberApplyActivity.class);
                break;
            case R.id.rl_kick_out_member://踢出的会员
                setClass(KickOutMemberActivity.class);
                break;
            case R.id.rl_black_list://黑名单
                setClass(BlacklistActivity.class);
                break;
            case R.id.rl_sign_in_manager://签到管理
                setClass(SignInManageActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        final UserBean userBean= MyApplication.userInfo.getData().getUser();
        Glide.with(mActivity).load(userBean.getUserImg()).override(30,30).error(R.mipmap.head_bg).into(imgHead);
    }
}
