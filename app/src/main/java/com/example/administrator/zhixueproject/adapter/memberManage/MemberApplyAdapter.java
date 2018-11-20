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
 * 会员申请adapter
 */

public class MemberApplyAdapter extends BaseQuickAdapter<AttendanceBean,BaseViewHolder> {
    public MemberApplyAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    public MemberApplyAdapter(@LayoutRes int layoutResId, @Nullable List<AttendanceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AttendanceBean item) {
        ImageView iv_member_head = helper.getView(R.id.iv_member_head);//头像
        GlideCirclePictureUtil.setCircleImg(mContext,item.getUserImg(),iv_member_head);
        ImageView iv_member_level = helper.getView(R.id.iv_member_level);//会员等级图片
        Glide.with(mContext).load(item.getAttendGradeImg()).error(R.mipmap.unify_image_ing).into(iv_member_level);
        helper.setText(R.id.tv_member_name,item.getAttendUsername());//昵称

        if(item.isShow()){
            helper.setVisible(R.id.iv_member_checked,true);
        }else {
            helper.setVisible(R.id.iv_member_checked,false);
        }
        if(item.isChecked()){
            helper.setImageResource(R.id.iv_member_checked,R.mipmap.blue_check);
        }else {
            helper.setImageResource(R.id.iv_member_checked,R.mipmap.gray_uncheck);
        }

    }


}
