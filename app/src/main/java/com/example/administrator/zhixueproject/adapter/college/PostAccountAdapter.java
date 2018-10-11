package com.example.administrator.zhixueproject.adapter.college;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.Post;

import java.util.List;

public class PostAccountAdapter extends BaseAdapter{

	private Activity activity;
	private List<Post.PostList> listAll;
	private Post.PostList postList;
	public PostAccountAdapter(Activity activity, List<Post.PostList> listAll) {
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
			view = LayoutInflater.from(activity).inflate(R.layout.post_account_item, null);
			holder.tvPostName=(TextView)view.findViewById(R.id.tv_post_name);
			holder.tvTopicName=(TextView)view.findViewById(R.id.tv_topic_name);
			holder.tvMoney=(TextView)view.findViewById(R.id.tv_tai_money);
			holder.tvTime=(TextView)view.findViewById(R.id.tv_time);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		postList=listAll.get(position);
		holder.tvPostName.setText(postList.getPostName());
		holder.tvTopicName.setText(postList.getTopicName());
		holder.tvMoney.setText(postList.getSumCost()+"元");
		holder.tvTime.setText(postList.getCreateDate());
		return view;
	}


	 private class ViewHolder{
		TextView tvPostName,tvTopicName,tvMoney,tvTime;
	 }
}
