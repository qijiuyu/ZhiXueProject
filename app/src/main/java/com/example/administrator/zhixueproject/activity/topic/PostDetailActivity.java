package com.example.administrator.zhixueproject.activity.topic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.activity.TabActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.eventBus.PostEvent;
import com.example.administrator.zhixueproject.bean.topic.PostListBean;
import com.example.administrator.zhixueproject.bean.topic.PostsDetailsBean;
import com.example.administrator.zhixueproject.bean.topic.ReleaseContentsBean;
import com.example.administrator.zhixueproject.fragment.topic.PlaybackDialogFragment;
import com.example.administrator.zhixueproject.fragment.topic.PostsDetailsTaskFragment;
import com.example.administrator.zhixueproject.fragment.topic.WorksListDetailsFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.DateUtil;
import com.example.administrator.zhixueproject.utils.GlideCirclePictureUtil;
import com.example.administrator.zhixueproject.utils.KeyboardUtils;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;
import com.example.administrator.zhixueproject.utils.ToolUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 无偿帖子类型
 *
 * @author petergee
 * @date 2018/10/10
 */
public class PostDetailActivity extends BaseActivity implements View.OnClickListener {

    private int PAGE = 1;
    private String LIMIT = "10";
    private String TIMESTAMP = "";
    public String mFloorUserName;
    private String mFloorId;
    private PostListBean postListBean;
    private String postType;  // 1.课程 2.大家谈 3.有偿提问
    private String type = "1"; //1.回复帖子 2.回复作业
    public String mFloorUserId;
    private String commentUserId = MyApplication.userInfo.getData().getUser().getUserId() + "";
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
    private ImageView imgArrow;
    //分享渠道
    private SHARE_MEDIA share_media;
    // 帖子内容Str
    private String postContentString;
    private int mPosition; //用于标记是帖子还是作业fragment

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        initView();
        initData();
        //关闭帖子上的小红点
        sendBroadcast(new Intent(TabActivity.ACTION_CLEAR_NEW_NEWS));
        LogUtils.e("commentUserId=== " + commentUserId);
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
        imgArrow = (ImageView) findViewById(R.id.img_topic_arrow);
        imgArrow.setOnClickListener(this);


        /**
         * 分享功能
         */
        ImageView imgShare = (ImageView) findViewById(R.id.img_right);
        imgShare.setImageDrawable(getResources().getDrawable(R.mipmap.share_icon));
        imgShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.share_pop, null);
                dialogPop(view, true);
                view.findViewById(R.id.img_share_wx).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        closeDialog();
                        share_media = SHARE_MEDIA.WEIXIN;
                        startShare();
                    }
                });


                view.findViewById(R.id.img_share_wxp).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        closeDialog();
                        share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
                        startShare();
                    }
                });

            }
        });

    }


    /**
     * 分享
     */
    private void startShare() {
        UMImage image = new UMImage(this, R.mipmap.ic_launcher);
        int id = postListBean.getPostId();
        LogUtils.e("getPostId===" + id);
        UMWeb web = new UMWeb("http://zxw.yl-mall.cn/zhixue_c/Wxpay/aftershareskip.html?floorPostId=" + String.valueOf(id));
        web.setTitle(TextUtils.isEmpty(postListBean.getPostName())?"帖子标题":postListBean.getPostName());
        if (!TextUtils.isEmpty(postContentString)) {
            if (postContentString.length() >=25) {
                web.setDescription(postContentString.substring(0, 25));
            } else {
                web.setDescription(postContentString);
            }
        } else {
            web.setDescription("帖子详情");
        }

        web.setThumb(image);
        new ShareAction(this).setPlatform(share_media)
                .setCallback(umShareListener)
                .withMedia(web)
                .share();
    }


    private UMShareListener umShareListener = new UMShareListener() {
        public void onStart(SHARE_MEDIA share_media) {
        }

        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                showMsg(getString(R.string.collect_success));
            } else {
                showMsg(getString(R.string.share_success));
            }
        }

        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t.getMessage().indexOf("2008") != -1) {
                if (platform.name().equals("WEIXIN") || platform.name().equals("WEIXIN_CIRCLE")) {
                    showMsg(getString(R.string.share_failed_install_wechat));
                } else if (platform.name().equals("QQ") || platform.name().equals("QZONE")) {
                    showMsg(getString(R.string.share_failed_install_qq));
                }
            }
            showMsg(getString(R.string.share_failed));
        }

        public void onCancel(SHARE_MEDIA platform) {
            showMsg(getString(R.string.share_canceled));
        }
    };


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0) {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        }
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
                LogUtils.e("position=== " + position);
                mPosition=position;
                type = String.valueOf(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        searchTopicDetail();
        //添加浏览量
        HttpMethod2.updatePostColl(String.valueOf(postListBean.getPostId()), mHandler);
    }

    // type :1 帖子列表进入   2：会员详情页进入
    public static void start(Context context, PostListBean postListBean, int type) {
        Intent starter = new Intent(context, PostDetailActivity.class);
        if (type == 1) {
            starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        starter.putExtra("postListBean", postListBean);
        context.startActivity(starter);
    }

    /**
     * 查询帖子详情
     */
    private void searchTopicDetail() {
        TIMESTAMP = DateUtil.getTime();
        showProgress(getString(R.string.loading));
        HttpMethod2.getPostDetail(String.valueOf(postListBean.getPostId()), PAGE + "", LIMIT,
                TIMESTAMP, HandlerConstant2.GET_POST_DETAIL_SUCCESS, mHandler);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_comment:
                // 如果是作业并且不是老师身份return
                LogUtils.e("身份是："+MyApplication.homeBean.getAttendType());
                 if (mPosition==1){
                     if (MyApplication.homeBean.getAttendType()!=2){
                         showMsg("只有老师身份才能发布作业！");
                         return;
                     }
                 }
                tvComment.setVisibility(View.GONE);
                llComment.setVisibility(View.VISIBLE);
                isFloorComment = false;
                break;
            case R.id.tv_commit:
                commentPostOrWork();
                break;
            //编辑贴子
            case R.id.tv_right:
                String price;
                if (postType.equals("3")) {
                    price = postListBean.getPostReward();
                } else {
                    price = postListBean.getPostPrice();
                }
                ReleasePostActivity.start(
                        view.getContext(),
                        String.valueOf(postListBean.getPostId()),
                        postListBean.getPostName(),
                        String.valueOf(postListBean.getPostIsFree()),
                        price,
                        String.valueOf(postListBean.getPostIsTop()), postType);
                break;
            case R.id.img_topic_arrow:
                // 点击收起
                break;
            case R.id.lin_back:
                finish();
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
            HttpMethod2.commentPost(String.valueOf(postListBean.getPostId()), type, commentContent, mHandler);
        } else {
            //回复楼层
            HttpMethod2.commentReply(String.valueOf(postListBean.getPostId()), mFloorId, commentUserId
                    , mFloorUserId, commentContent, mHandler);
        }
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
                case HandlerConstant2.GET_POST_DETAIL_SUCCESS:
                    PostsDetailsBean detailsBean = (PostsDetailsBean) msg.obj;
                    if (null == detailsBean) {
                        return;
                    }
                    if (detailsBean.isStatus()) {
                        LogUtils.e("获取帖子详情成功");
                        postsDetailsSuccess(detailsBean);
                    } else {
                        showMsg(detailsBean.getErrorMsg());
                    }
                    break;
                case HandlerConstant2.UPDATE_POST_COLL_SUCCESS:
                    LogUtils.e("添加浏览量成功");
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    showMsg(getString(R.string.net_error));
                    break;
            }

        }
    };

    /**
     * 帖子详情请求成功
     *
     * @param postsDetailsBean
     */
    public void postsDetailsSuccess(PostsDetailsBean postsDetailsBean) {
        PostsDetailsBean.PostDetailBeanOuter data = postsDetailsBean.getData();
        GlideCirclePictureUtil.setCircleImg(this, data.getPostContent().getUserImg(), ivHead);
        tvNickName.setText(data.getPostContent().getUserName());
        tvAttentionNum.setText(data.getPostContent().getAttentionNum() + "");
        if (!TextUtils.isEmpty(data.getPostContent().getPostContent())) {
            imgArrow.setVisibility(View.VISIBLE);
        }
        //帖子内容
        showDetail(data.getPostContent().getPostContentApp());
    }


    private Map<Integer,String> pathMap=new HashMap<>();
    private Map<Integer,Integer> timeMap=new HashMap<>();
    private void showDetail(String postContent) {
        if (!TextUtils.isEmpty(postContent)) {
            StringBuffer stringBuffer = new StringBuffer();
            try {
                final JSONArray jsonArray = new JSONArray(postContent);
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //文字
                    if (jsonObject.getInt("type") == 0) {
                        postContentString = jsonObject.getString("content");
                        stringBuffer.append("<p>" + jsonObject.getString("content") + "</p>");
                    }
                    //图片
                    if (jsonObject.getInt("type") == 1) {
                        stringBuffer.append("<img src='http://" + jsonObject.getString("content") + "'/>");
                    }
                    //音频
                    if (jsonObject.getInt("type") == 2) {
                        pathMap.put(i,jsonObject.getString("content"));
                        timeMap.put(i,jsonObject.getInt("timeLength"));
                        stringBuffer.append("<img src='http://1x9x.cn/college/res/img/Audiorun.png' onClick='window.hello.playAutio("+i+")'/>" + "0:00/" + jsonObject.getString("strLength"));
                    }
                }
                //帖子内容
                String html = ToolUtils.imgStyleHtml(stringBuffer.toString());
                initWebView();
                wvPostContent.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return true;
                    }
                });
                wvPostContent.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        WebSettings webSettings = wvPostContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        wvPostContent.setHorizontalScrollBarEnabled(false);//水平不显示
        wvPostContent.setVerticalScrollBarEnabled(false);
        wvPostContent.getSettings().setDomStorageEnabled(true);
        wvPostContent.addJavascriptInterface(this, "hello");
    }


    @JavascriptInterface
    public void playAutio(final int index) {
        mHandler.post(new Runnable() {
            public void run() {
                ReleaseContentsBean releaseContentsBean = new ReleaseContentsBean(pathMap.get(index), 2, null, timeMap.get(index));
                PlaybackDialogFragment fragmentPlay = PlaybackDialogFragment.newInstance(releaseContentsBean);
                fragmentPlay.show(getSupportFragmentManager(), PlaybackDialogFragment.class.getSimpleName());
            }
        });
    }

    /**
     * 评论成功后初始化
     */
    private void initComment() {
        llComment.setVisibility(View.GONE);
        tvComment.setVisibility(View.VISIBLE);
        etCommentContent.setText("");
        mFloorUserId = "";
        isFloorComment = false;
        KeyboardUtils.hideKeyBoard(this, tvComment);
    }

    @Subscribe
    public void replyCommentEvent(PostEvent postEvent) {
        switch (postEvent.getEventType()) {
            // 回复帖子
            case PostEvent.REPLY_POST:
                PostsDetailsBean.PostDetailBeanOuter.PostCommentListBean postData = (PostsDetailsBean.PostDetailBeanOuter.PostCommentListBean) postEvent.getData();
                mFloorId = String.valueOf(postData.getFloorInfo().getFloorId());
                mFloorUserName = postData.getFloorInfo().getUserName();
                mFloorUserId = postData.getFloorInfo().getFloorUserId();
                initFloorComment(mFloorUserName);
                break;
            case PostEvent.REPLY_WORK:
                PostsDetailsBean.PostDetailBeanOuter.WorkCommentListBean workData = (PostsDetailsBean.PostDetailBeanOuter.WorkCommentListBean) postEvent.getData();
                mFloorId = String.valueOf(workData.getFloorInfo().getFloorId());
                mFloorUserName = workData.getFloorInfo().getUserName();
                mFloorUserId = workData.getFloorInfo().getFloorUserId();
                initFloorComment(mFloorUserName);
                break;
            case PostEvent.REPLY_WORK_COMMENT:
                String workDataSt = (String) postEvent.getData();
                String[] split = workDataSt.split("=");
                mFloorUserId = split[0];
                mFloorUserName = split[1];
                mFloorId = split[2];
                initFloorComment(mFloorUserName);
                break;
            case PostEvent.REPLY_POST_COMMENT:
                // 回复帖子楼层
                String postDataSt = (String) postEvent.getData();
                String[] split2 = postDataSt.split("=");
                mFloorUserId = split2[0];
                mFloorUserName = split2[1];
                mFloorId = split2[2];
                initFloorComment(mFloorUserName);
                break;
            case PostEvent.MODIFY_POST_SUCCESS:
                LogUtils.e("postDetail 收到修改帖子成功通知");
                // 修改帖子成功
                searchTopicDetail();
                break;
        }

    }

    private void initFloorComment(String mFloorUserName) {
        isFloorComment = true;
        llComment.setVisibility(View.VISIBLE);
        tvComment.setVisibility(View.GONE);
        etCommentContent.setHint("@" + mFloorUserName);
        etCommentContent.setText("");
    }
}
