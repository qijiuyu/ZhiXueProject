package com.example.administrator.zhixueproject.adapter.topic;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.topic.TopicListActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import com.example.administrator.zhixueproject.utils.LogUtils;

import java.util.List;


public class TopicListAdapter extends BaseItemDraggableAdapter<TopicListBean, BaseViewHolder> {


    private boolean mIsPostList;
    public static final String TOPIC_ITEM_ID="topicItemId";
    private Context mContext=MyApplication.application;

    public TopicListAdapter(int layoutResId, List<TopicListBean> data, boolean isPostList) {
        super(layoutResId, data);
        this.mIsPostList = isPostList;
    }

    @Override
    protected void convert(BaseViewHolder helper, final TopicListBean item) {
        //关闭复用
        helper.setIsRecyclable(false);

        helper.setText(R.id.tv_topic_name, item.getTopicName());//话题名字
        ImageView iv_topic_img = helper.getView(R.id.iv_topic_img);
        Glide.with(MyApplication.application).load(item.getTopicImg()).into(iv_topic_img);
        int type = item.getTopicType();//话题类型
        String[] topics = mContext.getResources().getStringArray(R.array.topic_type);
        if (type == 1) {
            helper.setText(R.id.tv_post_status, topics[1]);
        } else if (type == 2) {
            helper.setText(R.id.tv_post_status, topics[2]);
        } else if (type == 3) {
            helper.setText(R.id.tv_post_status, topics[0]);
        }
        int costType = item.getTopicPayType();//话题付费类型
        String[] costs = mContext.getResources().getStringArray(R.array.add_topic);
        if (costType == 1) {
            helper.setText(R.id.tv_charge, costs[0]);
            helper.getView(R.id.tv_restrict).setVisibility(View.GONE);
        } else if (costType == 2) {
            helper.setText(R.id.tv_charge, costs[1]);
            helper.getView(R.id.tv_restrict).setVisibility(View.GONE);
        } else if (costType == 3) {
            helper.setText(R.id.tv_restrict, "限制：" + item.getTopicVipName());
            helper.getView(R.id.tv_charge).setVisibility(View.GONE);
        } else if (costType == 4) {
            helper.setText(R.id.tv_restrict, "限制：" + costs[3]);
            helper.getView(R.id.tv_charge).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_topic_time, item.getCreationTime());

        if (mIsPostList) {
            helper.setGone(R.id.menu_right, false);
        } else {
            helper.setVisible(R.id.menu_right, true);
            int topicUseyn = item.getTopicUseyn();//是否上架
            if (topicUseyn == 1) {
                helper.setText(R.id.tv_added, "已上架");
                helper.setText(R.id.tv_menu_two, "下架");//侧滑菜单文字
                helper.setBackgroundColor(R.id.tv_menu_two, mContext.getResources().getColor(R.color.color_ffffff));
                helper.setTextColor(R.id.tv_menu_two, mContext.getResources().getColor(R.color.color_999999));
            } else if (topicUseyn == 0) {
                helper.setText(R.id.tv_added, "已下架");
                helper.setText(R.id.tv_menu_two, "上架");
            }

            helper.addOnClickListener(R.id.tv_menu_one).addOnClickListener(R.id.tv_menu_two).addOnClickListener(R.id.content);
        }

        helper.getView(R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.e("topicItemClicked   id  is: "+item.getTopicId());
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(mContext,TopicListActivity.class);
                intent.putExtra(TOPIC_ITEM_ID,item.getTopicId());
                mContext.startActivity(intent);
            }
        });


    }

}
