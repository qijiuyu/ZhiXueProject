package com.example.administrator.zhixueproject.adapter.topic;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.topic.CostsListBean;
import java.util.List;


public class CostsListAdapter extends BaseQuickAdapter<CostsListBean,BaseViewHolder> {


    public CostsListAdapter(@LayoutRes int layoutResId, @Nullable List<CostsListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CostsListBean item) {
        helper.setText(R.id.tv_cost_item,item.getContent());
        if(item.isChecked()){
            helper.setTextColor(R.id.tv_cost_item,mContext.getResources().getColor(R.color.color_ff212c));
        }else {
            helper.setTextColor(R.id.tv_cost_item,mContext.getResources().getColor(R.color.color_666666));
        }
    }
}
