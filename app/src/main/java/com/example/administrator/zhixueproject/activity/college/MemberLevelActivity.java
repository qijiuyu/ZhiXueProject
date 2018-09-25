package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.MemberLevelAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.MemBerLevel;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.fragment.college.CollegeInfoFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;

import java.util.List;

/**
 * 会员等级设置
 */
public class MemberLevelActivity extends BaseActivity {

    private ListView listView;
    private List<MemBerLevel.MemberLevelList> list;
    private MemberLevelAdapter memberLevelAdapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_level);
        initView();
        getData();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.member_level_setting));
        listView=(ListView)findViewById(R.id.listView);
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                //查询会员等级信息
                case HandlerConstant1.SETTING_MEMBER_LEVEL_SUCCESS:
                     MemBerLevel memBerLevel= (MemBerLevel) msg.obj;
                     if(null==memBerLevel){
                         return;
                     }
                     if(memBerLevel.isStatus()){
                         list=memBerLevel.getData().getUserCollegeList();
                         memberLevelAdapter=new MemberLevelAdapter(mContext,list);
                         listView.setAdapter(memberLevelAdapter);
                     }else{
                         showMsg(memBerLevel.getErrorMsg());
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
     * 查询会员等级数据
     */
    private void getData(){
        showProgress(getString(R.string.loding));
        final UserBean userBean= MyApplication.userInfo.getData().getUser();
        HttpMethod1.settingMemberLevel(userBean.getUserId(), CollegeInfoFragment.homeBean.getCollegeId(),mHandler);
    }
}
