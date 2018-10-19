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





public class PostsTaskAdapter extends BaseQuickAdapter<PostsDetailsBean.PostDetailBeanOuter.PostCommentListBean, BaseViewHolder> {


    public PostsTaskAdapter(@LayoutRes int layoutResId, @Nullable List<PostsDetailsBean.PostDetailBeanOuter.PostCommentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final PostsDetailsBean.PostDetailBeanOuter.PostCommentListBean item) {
        helper.setText(R.id.tv_nickname, item.getFloorInfo().getUserName());
        helper.setText(R.id.tv_time, item.getFloorInfo().getFloorCreationtime());
        Glide.with(mContext).load(item.getFloorInfo().getUserImg()).error(R.mipmap.unify_circle_head).into((ImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_comment_floor, item.getFloorInfo().getFloorData());

        PostCommentReplyAdapter mAdapter = new PostCommentReplyAdapter(R.layout.post_comment_reply_item, item.getTalkInfo(),item.getFloorInfo().getFloorId());
        RecyclerView rvCommentReply = helper.getView(R.id.rv_comment_reply);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mContext);
        layoutManager.setAutoMeasureEnabled(true);
        rvCommentReply.setLayoutManager(layoutManager);
        rvCommentReply.setAdapter(mAdapter);
        rvCommentReply.setNestedScrollingEnabled(false);
    }
}
