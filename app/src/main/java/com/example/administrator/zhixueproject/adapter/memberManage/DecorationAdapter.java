package com.example.administrator.zhixueproject.adapter.memberManage;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.memberManage.MedalInfoBean;
import java.util.List;


/**
 * 勋章列表
 */

public class DecorationAdapter extends BaseQuickAdapter<MedalInfoBean,BaseViewHolder> {
    public DecorationAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    public DecorationAdapter(@LayoutRes int layoutResId, @Nullable List<MedalInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MedalInfoBean item) {
        //勋章图片
        ImageView iv_member_medal = helper.getView(R.id.iv_member_medal);
        Glide.with(mContext).load(item.getMedalTypeMig()).error(R.mipmap.medal_icon).into(iv_member_medal);

        helper.setText(R.id.tv_decoration_name,item.getMedalTypeName());
        if(item.isChecked()){
            helper.setImageResource(R.id.iv_member_checked,R.mipmap.blue_check);
        }else {
            helper.setImageResource(R.id.iv_member_checked,R.mipmap.gray_uncheck);
        }
    }
}
