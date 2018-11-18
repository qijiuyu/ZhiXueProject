package com.example.administrator.zhixueproject.adapter.college;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.ReportDetails;
import java.util.List;

public class ReportDetailsAdapter extends BaseAdapter{

	private Activity activity;
	private List<ReportDetails.listBean> listAll;
	private ReportDetails.listBean listBean;
	public ReportDetailsAdapter(Activity activity, List<ReportDetails.listBean> listAll) {
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
			view = LayoutInflater.from(activity).inflate(R.layout.report_details_item, null);
			holder.imageView=(ImageView)view.findViewById(R.id.img_head);
			holder.tvUserName=(TextView)view.findViewById(R.id.tv_userName);
			holder.tvType=(TextView)view.findViewById(R.id.tv_report_type);
			holder.tvContent=(TextView)view.findViewById(R.id.tv_content);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		listBean=listAll.get(position);
		String imgUrl=listBean.getUserImg();
		holder.imageView.setTag(R.id.imageid,imgUrl);
		if(holder.imageView.getTag(R.id.imageid)!=null && imgUrl==holder.imageView.getTag(R.id.imageid)){
			Glide.with(activity).load(imgUrl).override(50,50).centerCrop().into(holder.imageView);
		}
		holder.tvUserName.setText(listBean.getUserName());
		switch (listBean.getComplaintInfoType()){
			case 0:
				 holder.tvType.setText("侵权");
				 break;
			case 1:
				holder.tvType.setText("淫秽色情");
				break;
			case 2:
				holder.tvType.setText("内容与标题不符");
				break;
			case 3:
				holder.tvType.setText("敏感信息");
				break;
			case 4:
				holder.tvType.setText("垃圾广告");
				break;
		}
		holder.tvContent.setText(listBean.getComplaintInfo());
		return view;
	}


	 private class ViewHolder{
		private ImageView imageView;
		TextView tvUserName,tvType,tvContent;
	 }
}
