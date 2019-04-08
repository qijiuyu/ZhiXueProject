package com.example.administrator.zhixueproject.activity.topic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.example.administrator.zhixueproject.adapter.topic.ActionManageAdapter;
import com.example.administrator.zhixueproject.bean.topic.ActionManageBean;
import com.example.administrator.zhixueproject.bean.topic.ActivityListBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.DateUtil;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.view.DividerItemDecoration;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动管理
 *
 * @author PeterGee
 * @date 2018/10/18
 */
public class ActionManageActivity extends BaseActivity implements View.OnClickListener, MyRefreshLayoutListener, BaseQuickAdapter.OnItemChildClickListener {

    private ActionManageAdapter mAdapter;
    private List<ActivityListBean> listData = new ArrayList<>();
    private int PAGE = 1;
    private String LIMIT = "10";
    private String TIMESTAMP = "";
    private int mCurrentPosition;
    private MyRefreshLayout mrlActionManage;
    private RecyclerView rvActionManage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_manage);
        initView();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.action_manage));
        findViewById(R.id.lin_back).setOnClickListener(this);
        TextView tvRight = (TextView) findViewById(R.id.tv_right);
        tvRight.setBackground(getResources().getDrawable(R.mipmap.add_title_iv));
        tvRight.setOnClickListener(this);

        mrlActionManage = (MyRefreshLayout) findViewById(R.id.mrl_action_manage);
        rvActionManage = (RecyclerView) findViewById(R.id.rv_action_manage);
        rvActionManage.setLayoutManager(new LinearLayoutManager(this));
        //添加分隔线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, R.drawable.divider_activity_line, LinearLayoutManager.VERTICAL);
        rvActionManage.addItemDecoration(itemDecoration);
        mrlActionManage.setMyRefreshLayoutListener(this);//刷新加载
        registerBroadCast();
        getActivityList(HandlerConstant2.GET_ACTIVITY_LIST_SUCCESS);
        adapterView();
    }

    /**
     * 注册广播
     */
    private void registerBroadCast() {
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ReleaseActionActivity.RELAEASE_ACTION_SUCCESS);
        registerReceiver(mBroadCastReceiver,intentFilter);
    }

    public void getActivityList(int index) {
        TIMESTAMP= DateUtil.getTime();
        showProgress(getString(R.string.loading));
        HttpMethod2.getActivityList(TIMESTAMP, PAGE+"", LIMIT, index, mHandler);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            case R.id.tv_right:
                ReleaseActionActivity.start(this, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(View view) {
        PAGE = 1;
        getActivityList(HandlerConstant2.GET_ACTIVITY_LIST_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        PAGE++;
        getActivityList(HandlerConstant2.GET_ACTIVITY_LIST_SUCCESS2);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            ActionManageBean bean = (ActionManageBean) msg.obj;
            switch (msg.what) {
                case HandlerConstant2.GET_ACTIVITY_LIST_SUCCESS:
                    getDataSuccess(bean);
                    break;
                case HandlerConstant2.GET_ACTIVITY_LIST_SUCCESS2:
                    loadMoreSuccess(bean);
                    break;
                case HandlerConstant2.DELETE_ACTIVITY_SUCCESS:
                    deleteActionSuccess();
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
    private void getDataSuccess(ActionManageBean bean) {
        mrlActionManage.refreshComplete();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
             ActionManageBean.DataBean dataBean = bean.getData();
            listData = dataBean.getActivityList();
            if (dataBean.getActivityList().size()==0){
                return;
            }
            // adapterView();
            mAdapter.setNewData(listData);
            mAdapter.notifyDataSetChanged();
        } else {
           // showMsg(bean.errorMsg);

        }
    }

    /**
     * 加载更多
     */
    private void loadMoreSuccess(ActionManageBean bean) {
        mrlActionManage.loadMoreComplete();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            ActionManageBean.DataBean dataBean = bean.getData();
            if (dataBean.getActivityList().size() <= 0) {
                showMsg("无更多数据");
                return;
            }
            listData.addAll(dataBean.getActivityList());
            mAdapter.setNewData(listData);
            mAdapter.notifyDataSetChanged();
        } else {
           // showMsg(bean.errorMsg);
        }
    }

    /**
     * 删除活动成功
     */
    public void deleteActionSuccess() {
        showMsg("删除成功");
        listData.remove(mCurrentPosition);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 加载失败
     */
    private void requestError() {
        mrlActionManage.refreshComplete();
        mrlActionManage.loadMoreComplete();
        showMsg(getString(R.string.net_error));
    }

    /**
     * 设置adapter 数据
     */
    private void adapterView() {
        mAdapter = new ActionManageAdapter(R.layout.action_manage_item, listData);
        rvActionManage.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) rvActionManage.getParent());
        mAdapter.setOnItemChildClickListener(this);//侧滑菜单监听
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        this.mCurrentPosition = position;
        switch (view.getId()) {
            //编辑
            case R.id.tv_menu_one:
                ActivityListBean activityListBean = listData.get(position);
                ReleaseActionActivity.start(this, activityListBean);
                break;
            //删除
            case R.id.tv_menu_two:
                HttpMethod2.deleteActivity(String.valueOf(listData.get(position).getActivityId()),mHandler);
                break;
            default:
                break;
        }
    }

    private BroadcastReceiver mBroadCastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction()==ReleaseActionActivity.RELAEASE_ACTION_SUCCESS){
                LogUtils.e("刷新活动管理列表");
                PAGE = 1;
                getActivityList(HandlerConstant2.GET_ACTIVITY_LIST_SUCCESS);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBroadCastReceiver!=null){
            unregisterReceiver(mBroadCastReceiver);
        }
    }
}
