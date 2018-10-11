package com.example.administrator.zhixueproject.activity.topic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.live.SelectLecturersAdapter;
import com.example.administrator.zhixueproject.adapter.topic.TopicListAdapter;
import com.example.administrator.zhixueproject.bean.live.SelectLecturersBean;
import com.example.administrator.zhixueproject.bean.live.TeacherListBean;
import com.example.administrator.zhixueproject.bean.topic.TopicsListBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.InputMethodUtils;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;

import java.util.ArrayList;
import java.util.List;

/** 选择讲师
 * @author PeterGee
 * @date 2018/10/10
 */
public class SelectLecturersActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener, MyRefreshLayoutListener {
    public static final int REQUEST_CODE = 91;
    public static final String TEACHER_INFO="teacher_info";
    private SelectLecturersAdapter mAdapter;
    private List<TeacherListBean> listData = new ArrayList<>();
    private int PAGE=1;
    private String LIMIT = "999";
    private String TIMESTAMP="";
    private String CollegeId="58";
    private String key ="";
    private MyRefreshLayout mrlLectureList;
    private RecyclerView rvLecturerList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lectuer);
        initView();
    }

    private void initView() {
        LinearLayout linBack= (LinearLayout) findViewById(R.id.lin_back);
        linBack.setOnClickListener(this);
        TextView tvTitle= (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getResources().getString(R.string.select_lecturer));
        StatusBarUtils.transparencyBar(this);
        ImageView ivBack= (ImageView) findViewById(R.id.iv_back);
        ivBack.setImageResource(R.mipmap.down_arrow_big_white);
        mrlLectureList = (MyRefreshLayout) findViewById(R.id.mrl_lecturer_list);
        rvLecturerList = (RecyclerView) findViewById(R.id.rv_lecturer_list);
        final EditText etSearch= (EditText) findViewById(R.id.et_search);
        //搜索
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    InputMethodUtils.hideInputMethod(v);
                    key = etSearch.getText().toString().trim();
                    PAGE = 1;
                    getLecturersList(HandlerConstant2.GET_LECTURERS_LIST_SUCCESS1);
                    return true;
                }
                return false;
            }
        });
        // 查询讲师列表
        getLecturersList(HandlerConstant2.GET_LECTURERS_LIST_SUCCESS1);
    }
    public static void start(Context context) {
        Intent starter = new Intent(context, SelectLecturersActivity.class);
        ((Activity)context).startActivityForResult(starter, REQUEST_CODE);
        ((Activity)context).overridePendingTransition(R.anim.activity_bottom_in, R.anim.alpha);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lin_back:
                finish();
                overridePendingTransition(R.anim.alpha, R.anim.activity_bottom_out);
                break;
        }
    }

    /**
     * 查询讲师列表
     *
     * @param index handler消息
     */
    private void getLecturersList(int index) {
        showProgress(getString(R.string.loading));
        HttpMethod2.getLecturersList(CollegeId,key,PAGE+"",LIMIT,TIMESTAMP,index,mHandler);
    }

    @Override
    public void onRefresh(View view) {
        PAGE = 1;
        getLecturersList(HandlerConstant2.GET_LECTURERS_LIST_SUCCESS1);

    }

    @Override
    public void onLoadMore(View view) {
        PAGE++;
        getLecturersList(HandlerConstant2.GET_LECTURERS_LIST_SUCCESS2);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent();
        intent.putExtra(TEACHER_INFO, listData.get(position));
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.alpha, R.anim.activity_bottom_out);
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            SelectLecturersBean bean= (SelectLecturersBean) msg.obj;
            switch (msg.what){
                case HandlerConstant2.GET_LECTURERS_LIST_SUCCESS1:
                    getDataSuccess(bean);
                    break;
                case HandlerConstant2.GET_LECTURERS_LIST_SUCCESS2:
                    loadMoreSuccess(bean);
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    requestError();
                    break;
            }
        }
    };


    /**
     * 加载数据
     */
    private void getDataSuccess(SelectLecturersBean bean) {
        mrlLectureList.refreshComplete();
        listData.clear();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            SelectLecturersBean.DataBean dataBean = bean.getData();
            listData = dataBean.getTeacherList();
            adapterView();
        } else {
            showMsg(bean.errorMsg);

        }
    }

    /**
     * 加载更多
     */
    private void loadMoreSuccess(SelectLecturersBean bean) {
        mrlLectureList.loadMoreComplete();
        if (null == bean) {
            return;
        }
        if (bean.isStatus()) {
            SelectLecturersBean.DataBean dataBean = bean.getData();
            if (dataBean.getTeacherList().size() <= 0) {
                showMsg(getResources().getString(R.string.no_more_data));
                return;
            }
            listData.addAll(dataBean.getTeacherList());
            adapterView();
        } else {
            showMsg(bean.errorMsg);
        }
    }

    /**
     * 加载失败
     */
    private void requestError() {
        mrlLectureList.refreshComplete();
        mrlLectureList.loadMoreComplete();
        showMsg(getString(R.string.load_failed));
    }

    /**
     * 设置adapter 数据
     */
    private void adapterView() {
        mAdapter = new SelectLecturersAdapter(R.layout.select_lecturer_item, listData);
        rvLecturerList.setAdapter(mAdapter);
        rvLecturerList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(this);
        mrlLectureList.setMyRefreshLayoutListener(this);//刷新加载
    }
}
