package com.example.administrator.zhixueproject.adapter.college;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.GiveAccount;
import java.util.List;

public class GiveAccountAdapter extends BaseAdapter{

	private Activity activity;
	private List<GiveAccount.GiveList> listAll;
	private GiveAccount.GiveList giveList;
	public GiveAccountAdapter(Activity activity, List<GiveAccount.GiveList> listAll) {
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
			view = LayoutInflater.from(activity).inflate(R.layout.topic_account_item, null);
			holder.tvName=(TextView)view.findViewById(R.id.tv_tai_name);
			holder.tvTime=(TextView)view.findViewById(R.id.tv_tai_time);
			holder.tvMoney=(TextView)view.findViewById(R.id.tv_tai_money);
			holder.tvDes=(TextView) view.findViewById(R.id.tv_des);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		giveList=listAll.get(position);
		holder.tvName.setText(giveList.getGiveToName());
		holder.tvTime.setText(giveList.getCreateDate());
		holder.tvMoney.setText(giveList.getSumCost()+"元");
		holder.tvDes.setText("礼物收益金额");
		return view;
	}


	 private class ViewHolder{
		TextView tvName,tvTime,tvMoney,tvDes;
	 }
}
