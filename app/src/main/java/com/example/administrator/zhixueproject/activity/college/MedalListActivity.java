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
import com.example.administrator.zhixueproject.adapter.college.MedalItemAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.Medal;
import com.example.administrator.zhixueproject.bean.UserBean;
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
 * 勋章列表
 */
public class MedalListActivity extends BaseActivity  implements MyRefreshLayoutListener {

    private ListView listView;
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private MedalItemAdapter medalItemAdapter;
    private List<Medal.MedalList> listAll=new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medal);
        initView();
        getData(HandlerConstant1.GET_MEDAL_LIST_SUCCESS);
    }


    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.medal_manage));
        mRefreshLayout=(MyRefreshLayout)findViewById(R.id.re_list);
        listView=(ListView)findViewById(R.id.listView);
        final View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            Medal medal;
            switch (msg.what){
                case HandlerConstant1.GET_MEDAL_LIST_SUCCESS:
                     mRefreshLayout.refreshComplete();
                     medal= (Medal) msg.obj;
                     listAll.clear();
                     refresh(medal);
                     break;
                case HandlerConstant1.GET_MEDAL_LIST_SUCCESS2:
                     mRefreshLayout.loadMoreComplete();
                     medal= (Medal) msg.obj;
                     refresh(medal);
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
     * @param medal
     */
    private void refresh(Medal medal){
        if(null==medal){
            return;
        }
        if(medal.isStatus()){
            List<Medal.MedalList> list=medal.getData().getMedalTypeList();
            listAll.addAll(list);
            if(medalItemAdapter==null){
                medalItemAdapter=new MedalItemAdapter(mContext,listAll);
                listView.setAdapter(medalItemAdapter);
            }else{
                medalItemAdapter.notifyDataSetChanged();
            }
            if(list.size()<limit){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
        }else{
            showMsg(medal.getErrorMsg());
        }
    }

    @Override
    public void onRefresh(View view) {
        page=1;
        getData(HandlerConstant1.GET_MEDAL_LIST_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getData(HandlerConstant1.GET_MEDAL_LIST_SUCCESS2);
    }


    /**
     * 查询数据
     */
    private void getData(int index){
        showProgress(getString(R.string.loding));
        final UserBean userBean=MyApplication.userInfo.getData().getUser();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HttpMethod1.getMedalList(userBean.getUserId(), CollegeInfoFragment.homeBean.getCollegeId(),simpleDateFormat.format(new Date()),page,limit,index,mHandler);
    }
}
