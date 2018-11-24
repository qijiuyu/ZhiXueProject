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
import android.view.animation.PathInterpolator;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.topic.VoteNeophyteAdapter;
import com.example.administrator.zhixueproject.bean.topic.VoteDetailListBean;
import com.example.administrator.zhixueproject.bean.topic.VoteNeophyteBean;
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
 * 投票详情页
 *
 * @author PeterGee
 * @date 2018/10/18
 */
public class VoteNeophyteActivity extends BaseActivity implements View.OnClickListener, MyRefreshLayoutListener {

    private VoteNeophyteAdapter mAdapter;
    private List<VoteDetailListBean> listData = new ArrayList<>();
    private String voteId;//投票ID
    private int PAGE = 1;
    private String LIMIT = "10";
    private String TIMESTAMP = "";
    private MyRefreshLayout mrlVoteNeophyte;
    private RecyclerView rvVoteNeophyte;
    public static int position;// 被删除的投票者position

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_neophyte);
        initView();
    }

    private void initView() {
        StatusBarUtils.transparencyBar(this);
        voteId = getIntent().getStringExtra("voteId");
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.vote_neophyte));
        findViewById(R.id.lin_back).setOnClickListener(this);

        mrlVoteNeophyte = (MyRefreshLayout) findViewById(R.id.mrl_vote_neophyte);
        rvVoteNeophyte = (RecyclerView) findViewById(R.id.rv_vote_neophyte);
        rvVoteNeophyte.setLayoutManager(new LinearLayoutManager(this));
        mrlVoteNeophyte.setMyRefreshLayoutListener(this);//刷新加载
        getVoteUserList(HandlerConstant2.GET_VOTE_USER_LIST_SUCCESS);

    }

    private void getVoteUserList(int index) {
        TIMESTAMP = DateUtil.getTime();
        showProgress(getString(R.string.loading));
        HttpMethod2.getVoteUserList(voteId, TIMESTAMP, PAGE + "", LIMIT, index, mHandler);
    }

    public static void start(Context context, String voteId) {
        Intent starter = new Intent(context, VoteNeophyteActivity.class);
        starter.putExtra("voteId", voteId);
        context.startActivity(starter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            default:
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            VoteNeophyteBean bean = (VoteNeophyteBean) msg.obj;
            switch (msg.what) {
                case HandlerConstant2.GET_VOTE_USER_LIST_SUCCESS:
                    getDataSuccess(bean);
                    break;
                case HandlerConstant2.GET_VOTE_USER_LIST_SUCCESS2:
                    loadMoreSuccess(bean);
                    break;
                case HandlerConstant2.DELETE_VOTE_MEMBER_SUCCESS:
                    deleteMemberSuccess(bean);
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
     * 删除投票参与者成功
     * @param bean
     */
    private void deleteMemberSuccess(VoteNeophyteBean bean) {
        if (null==bean){
            return;
        }
        if (bean.isStatus()){
            showMsg("删除成功");
            listData.remove(position);
            if (mAdapter!=null){
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 加载数据
     */
    private void getDataSuccess(VoteNeophyteBean bean) {
        mrlVoteNeophyte.refreshComplete();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            VoteNeophyteBean.DataBean dataBean = bean.getData();
            listData = dataBean.getVoteDetailList();
            if (dataBean.getVoteDetailList().size() == 0) {
                return;
            }
            adapterView();
        } else {
            showMsg(bean.errorMsg);
        }
    }

    /**
     * 加载更多
     */
    private void loadMoreSuccess(VoteNeophyteBean bean) {
        mrlVoteNeophyte.loadMoreComplete();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            VoteNeophyteBean.DataBean dataBean = bean.getData();
            if (dataBean.getVoteDetailList().size() <= 0) {
                showMsg(getResources().getString(R.string.no_more_data));
                return;
            }
            listData.addAll(dataBean.getVoteDetailList());
            adapterView();
        } else {
            showMsg(bean.errorMsg);
        }
    }

    /**
     * 加载失败
     */
    private void requestError() {
        mrlVoteNeophyte.refreshComplete();
        mrlVoteNeophyte.loadMoreComplete();
        showMsg(getString(R.string.net_error));
    }

    /**
     * 设置adapter 数据
     */
    private void adapterView() {
        mAdapter = new VoteNeophyteAdapter(R.layout.vote_neophyte_item, listData);
        rvVoteNeophyte.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) rvVoteNeophyte.getParent());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                VoteNeophyteActivity.position=i;
                switch (view.getId()) {
                    case R.id.img_delete:
                        // 删除投票参与者
                        HttpMethod2.deleteVoteMember(listData.get(i).getVoteId()+"",mHandler);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @Override
    public void onRefresh(View view) {
        PAGE = 1;
        getVoteUserList(HandlerConstant2.GET_VOTE_USER_LIST_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        PAGE++;
        getVoteUserList(HandlerConstant2.GET_VOTE_USER_LIST_SUCCESS2);
    }


}
