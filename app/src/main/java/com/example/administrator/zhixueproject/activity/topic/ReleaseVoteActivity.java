package com.example.administrator.zhixueproject.activity.topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.bean.topic.VoteListBean;

/** 发布投票
 * @author petergee
 * @date 2018/10/10
 */
public class ReleaseVoteActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_vote);
    }

    public static void start(Context context, VoteListBean voteListBean) {
        Intent starter = new Intent(context, ReleaseVoteActivity.class);
        starter.putExtra("voteListBean", voteListBean);
        context.startActivity(starter);
    }
}
