package com.example.administrator.zhixueproject.activity.topic;

import android.content.Context;
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
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.topic.ActionNeophyteAdapter;
import com.example.administrator.zhixueproject.bean.topic.ActionNeophyteBean;
import com.example.administrator.zhixueproject.bean.topic.ActivityUserListBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.DateUtil;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动参与者
 *
 * @author PeterGee
 * @date 2018/10/18
 */
public class ActionNeophyteActivity extends BaseActivity implements MyRefreshLayoutListener {
    private ActionNeophyteAdapter mAdapter;
    private List<ActivityUserListBean> listData = new ArrayList<>();
    private String activityId;
    private int PAGE=1;
    private String LIMIT = "10";
    private String TIMESTAMP="";
    private MyRefreshLayout mrlActionNeophyte;
    private RecyclerView rvActionNeophyte;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_neophyte);
        initView();
    }

    private void initView() {
        StatusBarUtils.transparencyBar(this);
        activityId = getIntent().getStringExtra("activityId");
        TextView tvTitle= (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.action_neophyte));
        mrlActionNeophyte = (MyRefreshLayout) findViewById(R.id.mrl_action_neophyte);
        rvActionNeophyte = (RecyclerView) findViewById(R.id.rv_action_neophyte);
        rvActionNeophyte.setLayoutManager(new LinearLayoutManager(this));
        mrlActionNeophyte.setMyRefreshLayoutListener(this);//刷新加载
        getActivityUserList(HandlerConstant2.GET_ACTIVITY_USER_LIST_SUCCESS);

    }

    private void getActivityUserList(int index) {
        TIMESTAMP= DateUtil.getTime();
        showProgress(getString(R.string.loading));
        HttpMethod2.getActivityUserList(activityId+"",TIMESTAMP,PAGE+"",LIMIT,index,mHandler);
    }

    public static void start(Context context, String activityId) {
        Intent starter = new Intent(context, ActionNeophyteActivity.class);
        starter.putExtra("activityId", activityId);
        context.startActivity(starter);
    }


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            ActionNeophyteBean bean= (ActionNeophyteBean) msg.obj;
                switch (msg.what) {
                    case HandlerConstant2.GET_ACTIVITY_USER_LIST_SUCCESS:
                        getDataSuccess(bean);
                        break;
                    case HandlerConstant2.GET_ACTIVITY_USER_LIST_SUCCESS2:
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
    private void getDataSuccess(ActionNeophyteBean bean) {
        mrlActionNeophyte.refreshComplete();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            ActionNeophyteBean.DataBean dataBean = bean.getData();
            listData = dataBean.getActivityUserList();
            adapterView();
        } else {
            showMsg(bean.errorMsg);

        }
    }

    /**
     * 加载更多
     */
    private void loadMoreSuccess(ActionNeophyteBean bean) {
        mrlActionNeophyte.loadMoreComplete();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            ActionNeophyteBean.DataBean dataBean = bean.getData();
            if (dataBean.getActivityUserList().size() <= 0) {
                showMsg(getResources().getString(R.string.no_more_data));
                return;
            }
            listData.addAll(dataBean.getActivityUserList());
            adapterView();
        } else {
            showMsg(bean.errorMsg);
        }
    }

    /**
     * 加载失败
     */
    private void requestError() {
        mrlActionNeophyte.refreshComplete();
        mrlActionNeophyte.loadMoreComplete();
        showMsg(getString(R.string.net_error));
    }

    /**
     * 设置adapter 数据
     */
    private void adapterView() {
        mAdapter = new ActionNeophyteAdapter(R.layout.action_neophyte_item, listData);
        mAdapter.setEmptyView(R.layout.empty_view,(ViewGroup) rvActionNeophyte.getParent());
        rvActionNeophyte.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh(View view) {
        PAGE=1;
        getActivityUserList(HandlerConstant2.GET_ACTIVITY_USER_LIST_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        PAGE++;
        getActivityUserList(HandlerConstant2.GET_ACTIVITY_USER_LIST_SUCCESS2);
    }
}
