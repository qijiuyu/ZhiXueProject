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
import com.example.administrator.zhixueproject.activity.college.BuyVipActivity;
import com.example.administrator.zhixueproject.adapter.college.ColleteVipAdapter;
import com.example.administrator.zhixueproject.bean.ColleteVips;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 帖子fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class BuyVipFragment extends BaseFragment{

    private ListView listView;
    private List<ColleteVips.ColleteVipsBean.collegeGradeListBean> list=new ArrayList<>();
    private ColleteVipAdapter colleteVipAdapter;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_college_vips, container, false);
        listView=(ListView)view.findViewById(R.id.listView);
        getVips();
        return view;
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            clearTask();
            switch (msg.what){
                case HandlerConstant1.GET_COLLETE_VIPS_SUCCESS:
                     ColleteVips colleteVips= (ColleteVips) msg.obj;
                     if(null==colleteVips){
                         return;
                     }
                     if(colleteVips.isStatus()){
                         list.clear();
                         list.addAll(colleteVips.getData().getCollegeGradeList());
                         colleteVipAdapter=new ColleteVipAdapter(getActivity(),list);
                         listView.setAdapter(colleteVipAdapter);
                         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                 ColleteVips.ColleteVipsBean.collegeGradeListBean collegeGradeListBean=list.get(position);
                                 Intent intent=new Intent(mActivity, BuyVipActivity.class);
                                 intent.putExtra("collegeGradeListBean",collegeGradeListBean);
                                 mActivity.startActivity(intent);
                             }
                         });
                     }else{
                         showMsg(colleteVips.getErrorMsg());
                     }
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
     * 查询学院vip等级
     */
    private void getVips(){
        if(isVisibleToUser && view!=null){
            showProgress("数据查询中");
            HttpMethod1.getCollegeVips(mHandler);
        }
    }


    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //查询数据
        getVips();
    }

}
