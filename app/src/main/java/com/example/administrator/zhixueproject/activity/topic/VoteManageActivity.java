package com.example.administrator.zhixueproject.activity.topic;

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
import com.example.administrator.zhixueproject.adapter.topic.VoteManageAdapter;
import com.example.administrator.zhixueproject.bean.eventBus.PostEvent;
import com.example.administrator.zhixueproject.bean.topic.VoteListBean;
import com.example.administrator.zhixueproject.bean.topic.VoteManageBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.DateUtil;
import com.example.administrator.zhixueproject.view.DividerItemDecoration;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 投票管理
 *
 * @author PeterGee
 * @date 2018/10/18
 */
public class VoteManageActivity extends BaseActivity implements View.OnClickListener, MyRefreshLayoutListener, BaseQuickAdapter.OnItemChildClickListener {

    private VoteManageAdapter mAdapter;
    private List<VoteListBean> listData = new ArrayList<>();
    private int PAGE = 1;
    private String LIMIT = "10";
    private String TIMESTAMP ="";
    private int mCurrentPosition;
    private MyRefreshLayout mrlVoteManage;
    private RecyclerView rvVoteManage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_manage);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.vote_manage));
        findViewById(R.id.lin_back).setOnClickListener(this);
        TextView tvRight= (TextView) findViewById(R.id.tv_right);
        tvRight.setBackground(getResources().getDrawable(R.mipmap.add_title_iv));
        tvRight.setOnClickListener(this);
        mrlVoteManage = (MyRefreshLayout) findViewById(R.id.mrl_vote_manage);
        rvVoteManage = (RecyclerView) findViewById(R.id.rv_vote_manage);
        rvVoteManage.setLayoutManager(new LinearLayoutManager(this));
        //添加分隔线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, R.drawable.divider_activity_line, LinearLayoutManager.VERTICAL);
        rvVoteManage.addItemDecoration(itemDecoration);
        mrlVoteManage.setMyRefreshLayoutListener(this);
        getVoteList(HandlerConstant2.GET_VOTE_LIST_SUCCESS);
        adapterView();
    }

    private void getVoteList(int index) {
        TIMESTAMP= DateUtil.getTime();
        showProgress(getString(R.string.loading));
        HttpMethod2.getVoteList(TIMESTAMP, PAGE + "", LIMIT,index,mHandler);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            case R.id.tv_right:
                ReleaseVoteActivity.start(this, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(View view) {
        PAGE=1;
        getVoteList(HandlerConstant2.GET_VOTE_LIST_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        PAGE++;
        getVoteList(HandlerConstant2.GET_VOTE_LIST_SUCCESS2);
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            VoteManageBean bean= (VoteManageBean) msg.obj;
            switch (msg.what) {
                case HandlerConstant2.GET_VOTE_LIST_SUCCESS:
                    getDataSuccess(bean);
                    break;
                case HandlerConstant2.GET_VOTE_LIST_SUCCESS2:
                    loadMoreSuccess(bean);
                    break;
                case HandlerConstant2.DELETE_VOTE_SUCCESS:
                    deleteVoteSuccess(bean);
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
    private void getDataSuccess(VoteManageBean bean) {
        mrlVoteManage.refreshComplete();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            VoteManageBean.DataBean dataBean = bean.getData();
            listData = dataBean.getVoteList();
            if (dataBean.getVoteList().size()==0){
                return;
            }
            mAdapter.setNewData(listData);
            mAdapter.notifyDataSetChanged();
        } else {
           // showMsg(bean.errorMsg);
        }
    }

    /**
     * 加载更多
     */
    private void loadMoreSuccess(VoteManageBean bean) {
        mrlVoteManage.loadMoreComplete();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            VoteManageBean.DataBean dataBean = bean.getData();
            if (dataBean.getVoteList().size() <= 0) {
                showMsg("无更多数据");
                return;
            }
            listData.addAll(dataBean.getVoteList());
            mAdapter.setNewData(listData);
            mAdapter.notifyDataSetChanged();
        } else {
            // showMsg(bean.errorMsg);
        }
    }

    /**
     * 删除活动成功
     * @param bean
     */
    public void deleteVoteSuccess(VoteManageBean bean) {
        if (bean.isStatus()){
            showMsg("删除成功");
            listData.remove(mCurrentPosition);
            mAdapter.notifyDataSetChanged();
        }else {
            showMsg(bean.getErrorMsg());
        }
    }

    /**
     * 加载失败
     */
    private void requestError() {
        mrlVoteManage.refreshComplete();
        mrlVoteManage.loadMoreComplete();
        showMsg(getString(R.string.net_error));
    }

    /**
     * 设置adapter 数据
     */
    private void adapterView() {
        mAdapter = new VoteManageAdapter(R.layout.vote_manage_item, listData);
        rvVoteManage.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) rvVoteManage.getParent());
        mAdapter.setOnItemChildClickListener(this);//侧滑菜单监听
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_menu_one:
                ReleaseVoteActivity.start(this,listData.get(position));
                break;
            case R.id.tv_menu_two:
                this.mCurrentPosition = position;
                HttpMethod2.deleteVote(String.valueOf(listData.get(position).getVoteId()),mHandler);
                break;
            default:
                break;
        }
    }


    @Subscribe
    public void postEvent(PostEvent postEvent) {
        if (PostEvent.RELEASE_VOTE_SUCCESS == postEvent.getEventType()) {
            //查询投票列表
             PAGE = 1;
            getVoteList(HandlerConstant2.GET_VOTE_LIST_SUCCESS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
