package com.example.administrator.zhixueproject.adapter.topic;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.topic.PostsDetailsBean;
import java.util.List;



public class WorkCommentListAdapter extends BaseQuickAdapter<PostsDetailsBean.WorkCommentListBean, BaseViewHolder> {


    public WorkCommentListAdapter(@LayoutRes int layoutResId, @Nullable List<PostsDetailsBean.WorkCommentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PostsDetailsBean.WorkCommentListBean item) {

        helper.setText(R.id.tv_nickname, item.getFloorInfo().getUserName());
        helper.setText(R.id.tv_time, item.getFloorInfo().getFloorCreationtime());
        Glide.with(mContext).load(item.getFloorInfo().getUserImg()).error(R.mipmap.unify_circle_head).into((ImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_comment_floor, item.getFloorInfo().getFloorData());

        RecyclerView rv_comment_reply = helper.getView(R.id.rv_comment_reply);
        WorkCommentReplyAdapter mAdapter = new WorkCommentReplyAdapter(R.layout.post_comment_reply_item, item.getTalkInfo(),item.getFloorInfo().getFloorId());
//        mAdapter.setData(item.getVoteOptionBean());
        rv_comment_reply.setAdapter(mAdapter);
        rv_comment_reply.setLayoutManager(new LinearLayoutManager(mContext));
        rv_comment_reply.setNestedScrollingEnabled(false);

    }
}
