package com.example.administrator.zhixueproject.activity.memberManage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.memberManage.MedalIconAdapter;
import com.example.administrator.zhixueproject.bean.memberManage.AttendanceBean;
import com.example.administrator.zhixueproject.fragment.memberManage.PaidQuestionFragment;
import com.example.administrator.zhixueproject.fragment.memberManage.TalkAboutFragment;
import com.flyco.tablayout.SlidingTabLayout;
import java.util.ArrayList;
import java.util.List;

/** 会员详情
 * @author PeterGee
 * @date 2018/10/20
 */
public class MemberDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final int REQUEST_CODE=1;
    private MedalIconAdapter mMedalIconAdapter;
    public static final String ATTEND_ID="attendId";
    private AttendanceBean mMemberInfoBean;
    private SlidingTabLayout mTabTopic;
    private ViewPager mVpTopic;
    private RecyclerView mRvMedal;
    private TextView tvMemberName;
    private ImageView mIvHeadPic;
    private ImageView mIvMemberLevel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        initView();
        initData(mMemberInfoBean);
    }

    private void initView() {
        //会员基本信息
        mMemberInfoBean = getIntent().getParcelableExtra(MemberManagerActivity.MEMBER_INFO);
        TextView tvTitle= (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.member_detail));
        findViewById(R.id.lin_back).setOnClickListener(this);
        TextView tvRight= (TextView) findViewById(R.id.tv_right);
        tvRight.setBackgroundResource(R.mipmap.edit);
        mTabTopic = (SlidingTabLayout) findViewById(R.id.tab_topic);
        mVpTopic = (ViewPager) findViewById(R.id.vp_topic);
        mRvMedal = (RecyclerView) findViewById(R.id.rv_medal);
        tvMemberName = (TextView) findViewById(R.id.tv_member_name);
        mIvHeadPic = (ImageView) findViewById(R.id.iv_head_pic);
        mIvMemberLevel = (ImageView) findViewById(R.id.iv_member_level);
    }

    private void initData(AttendanceBean mMemberInfoBean) {
        if (mMemberInfoBean==null){
            return;
        }
        //头像
        Glide.with(this).load(mMemberInfoBean.getUserImg()).error(R.mipmap.unify_circle_head).into(mIvHeadPic);
        //昵称
        tvMemberName.setText(mMemberInfoBean.getAttendUsername());
        //会员等级图片
        Glide.with(this).load(mMemberInfoBean.getAttendGradeImg()).error(R.mipmap.unify_image_ing).into(mIvMemberLevel);
        //会员勋章图片（最多展示10张）
        List<String> medalTypeMig = mMemberInfoBean.getMedalTypeMig();
        mMedalIconAdapter = new MedalIconAdapter(R.layout.medal_icon_detail_item, medalTypeMig);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvMedal.setLayoutManager(linearLayoutManager);
        mRvMedal.setAdapter(mMedalIconAdapter);
        //初始化大家谈、有偿提问fm
        initFragment();
    }

    /**
     * 初始化大家谈、有偿提问fm
     */
    public void initFragment(){
        ArrayList<Fragment> fmList=new ArrayList<>();
        //初始化各fragment
        TalkAboutFragment talkAboutFragment = new TalkAboutFragment();
        PaidQuestionFragment paidQuestionFragment = new PaidQuestionFragment();
        //将fragment装进列表中
        fmList.add(talkAboutFragment);
        fmList.add(paidQuestionFragment);
        //将名称加载tab名字列表
        String[] titles = {getResources().getString(R.string.talk_about),
                getResources().getString(R.string.paid_question)};
        mTabTopic.setViewPager(mVpTopic,titles,this,fmList);
        // fragment中添加数据
        Bundle bundle = new Bundle();
        bundle.putString(ATTEND_ID, mMemberInfoBean.getAttendId()+"");
        talkAboutFragment.setArguments(bundle);
        paidQuestionFragment.setArguments(bundle);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lin_back:
                Intent mIntent = new Intent();
                mIntent.putExtra(MemberManagerActivity.MEMBER_INFO,mMemberInfoBean);
                setResult(MemberSettingActivity.RESULT_CODE,mIntent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==MemberSettingActivity.RESULT_CODE&&requestCode==REQUEST_CODE){
            mMemberInfoBean = data.getParcelableExtra(MemberManagerActivity.MEMBER_INFO);
            initData(mMemberInfoBean);
        }
    }
}
