package com.example.administrator.zhixueproject.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.activity.college.CollegeManageActivity;
import com.example.administrator.zhixueproject.adapter.topic.TopicListAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import com.example.administrator.zhixueproject.bean.topic.TopicsListBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.DateUtil;
import com.example.administrator.zhixueproject.view.CircleImageView;
import com.example.administrator.zhixueproject.view.DividerItemDecoration;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 帖子fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class InvitationFragment extends BaseActivity implements MyRefreshLayoutListener {
    //侧滑菜单
    public static DrawerLayout mDrawerLayout;
    private CircleImageView imgHead;
    private TextView tvHead;
    private TopicListAdapter mAdapter;
    private List<TopicListBean> listData = new ArrayList<>();
    private int PAGE = 1;
    private String LIMIT = "10";
    private String TIMESTAMP = DateUtil.getTime();
    ;
    private RecyclerView mRecyclerView;
    private MyRefreshLayout mRefreshLayout;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_list);
        initView();
        leftMenu();
        //注册广播
        registerReceiver();
    }

    private void initView() {
        imgHead = (CircleImageView) findViewById(R.id.img_fc_head);
        tvHead = (TextView) findViewById(R.id.tv_head);
        mRefreshLayout = (MyRefreshLayout) findViewById(R.id.mrl_topic_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_topic_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //添加分隔线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, R.drawable.divider_activity_line, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        imgHead.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        findViewById(R.id.iv_college).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setClass(CollegeManageActivity.class);
            }
        });
    }

    /**
     * 设置侧边栏
     */
    private void leftMenu() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // 设置遮盖主要内容的布颜色
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View content = mDrawerLayout.getChildAt(0);
                int offset = (int) (drawerView.getWidth() * slideOffset);
                content.setTranslationX(offset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
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
        if (null != listData && listData.size() == 0) {
            showProgress(getString(R.string.loading));
        }
        HttpMethod2.getTopicList(null, PAGE + "", LIMIT, index, mHandler);
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
        } else {
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
                return;
            }
            listData.addAll(dataBean.getTopicList());
            mAdapter = new TopicListAdapter(R.layout.topic_list_item, listData, true);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent());
        } else {
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

    @Override
    public void onResume() {
        super.onResume();
        PAGE = 1;
        getTopicList(HandlerConstant2.GET_TOPIC_LIST_SUCCESS);
        final UserBean userBean = MyApplication.userInfo.getData().getUser();
        tvHead.setText(MyApplication.homeBean.getCollegeName());
        Glide.with(mContext).load(userBean.getUserImg()).override(30, 30).error(R.mipmap.head_bg).into(imgHead);
    }


    /**
     * 注册广播
     */
    private void registerReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(LeftFragment.GET_COLLEGE_DETAILS);
        // 注册广播监听
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(LeftFragment.GET_COLLEGE_DETAILS)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                tvHead.setText(MyApplication.homeBean.getCollegeName());

                //重新加载
                PAGE = 1;
                getTopicList(HandlerConstant2.GET_TOPIC_LIST_SUCCESS);
            }
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
