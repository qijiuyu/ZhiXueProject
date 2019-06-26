package com.example.administrator.zhixueproject.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.fragment.InvitationFragment;
import com.example.administrator.zhixueproject.fragment.LeftFragment;
import com.example.administrator.zhixueproject.fragment.LiveFragment;
import com.example.administrator.zhixueproject.fragment.PersonalManagerFragment;
import com.example.administrator.zhixueproject.fragment.TopicFragment;
import com.example.administrator.zhixueproject.fragment.college.CollegeFragment;
import com.example.administrator.zhixueproject.utils.ActivitysLifecycle;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;
import com.example.administrator.zhixueproject.utils.UpdateVersionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class TabActivity extends android.app.TabActivity implements View.OnClickListener{

    // 按两次退出
    protected long exitTime = 0;
    private TabHost tabHost;
    private ImageView imgCollege,imgTopic,imgZhibo,imgHuati,imgRen;
    private TextView tvCollege,tvTopic,tvZhibo,tvHuati,tvRen;
    private List<ImageView> imgList=new ArrayList<>();
    private List<TextView> tvList=new ArrayList<>();
    private int[] notClick=new int[]{R.mipmap.tab_1_false,R.mipmap.tab_2_false,R.mipmap.tab_3_false,R.mipmap.tab_4_false,R.mipmap.tab_5_false};
    private int[] yesClick=new int[]{R.mipmap.tab_1_true,R.mipmap.tab_2_true,R.mipmap.tab_3_true,R.mipmap.tab_4_true,R.mipmap.tab_5_true};
    private ImageView imgRed;
    public static final String ACTION_SHOW_NEW_NEWS="com.zhixue.project.action.show.new.news";
    public static final String ACTION_CLEAR_NEW_NEWS="com.zhixue.project.action.clear.new.news";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.transparencyBar(this);
        setContentView(R.layout.activity_tag);
        initView();
        //注册广播
        registerBoradcastReceiver();
        //设置推送
        setPush();
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
        imgRed=(ImageView)findViewById(R.id.img_red);
        imgList.add(imgCollege);imgList.add(imgTopic);imgList.add(imgZhibo);imgList.add(imgHuati);imgList.add(imgRen);
        tvList.add(tvCollege);tvList.add(tvTopic);tvList.add(tvZhibo);tvList.add(tvHuati);tvList.add(tvRen);
        findViewById(R.id.lin_tab_college).setOnClickListener(this);
        findViewById(R.id.lin_tab_topic).setOnClickListener(this);
        findViewById(R.id.lin_tab_zhibo).setOnClickListener(this);
        findViewById(R.id.lin_tab_huati).setOnClickListener(this);
        findViewById(R.id.lin_tab_ren).setOnClickListener(this);

        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        spec=tabHost.newTabSpec("学院").setIndicator("学院").setContent(new Intent(this, CollegeFragment.class));
        tabHost.addTab(spec);
        spec=tabHost.newTabSpec("帖子").setIndicator("帖子").setContent(new Intent(this, InvitationFragment.class));
        tabHost.addTab(spec);
        spec=tabHost.newTabSpec("直播预告").setIndicator("直播预告").setContent(new Intent(this, LiveFragment.class));
        tabHost.addTab(spec);
        spec=tabHost.newTabSpec("话题管理").setIndicator("话题管理").setContent(new Intent(this, TopicFragment.class));
        tabHost.addTab(spec);
        spec=tabHost.newTabSpec("人员管理").setIndicator("人员管理").setContent(new Intent(this, PersonalManagerFragment.class));
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_tab_college:
                updateImg(0);
                tabHost.setCurrentTabByTag("学院");
                 break;
            case R.id.lin_tab_topic:
                updateImg(1);
                tabHost.setCurrentTabByTag("帖子");
                 break;
            case R.id.lin_tab_zhibo:
                updateImg(2);
                tabHost.setCurrentTabByTag("直播预告");
                 break;
            case R.id.lin_tab_huati:
                updateImg(3);
                tabHost.setCurrentTabByTag("话题管理");
                 break;
            case R.id.lin_tab_ren:
                updateImg(4);
                tabHost.setCurrentTabByTag("人员管理");
                 break;
             default:
                 break;
        }
    }


    private void updateImg(int type){
        for(int i=0;i<5;i++){
            if(i==type){
                imgList.get(i).setImageDrawable(getResources().getDrawable(yesClick[i]));
                tvList.get(i).setTextColor(getResources().getColor(R.color.color_48c6ef));
            }else{
                imgList.get(i).setImageDrawable(getResources().getDrawable(notClick[i]));
                tvList.get(i).setTextColor(getResources().getColor(R.color.color_91dcf5));
            }
        }
    }


    /**
     * 注册广播
     */
    private void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_SHOW_NEW_NEWS);
        myIntentFilter.addAction(ACTION_CLEAR_NEW_NEWS);
        myIntentFilter.addAction(LeftFragment.GET_COLLEGE_DETAILS);
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(LeftFragment.GET_COLLEGE_DETAILS)) {
                updateImg(0);
                tabHost.setCurrentTabByTag("学院");
            }
            switch (intent.getAction()){
                case ACTION_SHOW_NEW_NEWS:
                     imgRed.setVisibility(View.VISIBLE);
                     break;
                case ACTION_CLEAR_NEW_NEWS:
                     imgRed.setVisibility(View.GONE);
                     break;
                default:
                    break;
            }
        }
    };


    private boolean isFrist=false;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && !isFrist){
            isFrist=true;
            //查询最新版本
            new UpdateVersionUtils().searchVersion(TabActivity.this);
        }
    }


    /**
     * 设置推送
     */
    private void setPush(){
        //设置极光推送的别名
        Set<String> tags = new HashSet<>();
        tags.add(MyApplication.userInfo.getData().getUser().getUserId()+"");
        JPushInterface.setAliasAndTags(getApplicationContext(), MyApplication.userInfo.getData().getUser().getUserId()+"", tags, mAliasCallback);
    }


    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Set<String> tags = new HashSet<>();
                            tags.add(MyApplication.userInfo.getData().getUser().getUserId()+"");
                            JPushInterface.setAliasAndTags(getApplicationContext(), MyApplication.userInfo.getData().getUser().getUserId()+"", tags, mAliasCallback);
                        }
                    },60000);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
            LogUtils.e(logs);
        }
    };


    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN ) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(),"再按一次退出程序!",Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                //关闭广播
                 unregisterReceiver(mBroadcastReceiver);
                ActivitysLifecycle.getInstance().exit();
            }
            return false;
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
