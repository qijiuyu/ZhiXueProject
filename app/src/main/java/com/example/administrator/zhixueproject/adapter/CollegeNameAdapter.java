package com.example.administrator.zhixueproject.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.Colleges;
import com.example.administrator.zhixueproject.utils.LogUtils;

import java.util.List;


/**
 * 加入的学院
 * @author petergee
 * @date 2018/9/20
 */
public class CollegeNameAdapter extends BaseQuickAdapter<Colleges, BaseViewHolder> {
    public CollegeNameAdapter(@LayoutRes int layoutResId, @Nullable List<Colleges> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Colleges item) {
        LogUtils.e(helper.getAdapterPosition()+"++++++++++");
        helper.setText(R.id.tv_college_name, item.getCollegeName());
        ImageView imgCollegeIcon = helper.getView(R.id.iv_college_img);
        Glide.with(MyApplication.application).load(item.getCollegeLogo()).override(60,44).into(imgCollegeIcon);

    }
}
