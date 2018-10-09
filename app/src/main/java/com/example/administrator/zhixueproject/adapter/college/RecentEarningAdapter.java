package com.example.administrator.zhixueproject.adapter.college;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.RecentEarningList;
import java.util.List;

public class RecentEarningAdapter extends BaseQuickAdapter<RecentEarningList, BaseViewHolder> {

	public RecentEarningAdapter(@LayoutRes int layoutResId, @Nullable List<RecentEarningList> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, RecentEarningList item) {
		helper.setText(R.id.tv_income_type, item.getTypeName());//收益类型

		helper.setText(R.id.tv_income_price, item.getMoney());
		helper.setImageResource(R.id.iv_income_icon, item.getIcon());
	}
}
