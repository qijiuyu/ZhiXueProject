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
import com.example.administrator.zhixueproject.adapter.memberManage.BlackListAdapter;
import com.example.administrator.zhixueproject.bean.memberManage.AttendanceBean;
import com.example.administrator.zhixueproject.bean.memberManage.BlackListBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.util.List;

/**
 * 黑名单
 *
 * @author PeterGee
 * @date 2018/10/20
 */
public class BlacklistActivity extends BaseActivity implements View.OnClickListener, MyRefreshLayoutListener, BaseQuickAdapter.OnItemChildClickListener {
    private BlackListAdapter mBlackListAdapter;
    private int PAGE = 1;
    private String LIMIT = "10";
    private List<AttendanceBean> mBlackList;
    private int mPosition;
    private MyRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);
        initView();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.black_list));
        findViewById(R.id.lin_back).setOnClickListener(this);
        refreshLayout = (MyRefreshLayout) findViewById(R.id.refresh_layout);
        RecyclerView rvBlackList = (RecyclerView) findViewById(R.id.rv_black_list);
        mBlackListAdapter = new BlackListAdapter(R.layout.black_list_item);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvBlackList.setAdapter(mBlackListAdapter);
        rvBlackList.setLayoutManager(linearLayoutManager);
        refreshLayout.setMyRefreshLayoutListener(this);
        mBlackListAdapter.setOnItemChildClickListener(this);
        mBlackListAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) rvBlackList.getParent());
        requestNet(HandlerConstant2.GET_BLACK_LIST_SUCCESS);
    }

    /**
     * 查询黑名单数据
     *
     * @param index
     */
    private void requestNet(int index) {
        showProgress(getString(R.string.loading));
        HttpMethod2.getBlackList(PAGE + "", LIMIT, index, mHandler);
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
        PAGE=1;
        requestNet(HandlerConstant2.GET_BLACK_LIST_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        PAGE++;
        requestNet(HandlerConstant2.GET_BLACK_LIST_SUCCESS2);

    }

    /**
     * 取消拉黑
     * @param baseQuickAdapter
     * @param view
     * @param i
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (mBlackList==null){
            return;
        }
        this.mPosition=i;
        showProgress(getString(R.string.loading));
        HttpMethod2.removeBlackList(mBlackList.get(i).getAttendId()+"",mHandler);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            BlackListBean bean = (BlackListBean) msg.obj;
            switch (msg.what) {
                case HandlerConstant2.GET_BLACK_LIST_SUCCESS:
                    getBlackListSuccess(bean);
                    break;
                case HandlerConstant2.GET_BLACK_LIST_SUCCESS2:
                    loadMoreBlackListSuccess(bean);
                    break;
                case HandlerConstant2.REMOVE_BLACK_LIST_SUCCESS:
                    removeBlackListSuccess(bean);
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
     * 获取黑名单成功
     *
     * @param bean
     */
    private void getBlackListSuccess(BlackListBean bean) {
        refreshLayout.refreshComplete();
        if (bean.isStatus()) {
            mBlackList = bean.getData().getBlackList();
            if (bean.getData().getBlackList().size() <= 0) {
                return;
            }
            mBlackListAdapter.setNewData(mBlackList);
            mBlackListAdapter.notifyDataSetChanged();
        } else {
            // showMsg(bean.getErrorMsg());
        }
    }

    /**
     * 加载更多
     *
     * @param bean
     */
    private void loadMoreBlackListSuccess(BlackListBean bean) {
        refreshLayout.loadMoreComplete();
        if (bean.isStatus()) {
            mBlackList.addAll(bean.getData().getBlackList());
            if (bean.getData().getBlackList().size() <= 0) {
                return;
            }
            mBlackListAdapter.setNewData(mBlackList);
            mBlackListAdapter.notifyDataSetChanged();
        } else {
            // showMsg(bean.getErrorMsg());
        }
    }

    /**
     * 移除黑名单成功
     * @param bean
     */
    private void removeBlackListSuccess(BlackListBean bean) {
        if (bean.isStatus()){
            showMsg("取消拉黑成功");
            mBlackList.remove(mPosition);
            mBlackListAdapter.notifyDataSetChanged();
        }else {
            // showMsg(bean.getErrorMsg());
        }
    }


    /**
     * 请求失败
     */
    private void requestError() {
        refreshLayout.refreshComplete();
        refreshLayout.loadMoreComplete();
        showMsg(getString(R.string.net_error));
    }

}
