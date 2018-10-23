package com.example.administrator.zhixueproject.adapter.memberManage;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import java.util.List;

/**
 * 会员设置勋章图标adapter
 */

public class MedalIconAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public MedalIconAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_medal_pic = helper.getView(R.id.iv_medal_pic);//勋章图片
        Glide.with(mContext).load(item).error(R.mipmap.medal_icon).into(iv_medal_pic);
    }
}
