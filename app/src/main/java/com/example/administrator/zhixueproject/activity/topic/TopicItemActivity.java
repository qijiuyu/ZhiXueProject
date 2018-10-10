package com.example.administrator.zhixueproject.activity.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.topic.TopicListAdapter;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;

import java.util.ArrayList;

/**
 * 话题条目
 * @author PeterGee
 * @date 2018/10/8
 */
public class TopicItemActivity extends BaseActivity {

    private int type = 1;
    private String searchContent;
    //    private PostsCourseFragment postsFragment;
    // private PostsCourseFragment postsFragment1;
    // private PostsCourseFragment postsFragment2;
    // private PostsCourseFragment postsFragment3;
    private int postTopicId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_item);
        initView();
    }

    private void initView() {
        StatusBarUtils.transparencyBar(this);
        TextView tvTitle= (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getResources().getString(R.string.posts_list));

        postTopicId = getIntent().getIntExtra(TopicListAdapter.TOPIC_ITEM_ID, postTopicId);

        String[] strings = {"课程", "大家谈", "有偿提问"};
        ArrayList<Fragment> fragmentList = new ArrayList<>();

       /* postsFragment1 = new PostsCourseFragment();
        postsFragment1.setPostType(1);
        postsFragment1.setPostTopicId(String.valueOf(postTopicId));
        fragmentList.add(postsFragment1);

        postsFragment2 = new PostsCourseFragment();
        postsFragment2.setPostType(2);
        postsFragment2.setPostTopicId(String.valueOf(postTopicId));
        fragmentList.add(postsFragment2);

        postsFragment3 = new PostsCourseFragment();
        postsFragment3.setPostType(3);
        postsFragment3.setPostTopicId(String.valueOf(postTopicId));
        fragmentList.add(postsFragment3);*/


       /* tab_posts.setViewPager(vp_content, strings, this, fragmentList);

        vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                type = position + 1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_content.setOffscreenPageLimit(3);*/
    }
}
