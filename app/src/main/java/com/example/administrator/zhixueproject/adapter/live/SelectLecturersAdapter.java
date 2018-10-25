package com.example.administrator.zhixueproject.adapter.live;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.live.TeacherListBean;
import com.example.administrator.zhixueproject.utils.GlideCirclePictureUtil;

import java.util.List;



public class SelectLecturersAdapter extends BaseQuickAdapter<TeacherListBean, BaseViewHolder> {

    public SelectLecturersAdapter(@LayoutRes int layoutResId, @Nullable List<TeacherListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeacherListBean item) {

        helper.setText(R.id.tv_name, item.getUserName());
        ImageView ivHeadImg = helper.getView(R.id.iv_head_img);
        GlideCirclePictureUtil.setCircleImg(mContext,item.getUserImg(),ivHeadImg);
        helper.setText(R.id.tv_id, "ID："+item.getTeacherId());
    }
}
