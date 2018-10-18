package com.example.administrator.zhixueproject.fragment.college;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.college.ReportManagerActivity;
import com.example.administrator.zhixueproject.adapter.college.ReportListAdapter;
import com.example.administrator.zhixueproject.bean.Report;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 楼层举报fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class FloorReportFragment extends BaseFragment implements MyRefreshLayoutListener {

    private ListView listView;
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private ReportListAdapter reportListAdapter;
    private List<Report.ReportList> listAll=new ArrayList<>();
    //fragment是否可见
    private boolean isVisibleToUser=false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        register();//注册广播
    }


    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);
        mRefreshLayout=(MyRefreshLayout)view.findViewById(R.id.re_list);
        listView=(ListView)view.findViewById(R.id.listView);
        final View view = mActivity.getLayoutInflater().inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        reportListAdapter=new ReportListAdapter(mActivity,listAll);
        listView.setAdapter(reportListAdapter);
        //查询举报数据
        getData(HandlerConstant1.GET_REPORT_LIST_SUCCESS3);
        return view;
    }



    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            Report report;
            switch (msg.what){
                case HandlerConstant1.GET_REPORT_LIST_SUCCESS3:
                    mRefreshLayout.refreshComplete();
                    report= (Report) msg.obj;
                    listAll.clear();
                    refresh(report);
                    break;
                case HandlerConstant1.GET_REPORT_LIST_SUCCESS4:
                    mRefreshLayout.loadMoreComplete();
                    report= (Report) msg.obj;
                    refresh(report);
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
     * @param report
     */
    private void refresh(Report report){
        if(null==report){
            return;
        }
        if(report.isStatus()){
            List<Report.ReportList> list=report.getData().getComplaintList();
            listAll.addAll(list);
            reportListAdapter.notifyDataSetChanged();
            if(list.size()<limit){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
        }else{
            showMsg(report.getErrorMsg());
        }
    }


    @Override
    public void onRefresh(View view) {
        page=1;
        getData(HandlerConstant1.GET_REPORT_LIST_SUCCESS3);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getData(HandlerConstant1.GET_REPORT_LIST_SUCCESS4);
    }


    /**
     * 注册广播
     */
    private void register() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ReportManagerActivity.ACTION_SELECT_ORDERBY);//选择了排序
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ReportManagerActivity.ACTION_SELECT_ORDERBY)){
                page=1;
                getData(HandlerConstant1.GET_REPORT_LIST_SUCCESS3);
            }
        }
    };

    /**
     * 查询数据
     * @param index
     */
    private void getData(int index){
        if(isVisibleToUser && view!=null && listAll.size()==0){
            HttpMethod1.getReportList(2, ReportManagerActivity.key,page,limit,index,mHandler);
        }
    }



    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //查询举报数据
        getData(HandlerConstant1.GET_REPORT_LIST_SUCCESS3);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(mBroadcastReceiver);
    }
}
