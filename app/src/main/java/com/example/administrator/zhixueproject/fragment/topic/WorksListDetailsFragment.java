package com.example.administrator.zhixueproject.fragment.topic;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.adapter.topic.PostsTaskAdapter;
import com.example.administrator.zhixueproject.adapter.topic.WorkCommentListAdapter;
import com.example.administrator.zhixueproject.bean.eventBus.PostEvent;
import com.example.administrator.zhixueproject.bean.topic.PostsDetailsBean;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.view.DividerItemDecoration;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业
 */

public class WorksListDetailsFragment extends BaseFragment implements MyRefreshLayoutListener, BaseQuickAdapter.OnItemClickListener {

    private List<PostsDetailsBean.PostDetailBeanOuter.WorkCommentListBean> listData = new ArrayList<>();
    private String postId;
    private int PAGE = 1;
    private String LIMIT = "10";
    private String TIMESTAMP = System.currentTimeMillis()+"";
    private int type;//1讨论  2作业
    private MyRefreshLayout mrlPostsTask;
    private RecyclerView rvPostsTask;
    private WorkCommentListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.posts_task_fm, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mrlPostsTask = (MyRefreshLayout) view.findViewById(R.id.mrl_posts_task);
        rvPostsTask = (RecyclerView) view.findViewById(R.id.rv_posts_task);
        rvPostsTask.setLayoutManager(new LinearLayoutManager(getActivity()));
        //添加分隔线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_activity_line, LinearLayoutManager.VERTICAL);
        rvPostsTask.addItemDecoration(itemDecoration);
        //刷新加载
        mrlPostsTask.setMyRefreshLayoutListener(this);
        getPostDetailTask(HandlerConstant2.GET_POST_DETAIL_SUCCESS);
        adapterView();
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public void onRefresh(View view) {
        PAGE = 1;
        getPostDetailTask(HandlerConstant2.GET_POST_DETAIL_SUCCESS);
    }

    private void getPostDetailTask(int index) {
        showProgress(getString(R.string.loading));
        HttpMethod2.getPostDetail(postId, PAGE + "", LIMIT, TIMESTAMP, index, mHandler);
    }

    @Override
    public void onLoadMore(View view) {
        PAGE++;
        getPostDetailTask(HandlerConstant2.GET_POST_DETAIL_SUCCESS2);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            PostsDetailsBean detailsBean = (PostsDetailsBean) msg.obj;
            switch (msg.what) {
                case HandlerConstant2.GET_POST_DETAIL_SUCCESS:
                    if (null == detailsBean) {
                        return;
                    }
                    TIMESTAMP = detailsBean.getData().getTimestamp();
                    if (detailsBean.isStatus()) {
                        LogUtils.e("作业");
                        getDetailSuccess(detailsBean);
                    } else {
                        showMsg(detailsBean.getErrorMsg());
                    }
                    break;
                case HandlerConstant2.GET_POST_DETAIL_SUCCESS2:
                    if (null == detailsBean) {
                        return;
                    }
                    TIMESTAMP = detailsBean.getData().getTimestamp();
                    if (detailsBean.isStatus()) {
                        loadMoreSuccess(detailsBean);
                    } else {
                        showMsg(detailsBean.getErrorMsg());
                    }
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    requestError();
                    break;
            }
        }
    };

    private void getDetailSuccess(PostsDetailsBean bean) {
        mrlPostsTask.refreshComplete();
        if (null!=listData&&listData.size()>0){
            listData.clear();
        }
        listData = bean.getData().getWorkCommentList();
        mAdapter.setNewData(listData);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 加载更多
     */
    private void loadMoreSuccess(PostsDetailsBean bean) {
        mrlPostsTask.loadMoreComplete();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            if (bean.getData().getWorkCommentList().size() <= 0) {
                return;
            }
            listData.addAll(bean.getData().getWorkCommentList());
            mAdapter.setNewData(listData);
            mAdapter.notifyDataSetChanged();
        } else {
            showMsg(bean.errorMsg);
        }
    }

    /**
     * 加载失败
     */
    private void requestError() {
        mrlPostsTask.refreshComplete();
        mrlPostsTask.loadMoreComplete();
        showMsg(getString(R.string.net_error));
    }

    /**
     * 设置adapter 数据
     */
    private void adapterView() {
        mAdapter = new WorkCommentListAdapter(R.layout.posts_task_item, listData);
        rvPostsTask.setAdapter(mAdapter);
        //条目点击
        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PostsDetailsBean.PostDetailBeanOuter.WorkCommentListBean item = (PostsDetailsBean.PostDetailBeanOuter.WorkCommentListBean) adapter.getData().get(position);
        //回复楼层贴子
        EventBus.getDefault().post(new PostEvent().setEventType(PostEvent.REPLY_WORK).setData(item));
    }

    @Subscribe
    public void replyCommentEvent(PostEvent postEvent) {
        if (PostEvent.COMMENT_SUCCESS == postEvent.getEventType()) {
            PAGE = 1;
            getPostDetailTask(HandlerConstant2.GET_POST_DETAIL_SUCCESS);
        }
    }
}
