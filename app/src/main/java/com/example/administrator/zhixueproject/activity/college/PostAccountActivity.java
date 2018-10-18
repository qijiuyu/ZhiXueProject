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
import com.example.administrator.zhixueproject.adapter.college.EntryGroupAccountAdapter;
import com.example.administrator.zhixueproject.adapter.college.PostAccountAdapter;
import com.example.administrator.zhixueproject.bean.EntryGroup;
import com.example.administrator.zhixueproject.bean.Post;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 帖子收益明细
 */
public class PostAccountActivity extends BaseActivity   implements MyRefreshLayoutListener {

    private ListView listView;
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private List<Post.PostList> listAll=new ArrayList<>();
    private PostAccountAdapter postAccountAdapter;
    private String startTime="",endTime="";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_in);
        initView();
        startTime=getIntent().getStringExtra("startTime");
        endTime=getIntent().getStringExtra("endTime");
        showProgress(getString(R.string.loding));
        getData(HandlerConstant1.GET_POST_ACCOUNT_SUCCESS);
    }


    /**
     * 初始化控件
     */
    private void initView() {
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.income_post));
        mRefreshLayout=(MyRefreshLayout)findViewById(R.id.re_list);
        listView=(ListView)findViewById(R.id.listView);
        final View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        postAccountAdapter=new PostAccountAdapter(PostAccountActivity.this,listAll);
        listView.setAdapter(postAccountAdapter);
        //返回
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PostAccountActivity.this.finish();
            }
        });
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            Post post;
            switch (msg.what){
                case HandlerConstant1.GET_POST_ACCOUNT_SUCCESS:
                    mRefreshLayout.refreshComplete();
                    post= (Post) msg.obj;
                    listAll.clear();
                    refresh(post);
                    break;
                case HandlerConstant1.GET_POST_ACCOUNT_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    post= (Post) msg.obj;
                    refresh(post);
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
     * @param post
     */
    private void refresh( Post post){
        if(null==post){
            return;
        }
        if(post.isStatus()){
            List<Post.PostList> list=post.getData().getPostAccountList();
            listAll.addAll(list);
            postAccountAdapter.notifyDataSetChanged();
            if(list.size()<limit){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
        }else{
            showMsg(post.getErrorMsg());
        }
    }



    @Override
    public void onRefresh(View view) {
        page=1;
        getData(HandlerConstant1.GET_POST_ACCOUNT_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getData(HandlerConstant1.GET_POST_ACCOUNT_SUCCESS2);
    }


    /**
     * 查询数据
     * @param index
     */
    private void getData(int index){
        HttpMethod1.getPostAccount(startTime,endTime,page,limit,index,mHandler);
    }
}
