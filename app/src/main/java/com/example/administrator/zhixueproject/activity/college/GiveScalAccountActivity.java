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
import com.example.administrator.zhixueproject.adapter.college.GiveAccountAdapter;
import com.example.administrator.zhixueproject.adapter.college.GiveScalAccountAdapter;
import com.example.administrator.zhixueproject.bean.GiveAccount;
import com.example.administrator.zhixueproject.bean.GiveScalAccount;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 打赏分成收益明细
 */
public class GiveScalAccountActivity extends BaseActivity   implements MyRefreshLayoutListener {

    private ListView listView;
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private List<GiveScalAccount.GiveScalList> listAll=new ArrayList<>();
    private GiveScalAccountAdapter giveScalAccountAdapter;
    private String startTime="",endTime="";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_in);
        initView();
        startTime=getIntent().getStringExtra("startTime");
        endTime=getIntent().getStringExtra("endTime");
        showProgress(getString(R.string.loding));
        getData(HandlerConstant1.GET_GIVE_SCAL_ACCOUNT_SUCCESS);
    }


    /**
     * 初始化控件
     */
    private void initView() {
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.income_reward_divide));
        mRefreshLayout=(MyRefreshLayout)findViewById(R.id.re_list);
        listView=(ListView)findViewById(R.id.listView);
        final View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        giveScalAccountAdapter=new GiveScalAccountAdapter(GiveScalAccountActivity.this,listAll);
        listView.setAdapter(giveScalAccountAdapter);
        //返回
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GiveScalAccountActivity.this.finish();
            }
        });
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            GiveScalAccount giveScalAccount;
            switch (msg.what){
                case HandlerConstant1.GET_GIVE_SCAL_ACCOUNT_SUCCESS:
                    mRefreshLayout.refreshComplete();
                    giveScalAccount= (GiveScalAccount) msg.obj;
                    listAll.clear();
                    refresh(giveScalAccount);
                    break;
                case HandlerConstant1.GET_GIVE_SCAL_ACCOUNT_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    giveScalAccount= (GiveScalAccount) msg.obj;
                    refresh(giveScalAccount);
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
     * @param giveScalAccount
     */
    private void refresh(GiveScalAccount giveScalAccount){
        if(null==giveScalAccount){
            return;
        }
        if(giveScalAccount.isStatus()){
            List<GiveScalAccount.GiveScalList> list=giveScalAccount.getData().getGiveScalAccountList();
            listAll.addAll(list);
            giveScalAccountAdapter.notifyDataSetChanged();
            if(list.size()<limit){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
        }else{
            showMsg(giveScalAccount.getErrorMsg());
        }
    }



    @Override
    public void onRefresh(View view) {
        page=1;
        getData(HandlerConstant1.GET_GIVE_SCAL_ACCOUNT_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getData(HandlerConstant1.GET_GIVE_SCAL_ACCOUNT_SUCCESS2);
    }


    /**
     * 查询数据
     * @param index
     */
    private void getData(int index){
        HttpMethod1.getGiveScalAccount(startTime,endTime,page,limit,index,mHandler);
    }
}
