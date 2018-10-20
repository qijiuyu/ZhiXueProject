package com.example.administrator.zhixueproject.adapter.memberManage;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.memberManage.MemberLevelBean;
import java.util.List;

/**
 *
 * 会员等级adapter
 */

public class MemberLevelAdapter extends BaseQuickAdapter<MemberLevelBean,BaseViewHolder> {
    public MemberLevelAdapter(@LayoutRes int layoutResId, @Nullable List<MemberLevelBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberLevelBean item) {
        helper.setText(R.id.tv_member_level,item.getUserCollegegradeName());
        if(item.isChecked()){
            helper.setBackgroundColor(R.id.rl_member_level,mContext.getResources().getColor(R
                    .color.color_f8f8f8));
            helper.setTextColor(R.id.tv_member_level,mContext.getResources().getColor(R.color.color_fd703e));
        }else {
            helper.setBackgroundColor(R.id.rl_member_level,mContext.getResources().getColor(R
                    .color.color_ffffff));
            helper.setTextColor(R.id.tv_member_level,mContext.getResources().getColor(R.color
                    .color_666666));
        }
    }
}
