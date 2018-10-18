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
import com.example.administrator.zhixueproject.bean.Report;
import java.util.List;

public class ReportListAdapter extends BaseAdapter{

	private Activity activity;
	private List<Report.ReportList> listAll;
	private Report.ReportList reportList;
	public ReportListAdapter(Activity activity, List<Report.ReportList> listAll) {
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
			view = LayoutInflater.from(activity).inflate(R.layout.buyiness_out_item, null);
			holder.imgChoose=(ImageView)view.findViewById(R.id.iv_choose);
			holder.imageView=(ImageView)view.findViewById(R.id.iv_img);
			holder.tvTopicName=(TextView)view.findViewById(R.id.tv_report_topic);
			holder.tvCount=(TextView)view.findViewById(R.id.tv_count);
			holder.tvReportName=(TextView)view.findViewById(R.id.tv_report_name);
			holder.tvTime=(TextView)view.findViewById(R.id.tv_time);
			holder.tvContent=(TextView)view.findViewById(R.id.tv_content);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		reportList=listAll.get(position);
		String imgUrl=reportList.getTopicImg();
		holder.imageView.setTag(R.id.imageid,imgUrl);
		if(holder.imageView.getTag(R.id.imageid)!=null && imgUrl==holder.imageView.getTag(R.id.imageid)){
			Glide.with(activity).load(imgUrl).override(105,78).centerCrop().into(holder.imageView);
		}
		holder.tvTopicName.setText(reportList.getPostName());
		holder.tvReportName.setText(reportList.getPostWriterId());
		holder.tvTime.setText(reportList.getComplaintCreationTime());
		holder.tvCount.setText(reportList.getComplaintCount()+"人  举报");
		holder.tvContent.setText(reportList.getComplaintContent());
		return view;
	}


	 private class ViewHolder{
		private ImageView imageView,imgChoose;
		TextView tvTopicName,tvCount,tvReportName,tvTime,tvContent;
	 }
}
