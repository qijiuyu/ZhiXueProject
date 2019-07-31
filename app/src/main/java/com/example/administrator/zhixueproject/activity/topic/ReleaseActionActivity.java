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
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UploadFile;
import com.example.administrator.zhixueproject.bean.eventBus.PostEvent;
import com.example.administrator.zhixueproject.bean.live.TeacherListBean;
import com.example.administrator.zhixueproject.bean.topic.ActivityListBean;
import com.example.administrator.zhixueproject.fragment.topic.AddTopicFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HttpConstant;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.AddImageUtils;
import com.example.administrator.zhixueproject.utils.DateUtil;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.PopIco;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;
import com.example.administrator.zhixueproject.utils.TimeUtils;
import com.example.administrator.zhixueproject.view.CustomPopWindow;
import com.example.administrator.zhixueproject.view.SwitchButton;
import com.example.administrator.zhixueproject.view.time.TimePickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 发布活动
 *
 * @author petergee
 * @date 2018/10/10
 */
public class ReleaseActionActivity extends BaseActivity implements View.OnClickListener, AddTopicFragment.OnTopicListener {
    private int topicType = 1;// 活动类型
    private TimePickerView pvCustomTime;
    private CustomPopWindow mTopicTypePop;
    public int mItemViewType;
    public String mStartTime;
    public String mEndTime;
    public String mIsTop = "0";
    private AddTopicFragment mAddTopicFragment;
    private String topicId;
    private String topicImg;
    private int activityWriterId;
    public ActivityListBean mActivityListBean;
    private int mActivityId;
    private Uri mOutputUri;
    private PopIco popIco;
    private TextView tvIssuer;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private TextView tvActionTitle;
    private SwitchButton sbStick;
    private TextView tvTopicType;
    private LinearLayout llAddAction;
    private TextView tvTopic;
    private ImageView ivAddPicture;
    public static final String RELAEASE_ACTION_SUCCESS = "con.example.action.release";
    private int type; // 1管理员，2老师
    private RelativeLayout relIssuer;
    private ImageView ivRightIssuer;
    private long savedStartTime = 0;
    private long savedEndTime = 0;
    private String postContentApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusBarUtils.transparencyBar(this);
        setContentView(R.layout.activity_release_action);
        initView();
        initData();
    }


    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.release_action));
        findViewById(R.id.lin_back).setOnClickListener(this);
        tvActionTitle = (TextView) findViewById(R.id.tv_title_action);
        sbStick = (SwitchButton) findViewById(R.id.sb_stick);
        tvTopic = (TextView) findViewById(R.id.tv_topic);
        tvIssuer = (TextView) findViewById(R.id.tv_issuer);
        tvTopicType = (TextView) findViewById(R.id.tv_topic_type);
        tvTopicType.setText("课程");
        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        tvEndTime = (TextView) findViewById(R.id.tv_end_time);
        llAddAction = (LinearLayout) findViewById(R.id.ll_add_action);
        ivAddPicture = (ImageView) findViewById(R.id.iv_add_picture);
        ivAddPicture.setOnClickListener(this);
        relIssuer = (RelativeLayout) findViewById(R.id.rl_issuer);
        ivRightIssuer = (ImageView) findViewById(R.id.iv_right_issuer);

        relIssuer.setOnClickListener(this);
        findViewById(R.id.rl_start_time).setOnClickListener(this);
        findViewById(R.id.rl_end_time).setOnClickListener(this);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        findViewById(R.id.rl_action_type).setOnClickListener(this);
        findViewById(R.id.rl_topic).setOnClickListener(this);


        type = MyApplication.homeBean.getAttendType();
        String userName = MyApplication.userInfo.getData().getUser().getUserName() + "";
        // id
        int userId = (int) MyApplication.userInfo.getData().getUser().getUserId();
        // set default value
        tvIssuer.setText(userName);
        activityWriterId = userId;
        if (type == 2) {
            // 老师身份
            // 设置不能选择发布人
            relIssuer.setClickable(false);
            ivRightIssuer.setVisibility(View.INVISIBLE);
        }

        initCustomTimePicker();
        //是否置顶
        sbStick.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                    mIsTop = "1";
                else
                    mIsTop = "0";
            }
        });


    }

    private void initData() {
        topicId = getIntent().getIntExtra("topicId", 0) + "";
        tvTopic.setText(getIntent().getStringExtra("topicName"));
        String postTypeName = getIntent().getStringExtra("postTypeName");
        tvTopicType.setText(postTypeName);
        topicType = getIntent().getIntExtra("topicType", 0);


        mActivityListBean = (ActivityListBean) getIntent().getSerializableExtra("activityListBean");
        if (mActivityListBean != null) {
            mItemViewType = mActivityListBean.getActivityType();
            //设置回显示
            if (mItemViewType != 0) {
                topicType = mItemViewType;
                switch (mItemViewType) {
                    case 31:
                        tvTopicType.setText("课程");
                        break;
                    case 32:
                        tvTopicType.setText("大家谈");
                        break;
                    default:
                        tvTopicType.setText("付费问答");
                        break;
                }
            }
            savedStartTime = TimeUtils.getTimestamp(mActivityListBean.getStartTime());
            savedEndTime = TimeUtils.getTimestamp(mActivityListBean.getEndTime());
            mActivityId = mActivityListBean.getActivityId();
            tvActionTitle.setText(mActivityListBean.getActivityName());
            tvTopic.setText(mActivityListBean.getTopicName());
            tvStartTime.setText(mActivityListBean.getStartTime());
            mStartTime = mActivityListBean.getStartTime();
            tvEndTime.setText(mActivityListBean.getEndTime());
            mEndTime = mActivityListBean.getEndTime();
            topicId = mActivityListBean.getTopicId() + "";
            mIsTop = mActivityListBean.getPostIsTop() + "";
            postContentApp = mActivityListBean.getPostContentApp();
            if (mActivityListBean.getPostIsTop() == 0) {
                sbStick.setChecked(false);
            } else {
                sbStick.setChecked(true);
            }
            tvIssuer.setText(mActivityListBean.getUserName());
            if (!TextUtils.isEmpty(mActivityListBean.getPostPicture())) {
                topicImg = mActivityListBean.getPostPicture();
                Glide.with(mContext).load(mActivityListBean.getPostPicture()).error(R.mipmap.unify_image_ing).into(ivAddPicture);
            }
        }
    }


    public static void start(Context context, ActivityListBean activityListBean) {
        Intent starter = new Intent(context, ReleaseActionActivity.class);
        starter.putExtra("activityListBean", activityListBean);
        context.startActivity(starter);
    }

    public static void start(Context context, int topicId, String topicName, String postTypeName, int topicType) {
        Intent starter = new Intent(context, ReleaseActionActivity.class);
        starter.putExtra("topicId", topicId);
        starter.putExtra("topicName", topicName);
        starter.putExtra("postTypeName", postTypeName);
        starter.putExtra("topicType", topicType);
        context.startActivity(starter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            case R.id.rl_issuer:
                SelectLecturersActivity.start(this);
                break;
            case R.id.rl_start_time:
                pvCustomTime.show(tvStartTime);
                break;
            case R.id.rl_end_time:
                pvCustomTime.show(tvEndTime);
                break;
            case R.id.tv_confirm:
                //创建
                if (inputReal()) {
                    ReleaseContentsActivity.start(
                            view.getContext(),
                            String.valueOf(topicType),
                            tvActionTitle.getText().toString().trim(),
                            topicId,
                            String.valueOf(activityWriterId),
                            topicImg,
                            mStartTime,
                            mEndTime,
                            mIsTop,
                            String.valueOf(mActivityId),
                            postContentApp
                    );
                }

                break;
            case R.id.rl_action_type:
                showActionTypePop();
                break;
            case R.id.rl_topic:
                showTopicFragment(true);
                break;
            case R.id.iv_add_picture:
                addPic();
                break;
            default:
                break;
        }
    }

    /**
     * 上传图片
     */
    private void addPic() {
        popIco = new PopIco(this);
        popIco.showAsDropDown();
        popIco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_pop_ico_camera:
                        AddImageUtils.openCamera(ReleaseActionActivity.this);
                        break;
                    case R.id.tv_pop_ico_photo:
                        AddImageUtils.selectFromAlbum(ReleaseActionActivity.this);
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
                            AddImageUtils.handleImageOnKitKat(data, ReleaseActionActivity.this);
                        } else {
                            AddImageUtils.handleImageBeforeKitKat(data, ReleaseActionActivity.this);
                        }
                        mOutputUri = Uri.parse(AddImageUtils.cropPhoto(ReleaseActionActivity.this));
                    }
                    break;
                case AddImageUtils.REQUEST_CAPTURE://拍照
                    mOutputUri = Uri.parse(AddImageUtils.cropPhoto(ReleaseActionActivity.this));
                    break;
                case AddImageUtils.REQUEST_PICTURE_CUT://裁剪完成
                    if (data != null) {
                        try {
                            File mFileCamera = new File(mOutputUri.getPath());
                            if (!mFileCamera.isFile()) {
                                return;
                            }
                            List<File> list = new ArrayList<>();
                            list.add(mFileCamera);
                            showProgress("图片上传中");
                            //上传图片
                            HttpMethod1.uploadFile(HttpConstant.UPDATE_FILES, list, mHandler);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case SelectLecturersActivity.REQUEST_CODE:
                    TeacherListBean bean = (TeacherListBean) data.getSerializableExtra(SelectLecturersActivity.TEACHER_INFO);
                    tvIssuer.setText(bean.getUserName());
                    activityWriterId = bean.getTeacherId();
                    break;
            }

        }
    }

    private boolean inputReal() {
        if (TextUtils.isEmpty(tvActionTitle.getText().toString().trim())) {
            showMsg("请输入标题");
            return false;
        }
        if (TextUtils.isEmpty(tvTopic.getText().toString().trim())) {
            showMsg("请选择话题");
            return false;
        }
        if (TextUtils.isEmpty(String.valueOf(activityWriterId))) {
            showMsg("请选择发布人");
            return false;
        }
        if (TextUtils.isEmpty(tvTopicType.getText().toString().trim())) {
            showMsg("请选择活动类型");
            return false;
        }
        if (TextUtils.isEmpty(mStartTime)) {
            showMsg("请选择开始时间");
            return false;
        }
        if (TextUtils.isEmpty(mEndTime)) {
            showMsg("请选择结束时间");
            return false;
        }
        if (savedEndTime < savedStartTime) {
            showMsg("结束时间一定要在开始时间之后哦!");
            return false;
        }

        if (TextUtils.isEmpty(topicImg)) {
            showMsg("请上传活动图片");
            return false;
        }
        return true;
    }

    /**
     * 显示话题弹窗
     *
     * @param show
     */
    private void showTopicFragment(boolean show) {
        if (mAddTopicFragment == null) {
            mAddTopicFragment = new AddTopicFragment();
            mAddTopicFragment.setOnTopicListener(this);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (show) {
            //防止快速点击
            if (mAddTopicFragment.isAdded()) {
                return;
            }
            transaction.add(R.id.fl_topic, mAddTopicFragment).commit();
        } else {
            transaction.remove(mAddTopicFragment).commit();
        }
    }

    /**
     * 话题类型弹框
     */
    private void showActionTypePop() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_topic_type, null);
        handleTopicType(contentView);
        mTopicTypePop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.7f)
                .enableOutsideTouchableDissmiss(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimUp)
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .create();
        mTopicTypePop.showAtLocation(llAddAction, Gravity.BOTTOM, 0, 0);
    }

    private void handleTopicType(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTopicTypePop != null) {
                    mTopicTypePop.dissmiss();
                }
                switch (v.getId()) {

                    case R.id.tv_course:
                        tvTopicType.setText("课程");
                        topicType = 1;
                        break;
                    case R.id.tv_voices:
                        tvTopicType.setText("大家谈");
                        topicType = 2;
                        break;
                    case R.id.tv_price_ask:
                        tvTopicType.setText("付费问答");
                        topicType = 3;
                        break;
                }
            }
        };
        contentView.findViewById(R.id.tv_all).setVisibility(View.GONE);
        contentView.findViewById(R.id.tv_price_ask).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_course).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_voices).setOnClickListener(listener);
    }

    /**
     * 初始化时间选择
     */
    private void initCustomTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(3000, 12, 31);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            public void onTimeSelect(Date date, View v) {//选中事件回调

                if (DateUtil.IsToday(date.getTime())) {
                    if (v == tvStartTime) {
                        savedStartTime = date.getTime();
                        mStartTime = getTime(date);
                        tvStartTime.setText(mStartTime);
                    } else if (v == tvEndTime) {
                        savedEndTime = date.getTime();
                        if (savedEndTime < savedStartTime) {
                            showMsg("结束时间不能在开始时间之前");
                            return;
                        }
                        mEndTime = getTime(date);
                        tvEndTime.setText(mEndTime);
                    }
                } else {
                    showMsg("不能选择已过期的时间！");
                }

            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_sign_time, new com.example.administrator.zhixueproject.callback.CustomListener() {
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                        TextView v_bottom = (TextView) v.findViewById(R.id.v_bottom);
                        if (v_bottom != null) {
                            ViewGroup.MarginLayoutParams bottomParams = (ViewGroup.MarginLayoutParams) v_bottom.getLayoutParams();
                            v_bottom.setLayoutParams(bottomParams);
                        }
                        View view_bg = v.findViewById(R.id.view_bg);
                        view_bg.setBackgroundColor(getResources().getColor(R.color.translete));
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(getResources().getColor(R.color.color_dbdbdb))
                .setTextColorCenter(getResources().getColor(R.color.color_333333))
                .gravity(Gravity.CENTER)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
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
                        String url = bean.getData().getUrl();
                        topicImg = url;
                        Glide.with(mContext).load(url).error(R.mipmap.unify_image_ing).into(ivAddPicture);
                    } else {
                        // showMsg(bean.getErrorMsg());
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

    @Override
    public void closeTopicListener(View view) {
        showTopicFragment(false);
    }

    @Override
    public void topicListener(View view, String topic, String topicName) {
        this.topicId = topic;
        tvTopic.setText(topicName);
        showTopicFragment(false);
    }

    @Subscribe
    public void postEvent(PostEvent postEvent) {
        if (PostEvent.RELEASE_ACTIVITY_SUCCESS == postEvent.getEventType()) {
            // 发送广播
            sendLocalBroadCast();
            finish();
        }
    }

    private void sendLocalBroadCast() {
        Intent intent = new Intent();
        intent.setAction(RELAEASE_ACTION_SUCCESS);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
