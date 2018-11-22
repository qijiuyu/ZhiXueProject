package com.example.administrator.zhixueproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.login.LoginActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.SPUtil;

import org.json.JSONObject;

public class WelcomeActivity extends BaseActivity {

    private RelativeLayout relativeLayout;
    private Animation myAnimation_Alpha;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_welcome);
        getAccessToken();
        initView();
        initAnim();

    }

    private void initView() {
        relativeLayout=(RelativeLayout)findViewById(R.id.lin_start);
    }

    private void initAnim() {
        myAnimation_Alpha = new AlphaAnimation(0.1f, 1.0f);
        myAnimation_Alpha.setDuration(3000);
        myAnimation_Alpha.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(null== MyApplication.userInfo){
                    setClass(LoginActivity.class);
                }else{
                    setClass(TabActivity.class);
                }
                finish();
            }
        });
        relativeLayout.setAnimation(myAnimation_Alpha);
        myAnimation_Alpha.start();
    }

    private Handler mHandler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            if(msg.what== HandlerConstant1.AUTO_LOGIN_SUCCESS){
                final String message= (String) msg.obj;
                if(TextUtils.isEmpty(message)){
                    return true;
                }
                try {
                    final JSONObject jsonObject=new JSONObject(message);
                    if(jsonObject.getBoolean("status")){
                        final JSONObject jsonObject2=new JSONObject(jsonObject.getString("data"));
                        MyApplication.spUtil.addString(SPUtil.TOKEN,jsonObject2.getString("token"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return true;
        }
    });

    private void getAccessToken(){
        if(!TextUtils.isEmpty(MyApplication.spUtil.getString(SPUtil.TOKEN))){
            final UserBean userBean=MyApplication.userInfo.getData().getUser();
            HttpMethod1.autoLogin(userBean.getUserId(),mHandler);
        }
    }
}
