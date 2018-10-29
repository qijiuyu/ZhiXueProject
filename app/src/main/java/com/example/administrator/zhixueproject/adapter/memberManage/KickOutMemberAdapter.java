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
 * 踢出的会员adapter
 */

public class KickOutMemberAdapter extends BaseQuickAdapter<AttendanceBean,BaseViewHolder>{
    public KickOutMemberAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    public KickOutMemberAdapter(@LayoutRes int layoutResId, @Nullable List<AttendanceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AttendanceBean item) {
        ImageView iv_kick_member_head = helper.getView(R.id.iv_kick_member_head);//头像
        GlideCirclePictureUtil.setCircleImg(mContext,item.getUserImg(),iv_kick_member_head);
        ImageView iv_kick_member_level = helper.getView(R.id.iv_kick_member_level);//会员等级图片
        Glide.with(mContext).load(item.getAttendGradeImg()).error(R.mipmap.unify_image_ing).into(iv_kick_member_level);
        helper.setText(R.id.tv_kick_member_name,item.getAttendUsername());//昵称
        helper.setText(R.id.tv_kick_time,item.getUpdatetime());//踢出时间
        helper.addOnClickListener(R.id.tv_invite);//邀请
    }
}
