package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.college.VipDetailsAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.bean.VipDetails;
import com.example.administrator.zhixueproject.fragment.college.CollegeInfoFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * VIP申请明细
 */
public class VipDetailsActivity extends BaseActivity   implements MyRefreshLayoutListener {

    private ListView listView;
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private List<VipDetails.VipDtailsList> listAll=new ArrayList<>();
    private VipDetailsAdapter vipDetailsAdapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_details);
        initView();
        showProgress(getString(R.string.loding));
        getData(HandlerConstant1.GET_VIP_DETAILS_SUCCESS);
    }


    /**
     * 初始化控件
     */
    private void initView() {
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.vip_apply));
        mRefreshLayout=(MyRefreshLayout)findViewById(R.id.re_list);
        listView=(ListView)findViewById(R.id.listView);
        final View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        vipDetailsAdapter=new VipDetailsAdapter(mContext,listAll);
        listView.setAdapter(vipDetailsAdapter);
    }

    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            VipDetails vipDetails;
            switch (msg.what){
                case HandlerConstant1.GET_VIP_DETAILS_SUCCESS:
                    mRefreshLayout.refreshComplete();
                    vipDetails= (VipDetails) msg.obj;
                    listAll.clear();
                    refresh(vipDetails);
                    break;
                case HandlerConstant1.GET_VIP_DETAILS_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    vipDetails= (VipDetails) msg.obj;
                    refresh(vipDetails);
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    clearTask();
                    showMsg(getString(R.string.net_error));
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 刷新数据
     * @param vipDetails
     */
    private void refresh(VipDetails vipDetails){
        if(null==vipDetails){
            return;
        }
        if(vipDetails.isStatus()){
            List<VipDetails.VipDtailsList> list=vipDetails.getData().getVipDetailList();
            listAll.addAll(list);
            vipDetailsAdapter.notifyDataSetChanged();
            if(list.size()<limit){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
        }else{
            showMsg(vipDetails.getErrorMsg());
        }
    }



    @Override
    public void onRefresh(View view) {
        page=1;
        getData(HandlerConstant1.GET_VIP_DETAILS_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getData(HandlerConstant1.GET_VIP_DETAILS_SUCCESS2);
    }


    /**
     * 查询数据
     */
    private void getData(int index){
        final UserBean userBean= MyApplication.userInfo.getData().getUser();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HttpMethod1.getVipDetails(userBean.getUserId(), CollegeInfoFragment.homeBean.getCollegeId(),simpleDateFormat.format(new Date()),page,limit,index,mHandler);
    }
}
