package com.example.administrator.zhixueproject.fragment.college;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.college.ReportDetailsActivity;
import com.example.administrator.zhixueproject.activity.college.ReportManagerActivity;
import com.example.administrator.zhixueproject.adapter.college.ReportListAdapter;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.Report;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 帖子举报fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class TopicReportFragment extends BaseFragment implements MyRefreshLayoutListener {

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
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        reportListAdapter=new ReportListAdapter(mActivity,listAll);
        listView.setAdapter(reportListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Report.ReportList reportList=listAll.get(position);
                Intent intent=new Intent(mActivity, ReportDetailsActivity.class);
                intent.putExtra("reportList",reportList);
                intent.putExtra("complaintType",1);
                mActivity.startActivity(intent);
            }
        });
        //查询举报数据
        getData(HandlerConstant1.GET_REPORT_LIST_SUCCESS);
        return view;
    }



    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            Report report;
            switch (msg.what){
                case HandlerConstant1.GET_REPORT_LIST_SUCCESS:
                    mRefreshLayout.refreshComplete();
                    report= (Report) msg.obj;
                    listAll.clear();
                    refresh(report);
                    break;
                case HandlerConstant1.GET_REPORT_LIST_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    report= (Report) msg.obj;
                    refresh(report);
                    break;
                //删除
                case HandlerConstant1.DELETE_REPORT_SUCCESS:
                     final BaseBean baseBean= (BaseBean) msg.obj;
                     if(null==baseBean){
                         return;
                     }
                     if(baseBean.isStatus()){
                         showMsg("删除成功");
                         page=1;
                         HttpMethod1.getReportList(1, ReportManagerActivity.key,page,limit,HandlerConstant1.GET_REPORT_LIST_SUCCESS,mHandler);
                         //刷新楼层数据
                         mActivity.sendBroadcast(new Intent(ReportManagerActivity.REFRESH_LOU_CENT));
                     }else{
                         // showMsg(baseBean.getErrorMsg());
                     }
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
            // showMsg(report.getErrorMsg());
        }
    }


    @Override
    public void onRefresh(View view) {
        page=1;
        HttpMethod1.getReportList(1, ReportManagerActivity.key,page,limit,HandlerConstant1.GET_REPORT_LIST_SUCCESS,mHandler);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        HttpMethod1.getReportList(1, ReportManagerActivity.key,page,limit,HandlerConstant1.GET_REPORT_LIST_SUCCESS2,mHandler);
    }


    /**
     * 注册广播
     */
    private void register() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ReportManagerActivity.ACTION_SELECT_ORDERBY);//选择了排序
        myIntentFilter.addAction(ReportManagerActivity.ACTION_DUO_XUAN);
        myIntentFilter.addAction(ReportManagerActivity.ACTION_QUAN_BU_SHAN_CHU);
        myIntentFilter.addAction(ReportManagerActivity.ACTION_QUAN_XUAN);
        myIntentFilter.addAction(ReportManagerActivity.ACTION_SHAN_CHU);
        myIntentFilter.addAction(ReportManagerActivity.ACTION_QU_XIAO);
        mActivity.registerReceiver(mBroadcastReceiver,myIntentFilter);
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if(!isVisibleToUser){
                return;
            }
            switch (intent.getAction()){
                case ReportManagerActivity.ACTION_SELECT_ORDERBY:
                     page=1;
                     HttpMethod1.getReportList(1, ReportManagerActivity.key,page,limit,HandlerConstant1.GET_REPORT_LIST_SUCCESS,mHandler);
                     break;
                case ReportManagerActivity.ACTION_DUO_XUAN://多选
                     if(null!=reportListAdapter){
                         reportListAdapter.isSelect=true;
                         reportListAdapter.notifyDataSetChanged();
                     }
                     break;
                case ReportManagerActivity.ACTION_QUAN_BU_SHAN_CHU://全部删除
                     StringBuffer stringBuffer1=new StringBuffer();
                     for (int i=0;i<listAll.size();i++){
                         stringBuffer1.append(listAll.get(i).getComplaintToId()+",");
                     }
                      final String id=stringBuffer1.substring(0,stringBuffer1.toString().length()-1);
                      deleteReport(id);
                      break;
                case ReportManagerActivity.ACTION_QUAN_XUAN://全选
                     if(null!=reportListAdapter){
                        reportListAdapter.isAllSelect=true;
                        reportListAdapter.notifyDataSetChanged();
                     }
                     break;
                case ReportManagerActivity.ACTION_SHAN_CHU://删除
                     StringBuffer stringBuffer=new StringBuffer();
                     for (Map.Entry<String, String> entry : reportListAdapter.maps.entrySet()) {
                         stringBuffer.append(entry.getValue()+",");
                     }
                     if(TextUtils.isEmpty(stringBuffer)){
                         showMsg("请选择删除项！");
                         return;
                     }
                     final String ids=stringBuffer.substring(0,stringBuffer.toString().length()-1);
                     deleteReport(ids);
                     break;
                case ReportManagerActivity.ACTION_QU_XIAO://取消
                     if(null!=reportListAdapter){
                         reportListAdapter.maps.clear();
                         reportListAdapter.isSelect=false;
                         reportListAdapter.isAllSelect=false;
                         reportListAdapter.notifyDataSetChanged();
                     }
                     break;
            }

        }
    };


    /**
     * 删除举报内容
     * @param ids
     */
    private void deleteReport(String ids){
        showProgress("删除中...");
        HttpMethod1.deleteReport("1",ids,mHandler);
    }

    /**
     * 查询数据
     * @param index
     */
    private void getData(int index){
        if(isVisibleToUser && view!=null && listAll.size()==0){
            HttpMethod1.getReportList(1, ReportManagerActivity.key,page,limit,index,mHandler);
        }
    }



    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //查询举报数据
        getData(HandlerConstant1.GET_REPORT_LIST_SUCCESS);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(mBroadcastReceiver);
    }
}
