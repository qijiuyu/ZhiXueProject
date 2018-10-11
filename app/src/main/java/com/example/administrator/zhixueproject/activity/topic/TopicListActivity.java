package com.example.administrator.zhixueproject.activity.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.topic.TopicListAdapter;
import com.example.administrator.zhixueproject.fragment.topic.PostsCourseFragment;
import com.example.administrator.zhixueproject.utils.InputMethodUtils;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;
import com.example.administrator.zhixueproject.view.CustomPopWindow;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

/**
 * 话题列表
 *
 * @author PeterGee
 * @date 2018/10/8
 */
public class TopicListActivity extends BaseActivity implements View.OnClickListener {

    private int type = 1;
    private String searchContent;
    private PostsCourseFragment postsFragment1;
    private PostsCourseFragment postsFragment2;
    private PostsCourseFragment postsFragment3;
    private int postTopicId;
    private SlidingTabLayout tabs;
    private ViewPager vpContent;
    private EditText etSearch;
    private LinearLayout llRelease;
    private LinearLayout llPostSearch;
    private ImageView ivSearch;
    private CustomPopWindow mCustomPopWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_item);
        initView();
    }

    private void initView() {
        StatusBarUtils.transparencyBar(this);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title_topic);
        tvTitle.setText(getString(R.string.posts_list));
        tabs = (SlidingTabLayout) findViewById(R.id.tab_posts);
        vpContent = (ViewPager) findViewById(R.id.vp_content);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        llPostSearch = (LinearLayout) findViewById(R.id.ll_post_search);
        llRelease = (LinearLayout) findViewById(R.id.ll_release);
        llRelease.setOnClickListener(this);
        etSearch = (EditText) findViewById(R.id.et_search);

        findViewById(R.id.rl_back).setOnClickListener(this);
        findViewById(R.id.rl_search).setOnClickListener(this);
        findViewById(R.id.iv_close).setOnClickListener(this);

        postTopicId = getIntent().getIntExtra(TopicListAdapter.TOPIC_ITEM_ID, postTopicId);
        String[] titles = {"课程", "大家谈", "有偿提问"};
        ArrayList<Fragment> fragmentList = new ArrayList<>();

        postsFragment1 = new PostsCourseFragment();
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
        fragmentList.add(postsFragment3);

        tabs.setViewPager(vpContent, titles, this, fragmentList);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        vpContent.setOffscreenPageLimit(3);

        //编辑框，软键盘搜索按钮监听
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodUtils.hideInputMethod(v);//隐藏软键盘
                    searchRecord();
                    return true;
                }
                return false;
            }
        });

    }


    public void searchRecord() {
        searchContent = etSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(searchContent)) {
            if (type == 1) {
                postsFragment1.setKey(searchContent);
                postsFragment1.startRequest(1);
            } else if (type == 2) {
                postsFragment2.setKey(searchContent);
                postsFragment2.startRequest(2);
            } else if (type == 3) {
                postsFragment3.setKey(searchContent);
                postsFragment3.startRequest(3);
            }

        } else {
            showMsg("请输入搜索内容");
            return;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_release:
                showPopWindow(type);
                break;
            case R.id.rl_search:
                ivSearch.setVisibility(View.GONE);
                llPostSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_close:
                etSearch.setText("");
                ivSearch.setVisibility(View.VISIBLE);
                llPostSearch.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void showPopWindow(int type) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
        handleLogic(contentView, type);
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(false)
                .setBgDarkAlpha(0.7f)
                .enableOutsideTouchableDissmiss(true)
                .setAnimationStyle(R.style.AnimUp)
                .create();
        mCustomPopWindow.showAsDropDown(llRelease, 0, -(llRelease.getHeight() + mCustomPopWindow.getHeight()) - 30);

    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView, final int type) {
        LinearLayout menu2 = (LinearLayout) contentView.findViewById(R.id.menu2);
        LinearLayout menu3 = (LinearLayout) contentView.findViewById(R.id.menu3);

        if (type == 3) {
            menu2.setVisibility(View.GONE);
            menu3.setVisibility(View.GONE);
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.menu1:// 帖子
                        ReleasePostActivity.start(TopicListActivity.this, type, postTopicId);
                        break;
                    case R.id.menu2:// 活动
                        ReleaseActionActivity.start(TopicListActivity.this, null);
                        break;
                    case R.id.menu3:// 投票
                        ReleaseVoteActivity.start(TopicListActivity.this, null);
                        break;
                }
            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
    }


}
