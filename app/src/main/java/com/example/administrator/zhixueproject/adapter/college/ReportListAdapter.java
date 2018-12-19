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
import com.example.administrator.zhixueproject.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportListAdapter extends BaseAdapter{

	private Activity activity;
	private List<Report.ReportList> listAll;
	private Report.ReportList reportList;
	public boolean isSelect=false;
	public boolean isAllSelect=false;
	public Map<String,String> maps=new HashMap<>();
	public ReportListAdapter(Activity activity, List<Report.ReportList> listAll) {
		super();
		this.activity = activity;
		this.listAll=listAll;
	}

	@Override
	public int getCount() {
		return listAll.size();
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
			view = LayoutInflater.from(activity).inflate(R.layout.topic_report_item, null);
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
		holder.tvContent.setText(reportList.getComplaintInfo());
		holder.imgChoose.setTag(reportList.getComplaintToId());
		if(isSelect){
			holder.imgChoose.setVisibility(View.VISIBLE);
			holder.imgChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.unchecked_gray_report));
			holder.imgChoose.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if(null==v.getTag()){
						return;
					}
					ImageView imageView= (ImageView) v;
					final String complaintToId=v.getTag().toString();
					if(maps.get(complaintToId)==null){
						maps.put(complaintToId,complaintToId);
						imageView.setImageDrawable(activity.getResources().getDrawable(R.mipmap.checked_blue_report));
					}else{
						maps.remove(complaintToId);
						imageView.setImageDrawable(activity.getResources().getDrawable(R.mipmap.unchecked_gray_report));
					}
				}
			});
		}else{
            holder.imgChoose.setVisibility(View.GONE);
            holder.imgChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.unchecked_gray_report));
        }
		if(isAllSelect){
			holder.imgChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.checked_blue_report));
			if(maps.get(String.valueOf(reportList.getComplaintToId()))==null){
				maps.put(reportList.getComplaintToId()+"",reportList.getComplaintToId()+"");
			}
		}
		return view;
	}


	 private class ViewHolder{
		private ImageView imageView,imgChoose;
		TextView tvTopicName,tvCount,tvReportName,tvTime,tvContent;
	 }
}
