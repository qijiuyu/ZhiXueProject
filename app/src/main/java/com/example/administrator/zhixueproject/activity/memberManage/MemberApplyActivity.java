package com.example.administrator.zhixueproject.activity.memberManage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.bean.memberManage.AttendanceBean;
import com.example.administrator.zhixueproject.bean.memberManage.MemberApplyBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.util.List;

/**
 * 会员申请
 *
 * @author PeterGee
 * @date 2018/10/20
 */
public class MemberApplyActivity extends BaseActivity implements View.OnClickListener, MyRefreshLayoutListener, BaseQuickAdapter.OnItemClickListener {
    private MemberApplyAdapter mMemberApplyAdapter;
    private boolean isShowCheck = false;
    private boolean isAllChecked = false;
    public static final String APPLY_PASS="1";//会员申请通过
    public static final String APPLY_REFUSE="2";//拒绝
    private int PAGE = 1;
    private String LIMIT = "10";
    private List<AttendanceBean> mApplyVipList;
    private MyRefreshLayout refreshLayout;
    private LinearLayout llBottomChoose;
    private TextView tvRight;
    private ImageView ivAllChecked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_apply);
        initView();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.member_application));
        findViewById(R.id.lin_back).setOnClickListener(this);
        tvRight = (TextView) findViewById(R.id.tv_right);
        tvRight.setText(getString(R.string.choose));
        tvRight.setOnClickListener(this);
        findViewById(R.id.ll_delete).setOnClickListener(this);
        findViewById(R.id.ll_cancel).setOnClickListener(this);
        RecyclerView rvMemberApply = (RecyclerView) findViewById(R.id.rv_member_apply);
        refreshLayout = (MyRefreshLayout) findViewById(R.id.refresh_layout);
        llBottomChoose = (LinearLayout) findViewById(R.id.ll_bottom_choose);
        ivAllChecked = (ImageView) findViewById(R.id.iv_all_checked);
        ivAllChecked.setOnClickListener(this);

        mMemberApplyAdapter = new MemberApplyAdapter(R.layout.member_apply_item);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMemberApply.setAdapter(mMemberApplyAdapter);
        rvMemberApply.setLayoutManager(linearLayoutManager);
        refreshLayout.setMyRefreshLayoutListener(this);
        mMemberApplyAdapter.setOnItemClickListener(this);
        mMemberApplyAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) rvMemberApply.getParent());
        requestNet(HandlerConstant2.GET_APPLY_VIP_LIST_SUCCESS);

    }

    private void requestNet(int index) {
        showProgress(getString(R.string.loading));
        HttpMethod2.getApplyVipList(PAGE, LIMIT, index, mHandler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right://选择
                isShowCheck = !isShowCheck;
                if (isShowCheck) {
                    tvRight.setText(getString(R.string.cancel));
                    llBottomChoose.setVisibility(View.VISIBLE);
                } else {
                    tvRight.setText(getString(R.string.choose));
                    llBottomChoose.setVisibility(View.GONE);
                }
                isShowChoose(isShowCheck);
                break;
            case R.id.ll_all_check://全选
                isAllChecked = !isAllChecked;
                if (isAllChecked) {
                    ivAllChecked.setImageResource(R.mipmap.blue_check);
                } else {
                    ivAllChecked.setImageResource(R.mipmap.gray_uncheck);
                }
                for (int i = 0; i < mApplyVipList.size(); i++) {
                    mApplyVipList.get(i).setChecked(isAllChecked);
                }
                mMemberApplyAdapter.notifyDataSetChanged();
                break;
            case R.id.ll_delete://拒绝
                showProgress(getString(R.string.loading));
                HttpMethod2.applyVipPass(getCheckedMemberID(),APPLY_REFUSE,HandlerConstant2.APPLY_REFUSE_SUCCESS,mHandler);
                break;
            case R.id.ll_cancel://通过
                showProgress(getString(R.string.loading));
                HttpMethod2.applyVipPass(getCheckedMemberID(),APPLY_PASS,HandlerConstant2.APPLY_REFUSE_SUCCESS,mHandler);
                break;
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
        requestNet(HandlerConstant2.GET_APPLY_VIP_LIST_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        PAGE++;
        requestNet(HandlerConstant2.GET_APPLY_VIP_LIST_SUCCESS2);
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        boolean checked = mApplyVipList.get(position).isChecked();
        mApplyVipList.get(position).setChecked(!checked);
        mMemberApplyAdapter.notifyDataSetChanged();
    }

    /**
     * 是否显示勾选圈
     */
    public void isShowChoose(boolean isShowCheck) {
        for (int i = 0; i < mApplyVipList.size(); i++) {
            mApplyVipList.get(i).setShow(isShowCheck);
        }
        mMemberApplyAdapter.notifyDataSetChanged();
    }

    /**
     * 获取选中申请的会员attendId组拼
     * @return
     */
    public String getCheckedMemberID() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mApplyVipList.size(); i++) {
            if (mApplyVipList.get(i).isChecked()) {
                stringBuilder.append(mApplyVipList.get(i).getAttendId());
                stringBuilder.append(",");
            }
        }
        String attendIds = stringBuilder.substring(0, stringBuilder.length() - 1);
        return attendIds;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            MemberApplyBean bean = (MemberApplyBean) msg.obj;
            switch (msg.what) {
                case HandlerConstant2.GET_APPLY_VIP_LIST_SUCCESS:
                    getApplyVipListSuccess(bean);
                    break;
                case HandlerConstant2.GET_APPLY_VIP_LIST_SUCCESS2:
                    loadMoreDataSuccess(bean);
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    requestError();
                    break;
                default:
                    break;
            }
        }
    };


    private void getApplyVipListSuccess(MemberApplyBean bean) {
        refreshLayout.refreshComplete();
        if (bean.isStatus()){
            mApplyVipList=bean.getData().getApplyVipList();
            if (bean.getData().getApplyVipList().size()<=0){
                return;
            }
            mMemberApplyAdapter.setNewData(mApplyVipList);
            mMemberApplyAdapter.notifyDataSetChanged();
        }else {
            showMsg(bean.getErrorMsg());
        }
    }

    private void loadMoreDataSuccess(MemberApplyBean bean) {
        refreshLayout.loadMoreComplete();
        if (bean.isStatus()){
            mApplyVipList.addAll(bean.getData().getApplyVipList());
            if (bean.getData().getApplyVipList().size()<=0){
                showMsg(getString(R.string.no_more_data));
            }
            mMemberApplyAdapter.setNewData(mApplyVipList);
            mMemberApplyAdapter.notifyDataSetChanged();
        }else {
            showMsg(bean.getErrorMsg());
        }
    }

    private void requestError() {
        refreshLayout.refreshComplete();
        refreshLayout.loadMoreComplete();
        showMsg(getString(R.string.net_error));
    }

}
