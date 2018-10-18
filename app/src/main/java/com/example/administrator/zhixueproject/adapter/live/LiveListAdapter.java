package com.example.administrator.zhixueproject.adapter.live;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.live.Live;
import java.util.List;

public class LiveListAdapter extends BaseAdapter{

	private Activity activity;
	private List<Live.LiveList> listAll;
	private Live.LiveList liveList;
	public LiveListAdapter(Activity activity, List<Live.LiveList> listAll) {
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
			view = LayoutInflater.from(activity).inflate(R.layout.live_list_item, null);
			holder.imageView=(ImageView)view.findViewById(R.id.iv_live_img);
			holder.tvName=(TextView)view.findViewById(R.id.tv_action_name);
			holder.tvTopicName=(TextView)view.findViewById(R.id.tv_topic_name);
			holder.tvTime=(TextView)view.findViewById(R.id.tv_live_time);
			holder.tvTeacher=(TextView)view.findViewById(R.id.tv_lecturer);
			holder.tvContent=(TextView)view.findViewById(R.id.tv_notice_content);
			holder.tvUpdate=(TextView)view.findViewById(R.id.tv_menu_update);
			holder.tvDel=(TextView)view.findViewById(R.id.tv_menu_del);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		liveList=listAll.get(position);
		String imgUrl=liveList.getUserImg();
		holder.imageView.setTag(R.id.imageid,imgUrl);
		if(holder.imageView.getTag(R.id.imageid)!=null && imgUrl==holder.imageView.getTag(R.id.imageid)){
			Glide.with(activity).load(imgUrl).override(105,77).centerCrop().into(holder.imageView);
		}
		holder.tvName.setText(liveList.getPostName());
		holder.tvTopicName.setText("话题："+liveList.getTopicName());
		holder.tvTime.setText(liveList.getPostLivetime());
		holder.tvTeacher.setText("讲师："+liveList.getUserName());
		holder.tvContent.setText("预告："+liveList.getPostInfo());
		return view;
	}


	 private class ViewHolder{
		private ImageView imageView;
		TextView tvTime,tvName,tvTopicName,tvTeacher,tvContent,tvUpdate,tvDel;
	 }
}
