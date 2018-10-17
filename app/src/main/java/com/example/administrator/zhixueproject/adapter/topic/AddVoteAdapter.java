package com.example.administrator.zhixueproject.adapter.topic;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.topic.AddVoteBean;
import java.util.List;

/**
 * 发布投票
 */

public class AddVoteAdapter extends BaseQuickAdapter<AddVoteBean, BaseViewHolder> {

    public AddVoteAdapter(@LayoutRes int layoutResId, @Nullable List<AddVoteBean> data) {
        super(layoutResId, data);
    }

    public void setList(List<AddVoteBean> list) {
        this.list = list;
    }

    private List<AddVoteBean> list;



    @Override
    protected void convert(final BaseViewHolder helper, final AddVoteBean item) {

        helper.setText(R.id.tv_vote_content, item.getContent());

        helper.getView(R.id.tv_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(helper.getLayoutPosition());
                notifyDataSetChanged();
            }
        });

    }
}
