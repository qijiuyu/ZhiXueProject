package com.example.administrator.zhixueproject.activity.topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.bean.topic.ActivityListBean;

/**发布活动
 * @author petergee
 * @date 2018/10/10
 */
public class ReleaseActionActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_action);
    }

    public static void start(Context context, ActivityListBean activityListBean) {
        Intent starter = new Intent(context, ReleaseActionActivity.class);
        starter.putExtra("activityListBean", activityListBean);
        context.startActivity(starter);
    }
}
