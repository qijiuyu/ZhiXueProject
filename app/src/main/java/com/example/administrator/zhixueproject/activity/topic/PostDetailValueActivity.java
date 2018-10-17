package com.example.administrator.zhixueproject.activity.topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.bean.topic.PostListBean;

/** 有偿帖子
 * @author petergee
 * @date 2018/10/10
 */
public class PostDetailValueActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_value);
    }

    public static void start(Context context, PostListBean postListBean) {
        Intent starter = new Intent(context, PostDetailValueActivity.class);
        starter.putExtra("postListBean", postListBean);
        context.startActivity(starter);
    }
}
