package com.example.administrator.zhixueproject.activity.topic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.topic.TopicListAdapter;
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import com.example.administrator.zhixueproject.bean.topic.TopicsListBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.view.DividerItemDecoration;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 话题管理Activity
 *
 * @author petergee
 * @date 2018/10/8
 */
public class TopicListManageActivity extends BaseActivity implements View.OnClickListener, MyRefreshLayoutListener, BaseQuickAdapter.OnItemChildClickListener {
    private TopicListAdapter mAdapter;
    private List<TopicListBean> listData = new ArrayList<>();
    private int PAGE = 1;
    private String LIMIT = "10";
    private String TIMESTAMP = System.currentTimeMillis() + "";
    private RecyclerView mRecyclerView;
    private MyRefreshLayout mRefreshLayout;
    private int itemCheckedPosition;
    public static final String TOPIC_INFO = "topic_info";
    public static final String TYPE = "type";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list_manage);
        initData();
        initView();
    }

    private void initView() {
        // 标题
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.topic_list));
        findViewById(R.id.lin_back).setOnClickListener(this);
        mRefreshLayout = (MyRefreshLayout) findViewById(R.id.mrl_topic_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_topic_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加分隔线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, R.drawable.divider_activity_line, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);

    }

    private void initData() {
        if (listData.size() == 0) {
            showProgress(getString(R.string.loading));
            getTopicList(HandlerConstant2.GET_TOPIC_LIST_SUCCESS);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_back:
                finish();
                break;
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
            adapterView();
        } else {
            showMsg(bean.errorMsg);

        }
    }

    /**
     * 设置adapter 数据
     */
    private void adapterView() {
        mAdapter = new TopicListAdapter(R.layout.topic_list_item, listData, false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent());
        mAdapter.setOnItemChildClickListener(this);//侧滑菜单监听
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
            adapterView();
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
        showMsg(getString(R.string.load_failed));
    }

    /**
     * 侧滑监听
     *
     * @param baseQuickAdapter
     * @param view
     * @param position         索引
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        switch (view.getId()) {
            // 编辑
            case R.id.tv_menu_one:
                this.itemCheckedPosition = position;
                TopicListBean bean = listData.get(position);
                Intent intent = new Intent();
                intent.setClass(this, AddTopicActivity.class);
                // type 1:添加   2:编辑
                intent.putExtra(TYPE, 2);
                intent.putExtra(TOPIC_INFO, bean);
                startActivity(intent);
                break;
            // 上架、下架
            case R.id.tv_menu_two:
                int topicId = listData.get(position).getTopicId();
                int topicUseyn = listData.get(position).getTopicUseyn();
                if (topicUseyn == 0) {
                    //  "topicUseyn": 是否上架 (0否，1是),
                    // c 登录标识
                    // topicListManageP.isUpOrDown(C, topicId + "", "1", position);

                } else if (topicUseyn == 1) {
                    // topicListManageP.isUpOrDown(C, topicId + "", "0", position);

                }
                break;
            default:
                break;
        }
    }
}
