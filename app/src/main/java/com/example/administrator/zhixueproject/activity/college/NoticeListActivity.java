package com.example.administrator.zhixueproject.activity.college;

import android.content.Intent;
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
import com.example.administrator.zhixueproject.adapter.college.NoticeListAdapter;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.Notice;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 公告编辑
 */
public class NoticeListActivity extends BaseActivity  implements MyRefreshLayoutListener {

    private ListView listView;
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private List<Notice.NoticeList> listAll=new ArrayList<>();
    private NoticeListAdapter noticeListAdapter;
    //公告id
    private long noticeId;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_in);
        initView();
        showProgress(getString(R.string.loding));
        getData(HandlerConstant1.GET_NOTICE_LIST_SUCCESS);
    }


    /**
     * 初始化控件
     */
    private void initView() {
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.announcement_edit));
        TextView tvRight=(TextView)findViewById(R.id.tv_right);
        tvRight.setText(getString(R.string.new_add));
        mRefreshLayout=(MyRefreshLayout)findViewById(R.id.re_list);
        listView=(ListView)findViewById(R.id.listView);
        final View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        noticeListAdapter=new NoticeListAdapter(NoticeListActivity.this,listAll);
        listView.setAdapter(noticeListAdapter);
        //新增
        tvRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(mContext,AddNoticeActivity.class);
                startActivityForResult(intent,1);
            }
        });
        //返回
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NoticeListActivity.this.finish();
            }
        });
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            Notice notice;
            switch (msg.what){
                case HandlerConstant1.GET_NOTICE_LIST_SUCCESS:
                    mRefreshLayout.refreshComplete();
                    notice= (Notice) msg.obj;
                    listAll.clear();
                    refresh(notice);
                    break;
                case HandlerConstant1.GET_NOTICE_LIST_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    notice= (Notice) msg.obj;
                    refresh(notice);
                    break;
                //删除
                case HandlerConstant1.DELETE_NOTICE_SUCCESS:
                    BaseBean baseBean= (BaseBean) msg.obj;
                    if(null==baseBean){
                        return;
                    }
                    if(baseBean.isStatus()){
                        for (int i=0;i<listAll.size();i++){
                            if(noticeId==listAll.get(i).getNoticeId()){
                                listAll.remove(i);
                                break;
                            }
                        }
                        noticeListAdapter.notifyDataSetChanged();
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
     * @param notice
     */
    private void refresh(Notice notice){
        if(null==notice){
            return;
        }
        if(notice.isStatus()){
            List<Notice.NoticeList> list=notice.getData().getNoticeList();
            listAll.addAll(list);
            noticeListAdapter.notifyDataSetChanged();
            if(list.size()<limit){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
        }else{
            showMsg(notice.getErrorMsg());
        }
    }


    /**
     * 删除
     */
    public void deleteNotice(long noticeId){
        this.noticeId=noticeId;
        showProgress(getString(R.string.loding));
        HttpMethod1.deleteNotice(noticeId,mHandler);
    }

    @Override
    public void onRefresh(View view) {
        page=1;
        getData(HandlerConstant1.GET_NOTICE_LIST_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getData(HandlerConstant1.GET_NOTICE_LIST_SUCCESS2);
    }


    /**
     * 查询数据
     * @param index
     */
    private void getData(int index){
        HttpMethod1.getNoticeList(page,limit,index,mHandler);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            page=1;
            getData(HandlerConstant1.GET_NOTICE_LIST_SUCCESS);
        }
        if(resultCode==2){
            Notice.NoticeList noticeList= (Notice.NoticeList) data.getSerializableExtra("noticeList");
            if(null==noticeList){
                return;
            }
            for (int i=0;i<listAll.size();i++){
                if(noticeList.getNoticeId()==listAll.get(i).getNoticeId()){
                    listAll.remove(i);
                    listAll.add(i,noticeList);
                    break;
                }
            }
            noticeListAdapter.notifyDataSetChanged();
        }
    }
}
