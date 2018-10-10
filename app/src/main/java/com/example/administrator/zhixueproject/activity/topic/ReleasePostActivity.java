package com.example.administrator.zhixueproject.activity.topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;

/** 发布帖子
 * @author petergee
 * @date 2018/10/10
 */
public class ReleasePostActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_post);
    }

    /**
     * 发布贴子界面跳转
     *
     * @param context
     * @param postType
     * @param postTopicId
     */
    public static void start(Context context, int postType, int postTopicId) {
        Intent starter = new Intent(context, ReleasePostActivity.class);
        starter.putExtra("postType", postType);
        starter.putExtra("postTopicId", postTopicId);
        context.startActivity(starter);
    }

}
