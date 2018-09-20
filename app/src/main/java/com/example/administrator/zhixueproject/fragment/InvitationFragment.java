package com.example.administrator.zhixueproject.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.adapter.topic.TopicListAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import com.example.administrator.zhixueproject.bean.topic.TopicsListBean;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.view.DividerItemDecoration;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 帖子fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class InvitationFragment extends BaseFragment implements MyRefreshLayoutListener {
    private TopicListAdapter mAdapter;
    private List<TopicListBean> listData = new ArrayList<>();
    private int PAGE = 1;
    private String LIMIT = "10";
    private String TIMESTAMP = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.topic_list, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        MyRefreshLayout mRefreshLayout = (MyRefreshLayout) view.findViewById(R.id.mrl_topic_list);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_topic_list);
        mAdapter = new TopicListAdapter(R.layout.topic_list_item, listData, true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent());

        //添加分隔线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_activity_line, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
    }

    private void initData() {
        showProgress(getString(R.string.loading));
        getTopicList(HandlerConstant2.GET_TOPIC_LIST_SUCCESS);

    }

    @Override
    public void onRefresh(View view) {
        PAGE = 1;
        getTopicList(HandlerConstant2.GET_TOPIC_LIST_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        PAGE++;
        getTopicList(HandlerConstant2.GET_TOPIC_LIST_SUCCESS2);

    }

    /**
     * 查询话题列表数据
     *
     * @param index
     */
    private void getTopicList(int index) {
        int c = MyApplication.spUtil.getInteger("c");
        HttpMethod2.getTopicList(String.valueOf(c), "", TIMESTAMP, PAGE + "", LIMIT, index, mHandler);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            TopicsListBean.DataBean bean = null;
            switch (msg.what) {
                case HandlerConstant2.GET_TOPIC_LIST_SUCCESS:
                    bean = (TopicsListBean.DataBean) msg.obj;
                    listData.clear();
                    if (null == bean) {
                        return;
                    }
                    listData.addAll(bean.getTopicList());
                    break;
                case HandlerConstant2.GET_TOPIC_LIST_SUCCESS2:
                    bean = (TopicsListBean.DataBean) msg.obj;
                    listData.addAll(bean.getTopicList());
                    break;
                case HandlerConstant2.REQUST_ERROR:
                    showMsg(getString(R.string.load_failed));
                    break;
                default:
                    break;
            }
        }
    };
}
