package com.example.administrator.zhixueproject.adapter.memberManage;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.memberManage.AttendanceBean;
import com.example.administrator.zhixueproject.utils.GlideCirclePictureUtil;
import java.util.List;

/**
 * 黑名单adapter
 */

public class BlackListAdapter extends BaseQuickAdapter<AttendanceBean,BaseViewHolder>{
    public BlackListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AttendanceBean item) {
        ImageView iv_blacklist_head = helper.getView(R.id.iv_blacklist_head);//头像
        GlideCirclePictureUtil.setCircleImg(mContext,item.getUserImg(),iv_blacklist_head);
        ImageView iv_blacklist_level = helper.getView(R.id.iv_blacklist_level);//会员等级图片
        Glide.with(mContext).load(item.getAttendGradeImg()).error(R.mipmap.unify_image_ing).into(iv_blacklist_level);

        helper.setText(R.id.tv_blacklist_name,item.getAttendUsername());//昵称
        helper.setText(R.id.tv_black_time,item.getUpdatetime());//拉黑时间
        helper.addOnClickListener(R.id.tv_cancel_black);//取消拉黑
    }
}
