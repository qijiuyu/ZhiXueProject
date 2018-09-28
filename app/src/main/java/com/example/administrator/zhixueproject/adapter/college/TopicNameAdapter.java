package com.example.administrator.zhixueproject.adapter.college;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import java.util.List;

public class TopicNameAdapter extends BaseAdapter{

	private Activity activity;
	private List<TopicListBean> listAll;
	private TopicListBean topicListBean;
	public TopicNameAdapter(Activity activity, List<TopicListBean> listAll) {
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
			view = LayoutInflater.from(activity).inflate(R.layout.topic_item, null);
			holder.tvName=(TextView)view.findViewById(R.id.tv_topic);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		topicListBean=listAll.get(position);
		holder.tvName.setText(topicListBean.getTopicName());
		return view;
	}


	 private class ViewHolder{
		TextView tvName;
	 }
}
