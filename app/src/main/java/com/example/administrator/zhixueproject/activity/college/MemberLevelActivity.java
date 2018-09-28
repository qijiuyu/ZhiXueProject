package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.MemberLevelAdapter;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.MemBerLevel;
import com.example.administrator.zhixueproject.callback.MemberLevelCallBack;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;

import java.util.List;

/**
 * 会员等级设置
 */
public class MemberLevelActivity extends BaseActivity implements View.OnClickListener{

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
        findViewById(R.id.tv_save).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //保存
            case R.id.tv_save:
                 StringBuilder levelId = new StringBuilder();
                 StringBuilder levelName = new StringBuilder();
                 StringBuilder levelPoint = new StringBuilder();
                 for (int i = 0; i < list.size(); i++) {
                    levelId.append(list.get(i).getUserCollegegradeId());
                    levelId.append(",");
                    levelName.append(list.get(i).getUserCollegegradeName());
                    levelName.append(",");
                    levelPoint.append(list.get(i).getUserCollegegradePoints());
                    levelPoint.append(",");
                 }
                 String levelIds = levelId.substring(0, levelId.length() - 1);
                 String levelNames = levelName.substring(0, levelName.length() - 1);
                 String levelPoints = levelPoint.substring(0, levelPoint.length() - 1);
                 showProgress(getString(R.string.loding));
                 HttpMethod1.saveVipGrade(levelIds,levelNames,levelPoints,mHandler);
                 break;
            case R.id.lin_back:
                 finish();
                 break;
             default:
                 break;
        }
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
                         memberLevelAdapter.setCallBack(memberLevelCallBack);
                     }else{
                         showMsg(memBerLevel.getErrorMsg());
                     }
                     break;
                 //保存会员等级信息
                case HandlerConstant1.SAVE_VIP_GRADE_SUCCESS:
                     BaseBean baseBean= (BaseBean) msg.obj;
                     if(null==baseBean){
                         return;
                     }
                     if(baseBean.isStatus()){
                         showMsg("保存成功");
                     }else{
                         showMsg(baseBean.getErrorMsg());
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


    MemberLevelCallBack memberLevelCallBack=new MemberLevelCallBack() {
        @Override
        public void setData(int position, int data) {

        }
    };

    /**
     * 查询会员等级数据
     */
    private void getData(){
        showProgress(getString(R.string.loding));
        HttpMethod1.settingMemberLevel(mHandler);
    }

}
