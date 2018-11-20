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
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import com.example.administrator.zhixueproject.bean.topic.TopicsListBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.DateUtil;
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
    private String TIMESTAMP = DateUtil.getTime();;
    private RecyclerView mRecyclerView;
    private MyRefreshLayout mRefreshLayout;
    //fragment是否可见
    private boolean isVisibleToUser=false;

    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.topic_list, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.lin_back).setVisibility(View.GONE);
        mRefreshLayout = (MyRefreshLayout) view.findViewById(R.id.mrl_topic_list);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_topic_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //添加分隔线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_activity_line, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
    }

    private void initData() {
        if(isVisibleToUser && null!=view && listData.size()==0){
            showProgress(getString(R.string.loading));
            getTopicList(HandlerConstant2.GET_TOPIC_LIST_SUCCESS);
        }
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
     * @param index handler消息
     */
    private void getTopicList(int index) {
        HttpMethod2.getTopicList(TIMESTAMP, PAGE + "", LIMIT, index, mHandler);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            TopicsListBean bean = (TopicsListBean) msg.obj;
            switch (msg.what) {
                case HandlerConstant2.GET_TOPIC_LIST_SUCCESS:
                    getDataSuccess(bean);
                    break;
                case HandlerConstant2.GET_TOPIC_LIST_SUCCESS2:
                    loadMoreSuccess(bean);
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    requestError();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 加载数据
     */
    private void getDataSuccess(TopicsListBean bean) {
        mRefreshLayout.refreshComplete();
        listData.clear();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            TopicsListBean.DataBean dataBean = bean.getData();
            listData = dataBean.getTopicList();
            mAdapter = new TopicListAdapter(R.layout.topic_list_item, listData, true);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent());
        }else {
            showMsg(bean.errorMsg);

        }
    }

    /**
     * 加载更多
     */
    private void loadMoreSuccess(TopicsListBean bean) {
        mRefreshLayout.loadMoreComplete();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            TopicsListBean.DataBean dataBean = bean.getData();
            if (dataBean.getTopicList().size() <= 0) {
                showMsg(getResources().getString(R.string.no_more_data));
                return;
            }
            listData.addAll(dataBean.getTopicList());
            mAdapter = new TopicListAdapter(R.layout.topic_list_item, listData, true);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent());
        }else {
            showMsg(bean.errorMsg);
        }
    }

    /**
     * 加载失败
     */
    private void requestError() {
        mRefreshLayout.refreshComplete();
        mRefreshLayout.loadMoreComplete();
        showMsg(getString(R.string.net_error));
    }


    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //查询话题列表
        initData();
    }

}
