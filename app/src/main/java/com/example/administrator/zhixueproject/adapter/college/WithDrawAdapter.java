package com.example.administrator.zhixueproject.adapter.college;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.WithDraw;
import java.util.List;

public class WithDrawAdapter extends BaseAdapter{

	private Activity activity;
	private List<WithDraw.WithDrawList> listAll;
	private WithDraw.WithDrawList withDrawList;
	public WithDrawAdapter(Activity activity, List<WithDraw.WithDrawList> listAll) {
		super();
		this.activity = activity;
		this.listAll=listAll;
	}

	@Override
	public int getCount() {
		return listAll==null ? 0 : listAll.size();
	}

	@Override
	public Object getItem(int position) {
		return listAll.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		if(view==null){
			holder = new ViewHolder(); 
			view = LayoutInflater.from(activity).inflate(R.layout.withdraw_item, null);
			holder.tvMoney=(TextView)view.findViewById(R.id.tv_cash_money);
			holder.tvApplyTime=(TextView)view.findViewById(R.id.tv_apply_time);
			holder.tvState=(TextView)view.findViewById(R.id.tv_pay_state);
			holder.tvUpdateTime=(TextView)view.findViewById(R.id.tv_update_time);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		withDrawList=listAll.get(position);
		holder.tvMoney.setText(withDrawList.getCashValue()+"元");
		holder.tvApplyTime.setText(withDrawList.getCashCreationtime());
		switch (withDrawList.getPayStatus()){
			case 0:
				 holder.tvState.setText("申请中");
				 break;
			case 1:
				 holder.tvState.setText("已审批打款");
				 break;
			case 2:
				 holder.tvState.setText("驳回申请");
				 break;
				 default:
				 	break;
		}
		holder.tvUpdateTime.setText(withDrawList.getUpdatetime());
		return view;
	}


	 private class ViewHolder{
		TextView tvMoney,tvApplyTime,tvState,tvUpdateTime;
	 }
}
