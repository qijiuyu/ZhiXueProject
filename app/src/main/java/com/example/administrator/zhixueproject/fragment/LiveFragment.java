package com.example.administrator.zhixueproject.fragment;

import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.TabActivity;
import com.example.administrator.zhixueproject.activity.college.CollegeManageActivity;
import com.example.administrator.zhixueproject.activity.live.AddLiveActivity;
import com.example.administrator.zhixueproject.adapter.live.LiveListAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.bean.live.Live;
import com.example.administrator.zhixueproject.callback.LiveCallBack;
import com.example.administrator.zhixueproject.fragment.college.CollegeInfoFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.view.CircleImageView;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 直播fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class LiveFragment extends BaseFragment  implements MyRefreshLayoutListener,View.OnClickListener{

    private CircleImageView imgHead;
    private TextView tvHead;
    private ListView listView;
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private LiveListAdapter liveListAdapter;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    private List<Live.LiveList> listAll=new ArrayList<>();
    //直播id
    private long postId;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    View view=null;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_live, container, false);
        imgHead=(CircleImageView)view.findViewById(R.id.img_fc_head);
        imgHead.setOnClickListener(this);
        tvHead=(TextView)view.findViewById(R.id.tv_head);
        mRefreshLayout=(MyRefreshLayout)view.findViewById(R.id.re_list);
        view.findViewById(R.id.iv_college).setOnClickListener(this);
        view.findViewById(R.id.ll_release).setOnClickListener(this);
        listView=(ListView)view.findViewById(R.id.listView);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        liveListAdapter=new LiveListAdapter(mActivity,listAll);
        listView.setAdapter(liveListAdapter);
        liveListAdapter.setCallBack(liveCallBack);
        //查询举报数据
        getData(HandlerConstant1.GET_LIVE_LIST_SUCCESS);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击头像
            case R.id.img_fc_head:
                TabActivity.openLeft();
                break;
            //点击设置
            case R.id.iv_college:
                setClass(CollegeManageActivity.class);
                break;
             //发布
            case R.id.ll_release:
                 Intent intent=new Intent(mActivity,AddLiveActivity.class);
                 startActivityForResult(intent,1);
                 break;
            default:
                break;
        }
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            Live live;
            switch (msg.what){
                case HandlerConstant1.GET_LIVE_LIST_SUCCESS:
                    mRefreshLayout.refreshComplete();
                    live= (Live) msg.obj;
                    listAll.clear();
                    refresh(live);
                    break;
                case HandlerConstant1.GET_LIVE_LIST_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    live= (Live) msg.obj;
                    refresh(live);
                    break;
                //删除直播预告
                case HandlerConstant1.DELETE_LIVE_SUCEESSS:
                     final BaseBean baseBean= (BaseBean) msg.obj;
                     if(null==baseBean){
                        return;
                     }
                     if(baseBean.isStatus()){
                        for (int i=0;i<listAll.size();i++){
                             if(postId==listAll.get(i).getPostId()){
                                 listAll.remove(i);
                                 break;
                             }
                        }
                        liveListAdapter.notifyDataSetChanged();
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
     * @param live
     */
    private void refresh(Live live){
        if(null==live){
            return;
        }
        if(live.isStatus()){
            List<Live.LiveList> list=live.getData().getPostLiveList();
            listAll.addAll(list);
            liveListAdapter.notifyDataSetChanged();
            if(list.size()<limit){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
        }else{
            showMsg(live.getErrorMsg());
        }
    }


    @Override
    public void onRefresh(View view) {
        page=1;
        HttpMethod1.getLiveList(page,limit,HandlerConstant1.GET_LIVE_LIST_SUCCESS,mHandler);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        HttpMethod1.getLiveList(page,limit,HandlerConstant1.GET_LIVE_LIST_SUCCESS2,mHandler);
    }


    /**
     * 查询数据
     * @param index
     */
    private void getData(int index){
        if(isVisibleToUser && view!=null && listAll.size()==0){
            HttpMethod1.getLiveList(page,limit,index,mHandler);
        }
    }



    private LiveCallBack liveCallBack=new LiveCallBack() {
       //删除直播
        public void deleteLive(long postId) {
            LiveFragment.this.postId=postId;
            showProgress("删除数据中...");
            HttpMethod1.deleteLive(postId,mHandler);
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            page=1;
            HttpMethod1.getLiveList(page,limit,HandlerConstant1.GET_LIVE_LIST_SUCCESS,mHandler);
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //查询举报数据
        getData(HandlerConstant1.GET_LIVE_LIST_SUCCESS);
    }


    @Override
    public void onResume() {
        super.onResume();
        final UserBean userBean= MyApplication.userInfo.getData().getUser();
        Glide.with(mActivity).load(userBean.getUserImg()).override(30,30).error(R.mipmap.head_bg).into(imgHead);
        tvHead.setText(CollegeInfoFragment.homeBean.getCollegeName());
    }

}
