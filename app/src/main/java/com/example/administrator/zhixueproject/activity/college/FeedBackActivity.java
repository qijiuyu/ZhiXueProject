package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.college.FeedBackAdapter;
import com.example.administrator.zhixueproject.bean.FeedBack;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 意见反馈
 */
public class FeedBackActivity extends BaseActivity   implements MyRefreshLayoutListener ,View.OnClickListener{

    private LinearLayout linearLayout;
    private TextView tvRight,tvAll,tvRed,tvNoRed;
    private ImageView imgRight;
    private ListView listView;
    private MyRefreshLayout mRefreshLayout;
    private int page=1;
    private int limit=20;
    private List<FeedBack.FeedBackList> listAll=new ArrayList<>();
    private FeedBackAdapter feedBackAdapter;
    private String key="";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
        showProgress(getString(R.string.loding));
        getData(HandlerConstant1.GET_FEEDBACK_SUCCESS);
    }


    /**
     * 初始化控件
     */
    private void initView() {
        linearLayout=(LinearLayout)findViewById(R.id.lin);
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.feedback));
        tvRight=(TextView)findViewById(R.id.tv_right);
        tvRight.setText(getString(R.string.whole));
        imgRight=(ImageView)findViewById(R.id.img_right);
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageDrawable(getResources().getDrawable(R.mipmap.down_arrow_white));
        mRefreshLayout=(MyRefreshLayout)findViewById(R.id.re_list);
        listView=(ListView)findViewById(R.id.listView);
        listView.setDividerHeight(0);
        final View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        feedBackAdapter=new FeedBackAdapter(FeedBackActivity.this,listAll);
        listView.setAdapter(feedBackAdapter);
        findViewById(R.id.lin_right).setOnClickListener(this);
        findViewById(R.id.tv_feedback_platform).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //查询
            case R.id.lin_right:
                 if(linearLayout.getVisibility()==View.VISIBLE){
                     linearLayout.setVisibility(View.GONE);
                     imgRight.setImageDrawable(getResources().getDrawable(R.mipmap.down_arrow_white));
                 }else{
                     showSelectPop();
                 }
                 break;
            case R.id.tv_all:
                 updateTextView(0);
                 break;
            case R.id.tv_red:
                 updateTextView(1);
                 break;
            case R.id.tv_no_red:
                 updateTextView(2);
                 break;
            //添加反馈
            case R.id.tv_feedback_platform:
                 setClass(AddFeedBackActivity.class);
                 break;
            case R.id.lin_back:
                 finish();
                 break;
             default:
                 break;
        }
    }


    /**
     * 弹出选择框
     */
    private void showSelectPop(){
        imgRight.setImageDrawable(getResources().getDrawable(R.mipmap.up_arrow_white));
        View view= LayoutInflater.from(mContext).inflate(R.layout.feedback_select_pop,null);
        tvAll=(TextView)view.findViewById(R.id.tv_all);
        tvRed=(TextView)view.findViewById(R.id.tv_red);
        tvNoRed=(TextView)view.findViewById(R.id.tv_no_red);
        tvAll.setOnClickListener(this);
        tvRed.setOnClickListener(this);
        tvNoRed.setOnClickListener(this);
        switch (key){
            case "":
                 updateColor(0);
                 break;
            case "0":
                 updateColor(2);
                 break;
            case "1":
                 updateColor(1);
                 break;
        }
        linearLayout.removeAllViews();
        linearLayout.addView(view);
        linearLayout.setVisibility(View.VISIBLE);
    }


    /**
     * 修改选中的颜色
     * @param index
     */
    private void updateColor(int index){
        List<TextView> list=new ArrayList<>();
        list.add(tvAll);
        list.add(tvRed);
        list.add(tvNoRed);
        for (int i=0;i<list.size();i++){
             if(i==index){
                 list.get(i).setTextColor(getResources().getColor(R.color.color_fd703e));
                 list.get(i).setBackgroundColor(getResources().getColor(R.color.color_f8f8f8));
             }else{
                 list.get(i).setTextColor(getResources().getColor(R.color.color_666666));
                 list.get(i).setBackgroundColor(getResources().getColor(android.R.color.white));
             }
        }
    }


    /**
     * 选择后关闭弹框并查询数据
     * @param index
     */
    private void updateTextView(int index){
        linearLayout.setVisibility(View.GONE);
        imgRight.setImageDrawable(getResources().getDrawable(R.mipmap.down_arrow_white));
        switch (index){
            case 0:
                 tvRight.setText("全部");
                 key="";
                 break;
            case 1:
                 tvRight.setText("已读");
                 key="1";
                 break;
            case 2:
                 tvRight.setText("未读");
                 key="0";
                 break;
        }
        page=1;
        showProgress(getString(R.string.loding));
        getData(HandlerConstant1.GET_FEEDBACK_SUCCESS);
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            FeedBack feedBack;
            switch (msg.what){
                case HandlerConstant1.GET_FEEDBACK_SUCCESS:
                    mRefreshLayout.refreshComplete();
                    feedBack= (FeedBack) msg.obj;
                    listAll.clear();
                    refresh(feedBack);
                    break;
                case HandlerConstant1.GET_FEEDBACK_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    feedBack= (FeedBack) msg.obj;
                    refresh(feedBack);
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    showMsg(getString(R.string.net_error));
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 刷新数据
     * @param feedBack
     */
    private void refresh(FeedBack feedBack){
        if(null==feedBack){
            return;
        }
        if(feedBack.isStatus()){
            List<FeedBack.FeedBackList> list=feedBack.getData().getAdviceList();
            listAll.addAll(list);
            feedBackAdapter.notifyDataSetChanged();
            if(list.size()<limit){
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
        }else{
            showMsg(feedBack.getErrorMsg());
        }
    }



    @Override
    public void onRefresh(View view) {
        page=1;
        getData(HandlerConstant1.GET_FEEDBACK_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getData(HandlerConstant1.GET_FEEDBACK_SUCCESS2);
    }


    /**
     * 查询数据
     * @param index
     */
    private void getData(int index){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HttpMethod1.getFeedBack(key,page,limit,simpleDateFormat.format(new Date()),index,mHandler);
    }

}
