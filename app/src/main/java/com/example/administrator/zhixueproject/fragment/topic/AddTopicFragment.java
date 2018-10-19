package com.example.administrator.zhixueproject.fragment.topic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.adapter.topic.AddTopicAdapter;
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import com.example.administrator.zhixueproject.bean.topic.TopicsListBean;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import java.util.ArrayList;
import java.util.List;


/**
 * 添加所属话题弹窗
 */

public class AddTopicFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    private static String TIMESTAMP = System.currentTimeMillis() + "";
    private List<TopicListBean> listData = new ArrayList<>();
    private AddTopicAdapter mAddTopicAdapter;
    /**
     * 关闭话题列表弹窗的接口回调
     */
    private OnTopicListener mOnTopicListener;
    private int page = 1;
    private int limit = 100;
    private RecyclerView rvDecorationList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_decoration, container, false);
        view.setClickable(true);
        initView(view);
        return view;
    }

    private void initView(View view) {
        rvDecorationList = (RecyclerView) view.findViewById(R.id.rv_decoration_list);
        rvDecorationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.findViewById(R.id.bg_decoration).setOnClickListener(this);

        // 查询话题列表
        showProgress(getString(R.string.loading));
        HttpMethod2.getTopicList(TIMESTAMP, String.valueOf(page), String.valueOf(limit), HandlerConstant2.GET_TOPIC_LIST_SUCCESS, mHandler);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            TopicsListBean bean = (TopicsListBean) msg.obj;
            switch (msg.what) {
                case HandlerConstant2.GET_TOPIC_LIST_SUCCESS:
                    getDataSuccess(bean);
                    break;
            }
        }
    };

    /**
     * 加载数据
     */
    private void getDataSuccess(TopicsListBean bean) {
        listData.clear();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            TopicsListBean.DataBean dataBean = bean.getData();
            listData = dataBean.getTopicList();
            adapterView();
        } else {
            showMsg(bean.errorMsg);

        }
    }

    /**
     * 设置adapter 数据
     */
    private void adapterView() {
        mAddTopicAdapter = new AddTopicAdapter(R.layout.add_topic_item, listData);
        rvDecorationList.setAdapter(mAddTopicAdapter);
        mAddTopicAdapter.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        mOnTopicListener.topicListener(view, String.valueOf(listData.get(position).getTopicId()), listData.get(position).getTopicName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bg_decoration:
                mOnTopicListener.closeTopicListener(view);
                break;
            default:
                break;
        }
    }

    public interface OnTopicListener {
        void closeTopicListener(View view);

        void topicListener(View view, String topic, String topicName);
    }

    public void setOnTopicListener(OnTopicListener onTopicListener) {
        mOnTopicListener = onTopicListener;
    }
}