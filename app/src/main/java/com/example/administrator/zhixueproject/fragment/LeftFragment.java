package com.example.administrator.zhixueproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.college.MoreCollegeActivity;
import com.example.administrator.zhixueproject.activity.userinfo.UserInfoActivity;
import com.example.administrator.zhixueproject.adapter.college.CollegeNameAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.Colleges;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.bean.UserInfo;
import com.example.administrator.zhixueproject.view.CircleImageView;

import java.util.List;

/**
 * 侧边栏
 * Created by Administrator on 2018/1/3 0003.
 */

public class LeftFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    private CircleImageView imgHead;
    private TextView tvName,tvSign;
    private CollegeNameAdapter mAdapter;
    private View view = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_left_menu, container, false);
        initView();
        return view;
    }

    /**
     * 初始化
     */
    private void initView() {
        imgHead = (CircleImageView) view.findViewById(R.id.iv_menu_head);
        imgHead.setOnClickListener(this);
        view.findViewById(R.id.img_next).setOnClickListener(this);
        tvName = (TextView) view.findViewById(R.id.tv__menu_name);
        tvSign = (TextView) view.findViewById(R.id.tv_meun_sign);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_college_name);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layout);
        UserInfo userInfo = MyApplication.userInfo;
        if (userInfo == null) {
            return;
        }
        // adapter 数据
        List<Colleges> dataBean = userInfo.getData().getColleges();
        mAdapter = new CollegeNameAdapter(R.layout.joined_college_item, dataBean);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        Colleges colleges = mAdapter.getData().get(position);
        // 设置title

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu_head:
                setClass(UserInfoActivity.class);
                break;
            case R.id.img_next:
                setClass(MoreCollegeActivity.class);
                 break;
            default:
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        final UserBean userBean=MyApplication.userInfo.getData().getUser();
        // 头像
        Glide.with(MyApplication.application).load(userBean.getUserImg()).error(R.mipmap.head_bg).override(60,60).into(imgHead);
        // 昵称
        tvName.setText(userBean.getUserName());
        // 简介
        tvSign.setText(userBean.getUserIntro());
    }
}
