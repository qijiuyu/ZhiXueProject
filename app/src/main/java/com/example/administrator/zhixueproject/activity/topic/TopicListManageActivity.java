package com.example.administrator.zhixueproject.activity.topic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.topic.TopicListAdapter;
import com.example.administrator.zhixueproject.bean.eventBus.TopicEvent;
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import com.example.administrator.zhixueproject.bean.topic.TopicsListBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.VibratorUtil;
import com.example.administrator.zhixueproject.view.DividerItemDecoration;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;

/**
 * 话题列表管理Activity
 *
 * @author petergee
 * @date 2018/10/8
 */
public class TopicListManageActivity extends BaseActivity implements View.OnClickListener, MyRefreshLayoutListener, BaseQuickAdapter.OnItemChildClickListener, OnItemDragListener {
    private TopicListAdapter mAdapter;
    private List<TopicListBean> listData = new ArrayList<>();
    private int PAGE = 1;
    private String LIMIT = "10";
    private String TIMESTAMP = System.currentTimeMillis() + "";
    private RecyclerView mRecyclerView;
    private MyRefreshLayout mRefreshLayout;
    private int itemCheckedPosition;
    // 初始位置
    private int startPosition;
    public static final String TOPIC_INFO = "topic_info";
    public static final String TYPE = "type";
    private LinearLayout linBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list_manage);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    private void initView() {
        // 标题
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.topic_list));
        linBack = (LinearLayout) findViewById(R.id.lin_back);
        linBack.setOnClickListener(this);
        TextView tvAdd = (TextView) findViewById(R.id.tv_right);
        tvAdd.setBackground(getResources().getDrawable(R.mipmap.add_title_iv));
        tvAdd.setOnClickListener(this);

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
            // 添加话题
            case R.id.tv_right:
                Intent intent = new Intent();
                intent.setClass(this, AddTopicActivity.class);
                // type 1:添加   2:编辑
                intent.putExtra(TYPE, "1");
                startActivity(intent);
                break;
            default:
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
                // 上架
                case HandlerConstant2.IS_UP_OR_DOWN_SUCCESS:
                    isUpOrDownSuccess(bean, 1, itemCheckedPosition);
                    break;
                // 下架
                case HandlerConstant2.IS_UP_OR_DOWN_SUCCESS2:
                    isUpOrDownSuccess(bean, 0, itemCheckedPosition);
                    break;
                    // 话题排序
                case HandlerConstant2.UPDATE_SORT_SUCCESS:
                    updateSortSuccess(bean);
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
     * 上下架成功
     *
     * @param bean
     * @param i    1:上架成功   0：下架成功
     */
    private void isUpOrDownSuccess(TopicsListBean bean, int i, int position) {
        if ( null==bean) {
            return;
        }
        if (bean.status) {
            if (i == 1) {
                showMsg(getString(R.string.is_up_success));
                listData.get(position).setTopicUseyn(1);
            } else {
                showMsg(getString(R.string.is_dowm_success));
                listData.get(position).setTopicUseyn(0);
            }
            mAdapter.notifyItemChanged(position);
            mAdapter.notifyDataSetChanged();
        } else {
            showMsg(bean.getErrorMsg());
        }
    }

    /**
     * 话题排序
     * @param bean
     */
    private void updateSortSuccess(TopicsListBean bean) {
        if (null==bean){
            return;
        }
        if (bean.status){
            mAdapter.notifyDataSetChanged();
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
     * 设置adapter 数据
     */
    private void adapterView() {
        mAdapter = new TopicListAdapter(R.layout.topic_list_item, listData, false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent());
        mAdapter.setOnItemChildClickListener(this);//侧滑菜单监听
        //拖拽
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mAdapter.enableDragItem(itemTouchHelper, R.id.content, true);
        mAdapter.setOnItemDragListener(this);//拖拽监听
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
                intent.putExtra(TYPE, "2");
                intent.putExtra(TOPIC_INFO, bean);
                startActivity(intent);
                break;
            // 上架、下架
            case R.id.tv_menu_two:
                this.itemCheckedPosition=position;
                long topicId = listData.get(position).getTopicId();
                int topicUseyn = listData.get(position).getTopicUseyn();
                if (topicUseyn == 0) {
                    //  "topicUseyn": 是否上架 (0否，1是),
                    HttpMethod2.isUpOrDowm(topicId + "", "1", mHandler);
                } else if (topicUseyn == 1) {
                    HttpMethod2.isUpOrDowm(topicId + "", "0", mHandler);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
        BaseViewHolder holder = ((BaseViewHolder) viewHolder);
        holder.setBackgroundColor(R.id.content, getResources().getColor(R.color.color_dbdbdb));
        VibratorUtil.Vibrate(this, 70);   //震动70ms
        startPosition = pos;
    }

    @Override
    public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder viewHolder, int to) {
    }

    @Override
    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
        BaseViewHolder holder = ((BaseViewHolder) viewHolder);
        holder.setBackgroundColor(R.id.content, Color.WHITE);

        int endPosition = pos;
        long topicId1 = listData.get(startPosition).getTopicId();
        long topicId2 = listData.get(endPosition).getTopicId();

        LogUtils.e("topicId1: " + topicId1 + "--" + topicId2);
        // 话题重新排序
        HttpMethod2.updateSort(topicId1 + "", topicId2 + "", mHandler);
    }

    @Subscribe
    public void topicEvent(TopicEvent topicEvent) {
        if (TopicEvent.UPDATE_TOPIC_LIST == topicEvent.getEventType()) {
            PAGE = 1;
            getTopicList(HandlerConstant2.GET_TOPIC_LIST_SUCCESS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
