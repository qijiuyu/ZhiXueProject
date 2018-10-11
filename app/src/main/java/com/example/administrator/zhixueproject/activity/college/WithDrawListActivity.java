package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.college.WithDrawAdapter;
import com.example.administrator.zhixueproject.bean.WithDraw;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 提现明细
 */
public class WithDrawListActivity extends BaseActivity   implements MyRefreshLayoutListener {

    private ListView listView;
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private List<WithDraw.WithDrawList> listAll=new ArrayList<>();
    private WithDrawAdapter withDrawAdapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_in);
        initView();
        showProgress(getString(R.string.loding));
        getData(HandlerConstant1.GET_WITHDRAW_SUCCESS);
    }


    /**
     * 初始化控件
     */
    private void initView() {
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.cash_details));
        TextView tvRight=(TextView)findViewById(R.id.tv_right);
        tvRight.setText(getString(R.string.cash));
        mRefreshLayout=(MyRefreshLayout)findViewById(R.id.re_list);
        listView=(ListView)findViewById(R.id.listView);
        final View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        withDrawAdapter=new WithDrawAdapter(WithDrawListActivity.this,listAll);
        listView.setAdapter(withDrawAdapter);

        //提现
        tvRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View view1= LayoutInflater.from(mContext).inflate(R.layout.with_draw_pop,null);
                dialogPop(view1,true);
            }
        });
        //返回
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WithDrawListActivity.this.finish();
            }
        });
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            WithDraw withDraw;
            switch (msg.what){
                case HandlerConstant1.GET_WITHDRAW_SUCCESS:
                    mRefreshLayout.refreshComplete();
                    withDraw= (WithDraw) msg.obj;
                    listAll.clear();
                    refresh(withDraw);
                    break;
                case HandlerConstant1.GET_WITHDRAW_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    withDraw= (WithDraw) msg.obj;
                    refresh(withDraw);
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
     * @param withDraw
     */
    private void refresh(WithDraw withDraw){
        if(null==withDraw){
            return;
        }
        if(withDraw.isStatus()){
            List<WithDraw.WithDrawList> list=withDraw.getData().getCashRecordList();
            listAll.addAll(list);
            withDrawAdapter.notifyDataSetChanged();
            if(list.size()<limit){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
        }else{
            showMsg(withDraw.getErrorMsg());
        }
    }



    @Override
    public void onRefresh(View view) {
        page=1;
        getData(HandlerConstant1.GET_WITHDRAW_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getData(HandlerConstant1.GET_WITHDRAW_SUCCESS2);
    }


    /**
     * 查询数据
     * @param index
     */
    private void getData(int index){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HttpMethod1.getWithDraw(page,limit,simpleDateFormat.format(new Date()),index,mHandler);
    }
}
