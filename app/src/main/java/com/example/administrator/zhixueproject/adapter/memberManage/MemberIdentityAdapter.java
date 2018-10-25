package com.example.administrator.zhixueproject.adapter.memberManage;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.memberManage.MemberIdentityBean;
import java.util.List;

/**
 * 会员身份adapter
 */

public class MemberIdentityAdapter extends BaseQuickAdapter<MemberIdentityBean,BaseViewHolder> {
    public MemberIdentityAdapter(@LayoutRes int layoutResId, @Nullable List<MemberIdentityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberIdentityBean item) {
        helper.setText(R.id.tv_member_identity,item.getIdentity());
        if(item.isChecked()){
            helper.setTextColor(R.id.tv_member_identity,mContext.getResources().getColor(R.color.color_ff0000));
        }else {
            helper.setTextColor(R.id.tv_member_identity,mContext.getResources().getColor(R.color
                    .color_999999));
        }
    }
}
