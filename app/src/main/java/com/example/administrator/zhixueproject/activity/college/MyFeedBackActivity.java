package com.example.administrator.zhixueproject.activity.college;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.college.FeedBackAdapter;
import com.example.administrator.zhixueproject.bean.FeedBack;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 意见反馈
 */
public class MyFeedBackActivity extends BaseActivity   implements MyRefreshLayoutListener ,View.OnClickListener{

    private ListView listView;
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private List<FeedBack.FeedBackList> listAll=new ArrayList<>();
    private FeedBackAdapter feedBackAdapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
        showProgress(getString(R.string.loding));
        getData(HandlerConstant1.GET_FEEDBACK_SUCCESS);
    }


    /**
     * 初始化控件
     */
    private void initView() {
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.feedback));
        mRefreshLayout=(MyRefreshLayout)findViewById(R.id.re_list);
        listView=(ListView)findViewById(R.id.listView);
        listView.setDividerHeight(0);
        final View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        feedBackAdapter=new FeedBackAdapter(MyFeedBackActivity.this,listAll);
        listView.setAdapter(feedBackAdapter);
        findViewById(R.id.tv_feedback_platform).setVisibility(View.GONE);
        findViewById(R.id.lin_back).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_back:
                 finish();
                 break;
             default:
                 break;
        }
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            FeedBack feedBack;
            switch (msg.what){
                case HandlerConstant1.GET_FEEDBACK_SUCCESS:
                    mRefreshLayout.refreshComplete();
                    feedBack= (FeedBack) msg.obj;
                    listAll.clear();
                    refresh(feedBack);
                    break;
                case HandlerConstant1.GET_FEEDBACK_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    feedBack= (FeedBack) msg.obj;
                    refresh(feedBack);
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
     * @param feedBack
     */
    private void refresh(FeedBack feedBack){
        if(null==feedBack){
            return;
        }
        if(feedBack.isStatus()){
            List<FeedBack.FeedBackList> list=feedBack.getData().getAdviceList();
            listAll.addAll(list);
            feedBackAdapter.notifyDataSetChanged();
            if(list.size()<limit){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FeedBack.FeedBackList feedBackList=listAll.get(position);
                    Intent intent=new Intent(mContext,FeedBackDetailsActivity.class);
                    intent.putExtra("feedBackList",feedBackList);
                    startActivity(intent);

                    //设置状态未已读
                    listAll.get(position).setAdviceReadyn(1);
                    feedBackAdapter.notifyDataSetChanged();
                }
            });
        }else{
            showMsg(feedBack.getErrorMsg());
        }
    }



    @Override
    public void onRefresh(View view) {
        page=1;
        getData(HandlerConstant1.GET_FEEDBACK_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getData(HandlerConstant1.GET_FEEDBACK_SUCCESS2);
    }


    /**
     * 查询数据
     * @param index
     */
    private void getData(int index){
        HttpMethod1.getMyFeedBack(page,limit,index,mHandler);
    }

}
