package com.example.administrator.zhixueproject.adapter.topic;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import com.example.administrator.zhixueproject.utils.LogUtils;

import java.util.List;

/**
 *
 * 添加所属话题
 */

public class AddTopicAdapter extends BaseQuickAdapter<TopicListBean, BaseViewHolder> {

    public AddTopicAdapter(@LayoutRes int layoutResId, @Nullable List<TopicListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicListBean item) {
        LogUtils.e("话题名字==="+item.getTopicName());
        helper.setText(R.id.tv_topic, item.getTopicName());
    }
}
