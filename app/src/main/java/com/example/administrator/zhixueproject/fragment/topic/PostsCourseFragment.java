package com.example.administrator.zhixueproject.fragment.topic;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.topic.PostDetailActivity;
import com.example.administrator.zhixueproject.activity.topic.PostDetailValueActivity;
import com.example.administrator.zhixueproject.adapter.topic.PostsCourseAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.eventBus.PostEvent;
import com.example.administrator.zhixueproject.bean.topic.PostListBean;
import com.example.administrator.zhixueproject.bean.topic.PostsCourseBean;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.DateUtil;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.view.DividerItemDecoration;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;

/**
 * 帖子课程列表
 */

public class PostsCourseFragment extends BaseFragment implements MyRefreshLayoutListener, BaseQuickAdapter.OnItemClickListener {
    private PostsCourseAdapter mAdapter;
    private List<PostListBean> listData = new ArrayList<>();
    private int postType;//1 课程   2 大家谈  3 有偿提问
    private String key = "";
    private int PAGE = 1;
    private String LIMIT = "10";
    private String TIMESTAMP = "";
    private String postTopicId;
    private String type = "1"; //1 帖子列表  2 搜索
    private int sType;
    private MyRefreshLayout mrlPostsCourse;
    private RecyclerView rvPostsCourse;
    private Context mContext=MyApplication.application;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.posts_course_fm, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mrlPostsCourse = (MyRefreshLayout) view.findViewById(R.id.mrl_posts_course);
        rvPostsCourse = (RecyclerView) view.findViewById(R.id.rv_posts_course);
        rvPostsCourse.setLayoutManager(new LinearLayoutManager(MyApplication.application));
        key = TextUtils.isEmpty(getKey()) ? "" : getKey();
        postType = getPostType();
        // 查询帖子
        getPostList(HandlerConstant2.GET_POST_LIST_SUCCESS1);
    }


    public int getPostType() {
        return postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public void setPostTopicId(String postTopicId) {
        this.postTopicId = postTopicId;
    }

    /**
     * 搜索关键字
     *
     * @return
     */
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 搜索请求
     *
     * @param
     */
    public void startRequest(int searchType) {
        PAGE = 1;
        TIMESTAMP = "";
        type = "2";
        sType = searchType;
        showProgress(getString(R.string.loading));
        getPostList(HandlerConstant2.GET_POST_LIST_SEARCH_SUCCESS1);
    }

    @Override
    public void onRefresh(View view) {
        PAGE = 1;
        type = "1";
        getPostList(HandlerConstant2.GET_POST_LIST_SUCCESS1);
    }

    /**
     * 查询数据
     *
     * @param index
     */
    public void getPostList(int index) {
        TIMESTAMP= DateUtil.getTime();
        showProgress(getString(R.string.loading));
        HttpMethod2.getPostList(type, postType + "", postTopicId, key, PAGE + "", LIMIT, TIMESTAMP, index, mHandler);
    }

    @Override
    public void onLoadMore(View view) {
        PAGE++;
        if ("1".equals(type)) {
            // 帖子列表刷新
            getPostList(HandlerConstant2.GET_POST_LIST_SUCCESS2);
        } else if ("2".equals(type)) {
            // 搜索刷新
            getPostList(HandlerConstant2.GET_POST_LIST_SEARCH_SUCCESS2);
        }
    }

    @Subscribe
    public void postEvent(PostEvent postEvent) {
        if (PostEvent.RELEASE_SUCCESS == postEvent.getEventType()) {
            PAGE = 1;
            type = "1";
            getPostList(HandlerConstant2.GET_POST_LIST_SUCCESS1);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        int postType = listData.get(position).getPostType();
        if (postType == 1 || postType == 2) {
            LogUtils.e("免费帖子");
            PostDetailActivity.start(mContext,listData.get(position));
        } else if (postType == 3) {
            LogUtils.e("付费帖子");
            PostDetailValueActivity.start(mContext,listData.get(position));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(mContext);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            PostsCourseBean bean = (PostsCourseBean) msg.obj;
            switch (msg.what) {
                case HandlerConstant2.GET_POST_LIST_SUCCESS1:
                    getDataSuccess(bean);
                    break;
                case HandlerConstant2.GET_POST_LIST_SUCCESS2:
                    loadMoreSuccess(bean);
                    break;
                case HandlerConstant2.GET_POST_LIST_SEARCH_SUCCESS1:
                    getDataSuccess(bean);
                    break;
                case HandlerConstant2.GET_POST_LIST_SEARCH_SUCCESS2:
                    loadMoreSuccess(bean);
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    requestError();
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 加载数据
     */
    private void getDataSuccess(PostsCourseBean bean) {
        mrlPostsCourse.refreshComplete();
        listData.clear();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            PostsCourseBean.DataBean dataBean = bean.getData();
            listData = dataBean.getPostList();
            adapterView();
        } else {
            showMsg(bean.errorMsg);

        }
    }

    /**
     * 加载更多
     */
    private void loadMoreSuccess(PostsCourseBean bean) {
        mrlPostsCourse.loadMoreComplete();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            PostsCourseBean.DataBean dataBean = bean.getData();
            if (dataBean.getPostList().size() <= 0) {
                showMsg(getResources().getString(R.string.no_more_data));
                return;
            }
            listData.addAll(dataBean.getPostList());
            adapterView();
        } else {
            showMsg(bean.errorMsg);
        }
    }

    /**
     * 加载失败
     */
    private void requestError() {
        mrlPostsCourse.refreshComplete();
        mrlPostsCourse.loadMoreComplete();
        showMsg(getString(R.string.net_error));
    }

    /**
     * 设置adapter 数据
     */
    private void adapterView() {
        mAdapter = new PostsCourseAdapter(R.layout.posts_list_item, listData);
        rvPostsCourse.setAdapter(mAdapter);
        //条目点击
        mAdapter.setOnItemClickListener(this);
        //添加分隔线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_activity_line, LinearLayoutManager.VERTICAL);
        rvPostsCourse.addItemDecoration(itemDecoration);
        //刷新加载
        mrlPostsCourse.setMyRefreshLayoutListener(this);
    }

}
