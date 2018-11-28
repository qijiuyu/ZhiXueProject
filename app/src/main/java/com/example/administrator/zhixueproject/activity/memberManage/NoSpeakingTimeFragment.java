package com.example.administrator.zhixueproject.activity.memberManage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.adapter.memberManage.MemberIdentityAdapter;
import com.example.administrator.zhixueproject.bean.memberManage.MemberIdentityBean;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.utils.CommonUtils;
import com.example.administrator.zhixueproject.utils.CustomDialogUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 禁言时间弹窗
 */

public class NoSpeakingTimeFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    private List<MemberIdentityBean> list;
    private MemberIdentityAdapter mMemberIdentityAdapter;
    private String[] mTimes;
    private String checkedPosition;
    private String mTalkTime;//禁言终止时间
    private RecyclerView rvIdentityList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_identity,container,false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        rvIdentityList = (RecyclerView) view.findViewById(R.id.rv_identity_list);
        view.setClickable(true);
        view.findViewById(R.id.bg_identity).setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_confirm).setOnClickListener(this);
    }

    private void initData() {
        mTimes = getResources().getStringArray(R.array.nospeaking_time);
        list=new ArrayList<>();
        checkedPosition="";
        for (int i = 0; i < mTimes.length; i++) {
            MemberIdentityBean bean = new MemberIdentityBean();
            bean.setIdentity(mTimes[i]);
            bean.setChecked(false);
            list.add(bean);
        }

        mMemberIdentityAdapter = new MemberIdentityAdapter(R.layout.member_identity_item, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvIdentityList.setAdapter(mMemberIdentityAdapter);
        rvIdentityList.setLayoutManager(linearLayoutManager);
        mMemberIdentityAdapter.setOnItemClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bg_identity://会员身份上半部分白色透明背景
                mTimeCheckedListener.closeTimeCheckedListener(view);//关闭禁言时间选择弹窗
                break;
            case R.id.tv_cancel://取消
                mTimeCheckedListener.closeTimeCheckedListener(view);
                break;
            case R.id.tv_confirm://确定
                if(!TextUtils.isEmpty(checkedPosition)){
                    // mTimeCheckedListener.checkedTimeListener(checkedPosition,timeTip(getMinite()));
                    mTimeCheckedListener.checkedTimeListener(checkedPosition,getMinite());
                }else {
                    showTipPop();
                }
                break;
            default:
                break;
        }
    }
    /**
     * 勋章条目点击事件
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(false);
        }
        list.get(position).setChecked(true);
        checkedPosition=position+"";
        mMemberIdentityAdapter.notifyDataSetChanged();
    }

    /**
     * 显示“请选择禁言时间”弹框提示
     */
    public void showTipPop(){
        CustomDialogUtils.getInstance().createDialog(getContext(), getResources().getString(R
                .string.nospeaking_time_tip), new View.OnClickListener() {
            @Override
            public void onClick(View v) {//确定

            }}, new View.OnClickListener() {
            @Override
            public void onClick(View v) {//取消
                mTimeCheckedListener.closeTimeCheckedListener(v);
            }
        });
    }
    public String getMinite(){
        if(TextUtils.equals(checkedPosition,"0")){
            return 10+"分钟";
        }else if(TextUtils.equals(checkedPosition,"1")){
            return 30+"分钟";
        }else {
            return 1+"小时";
        }
    }
    /**
     * 提示：禁言至xxxx-xx-xx xx:xx:xx
     * @param minute
     * @return
     */
    public String timeTip(int minute){
        long currentTimeMillis = 0;
        if(TextUtils.isEmpty(mTalkTime)){
            currentTimeMillis = System.currentTimeMillis();
        }else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                currentTimeMillis = format.parse(mTalkTime).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        currentTimeMillis+=minute*60*1000;
        Date date = new Date(currentTimeMillis);
        String time = CommonUtils.getHHmmss(date);
        return time;
    }
    /**
     * 关闭禁言时间列表弹窗的接口回调
     */
    private OnTimeCheckedListener mTimeCheckedListener;
    public interface OnTimeCheckedListener{
        void closeTimeCheckedListener(View view);
        void checkedTimeListener(String position, String tip);
    }
    public OnTimeCheckedListener getTimeCheckedListener() {
        return mTimeCheckedListener;
    }

    public void setTimeCheckedListener(OnTimeCheckedListener timeCheckedListener) {
        mTimeCheckedListener = timeCheckedListener;
    }
    public String getTalkTime() {
        return mTalkTime;
    }

    public void setTalkTime(String talkTime) {
        mTalkTime = talkTime;
    }
}
