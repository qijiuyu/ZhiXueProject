package com.example.administrator.zhixueproject.adapter.topic;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.topic.VoteDetailListBean;

import java.util.ArrayList;
import java.util.List;



public class VoteNeophyteAdapter extends BaseQuickAdapter<VoteDetailListBean, BaseViewHolder> {


    public VoteNeophyteAdapter(@LayoutRes int layoutResId, @Nullable List<VoteDetailListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteDetailListBean item) {
        helper.setText(R.id.tv_vote_name, item.getVoteName());
        helper.setText(R.id.tv_voter_name, item.getUserName());
        helper.setText(R.id.tv_phone_num,"联系方式："+item.getUserPhone());
        helper.setText(R.id.tv_email, "邮箱："+ item.getUserEmail());
        helper.setText(R.id.tv_join_time, item.getVoteCreattime());
        helper.addOnClickListener(R.id.img_delete);
        VoteActionListAdapter mAdapter = new VoteActionListAdapter(R.layout.post_comment_reply_item, item.getVoteSsecName());
        RecyclerView rvCommentReply = helper.getView(R.id.rv_vote_option);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mContext);
        layoutManager.setAutoMeasureEnabled(true);
        rvCommentReply.setLayoutManager(layoutManager);
        rvCommentReply.setAdapter(mAdapter);
    }
}
