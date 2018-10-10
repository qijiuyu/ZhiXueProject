package com.example.administrator.zhixueproject.adapter.college;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.TopicAccount;
import java.util.List;

public class TopicAccountAdapter extends BaseAdapter{

	private Activity activity;
	private List<TopicAccount.TopicAccountList> listAll;
	private TopicAccount.TopicAccountList topicAccountList;
	public TopicAccountAdapter(Activity activity, List<TopicAccount.TopicAccountList> listAll) {
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
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		topicAccountList=listAll.get(position);
		holder.tvName.setText(topicAccountList.getTopicName());
		holder.tvTime.setText(topicAccountList.getCreateDate());
		holder.tvMoney.setText(topicAccountList.getSumCost());
		return view;
	}


	 private class ViewHolder{
		TextView tvName,tvTime,tvMoney;
	 }
}
