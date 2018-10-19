package com.example.administrator.zhixueproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.topic.ActionManageActivity;
import com.example.administrator.zhixueproject.activity.topic.TopicListManageActivity;
import com.example.administrator.zhixueproject.activity.topic.VoteManageActivity;

/**
 * 话题管理fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class TopicFragment extends BaseFragment implements View.OnClickListener {

    View view = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topic_manage, container, false);
        TextView tvTopicManage = (TextView) view.findViewById(R.id.tv_topic);
        tvTopicManage.setOnClickListener(this);
        TextView tvActivityManage = (TextView) view.findViewById(R.id.tv_action);
        tvActivityManage.setOnClickListener(this);
        TextView tvVoteManage = (TextView) view.findViewById(R.id.tv_vote);
        tvVoteManage.setOnClickListener(this);
        view.findViewById(R.id.lin_back).setVisibility(View.GONE);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
}
