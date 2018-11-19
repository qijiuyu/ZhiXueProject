package com.example.administrator.zhixueproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.TabActivity;
import com.example.administrator.zhixueproject.activity.college.CollegeManageActivity;
import com.example.administrator.zhixueproject.activity.topic.ActionManageActivity;
import com.example.administrator.zhixueproject.activity.topic.TopicListManageActivity;
import com.example.administrator.zhixueproject.activity.topic.VoteManageActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.view.CircleImageView;

/**
 * 话题管理fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class TopicFragment extends BaseFragment implements View.OnClickListener {

    View view = null;
    private CircleImageView imgHead;
    private TextView tvHead;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topic_manage, container, false);
        imgHead=(CircleImageView)view.findViewById(R.id.img_fc_head);
        imgHead.setOnClickListener(this);
        tvHead=(TextView)view.findViewById(R.id.tv_head);
        tvHead.setText("话题管理");
        TextView tvTopicManage = (TextView) view.findViewById(R.id.tv_topic);
        tvTopicManage.setOnClickListener(this);
        TextView tvActivityManage = (TextView) view.findViewById(R.id.tv_action);
        tvActivityManage.setOnClickListener(this);
        TextView tvVoteManage = (TextView) view.findViewById(R.id.tv_vote);
        tvVoteManage.setOnClickListener(this);
        view.findViewById(R.id.iv_college).setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击头像
            case R.id.img_fc_head:
                TabActivity.openLeft();
                break;
            //点击设置
            case R.id.iv_college:
                setClass(CollegeManageActivity.class);
                break;
            // 话题列表管理
            case R.id.tv_topic:
                setClass(TopicListManageActivity.class);
                break;
            // 活动管理
            case R.id.tv_action:
                setClass(ActionManageActivity.class);
                break;
            // 投票管理
            case R.id.tv_vote:
                setClass(VoteManageActivity.class);
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
