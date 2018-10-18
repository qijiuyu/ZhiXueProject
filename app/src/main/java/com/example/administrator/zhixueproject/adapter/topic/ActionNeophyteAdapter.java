package com.example.administrator.zhixueproject.adapter.topic;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.topic.ActivityUserListBean;

import java.util.List;



public class ActionNeophyteAdapter extends BaseQuickAdapter<ActivityUserListBean,BaseViewHolder> {

    public ActionNeophyteAdapter(@LayoutRes int layoutResId, @Nullable List<ActivityUserListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivityUserListBean item) {

        helper.setText(R.id.tv_player, item.getUserName());
        helper.setText(R.id.tv_phone_num, "联系方式："+item.getTel());
        helper.setText(R.id.tv_join_time, item.getActivityTime());

    }
}
