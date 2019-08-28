package com.example.administrator.zhixueproject.adapter.topic;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.topic.AddVoteBean;
import com.example.administrator.zhixueproject.bean.topic.VoteDetailListBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class VoteNeophyteAdapter extends BaseQuickAdapter<VoteDetailListBean, BaseViewHolder> {
    ArrayList<AddVoteBean> list = new ArrayList<>();

    public VoteNeophyteAdapter(@LayoutRes int layoutResId, @Nullable List<VoteDetailListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteDetailListBean item) {
        helper.setText(R.id.tv_vote_name, item.getVoteName());
        helper.setText(R.id.tv_voter_name, item.getUserName());
        helper.setText(R.id.tv_phone_num, "联系方式：" + item.getUserPhone());
        helper.setText(R.id.tv_email, "邮箱：" + item.getUserEmail());
        helper.setText(R.id.tv_join_time, item.getVoteCreattime());
        helper.addOnClickListener(R.id.img_delete);

        String voteSecNames = item.getVoteSecNames();
       // String voteSecNames = "[{\"content\":\"微微一笑\"},{\"content\":\"你问一下\"}]";
        if (TextUtils.isEmpty(voteSecNames)) return;
        try {
            JSONArray jsonArray = new JSONArray(voteSecNames);
            if (jsonArray.length() == 0) return;
            for (int i = 0; i < jsonArray.length(); i++) {
                AddVoteBean bean = new AddVoteBean();
                JSONObject innerObject = jsonArray.getJSONObject(i);
                bean.setContent(innerObject.getString("content"));
                list.add(bean);
            }

        } catch (Exception e) {

        }

        VoteActionListAdapter mAdapter = new VoteActionListAdapter(R.layout.post_comment_reply_item, list);
        RecyclerView rvCommentReply = helper.getView(R.id.rv_vote_option);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setAutoMeasureEnabled(true);
        rvCommentReply.setLayoutManager(layoutManager);
        rvCommentReply.setAdapter(mAdapter);
    }
}
