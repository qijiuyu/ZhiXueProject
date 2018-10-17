package com.example.administrator.zhixueproject.fragment.college;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.adapter.college.TopicNameAdapter;
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
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private List<TopicListBean> listAll=new ArrayList<>();
    private TopicNameAdapter topicNameAdapter;
    private TopicListBean topicListBean;
    public static final String ACTION_TOPIC_TITLE="com.admin.broadcast.action.topic.title";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topic_list, container, false);
        mRefreshLayout=(MyRefreshLayout)view.findViewById(R.id.re_list);
        listView=(ListView)view.findViewById(R.id.listView);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        topicNameAdapter=new TopicNameAdapter(mActivity,listAll);
        listView.setAdapter(topicNameAdapter);
        //确定
        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(ACTION_TOPIC_TITLE);
                intent.putExtra("topicListBean",topicListBean);
                mActivity.sendBroadcast(intent);
            }
        });
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
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    topicListBean=listAll.get(position);
                    topicNameAdapter.setIndex(position);
                    topicNameAdapter.notifyDataSetChanged();
                }
            });
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
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HttpMethod2.getTopicList(simpleDateFormat.format(new Date()),page+"",limit+"",index,mHandler);
    }

}
