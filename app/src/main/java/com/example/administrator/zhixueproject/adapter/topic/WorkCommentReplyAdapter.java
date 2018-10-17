package com.example.administrator.zhixueproject.adapter.topic;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.bean.eventBus.PostEvent;
import com.example.administrator.zhixueproject.bean.topic.PostsDetailsBean;
import org.greenrobot.eventbus.EventBus;
import java.util.List;



public class WorkCommentReplyAdapter extends BaseQuickAdapter<PostsDetailsBean.PostDetailBeanOuter.WorkCommentListBean.TalkInfoBean, BaseViewHolder> {

    private int floorId;

    public WorkCommentReplyAdapter(@LayoutRes int layoutResId, @Nullable List<PostsDetailsBean.PostDetailBeanOuter.WorkCommentListBean.TalkInfoBean> data, int floorId) {
        super(layoutResId, data);
        this.floorId = floorId;
    }

    @Override
    protected void convert(BaseViewHolder helper, PostsDetailsBean.PostDetailBeanOuter.WorkCommentListBean.TalkInfoBean item) {
        final String finalMFloorUserId = PostCommentReplyAdapter.showReply(helper, item.getWorkTalkStr(), floorId);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回复楼层的回调
                EventBus.getDefault().post(new PostEvent().setEventType(PostEvent.REPLY_WORK_COMMENT).setData(finalMFloorUserId + "=" + floorId));
            }
        });
    }


}
