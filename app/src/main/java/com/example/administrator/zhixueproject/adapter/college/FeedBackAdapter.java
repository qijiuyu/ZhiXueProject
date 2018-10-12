package com.example.administrator.zhixueproject.adapter.college;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.FeedBack;
import java.util.List;

public class FeedBackAdapter extends BaseAdapter{

	private Activity activity;
	private List<FeedBack.FeedBackList> listAll;
	private FeedBack.FeedBackList feedBackList;
	public FeedBackAdapter(Activity activity, List<FeedBack.FeedBackList> listAll) {
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
			view = LayoutInflater.from(activity).inflate(R.layout.feedback_item, null);
			holder.tvName=(TextView)view.findViewById(R.id.tv_feedback_name);
			holder.tvTime=(TextView)view.findViewById(R.id.tv_feedback_time);
			holder.tvType=(TextView)view.findViewById(R.id.tv_feedback_type);
			holder.tvIsRed=(TextView)view.findViewById(R.id.tv_feedback_isRead);
			holder.tvPhone=(TextView)view.findViewById(R.id.tv_feedback_phone);
			holder.tvEmail=(TextView)view.findViewById(R.id.tv_feedback_email);
			holder.tvContent=(TextView)view.findViewById(R.id.tv_feedback_content);

			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		feedBackList=listAll.get(position);
		holder.tvName.setText(feedBackList.getUserName());
		holder.tvTime.setText(feedBackList.getAdviceCreationTime());
		int type = feedBackList.getAdviceType();//反馈来源（0：用户，1：学院）
		String[] feedback_type = activity.getResources().getStringArray(R.array.feedback_type);
		holder.tvType.setText(feedback_type[type]);
		int adviceReadyn = feedBackList.getAdviceReadyn();//是否已读（0：否，1：是）
		String[] adviceReadyn_type = activity.getResources().getStringArray(R.array.adviceReadyn);
		holder.tvIsRed.setText(adviceReadyn_type[adviceReadyn]);
		holder.tvPhone.setText(feedBackList.getUserPhone());
		holder.tvEmail.setText(feedBackList.getUserEmail());
		holder.tvContent.setText(feedBackList.getAdviceContent());
		return view;
	}


	 private class ViewHolder{
		TextView tvName,tvTime,tvType,tvIsRed,tvPhone,tvEmail,tvContent;
	 }
}
