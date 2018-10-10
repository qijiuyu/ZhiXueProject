package com.example.administrator.zhixueproject.adapter.college;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.Teacher;
import com.example.administrator.zhixueproject.view.CircleImageView;
import java.util.List;

public class SelectTeacherAdapter extends BaseAdapter{

	private Activity activity;
	private List<Teacher> listAll;
	private Teacher teacher;
	public SelectTeacherAdapter(Activity activity, List<Teacher> listAll) {
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
			view = LayoutInflater.from(activity).inflate(R.layout.teacher_item, null);
			holder.imageView=(CircleImageView)view.findViewById(R.id.iv_head_img);
			holder.tvName=(TextView)view.findViewById(R.id.tv_name);
			holder.tvId=(TextView)view.findViewById(R.id.tv_id);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		teacher=listAll.get(position);
		String imgUrl=teacher.getUserImg();
		holder.imageView.setTag(R.id.imageid,imgUrl);
		if(holder.imageView.getTag(R.id.imageid)!=null && imgUrl==holder.imageView.getTag(R.id.imageid)){
			Glide.with(activity).load(imgUrl).override(36,36).centerCrop().into(holder.imageView);
		}
		holder.tvName.setText(teacher.getUserName());
		holder.tvId.setText(teacher.getTeacherId()+"");
		return view;
	}


	 private class ViewHolder{
		 CircleImageView imageView;
		 TextView tvName,tvId;
	 }
}
