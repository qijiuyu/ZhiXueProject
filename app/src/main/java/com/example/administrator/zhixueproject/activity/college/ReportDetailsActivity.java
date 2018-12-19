package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.college.ReportDetailsAdapter;
import com.example.administrator.zhixueproject.bean.Report;
import com.example.administrator.zhixueproject.bean.ReportDetails;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.ToolUtils;
import com.example.administrator.zhixueproject.view.CustomListView;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 举报详情
 * Created by Administrator on 2018/11/18.
 */

public class ReportDetailsActivity extends BaseActivity  implements MyRefreshLayoutListener {

    private CustomListView listView;
    private MyRefreshLayout mRefreshLayout;
    private WebView webView;
    private int page=1;
    private int complaintType;
    private Report.ReportList reportList;
    private ReportDetailsAdapter reportDetailsAdapter;
    private List<ReportDetails.listBean> listAll=new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);
        initView();
        showTopic();
        showProgress(getString(R.string.loding));
        getData(HandlerConstant1.GET_REPORT_DETAILS_SUCCESS);
    }

    private void initView(){
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText("举报明细");
        mRefreshLayout=(MyRefreshLayout)findViewById(R.id.re_list);
        listView=(CustomListView)findViewById(R.id.listView);
        webView=(WebView)findViewById(R.id.wv_post_content);
        final View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        reportDetailsAdapter=new ReportDetailsAdapter(ReportDetailsActivity.this,listAll);
        listView.setAdapter(reportDetailsAdapter);
        //返回
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ReportDetailsActivity.this.finish();
            }
        });
    }


    private void showTopic(){
        reportList= (Report.ReportList) getIntent().getSerializableExtra("reportList");
        complaintType=getIntent().getIntExtra("complaintType",0);
        ImageView imageView=(ImageView)findViewById(R.id.iv_img);
        TextView tvTopicName=(TextView)findViewById(R.id.tv_report_topic);
        TextView tvCount=(TextView)findViewById(R.id.tv_count);
        TextView tvName=(TextView)findViewById(R.id.tv_report_name);
        TextView tvTime=(TextView)findViewById(R.id.tv_time);
        TextView tvContent=(TextView)findViewById(R.id.tv_content);
        Glide.with(mContext).load(reportList.getTopicImg()).override(105,78).centerCrop().into(imageView);
        tvTopicName.setText(reportList.getPostName());
        tvCount.setText(reportList.getComplaintCount()+"人  举报");
        tvName.setText(reportList.getPostWriterId());
        tvTime.setText(reportList.getComplaintCreationTime());
        tvContent.setText(reportList.getComplaintContent());
    }



    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            ReportDetails reportDetails;
            switch (msg.what){
                case HandlerConstant1.GET_REPORT_DETAILS_SUCCESS:
                    mRefreshLayout.refreshComplete();
                    reportDetails= (ReportDetails) msg.obj;
                    listAll.clear();
                    refresh(reportDetails);
                    break;
                case HandlerConstant1.GET_REPORT_DETAILS_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    reportDetails= (ReportDetails) msg.obj;
                    refresh(reportDetails);
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    showMsg(getString(R.string.net_error));
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 刷新数据
     * @param reportDetails
     */
    String content;
    private void refresh(ReportDetails reportDetails){
        if(null==reportDetails){
            return;
        }

        if(reportDetails.isStatus()){
            List<ReportDetails.listBean> list=reportDetails.getData().getComplaintList();
            listAll.addAll(list);
            reportDetailsAdapter.notifyDataSetChanged();
            if(list.size()<20){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }


            //显示帖子详情
            if(TextUtils.isEmpty(content)){
                //帖子内容
                String html = ToolUtils.imgStyleHtml(reportDetails.getData().getPost().getPostContent());
                webView.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return true;
                    }
                });
                WebSettings settings = webView.getSettings();
                settings.setJavaScriptEnabled(true);
                webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
            }
        }else{
            showMsg(reportDetails.getErrorMsg());
        }
    }

    @Override
    public void onRefresh(View view) {
        page=1;
        getData(HandlerConstant1.GET_REPORT_DETAILS_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getData(HandlerConstant1.GET_REPORT_DETAILS_SUCCESS2);
    }


    /**
     * 查询数据
     * @param index
     */
    private void getData(int index){
        HttpMethod1.getReportDetails(complaintType,reportList.getComplaintToId(),page, index,mHandler);
    }
}
