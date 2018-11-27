package com.example.administrator.zhixueproject.activity.memberManage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.memberManage.MedalIconAdapter;
import com.example.administrator.zhixueproject.bean.memberManage.AttendanceBean;
import com.example.administrator.zhixueproject.bean.memberManage.MemberSettingBean;
import com.example.administrator.zhixueproject.fragment.memberManage.IdentityFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import java.util.List;

/**
 * 会员设置
 *
 * @author PeterGee
 * @date 2018/10/20
 */
public class MemberSettingActivity extends BaseActivity implements View.OnClickListener, DecorationFragment.OnDecorationListener, IdentityFragment.OnIdentityListener, NoSpeakingTimeFragment.OnTimeCheckedListener {
    private boolean isBlack;//是否拉黑
    private boolean isNospeaking;//是否禁言
    private DecorationFragment mDecorationFragment;
    private IdentityFragment mIdentityFragment;
    private MedalIconAdapter mMedalIconAdapter;
    public static final int RESULT_CODE = 10;
    private AttendanceBean mMemberInfoBean;
    private int attendType;//会员身份(0：学生、1：管理、2：老师、默认为0)
    private int attendAllowYn;//是否拉黑(是否允许再加入：1：是、2：否)
    private int attendTalkLimit;//是否禁言(0：否、1：是)
    private String attendTalkTime = "";//禁言时间
    private String medalTypeIds = "";//勋章id
    public static final int BLACK_TRUE = 2;//黑名单
    public static final int BLACK_FALSE = 1;
    public static final int NOSPEAKING_TRUE = 1;//禁言
    public static final int NOSPEAKING_FALSE = 0;
    private boolean isNospeakingInit;//会员初始化时的禁言状态
    private NoSpeakingTimeFragment mTimeFragment;
    private String mTalkTime;
    private int mAttendId;
    private ImageView ivIsBlack;
    private ImageView ivIsNoSpeaking;
    private TextView tvNoSpeakingTime;
    private TextView tvIdentity;
    private RecyclerView rvMedalIcon;
    private EditText etMemberName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_setting);
        initView();
        initData();
    }

    private void initView() {
        //会员基本信息
        mMemberInfoBean = getIntent().getParcelableExtra(MemberManagerActivity.MEMBER_INFO);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.member_setting));
        findViewById(R.id.lin_back).setOnClickListener(this);
        etMemberName = (EditText) findViewById(R.id.et_member_name);
        // 黑名单
        ivIsBlack = (ImageView) findViewById(R.id.iv_is_black);
        ivIsBlack.setOnClickListener(this);
        // 禁言
        ivIsNoSpeaking = (ImageView) findViewById(R.id.iv_is_nospeaking);
        ivIsNoSpeaking.setOnClickListener(this);
        // 禁言至
        tvNoSpeakingTime = (TextView) findViewById(R.id.tv_nospeaking_time);
        // 身份
        tvIdentity = (TextView) findViewById(R.id.tv_identity);
        rvMedalIcon = (RecyclerView) findViewById(R.id.rv_medal_icon);
        findViewById(R.id.rl_show_identity).setOnClickListener(this);
        findViewById(R.id.rl_show_decoration).setOnClickListener(this);
        findViewById(R.id.tv_setting_save).setOnClickListener(this);
    }

    private void initData() {
        etMemberName.setText(mMemberInfoBean.getAttendUsername());
        etMemberName.requestFocus();
        etMemberName.setSelection(mMemberInfoBean.getAttendUsername().length());//将光标移至文字末尾
        mAttendId = mMemberInfoBean.getAttendId();
        //人员类型(0：学生、1：管理员、2老师
        attendType = mMemberInfoBean.getAttendType();
        String[] identity = getResources().getStringArray(R.array.member_identity);
        if (attendType == 0) {
            tvIdentity.setText(identity[1]);
        } else if (attendType == 1) {
            tvIdentity.setText(identity[2]);
        } else if (attendType == 2) {
            tvIdentity.setText(identity[0]);
        }
        attendAllowYn = mMemberInfoBean.getAttendAllowYn();
        //1不是黑名单 2是黑名单
        if (mMemberInfoBean.getAttendAllowYn() == BLACK_FALSE) {
            isBlack = false;
        } else if (mMemberInfoBean.getAttendAllowYn() == BLACK_TRUE) {
            isBlack = true;
        }
        showBlackIcon(isBlack);
        //是否禁言(0：否、1：是)
        attendTalkLimit = mMemberInfoBean.getAttendTalkLimit();
        //禁言时间
        mTalkTime = mMemberInfoBean.getAttendTalkTime();
        if (mMemberInfoBean.getAttendTalkLimit() == NOSPEAKING_FALSE) {
            isNospeaking = false;
            isNospeakingInit = false;
        } else if (mMemberInfoBean.getAttendTalkLimit() == NOSPEAKING_TRUE) {
            isNospeaking = true;
            isNospeakingInit = true;
        }
        showNoSpeakingIcon(isNospeaking);

        mMedalIconAdapter = new MedalIconAdapter(R.layout.medal_icon_item, mMemberInfoBean.getMedalTypeMig());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMedalIcon.setAdapter(mMedalIconAdapter);
        rvMedalIcon.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            case R.id.iv_is_black://是否拉黑
                isBlack = !isBlack;
                showBlackIcon(isBlack);
                break;
            case R.id.iv_is_nospeaking://是否禁言
                isNospeaking = !isNospeaking;
                showNoSpeakingIcon(isNospeaking);
                showNospeakingTimeFragment(isNospeaking);
                break;
            case R.id.rl_show_identity://显示会员身份弹窗
                showIdentityFragment(true);
                break;
            case R.id.rl_show_decoration://显示勋章弹窗
                showFragment(true);
                break;
            case R.id.tv_setting_save:
                saveVip();
                break;
            default:
                break;
        }
    }

    private void saveVip() {
        // 保存会员信息
        String attendUsername = etMemberName.getText().toString().trim() + "";
        if (TextUtils.isEmpty(attendUsername)){
            showMsg("会员名称不能为空");
            return;
        }
        showProgress(getString(R.string.loading));
        HttpMethod2.saveVip(mAttendId + "", attendUsername, attendType + "", attendAllowYn + "",
                attendTalkLimit + "", attendTalkTime, medalTypeIds, mHandler);
    }

    public void showBlackIcon(boolean flag) {
        if (flag) {
            ivIsBlack.setBackgroundResource(R.mipmap.open);
            attendAllowYn = BLACK_TRUE;//黑名单
        } else {
            ivIsBlack.setBackgroundResource(R.mipmap.close);
            attendAllowYn = BLACK_FALSE;//1不是黑名单
        }
    }

    public void showNoSpeakingIcon(boolean flag) {
        if (flag) {
            ivIsNoSpeaking.setBackgroundResource(R.mipmap.open);
            attendTalkLimit = NOSPEAKING_TRUE;//1：是禁言
            tvNoSpeakingTime.setVisibility(View.VISIBLE);
            attendTalkTime = "3";//0分钟
            if (!TextUtils.isEmpty(mTalkTime)) {
                tvNoSpeakingTime.setText("禁言".concat(mTalkTime));
            }
        } else {
            ivIsNoSpeaking.setBackgroundResource(R.mipmap.close);
            attendTalkLimit = NOSPEAKING_FALSE;//否禁言(0：否
            tvNoSpeakingTime.setVisibility(View.GONE);
            attendTalkTime = "";
        }
    }

    /**
     * 显示身份弹窗
     *
     * @param show
     */
    private void showIdentityFragment(boolean show) {
        if (mIdentityFragment == null) {
            mIdentityFragment = new IdentityFragment();
            mIdentityFragment.setIdentityListener(this);
        }
        mIdentityFragment.setIdentity_type(attendType);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (show) {
            //防止快速点击
            if (mIdentityFragment.isAdded()) {
                return;
            }
            transaction.add(R.id.fl_identity, mIdentityFragment).commit();
        } else {
            transaction.remove(mIdentityFragment).commit();
        }
    }

    /**
     * 显示禁言时间弹窗
     *
     * @param show
     */
    private void showNospeakingTimeFragment(boolean show) {
        if (mTimeFragment == null) {
            mTimeFragment = new NoSpeakingTimeFragment();
            mTimeFragment.setTimeCheckedListener(this);
        }
        mTimeFragment.setTalkTime(mTalkTime);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (show) {
            //防止快速点击
            if (mTimeFragment.isAdded()) {
                return;
            }
            transaction.add(R.id.fl_identity, mTimeFragment).commit();
        } else {
            transaction.remove(mTimeFragment).commit();
        }
    }

    /**
     * 显示勋章弹窗
     *
     * @param show
     */
    private void showFragment(boolean show) {
        if (mDecorationFragment == null) {
            mDecorationFragment = new DecorationFragment();
            mDecorationFragment.setDecorationListener(this);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (show) {
            //防止快速点击
            if (mDecorationFragment.isAdded()) {
                return;
            }
            transaction.add(R.id.fl_decoration, mDecorationFragment).commit();
        } else {
            transaction.remove(mDecorationFragment).commit();
        }
    }

    /**
     * 关闭勋章列表弹窗的回调
     *
     * @param view
     */
    @Override
    public void closeDecorationListener(View view) {
        showFragment(false);
    }

    /**
     * 选中勋章回调
     *
     * @param view
     */
    @Override
    public void medalIconListener(View view, String ids, List<String> medalImg) {
        this.medalTypeIds = ids;
        mMedalIconAdapter.setNewData(medalImg);
        mMedalIconAdapter.notifyDataSetChanged();
        showFragment(false);
    }

    /**
     * 关闭会员身份弹窗的回调
     *
     * @param view
     */
    @Override
    public void closeIdentityListener(View view) {
        showIdentityFragment(false);
    }

    /**
     * 选中身份回调
     *
     * @param identity
     */
    @Override
    public void checkedIdentityListener(String identity, int type) {
        tvIdentity.setText(identity);
        this.attendType = type;
        showIdentityFragment(false);
    }

    /**
     * 关闭禁言时间弹窗
     *
     * @param view
     */
    @Override
    public void closeTimeCheckedListener(View view) {
        showNoSpeakingIcon(isNospeakingInit);
        showNospeakingTimeFragment(false);

    }

    /**
     * 选中禁言时间弹窗
     */
    @Override
    public void checkedTimeListener(String position, String tip) {
        this.mTalkTime = tip;
        isNospeaking = true;
        showNoSpeakingIcon(isNospeaking);
        this.attendTalkTime = position;
        showNospeakingTimeFragment(false);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            MemberSettingBean bean = (MemberSettingBean) msg.obj;
            switch (msg.what) {
                case HandlerConstant2.SAVE_VIP_SUCCESS:
                    saveVipSuccess(bean);
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    showMsg(getString(R.string.net_error));
                    break;
                default:
                    break;

            }
        }
    };

    /**
     *  保存会员信息成功
     */
    private void saveVipSuccess(MemberSettingBean bean) {
        if (null==bean){
            return;
        }
        if (bean.isStatus()){
            showMsg("保存成功");
            AttendanceBean result=bean.getData().getAttendance();
            Intent mIntent = new Intent();
            mIntent.putExtra(MemberManagerActivity.MEMBER_INFO,result);
            setResult(MemberSettingActivity.RESULT_CODE,mIntent);
            finish();
        }else {
            showMsg(bean.getErrorMsg());
        }
    }
}
