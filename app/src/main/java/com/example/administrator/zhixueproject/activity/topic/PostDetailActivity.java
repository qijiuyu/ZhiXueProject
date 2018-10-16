package com.example.administrator.zhixueproject.activity.topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.bean.eventBus.PostEvent;
import com.example.administrator.zhixueproject.bean.topic.PostListBean;
import com.example.administrator.zhixueproject.bean.topic.PostsDetailsBean;
import com.example.administrator.zhixueproject.fragment.topic.PostsDetailsTaskFragment;
import com.example.administrator.zhixueproject.fragment.topic.WorksListDetailsFragment;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;
import com.flyco.tablayout.SlidingTabLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;

/**
 * 无偿帖子类型
 *
 * @author petergee
 * @date 2018/10/10
 */
public class PostDetailActivity extends BaseActivity implements View.OnClickListener {

    private boolean isShow = false;
    // private PostsDetailsP postsDetailsP;
    private int PAGE = 1;
    private String LIMIT = "10";
    private String TIMESTAMP = "";
    private PostsDetailsBean contentBean;
    // public CommentPostP mCommentPostP;
    // private ReplyCommentP mReplyCommentP;
    public String mFloorUserName;
    private String mFloorId;
    private PostListBean postListBean;
    private String postType;
    public String mFloorUserId;
    private boolean isFloorComment;
    private TextView tvComment;
    private EditText etCommentContent;
    private LinearLayout llComment;
    private ImageView ivHead;
    private TextView tvNickName;
    private TextView tvAttentionNum;
    private WebView wvPostContent;
    private SlidingTabLayout tabPostsDetail;
    private ViewPager vpContent;
    private LinearLayout llPostDetailHead;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.lin_back).setOnClickListener(this);
        TextView tvRight = (TextView) findViewById(R.id.tv_right);
        tvRight.setBackground(getResources().getDrawable(R.mipmap.edit_iv));
        tvRight.setOnClickListener(this);
        tvComment = (TextView) findViewById(R.id.tv_comment);
        tvComment.setOnClickListener(this);
        etCommentContent = (EditText) findViewById(R.id.et_comment_content);
        llComment = (LinearLayout) findViewById(R.id.ll_comment);
        llComment.setOnClickListener(this);
        ivHead = (ImageView) findViewById(R.id.iv_head);
        tvNickName = (TextView) findViewById(R.id.tv_nickname);
        tvAttentionNum = (TextView) findViewById(R.id.tv_attention_num);
        wvPostContent = (WebView) findViewById(R.id.wv_post_content);
        tabPostsDetail = (SlidingTabLayout) findViewById(R.id.tab_posts_details);
        vpContent = (ViewPager) findViewById(R.id.vp_content);
        llPostDetailHead = (LinearLayout) findViewById(R.id.ll_post_details_head);
        findViewById(R.id.tv_commit).setOnClickListener(this);

    }

    private void initData() {
        EventBus.getDefault().register(this);
        StatusBarUtils.transparencyBar(this);
        postListBean = (PostListBean) getIntent().getSerializableExtra("postListBean");
        postType = String.valueOf(postListBean.getPostType());
        //设置头部随着滚动
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) llPostDetailHead.getLayoutParams();
        layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);

        String[] strings = {"讨论", "作业"};
        ArrayList<Fragment> fragmentList = new ArrayList<>();

        PostsDetailsTaskFragment postsFragment1 = new PostsDetailsTaskFragment();
        postsFragment1.setType(1);
        postsFragment1.setPostId(String.valueOf(postListBean.getPostId()));
        fragmentList.add(postsFragment1);

        WorksListDetailsFragment postsFragment2 = new WorksListDetailsFragment();
        postsFragment2.setType(2);
        postsFragment2.setPostId(String.valueOf(postListBean.getPostId()));
        fragmentList.add(postsFragment2);


        tabPostsDetail.setViewPager(vpContent, strings, this, fragmentList);

        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                postType = String.valueOf(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static void start(Context context, PostListBean postListBean) {
        Intent starter = new Intent(context, PostDetailActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        starter.putExtra("postListBean", postListBean);
        context.startActivity(starter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_comment:
                tvComment.setVisibility(View.GONE);
                llComment.setVisibility(View.VISIBLE);
                isFloorComment = false;
                break;
            case R.id.tv_commit:
                commentPostOrWork();
                break;
            //编辑贴子
            case R.id.tv_right:
                ReleasePostActivity.start(
                        view.getContext(),
                        String.valueOf(postListBean.getPostId()),
                        postListBean.getPostName(),
                        String.valueOf(postListBean.getPostIsFree()),
                        postListBean.getPostReward(),
                        String.valueOf(postListBean.getPostIsTop()), postType);
                break;
            default:
                break;
        }
    }

    /**
     * 评论贴子
     */
    private void commentPostOrWork() {
        String commentContent = etCommentContent.getText().toString().trim();
        if (TextUtils.isEmpty(commentContent)) {
            showMsg("请输入评论内容");
            return;
        }

        //回复贴子
        if (!isFloorComment) {
            // mCommentPostP.setCommentPost(String.valueOf(postListBean.getPostId()), postType, commentContent);
        } else {
            //回复楼层
          /*  mReplyCommentP.setReplyComment(String.valueOf(postListBean.getPostId())
                    , mFloorId, application.getC(), mFloorUserId, commentContent);*/
        }
    }
    @Subscribe
    public void replyCommentEvent(PostEvent postEvent) {

        switch (postEvent.getEventType()) {
            case PostEvent.REPLY_POST:

                initFloorComment();

                PostsDetailsBean.PostCommentListBean postData = (PostsDetailsBean.PostCommentListBean) postEvent.getData();
                mFloorId = String.valueOf(postData.getFloorInfo().getFloorId());
                mFloorUserName = postData.getFloorInfo().getUserName();
                mFloorUserId = postData.getFloorInfo().getFloorUserId();
                break;

            case PostEvent.REPLY_WORK:
                initFloorComment();
                PostsDetailsBean.WorkCommentListBean workData = (PostsDetailsBean.WorkCommentListBean) postEvent.getData();
                mFloorId = String.valueOf(workData.getFloorInfo().getFloorId());
                mFloorUserName = workData.getFloorInfo().getUserName();
                mFloorUserId = workData.getFloorInfo().getFloorUserId();
                break;

            case PostEvent.REPLY_WORK_COMMENT:
                initFloorComment();
                String workDataSt = (String) postEvent.getData();
                String[] split = workDataSt.split("=");
                mFloorUserId = split[0];
                mFloorUserName = split[1];
                mFloorId = split[2];
                break;

            case PostEvent.REPLY_POST_COMMENT:
                initFloorComment();
                String postDataSt = (String) postEvent.getData();
                String[] split2 = postDataSt.split("=");
                mFloorUserId = split2[0];
                mFloorUserName = split2[1];
                mFloorId = split2[2];
                break;
        }

    }

    private void initFloorComment() {
        isFloorComment = true;
        llComment.setVisibility(View.VISIBLE);
        tvComment.setVisibility(View.GONE);
        etCommentContent.setText("");
    }

}
