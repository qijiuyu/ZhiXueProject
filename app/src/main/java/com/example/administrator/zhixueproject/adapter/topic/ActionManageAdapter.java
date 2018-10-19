package com.example.administrator.zhixueproject.adapter.topic;

import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.topic.ActionNeophyteActivity;
import com.example.administrator.zhixueproject.bean.topic.ActivityListBean;
import java.util.List;





public class ActionManageAdapter extends BaseItemDraggableAdapter<ActivityListBean,BaseViewHolder> {

    public ActionManageAdapter(int layoutResId, List<ActivityListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ActivityListBean item) {
        helper.setText(R.id.tv_action_name,item.getActivityName());
        helper.setText(R.id.tv_topic_name,"话题："+item.getTopicName());
        helper.setText(R.id.tv_start_time, item.getStartTime());
        helper.setText(R.id.tv_end_time, item.getEndTime());
        helper.setText(R.id.tv_action_num, item.getPostJoinNum()+"");
        ImageView iv_action_img = helper.getView(R.id.iv_action_img);
        Glide.with(mContext).load(item.getPostPicture()).error(R.mipmap.unify_image_ing).into(iv_action_img);

        helper.addOnClickListener(R.id.tv_menu_one).addOnClickListener(R.id.tv_menu_two);

        helper.getView(R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionNeophyteActivity.start(mContext, item.getActivityId()+"");
            }
        });

    }

    private OnItemListener onItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public interface OnItemListener{
        void itemListener(View view, int position);
    }

}
