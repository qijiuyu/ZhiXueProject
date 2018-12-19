package com.example.administrator.zhixueproject.activity.topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.activity.TabActivity;
import com.example.administrator.zhixueproject.adapter.topic.PostsTaskAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.eventBus.PostEvent;
import com.example.administrator.zhixueproject.bean.topic.PostListBean;
import com.example.administrator.zhixueproject.bean.topic.PostsDetailsBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.DateUtil;
import com.example.administrator.zhixueproject.utils.GlideCirclePictureUtil;
import com.example.administrator.zhixueproject.utils.KeyboardUtils;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;
import com.example.administrator.zhixueproject.utils.ToolUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 有偿帖子
 *
 * @author petergee
 * @date 2018/10/10
 */
public class PostDetailValueActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    private String postType = "1";
    private String postId;
    private int PAGE = 1;
    private String LIMIT = "10";
    private String TIMESTAMP = "";
    private String commentUserId = MyApplication.userInfo.getData().getUser().getUserId() + "";
    private PostListBean postListBean;
    public PostsTaskAdapter mAdapter;
    private boolean isFloorComment;
    private String mFloorId;
    private String mFloorUserId;
    private String mFloorUserName;
    private ImageView ivHead;
    private TextView tvNickName;
    private TextView tvAttentionNum;
    private WebView wvPostContent;
    private TextView tvMoneyReward;
    private TextView tvPeepNum;
    private TextView tvComment;
    private EditText etCommonContent;
    private LinearLayout llComment;
    private RecyclerView rvPostTask;
    private ImageView imgArrow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_value);
        initView();
        initData();
        //关闭帖子上的小红点
        sendBroadcast(new Intent(TabActivity.ACTION_CLEAR_NEW_NEWS));
    }

    private void initView() {
        findViewById(R.id.lin_back).setOnClickListener(this);
        TextView tvRight = (TextView) findViewById(R.id.tv_right);
        tvRight.setBackground(getResources().getDrawable(R.mipmap.edit_iv));
        tvRight.setOnClickListener(this);
        ivHead = (ImageView) findViewById(R.id.iv_head);
        tvNickName = (TextView) findViewById(R.id.tv_nickname);
        tvAttentionNum = (TextView) findViewById(R.id.tv_attention_num);
        wvPostContent = (WebView) findViewById(R.id.wv_post_content);
        tvMoneyReward = (TextView) findViewById(R.id.tv_money_reward);
        tvPeepNum = (TextView) findViewById(R.id.tv_peep_num);
        tvComment = (TextView) findViewById(R.id.tv_comment);
        tvComment.setOnClickListener(this);
        etCommonContent = (EditText) findViewById(R.id.et_comment_content);
        llComment = (LinearLayout) findViewById(R.id.ll_comment);
        rvPostTask = (RecyclerView) findViewById(R.id.rv_posts_task);
        rvPostTask.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.tv_comment).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        imgArrow = (ImageView) findViewById(R.id.img_topic_arrow);
        imgArrow.setOnClickListener(this);
    }

    private void initData() {
        EventBus.getDefault().register(this);
        StatusBarUtils.transparencyBar(this);
        postListBean = (PostListBean) getIntent().getSerializableExtra("postListBean");
        postId = String.valueOf(postListBean.getPostId());
        searchYouChangDetail(HandlerConstant2.GET_YOU_CHANG_DETAIL_SUCCESS);
        //添加浏览量
        HttpMethod2.updatePostColl(String.valueOf(postListBean.getPostId()),mHandler);
    }

    private void searchYouChangDetail(int index) {
        TIMESTAMP= DateUtil.getTime();
        HttpMethod2.getYouChangDetail(postId, PAGE + "", LIMIT, TIMESTAMP, index, mHandler);
    }

    // type :1 帖子列表进入   2：会员详情页进入
    public static void start(Context context, PostListBean postListBean,int type) {
        Intent starter = new Intent(context, PostDetailValueActivity.class);
        if (type==1){
            starter.setFlags(FLAG_ACTIVITY_NEW_TASK);
        }
        starter.putExtra("postListBean", postListBean);
        context.startActivity(starter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            case R.id.tv_comment:
                tvComment.setVisibility(View.GONE);
                llComment.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_commit:
                commentPostOrWork();
                break;
            case R.id.tv_right:
                ReleasePostActivity.start(
                        view.getContext(),
                        String.valueOf(postListBean.getPostId()),
                        postListBean.getPostName(),
                        String.valueOf(postListBean.getPostIsFree()),
                        postListBean.getPostReward(),
                        String.valueOf(postListBean.getPostIsTop()), postType);
                break;
            case R.id.img_topic_arrow:
                // 收起箭头
                break;
            default:
                break;
        }
    }

    /**
     * 评论贴子
     */
    private void commentPostOrWork() {
        String commentContent = etCommonContent.getText().toString().trim();
        if (TextUtils.isEmpty(commentContent)) {
            showMsg("请输入评论内容");
            return;
        }

        //回复贴子
        if (!isFloorComment) {
            HttpMethod2.commentPost(String.valueOf(postListBean.getPostId()), postType, commentContent, mHandler);
        } else {
            //回复楼层
            HttpMethod2.commentReply(String.valueOf(postListBean.getPostId()), mFloorId, commentUserId
                   , mFloorUserId, commentContent, mHandler);
        }
    }

    /**
     * 评论成功后初始化
     */
    private void initComment() {
        llComment.setVisibility(View.GONE);
        tvComment.setVisibility(View.VISIBLE);
        etCommonContent.setText("");
        KeyboardUtils.hideKeyBoard(this, tvComment);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            BaseBean bean = null;
            switch (msg.what) {
                case HandlerConstant2.COMMENT_POST_SUCCESS:
                    bean = (BaseBean) msg.obj;
                    if (null == bean) {
                        return;
                    }
                    if (bean.isStatus()) {
                        LogUtils.e("评论帖子成功");
                        EventBus.getDefault().post(new PostEvent().setEventType(PostEvent.COMMENT_SUCCESS));
                        initComment();
                    } else {
                        showMsg(bean.getErrorMsg());
                    }
                    break;
                case HandlerConstant2.COMMENT_REPLY_SUCCESS:
                    bean = (BaseBean) msg.obj;
                    if (null == bean) {
                        return;
                    }
                    if (bean.isStatus()) {
                        LogUtils.e("回复楼层成功");
                        EventBus.getDefault().post(new PostEvent().setEventType(PostEvent.COMMENT_SUCCESS));
                        initComment();
                    } else {
                        showMsg(bean.getErrorMsg());
                    }
                    break;
                case HandlerConstant2.GET_YOU_CHANG_DETAIL_SUCCESS:
                    PostsDetailsBean detailsBean = (PostsDetailsBean) msg.obj;
                    if (detailsBean.isStatus()) {
                        postsDetailsSuccess(detailsBean);
                    }
                    break;
                case HandlerConstant2.UPDATE_POST_COLL_SUCCESS:
                        LogUtils.e("添加浏览量成功");
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    showMsg(getString(R.string.net_error));
                    break;
                default:
                    break;
            }
        }
    };

    public void postsDetailsSuccess(PostsDetailsBean postsDetailsBean) {
        PostsDetailsBean.PostDetailBeanOuter data = postsDetailsBean.getData();
        PostsDetailsBean.PostDetailBeanOuter.PostContentBean postContent = data.getPostContent();
        GlideCirclePictureUtil.setCircleImg(mContext,postContent.getUserImg(),ivHead);
        tvNickName.setText(postContent.getUserName());
        tvAttentionNum.setText(String.valueOf(postContent.getAttentionNum()));
        tvMoneyReward.setText("赏金" + postContent.getPostReward());
        tvPeepNum.setText(TextUtils.isEmpty(postContent.getPostPeepNum())?"0":postContent.getPostPeepNum());

        //评论区域
        mAdapter = new PostsTaskAdapter(R.layout.posts_task_item, data.getPostCommentList());
        if (data.getPostCommentList().size()==0){
            rvPostTask.setVisibility(View.GONE);
        }
        rvPostTask.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        /*//没有人偷看贴子。则不显示评论，
        if (data.isIsUpdated()) {

        }*/
        if (!TextUtils.isEmpty(data.getPostContent().getPostContent())){
            imgArrow.setVisibility(View.VISIBLE);
        }
        //帖子内容
        String html = ToolUtils.imgStyleHtml(postContent.getPostContent());
        wvPostContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        wvPostContent.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LogUtils.e("itemClick");
        isFloorComment = true;
        //回复楼层贴子
        PostsDetailsBean.PostDetailBeanOuter.PostCommentListBean item = (PostsDetailsBean.PostDetailBeanOuter.PostCommentListBean) adapter.getData().get(position);
        tvComment.setVisibility(View.GONE);
        llComment.setVisibility(View.VISIBLE);
        mFloorId = String.valueOf(item.getFloorInfo().getFloorId());
        mFloorUserId = item.getFloorInfo().getFloorUserId();

    }
    @Subscribe
    public void replyCommentEvent(PostEvent postEvent) {
        switch (postEvent.getEventType()) {
            case PostEvent.REPLY_POST_COMMENT:
                initFloorComment();
                String postDataSt = (String) postEvent.getData();
                String[] split2 = postDataSt.split("=");
                mFloorUserId = split2[0];
                mFloorUserName = split2[1];
                mFloorId = split2[2];
                break;
            case PostEvent.COMMENT_SUCCESS:
                PAGE = 1;
                searchYouChangDetail(HandlerConstant2.GET_YOU_CHANG_DETAIL_SUCCESS);
                break;
        }
    }

    private void initFloorComment() {
        isFloorComment = true;
        llComment.setVisibility(View.VISIBLE);
        tvComment.setVisibility(View.GONE);
        etCommonContent.setText("");
    }

}
