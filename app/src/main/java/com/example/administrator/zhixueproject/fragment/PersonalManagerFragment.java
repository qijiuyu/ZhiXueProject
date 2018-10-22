package com.example.administrator.zhixueproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.memberManage.BlacklistActivity;
import com.example.administrator.zhixueproject.activity.memberManage.KickOutMemberActivity;
import com.example.administrator.zhixueproject.activity.memberManage.MemberApplyActivity;
import com.example.administrator.zhixueproject.activity.memberManage.MemberManagerActivity;
import com.example.administrator.zhixueproject.activity.memberManage.SignInManageActivity;

/**
 * 人员管理fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class PersonalManagerFragment extends BaseFragment implements View.OnClickListener {

    View view=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_personal_manager, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        // 会员管理
        view.findViewById(R.id.rl_member_manager).setOnClickListener(this);
        view.findViewById(R.id.rl_member_application).setOnClickListener(this);
        view.findViewById(R.id.rl_kick_out_member).setOnClickListener(this);
        view.findViewById(R.id.rl_black_list).setOnClickListener(this);
        view.findViewById(R.id.rl_sign_in_manager).setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
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
}
