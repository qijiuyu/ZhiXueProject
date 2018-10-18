package com.example.administrator.zhixueproject.activity.college;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.college.MedalItemAdapter;
import com.example.administrator.zhixueproject.adapter.college.SelectTeacherAdapter;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.Medal;
import com.example.administrator.zhixueproject.bean.Teacher;
import com.example.administrator.zhixueproject.bean.TeacherBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *选择讲师
 * Created by Administrator on 2018/9/30.
 */

public class SelectTeacherActivity extends BaseActivity   implements MyRefreshLayoutListener ,TextView.OnEditorActionListener {

    private EditText etKey;
    private List<Teacher> listAll=new ArrayList<>();
    private ListView listView;
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private SelectTeacherAdapter selectTeacherAdapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_teacher);
        initView();
        getData(HandlerConstant1.GET_TEACHER_LIST_SUCCESS);
    }


    /**
     * 初始化
     */
    private void initView(){
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.select_lecturer));
        etKey=(EditText)findViewById(R.id.et_search);
        mRefreshLayout=(MyRefreshLayout)findViewById(R.id.re_list);
        listView=(ListView)findViewById(R.id.listView);
        etKey.setOnEditorActionListener(this);
        listView.setDividerHeight(0);
        final View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        selectTeacherAdapter=new SelectTeacherAdapter(this,listAll);
        listView.setAdapter(selectTeacherAdapter);
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            TeacherBean teacherBean;
            switch (msg.what){
                case HandlerConstant1.GET_TEACHER_LIST_SUCCESS:
                    mRefreshLayout.refreshComplete();
                    teacherBean= (TeacherBean) msg.obj;
                    listAll.clear();
                    selectTeacherAdapter.notifyDataSetChanged();
                    refresh(teacherBean);
                    break;
                case HandlerConstant1.GET_TEACHER_LIST_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    teacherBean= (TeacherBean) msg.obj;
                    refresh(teacherBean);
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
     * @param teacherBean
     */
    private void refresh(TeacherBean teacherBean){
        if(null==teacherBean){
            return;
        }
        if(teacherBean.isStatus()){
            List<Teacher> list=teacherBean.getData().getTeacherList();
            listAll.addAll(list);
            selectTeacherAdapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Teacher teacher=listAll.get(position);
                    Intent intent=new Intent();
                    intent.putExtra("teacher",teacher);
                    setResult(1,intent);
                    finish();
                    overridePendingTransition(R.anim.alpha, R.anim.activity_bottom_out);
                }
            });
            if(list.size()<limit){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
        }else{
            showMsg(teacherBean.getErrorMsg());
        }
    }


    /**
     * 搜索键触发事件
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH){
            //隐藏软键盘
            lockKey(etKey);
            if(TextUtils.isEmpty(etKey.getText().toString().trim())){
                showMsg("请输入要搜索的关键字！");
                return false;
            }
            page=1;
            showProgress(getString(R.string.loding));
            getData(HandlerConstant1.GET_TEACHER_LIST_SUCCESS);
            return true;
        }
        return false;
    }


    @Override
    public void onRefresh(View view) {
        page=1;
        getData(HandlerConstant1.GET_TEACHER_LIST_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getData(HandlerConstant1.GET_TEACHER_LIST_SUCCESS2);
    }


    /**
     * 查询数据
     */
    private void getData(int index){
        final String keys=etKey.getText().toString().trim();
        HttpMethod1.getTeacherList(keys,page,limit,index,mHandler);
    }
}
