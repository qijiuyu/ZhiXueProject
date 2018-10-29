package com.example.administrator.zhixueproject.activity.memberManage;

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
import com.example.administrator.zhixueproject.adapter.memberManage.KickOutMemberAdapter;
import com.example.administrator.zhixueproject.bean.memberManage.AttendanceBean;
import com.example.administrator.zhixueproject.bean.memberManage.KickOutMemberBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;

import java.util.List;

/**
 * 踢出的会员
 *
 * @author PeterGee
 * @date 2018/10/20
 */
public class KickOutMemberActivity extends BaseActivity implements View.OnClickListener, MyRefreshLayoutListener, BaseQuickAdapter.OnItemChildClickListener {

    private MyRefreshLayout refreshLayout;
    private KickOutMemberAdapter mKickOutMemberAdapter;
    private int PAGE = 1;
    private String LIMIT = "10";
    private List<AttendanceBean> kickOutList;
    private int mPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kick_out_member);
        initView();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.kick_out_member));
        findViewById(R.id.lin_back).setOnClickListener(this);
        refreshLayout = (MyRefreshLayout) findViewById(R.id.refresh_layout);
        RecyclerView rvKickOutMember = (RecyclerView) findViewById(R.id.rv_kick_out_member);
        mKickOutMemberAdapter = new KickOutMemberAdapter(R.layout.kick_out_member_item);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvKickOutMember.setAdapter(mKickOutMemberAdapter);
        rvKickOutMember.setLayoutManager(linearLayoutManager);
        refreshLayout.setMyRefreshLayoutListener(this);
        mKickOutMemberAdapter.setOnItemChildClickListener(this);
        mKickOutMemberAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) rvKickOutMember.getParent());
        requestNet(HandlerConstant2.GET_DELYN_VIP_LIST_SUCCESS);
    }

    /**
     * 请求网络数据
     *
     * @param index
     */
    private void requestNet(int index) {
        showProgress(getString(R.string.loading));
        HttpMethod2.getDelynVipList(PAGE + "", LIMIT, index, mHandler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(View view) {
        PAGE = 1;
        requestNet(HandlerConstant2.GET_DELYN_VIP_LIST_SUCCESS);

    }

    @Override
    public void onLoadMore(View view) {
        PAGE++;
        requestNet(HandlerConstant2.GET_DELYN_VIP_LIST_SUCCESS2);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        this.mPosition = position;
        showMsg(getString(R.string.loading));
        HttpMethod2.delynVipInvite(kickOutList.get(position).getAttendId() + "", mHandler);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            KickOutMemberBean bean = (KickOutMemberBean) msg.obj;
            switch (msg.what) {
                case HandlerConstant2.GET_DELYN_VIP_LIST_SUCCESS:
                    getDelynVipListSuccess(bean);
                    break;
                case HandlerConstant2.GET_DELYN_VIP_LIST_SUCCESS2:
                    loadMoreDataSuccess(bean);
                    break;
                case HandlerConstant2.DELYN_VIP_INVITE_SUCCESS:
                    delynInviteSuccess(bean);
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
     * 获取被踢出人员名单成功
     *
     * @param bean
     */
    private void getDelynVipListSuccess(KickOutMemberBean bean) {
        refreshLayout.refreshComplete();
        if (bean.isStatus()) {
            this.kickOutList = bean.getData().getDelynVipList();
            if (bean.getData().getDelynVipList().size() <= 0) {
                return;
            }
            mKickOutMemberAdapter.setNewData(kickOutList);
            mKickOutMemberAdapter.notifyDataSetChanged();
        } else {
            showMsg(bean.getErrorMsg());
        }
    }

    /**
     * 下拉加载成功
     *
     * @param bean
     */
    private void loadMoreDataSuccess(KickOutMemberBean bean) {
        refreshLayout.loadMoreComplete();
        if (bean.isStatus()) {
            kickOutList.addAll(bean.getData().getDelynVipList());
            if (bean.getData().getDelynVipList().size() <= 0) {
                showMsg(getString(R.string.no_more_data));
            }
            mKickOutMemberAdapter.setNewData(kickOutList);
            mKickOutMemberAdapter.notifyDataSetChanged();
        } else {
            showMsg(bean.getErrorMsg());
        }
    }

    /**
     * 请求错误
     */
    private void requestError() {
        refreshLayout.refreshComplete();
        refreshLayout.loadMoreComplete();
        showMsg(getString(R.string.net_error));
    }

    /**
     * 踢出会员邀请
     *
     * @param bean
     */
    private void delynInviteSuccess(KickOutMemberBean bean) {
        if (bean.isStatus()) {
            showMsg(getString(R.string.invite_success));
            kickOutList.remove(mPosition);
            mKickOutMemberAdapter.notifyDataSetChanged();
        } else {
            showMsg(bean.getErrorMsg());
        }
    }
}
