package com.example.administrator.zhixueproject.activity.topic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.live.SelectLecturersAdapter;
import com.example.administrator.zhixueproject.bean.live.TeacherListBean;
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
    private String C="702";
    private String CollegeId="58";
    // private SelectLecturersP selectLecturersP;
    private String key ="";
    private MyRefreshLayout mrlLectureList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lectuer);
        initView();
    }

    private void initView() {
        LinearLayout linBack= (LinearLayout) findViewById(R.id.lin_back);
        linBack.setOnClickListener(this);
        StatusBarUtils.transparencyBar(this);
        setTitle(getResources().getString(R.string.select_lecturer));
        ImageView ivBack= (ImageView) findViewById(R.id.iv_back);
        ivBack.setImageResource(R.mipmap.down_arrow_big_white);
        mrlLectureList = (MyRefreshLayout) findViewById(R.id.mrl_lecturer_list);
        RecyclerView rvLecturerList=(RecyclerView) findViewById(R.id.rv_lecturer_list);
        mAdapter = new SelectLecturersAdapter(R.layout.select_lecturer_item, listData);
        rvLecturerList.setAdapter(mAdapter);
        rvLecturerList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(this);
        mrlLectureList.setMyRefreshLayoutListener(this);//刷新加载
        final EditText etSearch= (EditText) findViewById(R.id.et_search);
        //搜索
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    InputMethodUtils.hideInputMethod(v);
                    key = etSearch.getText().toString().trim();
                    PAGE = 1;
                    // selectLecturersP.getTeacherList(selectLecturersP.REFRESH,C,CollegeId,TIMESTAMP,PAGE+"",LIMIT,key);
                    return true;
                }
                return false;
            }
        });
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

    @Override
    public void onRefresh(View view) {
        PAGE = 1;
        // selectLecturersP.getTeacherList(selectLecturersP.REFRESH,C,CollegeId,TIMESTAMP,PAGE+"",LIMIT,"");

    }

    @Override
    public void onLoadMore(View view) {
        PAGE++;
        // selectLecturersP.getTeacherList(selectLecturersP.LOAD,C,CollegeId,TIMESTAMP,PAGE+"",LIMIT,"");

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent();
        intent.putExtra(TEACHER_INFO, listData.get(position));
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.alpha, R.anim.activity_bottom_out);
    }

}
