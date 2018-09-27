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
import com.example.administrator.zhixueproject.adapter.college.BuyInessInAdapter;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.BuyIness;
import com.example.administrator.zhixueproject.fragment.college.CollegeInfoFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 友商购进
 */
public class BuyInessInActivity extends BaseActivity  implements MyRefreshLayoutListener {

    private ListView listView;
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private List<BuyIness.BusInessList> listAll=new ArrayList<>();
    private BuyInessInAdapter buyInessInAdapter;
    //友商购买id
    private long buytopicId;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_in);
        initView();
        showProgress(getString(R.string.loding));
        getData(HandlerConstant1.BUY_INESS_IN_SUCCESS);
    }


    /**
     * 初始化控件
     */
    private void initView() {
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.friendly_business_in));
        mRefreshLayout=(MyRefreshLayout)findViewById(R.id.re_list);
        listView=(ListView)findViewById(R.id.listView);
        final View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        buyInessInAdapter=new BuyInessInAdapter(BuyInessInActivity.this,listAll);
        listView.setAdapter(buyInessInAdapter);
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BuyInessInActivity.this.finish();
            }
        });
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            BuyIness buyIness;
            switch (msg.what){
                case HandlerConstant1.BUY_INESS_IN_SUCCESS:
                    mRefreshLayout.refreshComplete();
                    buyIness= (BuyIness) msg.obj;
                    listAll.clear();
                    refresh(buyIness);
                    break;
                case HandlerConstant1.BUY_INESS_IN_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    buyIness= (BuyIness) msg.obj;
                    refresh(buyIness);
                    break;
                 //删除
                case HandlerConstant1.DEL_BUY_INESS_SUCCESS:
                    BaseBean baseBean= (BaseBean) msg.obj;
                    if(null==baseBean){
                        return;
                    }
                    if(baseBean.isStatus()){
                        for (int i=0;i<listAll.size();i++){
                            if(buytopicId==listAll.get(i).getBuytopicId()){
                                listAll.remove(i);
                                break;
                            }
                        }
                        buyInessInAdapter.notifyDataSetChanged();
                    }else{
                        showMsg(baseBean.getErrorMsg());
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
     * @param buyIness
     */
    private void refresh(BuyIness buyIness){
        if(null==buyIness){
            return;
        }
        if(buyIness.isStatus()){
            List<BuyIness.BusInessList> list=buyIness.getData().getList();
            listAll.addAll(list);
            buyInessInAdapter.notifyDataSetChanged();
            if(list.size()<limit){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
        }else{
            showMsg(buyIness.getErrorMsg());
        }
    }


    /**
     * 删除
     */
    public void deleteBuyIness(BuyIness.BusInessList busInessList){
        this.buytopicId=busInessList.getBuytopicId();
        showProgress(getString(R.string.loding));
        HttpMethod1.delBuyIness(buytopicId,mHandler);
    }

    @Override
    public void onRefresh(View view) {
        page=1;
        getData(HandlerConstant1.BUY_INESS_IN_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getData(HandlerConstant1.BUY_INESS_IN_SUCCESS2);
    }


    /**
     * 查询数据
     * @param index
     */
    private void getData(int index){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HttpMethod1.buyInessIn(CollegeInfoFragment.homeBean.getCollegeId(),simpleDateFormat.format(new Date()),page,limit,index,mHandler);
    }
}
