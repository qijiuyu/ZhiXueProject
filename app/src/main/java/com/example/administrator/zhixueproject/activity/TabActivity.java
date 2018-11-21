package com.example.administrator.zhixueproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.fragment.InvitationFragment;
import com.example.administrator.zhixueproject.fragment.LiveFragment;
import com.example.administrator.zhixueproject.fragment.PersonalManagerFragment;
import com.example.administrator.zhixueproject.fragment.TopicFragment;
import com.example.administrator.zhixueproject.fragment.college.CollegeFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.ActivitysLifecycle;
import com.example.administrator.zhixueproject.utils.SPUtil;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends android.app.TabActivity implements View.OnClickListener{

    // 按两次退出
    protected long exitTime = 0;
    private TabHost tabHost;
    private ImageView imgCollege,imgTopic,imgZhibo,imgHuati,imgRen;
    private TextView tvCollege,tvTopic,tvZhibo,tvHuati,tvRen;
    private List<ImageView> imgList=new ArrayList<>();
    private List<TextView> tvList=new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.transparencyBar(this);
        setContentView(R.layout.activity_tag);
        initView();
        updateType();
    }


    private void initView(){
        imgCollege=(ImageView)findViewById(R.id.img_tab_college);
        tvCollege=(TextView)findViewById(R.id.tv_tab_college);
        imgTopic=(ImageView)findViewById(R.id.img_tab_topic);
        tvTopic=(TextView)findViewById(R.id.tv_tab_topic);
        imgZhibo=(ImageView)findViewById(R.id.img_tab_zhibo);
        tvZhibo=(TextView)findViewById(R.id.tv_tab_zhibo);
        imgHuati=(ImageView)findViewById(R.id.img_tab_huati);
        tvHuati=(TextView)findViewById(R.id.tv_tab_huati);
        imgRen=(ImageView)findViewById(R.id.img_tab_ren);
        tvRen=(TextView)findViewById(R.id.tv_tab_ren);
        imgList.add(imgCollege);imgList.add(imgTopic);imgList.add(imgZhibo);imgList.add(imgHuati);imgList.add(imgRen);
        tvList.add(tvCollege);tvList.add(tvTopic);tvList.add(tvZhibo);tvList.add(tvHuati);tvList.add(tvRen);
        findViewById(R.id.lin_tab_college).setOnClickListener(this);
        findViewById(R.id.lin_tab_topic).setOnClickListener(this);
        findViewById(R.id.lin_tab_zhibo).setOnClickListener(this);
        findViewById(R.id.lin_tab_huati).setOnClickListener(this);
        findViewById(R.id.lin_tab_ren).setOnClickListener(this);

        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        if(MyApplication.userInfo.getType()==1){
            spec=tabHost.newTabSpec("学院").setIndicator("学院").setContent(new Intent(this, CollegeFragment.class));
            tabHost.addTab(spec);
            spec=tabHost.newTabSpec("帖子").setIndicator("帖子").setContent(new Intent(this, InvitationFragment.class));
            tabHost.addTab(spec);
        }else{
            spec=tabHost.newTabSpec("帖子").setIndicator("帖子").setContent(new Intent(this, InvitationFragment.class));
            tabHost.addTab(spec);
            spec=tabHost.newTabSpec("学院").setIndicator("学院").setContent(new Intent(this, CollegeFragment.class));
            tabHost.addTab(spec);
        }
        spec=tabHost.newTabSpec("直播预告").setIndicator("直播预告").setContent(new Intent(this, LiveFragment.class));
        tabHost.addTab(spec);
        spec=tabHost.newTabSpec("话题管理").setIndicator("话题管理").setContent(new Intent(this, TopicFragment.class));
        tabHost.addTab(spec);
        spec=tabHost.newTabSpec("人员管理").setIndicator("人员管理").setContent(new Intent(this, PersonalManagerFragment.class));
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);
    }

    private void updateType(){
        if(MyApplication.userInfo.getType()==2){
            imgCollege.setImageDrawable(getResources().getDrawable(R.mipmap.tab_2_false));
            tvCollege.setText("帖子");

            imgTopic.setImageDrawable(getResources().getDrawable(R.mipmap.tab_1_false));
            tvTopic.setText("学院");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_tab_college:
                if(MyApplication.userInfo.getType()==1){
                    tabHost.setCurrentTabByTag("学院");
                }else{
                    tabHost.setCurrentTabByTag("帖子");
                }
                 break;
            case R.id.lin_tab_topic:
                if(MyApplication.userInfo.getType()==1){
                    tabHost.setCurrentTabByTag("帖子");
                }else{
                    tabHost.setCurrentTabByTag("学院");
                }
                 break;
            case R.id.lin_tab_zhibo:
                tabHost.setCurrentTabByTag("直播预告");
                 break;
            case R.id.lin_tab_huati:
                tabHost.setCurrentTabByTag("话题管理");
                 break;
            case R.id.lin_tab_ren:
                tabHost.setCurrentTabByTag("人员管理");
                 break;
             default:
                 break;
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                //获取个人资料
                case HandlerConstant1.GET_USER_INFO_SUCCESS:
                    final String message=msg.obj.toString();
                    if(TextUtils.isEmpty(message)){
                        return;
                    }
                    try {
                        final JSONObject jsonObject=new JSONObject(message);
                        if(jsonObject.getBoolean("status")){
                            final UserBean userBean= MyApplication.gson.fromJson(jsonObject.getString("data"),UserBean.class);
                            if(null==userBean){
                                return;
                            }
                            MyApplication.userInfo.getData().setUser(userBean);
                            MyApplication.spUtil.addString(SPUtil.USER_INFO,MyApplication.gson.toJson(MyApplication.userInfo));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };


    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN ) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(),"再按一次退出程序!",Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivitysLifecycle.getInstance().exit();
            }
            return false;
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //获取个人信息
        HttpMethod1.getUserInfo(mHandler);
    }

}
