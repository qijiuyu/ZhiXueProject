package com.example.administrator.zhixueproject.activity.topic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.topic.AddVoteAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.eventBus.PostEvent;
import com.example.administrator.zhixueproject.bean.live.TeacherListBean;
import com.example.administrator.zhixueproject.bean.topic.AddVoteBean;
import com.example.administrator.zhixueproject.bean.topic.VoteListBean;
import com.example.administrator.zhixueproject.fragment.topic.AddTopicFragment;
import com.example.administrator.zhixueproject.utils.KeyboardUtils;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;
import com.example.administrator.zhixueproject.view.CustomPopWindow;
import com.example.administrator.zhixueproject.view.SwitchButton;
import com.example.administrator.zhixueproject.view.time.TimePickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 发布投票
 *
 * @author petergee
 * @date 2018/10/10
 */
public class ReleaseVoteActivity extends BaseActivity implements View.OnClickListener, SwitchButton.OnCheckedChangeListener, BaseQuickAdapter.OnItemChildClickListener, AddTopicFragment.OnTopicListener {
    private AddVoteAdapter mAdapter;
    private List<AddVoteBean> list = new ArrayList<>();
    private TimePickerView pvCustomTime;
    private CustomPopWindow mAddVotePop;
    public String mIsTop = "1";
    private boolean mIsMultiple;
    private AddTopicFragment mAddTopicFragment;
    private CustomPopWindow mTopicTypePop;
    private int activityWriterId;
    private String topicId;
    private int topicType;
    public String mStartTime;
    public String mEndTime;
    public VoteListBean mVoteListBean;
    private LinearLayout llReleaseVote;
    private TextView tvIssuer;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private EditText etTitle;
    private SwitchButton sbStick;
    private SwitchButton sbMultiSelect;
    private RecyclerView rvVote;
    private TextView tvVoteType;
    private TextView tvTopic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_vote);
        initView();
        initData();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        StatusBarUtils.transparencyBar(this);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        findViewById(R.id.lin_back).setOnClickListener(this);
        tvTitle.setText(getString(R.string.release_vote));
        llReleaseVote = (LinearLayout) findViewById(R.id.ll_release_vote);
        etTitle = (EditText) findViewById(R.id.et_title);
        tvIssuer = (TextView) findViewById(R.id.tv_issuer);
        tvTopic = (TextView) findViewById(R.id.tv_topic);
        tvVoteType = (TextView) findViewById(R.id.tv_vote_type);
        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        tvEndTime = (TextView) findViewById(R.id.tv_end_time);
        sbMultiSelect = (SwitchButton) findViewById(R.id.sb_select);
        sbStick = (SwitchButton) findViewById(R.id.sb_stick);
        rvVote = (RecyclerView) findViewById(R.id.rv_vote);

        findViewById(R.id.rl_issuer).setOnClickListener(this);
        findViewById(R.id.rl_start_time).setOnClickListener(this);
        findViewById(R.id.rl_end_time).setOnClickListener(this);
        findViewById(R.id.tv_add_vote).setOnClickListener(this);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        findViewById(R.id.rl_topic).setOnClickListener(this);
        findViewById(R.id.rl_vote_type).setOnClickListener(this);
        initCustomTimePicker();
        sbStick.setOnCheckedChangeListener(this);
        sbMultiSelect.setOnCheckedChangeListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        rvVote.setLayoutManager(layoutManager);
        mAdapter = new AddVoteAdapter(R.layout.add_vote_item, list);
        rvVote.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);//侧滑菜单监听
    }

    private void initData() {
        mVoteListBean = (VoteListBean) getIntent().getSerializableExtra("voteListBean");
        if (mVoteListBean != null) {
            String mItemViewType=TextUtils.isEmpty(mVoteListBean.getVoteType())?"":mVoteListBean.getVoteType();
            //设置回显示
            if (!"0".equals(mItemViewType)) {
                switch (mItemViewType) {
                    case "1":
                        tvVoteType.setText("课程");
                        break;
                    case "2":
                        tvVoteType.setText("大家谈");
                        break;
                    default:
                        tvVoteType.setText("全部");
                        break;
                }
            }

            etTitle.setText(mVoteListBean.getVoteName());
            tvStartTime.setText(mVoteListBean.getStartTime());
            mStartTime = mVoteListBean.getStartTime();
            tvEndTime.setText(mVoteListBean.getEndTime());
            mEndTime = mVoteListBean.getEndTime();
            topicId = mVoteListBean.getVoteId() + "";
            tvIssuer.setText(mVoteListBean.getUserName());
            tvTopic.setText(mVoteListBean.getTopicName());// 话题名称
        }
    }

    public static void start(Context context, VoteListBean voteListBean) {
        Intent starter = new Intent(context, ReleaseVoteActivity.class);
        starter.putExtra("voteListBean", voteListBean);
        context.startActivity(starter);
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.sb_stick:
                //是否置顶
                if (isChecked)
                    mIsTop = "1";
                else
                    mIsTop = "0";
                break;
            case R.id.sb_select:
                //是否多选
                mIsMultiple = isChecked;
                break;
        }
    }

    /**
     * 显示话题弹窗
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

    public void onClick(View view) {
        KeyboardUtils.hideKeyBoard(view.getContext(), view);
        switch (view.getId()) {
            case R.id.rl_issuer:
                SelectLecturersActivity.start(this);
                break;
            case R.id.rl_start_time:
                pvCustomTime.show(tvStartTime);
                break;
            case R.id.rl_end_time:
                pvCustomTime.show(tvEndTime);
                break;
            case R.id.tv_add_vote:
                // 不是多选才能添加
                if (list.size() >= 1 && mIsMultiple == false) {
                    showMsg("您未开启多选，不能添加多个投票项");
                } else {
                    showAddVotePop();
                }
                break;
            case R.id.tv_confirm:
                String voteName = etTitle.getText().toString().trim();

                if (TextUtils.isEmpty(voteName)) {
                    showMsg("请输入标题");
                    return;
                }
                if (activityWriterId==0){
                    showMsg("请选择发布人");
                    return;
                }
                if (TextUtils.isEmpty(topicId)) {
                    showMsg("请输入话题");
                    return;
                }
                if (topicType==0){
                    showMsg("请选择话题类型");
                    return;
                }
                if (TextUtils.isEmpty(mStartTime)) {
                    showMsg("请输入开始时间");
                    return;
                }
                if (TextUtils.isEmpty(mEndTime)) {
                    showMsg("请输入结束时间");
                    return;
                }
                if (list.size() > 1 && mIsMultiple == false) {
                    showMsg("您未开启多选，不能添加多个投票项");
                    return;
                }
                // 跳转到发布内容页
                ReleaseContentsActivity.start(this, topicId, voteName, String.valueOf(topicType), mIsTop, String.valueOf(activityWriterId)
                        , mStartTime, mEndTime, MyApplication.gson.toJson(list), mIsMultiple);
                break;
            case R.id.rl_vote_type:
                showVoteTypePop();
                break;
            case R.id.rl_topic:
                showTopicFragment(true);
                break;
            case R.id.lin_back:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 添加投票项弹框
     */
    private void showAddVotePop() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_add_vote, null);
        handleAddVote(contentView);
        mAddVotePop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.7f)
                .enableOutsideTouchableDissmiss(false)
                .create();
        mAddVotePop.showAtLocation(llReleaseVote, Gravity.CENTER, 0, 0);
    }


    /**
     * 话题类型弹框
     */
    private void showVoteTypePop() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_topic_type, null);
        handleTopicType(contentView);
        mTopicTypePop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.7f)
                .enableOutsideTouchableDissmiss(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimUp)
                .create();
        mTopicTypePop.showAtLocation(llReleaseVote, Gravity.BOTTOM, 0, 0);
    }


    /**
     * 选择类型
     */
    private void handleTopicType(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTopicTypePop != null) {
                    mTopicTypePop.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.tv_course:
                        tvVoteType.setText("课程");
                        topicType = 1;
                        break;
                    case R.id.tv_voices:
                        tvVoteType.setText("大家谈");
                        topicType = 2;
                        break;
                }
            }
        };
        contentView.findViewById(R.id.tv_all).setVisibility(View.GONE);
        contentView.findViewById(R.id.tv_course).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_voices).setOnClickListener(listener);
    }

    private void handleAddVote(View contentView) {
        final EditText et_vote = (EditText) contentView.findViewById(R.id.et_vote);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.tv_preserve:
                        String content = et_vote.getText().toString().trim();
                        if (content.isEmpty()) {
                            showMsg("请添加内容");
                        } else {
                            AddVoteBean bean = new AddVoteBean();
                            bean.setContent(content);
                            list.add(bean);
                            mAdapter.setList(list);
                            mAdapter.notifyDataSetChanged();
                            mAddVotePop.dissmiss();
                        }
                        break;
                    case R.id.tv_cancel:
                        mAddVotePop.dissmiss();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.tv_preserve).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(listener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == SelectLecturersActivity.REQUEST_CODE) {
            TeacherListBean bean = (TeacherListBean) data.getSerializableExtra(SelectLecturersActivity.TEACHER_INFO);
            tvIssuer.setText(bean.getUserName());
            activityWriterId = bean.getTeacherId();
        }
    }

    /**
     * 侧滑删除
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_menu:
                showMsg("删除");
                list.remove(position);
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
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
                if (v == tvStartTime) {
                    mStartTime = getTime(date);
                    tvStartTime.setText(mStartTime);
                } else if (v == tvEndTime) {
                    mEndTime = getTime(date);
                    tvEndTime.setText(mEndTime);
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
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(getResources().getColor(R.color.color_dbdbdb))
                .setTextColorCenter(getResources().getColor(R.color.color_333333))
                .gravity(Gravity.CENTER)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

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
        if (PostEvent.RELEASE_SUCCESS == postEvent.getEventType()) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
