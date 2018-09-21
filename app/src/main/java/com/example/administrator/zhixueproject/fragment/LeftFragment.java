package com.example.administrator.zhixueproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.userinfo.UserInfoActivity;
import com.example.administrator.zhixueproject.adapter.CollegeNameAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.Colleges;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.bean.UserInfo;

import java.util.List;

import retrofit2.http.POST;

/**
 * 侧边栏
 * Created by Administrator on 2018/1/3 0003.
 */

public class LeftFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    private CollegeNameAdapter mAdapter;
    private View view = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_left_menu, container, false);
        initView();
        return view;
    }

    private void initView() {
        ImageView imgHead = (ImageView) view.findViewById(R.id.iv_menu_head);
        imgHead.setOnClickListener(this);
        TextView tvName = (TextView) view.findViewById(R.id.tv__menu_name);
        TextView tvSign = (TextView) view.findViewById(R.id.tv_meun_sign);
        // recyclerView
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_college_name);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layout);
        UserInfo userInfo = MyApplication.userInfo;
        if (userInfo == null) {
            return;
        }
        UserBean bean = userInfo.getData().getUser();
        // 头像
        Glide.with(MyApplication.application).
                load(bean.getUserImg()).
                into(imgHead);
        // 昵称
        tvName.setText(bean.getUserName());
        // 简介
        tvSign.setText(bean.getUserIntro());
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
            default:
                break;
        }
    }
}
