package com.example.administrator.zhixueproject.fragment.memberManage;

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
import com.example.administrator.zhixueproject.activity.memberManage.MemberManagerActivity;
import com.example.administrator.zhixueproject.adapter.memberManage.MemberLevelAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.memberManage.MemberLevelBean;
import com.example.administrator.zhixueproject.fragment.BaseFragment;

import java.util.List;

/**
 * 会员等级列表弹窗
 */

public class MemberLevelFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    private List<MemberLevelBean> list;
    private MemberLevelAdapter mMemberLevelAdapter;
    private RecyclerView rv_level_list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fm_member_level, container, false);
        initView(view);
        view.setClickable(true);
        return view;
    }

    private void initView(View view) {
        rv_level_list = (RecyclerView) view.findViewById(R.id.rv_level_list);
        view.findViewById(R.id.rl_level_bg).setOnClickListener(this);
        //首选项中保存的选中level
        String position = MyApplication.spUtil.getString(MemberManagerActivity.LEVEL);
        if (!TextUtils.isEmpty(position)) {
            list.get(Integer.valueOf(position)).setChecked(true);
        } else {
            list.get(0).setChecked(true);
        }

        mMemberLevelAdapter = new MemberLevelAdapter(R.layout.member_level_item, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_level_list.setAdapter(mMemberLevelAdapter);
        rv_level_list.setLayoutManager(linearLayoutManager);
        mMemberLevelAdapter.setOnItemClickListener(this);
    }



    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_level_bg://等级列表下半部分白色透明背景
                //关闭当前的城市列表
                mOnMemberLevelListener.closeLevelListListener(view, getCheckedPosition());//关闭城市列表
                break;
            default:
                break;
        }
    }

    /**
     * 条目点击事件
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        boolean checked = list.get(position).isChecked();
        checked = !checked;
        //如果点击事件是选中等级，将所有等级设为未选中
        if (checked) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setChecked(!checked);
            }
            list.get(position).setChecked(checked);
        }
        mMemberLevelAdapter.notifyDataSetChanged();
        mOnMemberLevelListener.closeLevelListListener(view, position);
    }

    /**
     * 获取选择的会员位置
     *
     * @return
     */
    public int getCheckedPosition() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked()) {
                return i;
            }
        }
        return 0;
    }

    public List<MemberLevelBean> getList() {
        return list;
    }

    public void setList(List<MemberLevelBean> list) {
        this.list = list;
    }

    /**
     * 关闭会员等级列表弹窗的接口回调
     */
    private OnMemberLevelListener mOnMemberLevelListener;

    public interface OnMemberLevelListener {
        void closeLevelListListener(View view, int position);
    }

    public OnMemberLevelListener getOnMemberLevelListener() {
        return mOnMemberLevelListener;
    }

    public void setOnMemberLevelListener(OnMemberLevelListener onMemberLevelListener) {
        mOnMemberLevelListener = onMemberLevelListener;
    }
}
