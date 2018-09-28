package com.example.administrator.zhixueproject.fragment.college;

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
import com.example.administrator.zhixueproject.adapter.college.TopicNameAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import com.example.administrator.zhixueproject.bean.topic.TopicsListBean;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 话题列表
 */
public class TopicListFragment  extends BaseFragment  implements MyRefreshLayoutListener {

    private ListView listView;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private List<TopicListBean> listAll=new ArrayList<>();
    private TopicNameAdapter topicNameAdapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topic_list, container, false);
        mRefreshLayout=(MyRefreshLayout)view.findViewById(R.id.re_list);
        listView=(ListView)view.findViewById(R.id.listView);
        final View view = LayoutInflater.from(mActivity).inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        topicNameAdapter=new TopicNameAdapter(mActivity,listAll);
        listView.setAdapter(topicNameAdapter);
        //查询话题列表
        getData(HandlerConstant2.GET_TOPIC_LIST_SUCCESS);
        return view;
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            TopicsListBean topicsListBean;
            switch (msg.what){
                case HandlerConstant2.GET_TOPIC_LIST_SUCCESS:
                    mRefreshLayout.refreshComplete();
                    topicsListBean= (TopicsListBean) msg.obj;
                    listAll.clear();
                    refresh(topicsListBean);
                    break;
                case HandlerConstant2.GET_TOPIC_LIST_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    topicsListBean= (TopicsListBean) msg.obj;
                    refresh(topicsListBean);
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
     * @param topicsListBean
     */
    private void refresh(TopicsListBean topicsListBean){
        if(null==topicsListBean){
            return;
        }
        if(topicsListBean.isStatus()){
            List<TopicListBean> list=topicsListBean.getData().getTopicList();
            listAll.addAll(list);
            topicNameAdapter.notifyDataSetChanged();
            if(list.size()<limit){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
        }else{
            showMsg(topicsListBean.getErrorMsg());
        }
    }


    @Override
    public void onRefresh(View view) {
        page=1;
        getData(HandlerConstant2.GET_TOPIC_LIST_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getData(HandlerConstant2.GET_TOPIC_LIST_SUCCESS2);
    }



    /**
     * 查询话题列表
     */
    private void getData(int index){
        if(isVisibleToUser && view!=null && listAll.size()==0){
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HttpMethod2.getTopicList(simpleDateFormat.format(new Date()),page+"",limit+"",index,mHandler);
        }
    }


    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //查询话题列表
        getData(HandlerConstant2.GET_TOPIC_LIST_SUCCESS);
    }
}