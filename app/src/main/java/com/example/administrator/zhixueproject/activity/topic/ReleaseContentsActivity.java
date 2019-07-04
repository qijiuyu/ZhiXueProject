package com.example.administrator.zhixueproject.activity.topic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.topic.ReleaseContentsAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UploadFile;
import com.example.administrator.zhixueproject.bean.eventBus.PostEvent;
import com.example.administrator.zhixueproject.bean.topic.ReleaseContentsBean;
import com.example.administrator.zhixueproject.fragment.topic.PlaybackDialogFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.HttpConstant;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.AddImageUtils;
import com.example.administrator.zhixueproject.utils.FileStorage;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.PopIco;
import com.example.administrator.zhixueproject.utils.SoftInputUtils;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;
import com.example.administrator.zhixueproject.utils.record.RecordUtil;
import com.example.administrator.zhixueproject.utils.record.VoiceManager;
import com.example.administrator.zhixueproject.view.CustomPopWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;

/**
 * 发布内容
 *
 * @author petergee
 * @date 2018/10/11
 */
public class ReleaseContentsActivity extends BaseActivity implements View.OnClickListener, EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {
    private List<ReleaseContentsBean> listData = new ArrayList<>();//发布内容的Json数据
    private ReleaseContentsAdapter mAdapter;
    private boolean mIsFocus;
    private CustomPopWindow recordPopWindow;
    private VoiceManager voiceManager;
    private TextView tv_time_length;
    private String postType;//帖子类型
    private String postTopicId;
    private String postIsTop = "0";
    private String postWriterId;
    private String postIsFree; //1免费  2付费
    private String postName;
    private String postPrice;
    private String topicImg;
    private String startTime;
    private String endTime;
    private String activityId;
    private PopIco popIco;
    private Uri mOutputUri;
    private int fileType;
    private long voiceLength = 0;
    public File mFileCamera;
    public File mVoiceFile;
    private String voiceStrLength;
    private String postId;
    private EmojiconEditText etContent;
    private RecyclerView rvReleaseContent;
    private LinearLayout llContent;
    private ImageView ivPicture;
    private LinearLayout llReleaseContents;
    private FrameLayout flEmoji;
    private LinearLayout llRelease;
    private String topicID;
    private String voteName;
    private String topicType;
    private String voteIsTop;
    private String voteWriterId;
    private String voteSecNames;
    private Boolean isMultipleChoice;
    private String postContentApp;
    //是否在录音
    private boolean isRecord = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_content);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        StatusBarUtils.transparencyBar(this);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.release_content));
        findViewById(R.id.lin_back).setOnClickListener(this);
        voiceManager = VoiceManager.getInstance(this);
        postType = getIntent().getStringExtra("postType");
        postTopicId = getIntent().getStringExtra("postTopicId");
        postIsTop = getIntent().getStringExtra("postIsTop");
        LogUtils.e("postIsTop  ->"+ postIsTop);
        postWriterId = getIntent().getStringExtra("postWriterId");
        postIsFree = getIntent().getStringExtra("postIsFree");
        postName = getIntent().getStringExtra("postName");
        postPrice = getIntent().getStringExtra("postPrice");
        topicImg = getIntent().getStringExtra("topicImg");
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");
        activityId = getIntent().getStringExtra("activityId");
        postId = getIntent().getStringExtra("postId");
        postContentApp = getIntent().getStringExtra("postContentApp");
        LogUtils.e("ReleaseContentActivity postContentApp=>"+postContentApp);

        // 添加投票
        topicID = getIntent().getStringExtra("topicId");
        voteName = getIntent().getStringExtra("voteName");
        topicType = getIntent().getStringExtra("topicType");
        voteIsTop = getIntent().getStringExtra("voteIsTop");
        LogUtils.e("voteIsTop   -> "+voteIsTop);
        voteWriterId = getIntent().getStringExtra("voteWriterId");
        voteSecNames = getIntent().getStringExtra("voteSecNames");
        isMultipleChoice = getIntent().getBooleanExtra("isMultipleChoice", false);



        llContent = (LinearLayout) findViewById(R.id.ll_content);
        findViewById(R.id.iv_expression).setOnClickListener(this);
        ivPicture = (ImageView) findViewById(R.id.iv_picture);
        ivPicture.setOnClickListener(this);
        findViewById(R.id.iv_voice).setOnClickListener(this);
        findViewById(R.id.tv_release).setOnClickListener(this);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        llReleaseContents = (LinearLayout) findViewById(R.id.ll_release_contents);
        llRelease = (LinearLayout) findViewById(R.id.ll_release);
        mAdapter = new ReleaseContentsAdapter(null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvReleaseContent = (RecyclerView) findViewById(R.id.rv_release_content);
        rvReleaseContent.setAdapter(mAdapter);
        rvReleaseContent.setLayoutManager(linearLayoutManager);
        parseJsonStr();
        institutionListener();

        etContent = (EmojiconEditText) findViewById(R.id.et_content);
        etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                mIsFocus = isFocus;
            }
        });
        flEmoji = (FrameLayout) findViewById(R.id.emojicons);
        setEmojiconFragment(false);

    }

    private void parseJsonStr() {
        LogUtils.e("帖子内容=== 》"+postContentApp);
        if (!TextUtils.isEmpty(postContentApp)) {
            try {
                JSONArray jsonArray = new JSONArray(postContentApp);
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getInt("type") == 0){
                        String text=jsonObject.getString("content");
                        addList(text,ReleaseContentsBean.TEXT,null,0,true);
                    }
                    // 图片
                    if (jsonObject.getInt("type") == 1) {
                        String imgUrl=jsonObject.getString("content");
                        if (!imgUrl.contains("http://")){
                            imgUrl="http://"+imgUrl;
                        }
                        addList(imgUrl,ReleaseContentsBean.IMG,null,0,true);
                    }
                    //音频
                    if (jsonObject.getInt("type") == 2) {
                        String path=jsonObject.getString("content");
                        int timeLength=jsonObject.getInt("timeLength");
                        String strLength=jsonObject.getString("strLength");
                        addList(path,ReleaseContentsBean.RECORD,strLength,timeLength,true);
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(etContent, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(etContent);
    }

    /**
     * 发布贴子的
     *
     * @param context
     * @param postType
     * @param postName
     * @param postTopicId
     * @param postWriterId
     * @param postIsFree
     * @param postPrice
     * @param postIsTop
     */
    public static void start(Context context, String postType, String postName, String postTopicId,
                             String postWriterId, String postIsFree, String postPrice, String postIsTop) {
        Intent starter = new Intent(context, ReleaseContentsActivity.class);
        starter.putExtra("postType", postType);
        starter.putExtra("postName", postName);
        starter.putExtra("postTopicId", postTopicId);
        starter.putExtra("postWriterId", postWriterId);
        starter.putExtra("postIsFree", postIsFree);
        starter.putExtra("postPrice", postPrice);
        starter.putExtra("postIsTop", postIsTop);
        context.startActivity(starter);
    }


    /**
     * 修改贴子
     *
     * @param context
     * @param postId
     * @param postName
     * @param postTopicId
     * @param postIsFree
     * @param postPrice
     * @param postIsTop
     */
    public static void start(Context context, String postId, String postName, String postTopicId,
                             String postIsFree, String postPrice, String postIsTop) {

        Intent starter = new Intent(context, ReleaseContentsActivity.class);
        starter.putExtra("postId", postId);
        starter.putExtra("postName", postName);
        starter.putExtra("postTopicId", postTopicId);
        starter.putExtra("postIsFree", postIsFree);
        starter.putExtra("postPrice", postPrice);
        starter.putExtra("postIsTop", postIsTop);
        context.startActivity(starter);
    }

    /**
     * 修改帖子
     *
     * @param context
     * @param postId
     * @param postName
     * @param postTopicId
     * @param postIsFree
     * @param postPrice
     * @param postIsTop
     * @param postContentApp
     */
    public static void startT(Context context, String postId, String postName, String postTopicId,
                              String postIsFree, String postPrice, String postIsTop, String postContentApp) {

        Intent starter = new Intent(context, ReleaseContentsActivity.class);
        starter.putExtra("postId", postId);
        starter.putExtra("postName", postName);
        starter.putExtra("postTopicId", postTopicId);
        starter.putExtra("postIsFree", postIsFree);
        starter.putExtra("postPrice", postPrice);
        starter.putExtra("postIsTop", postIsTop);
        starter.putExtra("postContentApp", postContentApp);
        context.startActivity(starter);
    }


    /**
     * 发布活动的
     *
     * @param context
     * @param postType
     * @param postName
     * @param postTopicId
     * @param postWriterId
     * @param topicImg
     * @param startTime
     * @param endTime
     * @param postIsTop
     */
    public static void start(Context context,
                             String postType,
                             String postName,
                             String postTopicId,
                             String postWriterId,
                             String topicImg,
                             String startTime,
                             String endTime,
                             String postIsTop) {
        Intent starter = new Intent(context, ReleaseContentsActivity.class);
        starter.putExtra("postType", postType);
        starter.putExtra("postName", postName);
        starter.putExtra("postTopicId", postTopicId);
        starter.putExtra("postWriterId", postWriterId);
        starter.putExtra("topicImg", topicImg);
        starter.putExtra("startTime", startTime);
        starter.putExtra("endTime", endTime);
        starter.putExtra("postIsTop", postIsTop);
        context.startActivity(starter);
    }


    /**
     * 修改活动
     *
     * @param context
     * @param postType
     * @param postName
     * @param postTopicId
     * @param postWriterId
     * @param topicImg
     * @param startTime
     * @param endTime
     * @param postIsTop
     * @param activityId
     */
    public static void start(Context context,
                             String postType,
                             String postName,
                             String postTopicId,
                             String postWriterId,
                             String topicImg,
                             String startTime,
                             String endTime,
                             String postIsTop,
                             String activityId) {
        Intent starter = new Intent(context, ReleaseContentsActivity.class);
        starter.putExtra("postType", postType);
        starter.putExtra("postName", postName);
        starter.putExtra("postTopicId", postTopicId);
        starter.putExtra("postWriterId", postWriterId);
        starter.putExtra("topicImg", topicImg);
        starter.putExtra("startTime", startTime);
        starter.putExtra("endTime", endTime);
        starter.putExtra("postIsTop", postIsTop);
        starter.putExtra("activityId", activityId);
        context.startActivity(starter);
    }

    /**
     * 发布投票
     *
     * @param context
     * @param topicId
     * @param voteName
     * @param topicType
     * @param voteIsTop
     * @param voteWriterId
     * @param startTime
     * @param endTime
     * @param voteSecNames
     * @param isMultipleChoice
     */
    public static void start(Context context, String topicId, String voteName, String topicType, String voteIsTop, String voteWriterId,
                             String startTime, String endTime, String voteSecNames, boolean isMultipleChoice,String postContentApp,String postId) {
        Intent starter = new Intent(context, ReleaseContentsActivity.class);
        starter.putExtra("topicId", topicId);
        starter.putExtra("voteName", voteName);
        starter.putExtra("topicType", topicType);
        starter.putExtra("voteIsTop", voteIsTop);
        starter.putExtra("voteWriterId", voteWriterId);
        starter.putExtra("startTime", startTime);
        starter.putExtra("endTime", endTime);
        starter.putExtra("voteSecNames", voteSecNames);
        starter.putExtra("isMultipleChoice", isMultipleChoice);
        starter.putExtra("postContentApp", postContentApp);
        starter.putExtra("postId", postId);
        context.startActivity(starter);
    }


    /**
     * RecyclerView监听
     */
    private void institutionListener() {
        //文字改变的监听
        mAdapter.setChangInstitutionDataListener(new ReleaseContentsAdapter.ChangInstitutionDataListener() {
            @Override
            public void onChangInstitutionDataListener(int position, String data) {
            }

            @Override
            public void onDeleteItemListener(int position) {
                mAdapter.remove(position);
                listData.remove(position);
            }
        });
        //条目子控件的点击事件监听
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete:
                        mAdapter.remove(position);
                        listData.remove(position);
                        break;
                    case R.id.iv_record_play:
                        LogUtils.e("点击播放按钮  地址是---》"+listData.get(position).getContent());
                        PlaybackDialogFragment fragmentPlay = PlaybackDialogFragment.newInstance(listData.get(position));
                        fragmentPlay.show(getSupportFragmentManager(), PlaybackDialogFragment.class.getSimpleName());
                        break;
                }
            }
        });

    }


    //相机和相册选择图片的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AddImageUtils.REQUEST_PICK_IMAGE://从相册选择
                    if (data != null) {
                        if (Build.VERSION.SDK_INT >= 19) {
                            AddImageUtils.handleImageOnKitKat(data, ReleaseContentsActivity.this);
                        } else {
                            AddImageUtils.handleImageBeforeKitKat(data, ReleaseContentsActivity.this);
                        }
                        mOutputUri = AddImageUtils.cropPhotoSmall(ReleaseContentsActivity.this);

                    }
                    break;
                case AddImageUtils.REQUEST_CAPTURE://拍照
                    mOutputUri = AddImageUtils.cropPhotoSmall(ReleaseContentsActivity.this);
                    break;
                case AddImageUtils.REQUEST_PICTURE_CUT_SMALL://裁剪完成
                    if (data != null) {
                        try {
                            mFileCamera = new File(mOutputUri.getPath());
                            if (!mFileCamera.isFile()) {
                                return;
                            }
                            List<File> list = new ArrayList<>();
                            list.add(mFileCamera);
                            showProgress("图片上传中");
                            //本地显示
                             addList(mOutputUri.getPath(), fileType, voiceStrLength, voiceLength,false);
                            //上传图片
                            HttpMethod1.uploadFile(HttpConstant.UPDATE_FILES, list, mHandler);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            // emoji表情
            case R.id.iv_expression:
                if (flEmoji.getVisibility() == View.VISIBLE) {
                    hideEmoji();
                } else {
                    shouEmoji();
                }
                break;
            case R.id.iv_picture:
                addPic();
                break;
            case R.id.tv_confirm:
                fileType = ReleaseContentsBean.TEXT;
                if (!TextUtils.isEmpty(etContent.getText().toString().trim())) {
                    addList(etContent.getText().toString().trim(), fileType, null, 0,false);
                    listData.add(new ReleaseContentsBean(etContent.getText().toString().trim(), fileType, voiceStrLength, voiceLength));
                    etContent.setText("");
                }
                break;
            case R.id.tv_release:
                if (!TextUtils.isEmpty(startTime) && "0".equals(activityId)) {
                    // 发布活动
                    HttpMethod2.addActivity(postTopicId, topicImg, postName, postType, postIsTop, postWriterId, startTime, endTime
                            , MyApplication.gson.toJson(listData), mHandler);
                } else if (!TextUtils.isEmpty(activityId) && !"0".equals(activityId)) {
                    // 修改活动
                    HttpMethod2.updateActivity(postTopicId, activityId, topicImg, postName, postType, postIsTop, postWriterId
                            , startTime, endTime, MyApplication.gson.toJson(listData), mHandler);
                } else if (!TextUtils.isEmpty(voteName)) {
                    // 添加投票
                    HttpMethod2.addVote(topicID, voteName, topicType, voteIsTop, voteWriterId
                            , startTime, endTime, voteSecNames, isMultipleChoice, MyApplication.gson.toJson(listData),postId, mHandler);

                } else {
                    // 发布贴子
                    if (TextUtils.isEmpty(postId)) {
                        HttpMethod2.addPost(postType, postName, postTopicId, postWriterId, postIsFree, postPrice, postIsTop,
                                MyApplication.gson.toJson(listData), mHandler);
                    } else {
                        // 修改贴子
                        HttpMethod2.updatePost(postId, postName, postIsFree, postPrice, postIsTop,
                                MyApplication.gson.toJson(listData), mHandler);
                    }
                }
                Log.i("ReleaseContentsUI", listData.toString());
                break;
            case R.id.iv_voice:
                closeSoftInput();
                showRecordPopWindow();
                break;
            case R.id.lin_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void shouEmoji() {
        startAnim(flEmoji, 2);
        flEmoji.setVisibility(View.VISIBLE);
        // 隐藏发布按钮
        llRelease.setVisibility(View.GONE);
    }

    private void hideEmoji() {
        startAnim(flEmoji, 1);
        flEmoji.setVisibility(View.GONE);
        llRelease.setVisibility(View.VISIBLE);
    }

    /**
     * emoji动画
     *
     * @param flEmoji
     * @param index
     */
    private void startAnim(FrameLayout flEmoji, int index) {
        Animation animationIn = AnimationUtils.loadAnimation(this, R.anim.emoji_enter);
        Animation animationOut = AnimationUtils.loadAnimation(this, R.anim.emoji_exit);
        if (index == 1) {
            flEmoji.startAnimation(animationOut);
        } else {
            flEmoji.startAnimation(animationIn);
        }
    }

    /**
     * 上传图片
     */
    private void addPic() {
        fileType = ReleaseContentsBean.IMG;
        popIco = new PopIco(this);
        popIco.showAsDropDown();
        popIco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_pop_ico_camera:
                        AddImageUtils.openCamera(ReleaseContentsActivity.this);
                        break;
                    case R.id.tv_pop_ico_photo:
                        AddImageUtils.selectFromAlbum(ReleaseContentsActivity.this);
                        break;
                }
            }
        });
    }

    private void showRecordPopWindow() {
        fileType = ReleaseContentsBean.RECORD;
        View contentView = LayoutInflater.from(this).inflate(R.layout.fragment_record_audio, null);
        handleLogic(contentView);
        recordPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.7f)
                .enableOutsideTouchableDissmiss(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        recordPopWindow.showAtLocation(llReleaseContents, Gravity.BOTTOM, 0, 0);

    }

    private void handleLogic(View contentView) {
        tv_time_length = (TextView) contentView.findViewById(R.id.tv_time_length);
        tv_time_length.setText("00:00:00");
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_record_delete:
                        if (voiceManager != null) {
                            isRecord = false;
                            voiceManager.cancelVoiceRecord();
                        }
                        recordPopWindow.dissmiss();
                        break;
                    case R.id.tv_record_start:
                        if (!isRecord) {
                            isRecord = true;
                            startRecord();
                        }
                        break;
                    case R.id.tv_record_conform:
                        if (voiceManager != null) {
                            isRecord = false;
                            voiceManager.stopVoiceRecord();
                        }
                        recordPopWindow.dissmiss();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.tv_record_delete).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_record_start).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_record_conform).setOnClickListener(listener);

    }

    private void startRecord() {
        voiceManager.setVoiceRecordListener(new VoiceManager.VoiceRecordCallBack() {
            @Override
            public void recDoing(long time, String strTime) {
                tv_time_length.setText(strTime);
            }

            @Override
            public void recVoiceGrade(int grade) {
            }

            @Override
            public void recStart(boolean init) {
            }

            @Override
            public void recPause(String str) {
            }


            @Override
            public void recFinish(long length, String strLength, String path) {
                mVoiceFile = new File(path);
                List<File> list = new ArrayList<>();
                list.add(mVoiceFile);
                HttpMethod1.uploadFile(HttpConstant.UPDATE_FILES, list, mHandler);
                voiceStrLength = strLength;
                voiceLength = length;
                //本地显示
                addList(path, fileType, strLength, length,false);
            }
        });
        voiceManager.startVoiceRecord(RecordUtil.getAudioPath());
    }


    /**
     * 添加数据--本地显示
     *
     * @param content 数据内容
     * @param type    数据类型 0：文字；1：图片；2：录音
     */
    private void addList(String content, int type, String strLength, long length,boolean add) {
        ReleaseContentsBean data = new ReleaseContentsBean(content, type, strLength, length);
        mAdapter.addData(data);
        if (add){
            listData.add(data);
        }
        mAdapter.notifyDataSetChanged();
        rvReleaseContent.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (isHideSoftInput(view, (int) ev.getX(), (int) ev.getY())) {
            closeSoftInput();
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void closeSoftInput() {
        etContent.clearFocus();
        SoftInputUtils.closeSoftInput(this);
    }

    private boolean isHideSoftInput(View view, int x, int y) {
        if (view == null || !(view instanceof EditText) || !mIsFocus) {
            return false;
        }
        return x < llContent.getLeft() ||
                x > llContent.getRight() ||
                y < llContent.getTop();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            UploadFile bean = (UploadFile) msg.obj;
            switch (msg.what) {
                case HandlerConstant1.UPLOAD_HEAD_SUCCESS:
                    if (null == bean) {
                        return;
                    }
                    if (bean.isStatus()) {
                        String head = "http://";
                        String url = bean.getData().getUrl();
                        /*if (url.contains(head)) {
                            url = url.substring(head.length(), url.length());
                        }*/
                        //发布时需要用到的去http
                        listData.add(new ReleaseContentsBean(url, fileType, voiceStrLength, voiceLength));
                        voiceStrLength = "";
                        voiceLength = 0;
                    } else {
                        showMsg(bean.getErrorMsg());
                    }
                    break;
                // 发布帖子成功
                case HandlerConstant2.ADD_POST_SUCCESS:
                    if (null == bean) {
                        return;
                    }
                    if (bean.status) {
                        showMsg("发布成功");
                        //删除文件
                        if (mFileCamera != null) {
                            FileStorage.deleteFile(mFileCamera.getAbsolutePath());
                        }
                        if (mVoiceFile != null) {
                            FileStorage.deleteFile(mVoiceFile.getAbsolutePath());
                        }
                        LogUtils.e("发布帖子成功");
                        //发布贴子成功
                        finish();
                        postEvent();
                    } else {
                        showMsg(bean.getErrorMsg());
                    }
                    break;
                // 修改帖子成功
                case HandlerConstant2.UPDATE_POST_SUCCESS:
                    if (null == bean) {
                        return;
                    }
                    LogUtils.e("修改帖子成功");
                    if (bean.status) {
                        showMsg("编辑成功");
                        finish();
                        postEvent();
                    } else {
                        showMsg(bean.getErrorMsg());
                    }
                    break;
                // 发布活动成功
                case HandlerConstant2.ADD_ACTIVITY_SUCCESS:
                    if (null == bean) {
                        return;
                    }
                    LogUtils.e("发布活动成功");
                    if (bean.status) {
                        showMsg("发布成功");
                        finish();
                        postEvent();
                        postActivityEvent();
                    } else {
                        showMsg(bean.getErrorMsg());
                    }
                    break;
                // 修改活动成功
                case HandlerConstant2.UPDATE_ACTIVITY_SUCCESS:
                    if (null == bean) {
                        return;
                    }
                    LogUtils.e("修改活动成功");
                    if (bean.isStatus()) {
                        showMsg("编辑成功");
                        finish();
                        postEvent();
                        postActivityEvent();
                    } else {
                        showMsg(bean.getErrorMsg());
                    }
                    break;
                case HandlerConstant2.ADD_VOTE_SUCCESS:
                    // 发布投票
                    if (null == bean) {
                        return;
                    }
                    if (bean.isStatus()) {
                        showMsg("发布成功");
                        finish();
                        postEvent();
                        postVoteEvent();
                    } else {
                        showMsg(bean.errorMsg);
                    }
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    showMsg(getString(R.string.net_error));
                    break;
                default:
                    break;

            }
        }
    };

    private void postEvent() {
        EventBus.getDefault().post(new PostEvent().setEventType(PostEvent.RELEASE_SUCCESS));
    }

    private void postVoteEvent() {
        EventBus.getDefault().post(new PostEvent().setEventType(PostEvent.RELEASE_VOTE_SUCCESS));
    }

    private void postActivityEvent() {
        EventBus.getDefault().post(new PostEvent().setEventType(PostEvent.RELEASE_ACTIVITY_SUCCESS));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void postEvent(PostEvent postEvent) {

    }

}
