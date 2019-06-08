package com.example.administrator.zhixueproject.adapter.college;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.Report;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FloorReportAdapter extends BaseAdapter{

	private Activity activity;
	private List<Report.ReportList> listAll;
	private Report.ReportList reportList;
	public boolean isSelect=false;
	public boolean isAllSelect=false;
	public Map<String,String> maps=new HashMap<>();
	public FloorReportAdapter(Activity activity, List<Report.ReportList> listAll) {
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
			view = LayoutInflater.from(activity).inflate(R.layout.floor_report_item, null);
			holder.imgChoose=(ImageView)view.findViewById(R.id.iv_choose);
			holder.tvUserName=(TextView)view.findViewById(R.id.tv_user_name);
			holder.tvTitle=(TextView)view.findViewById(R.id.tv_title);
			holder.tvContent=(TextView)view.findViewById(R.id.tv_content);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		reportList=listAll.get(position);
		holder.tvUserName.setText(Html.fromHtml("<font color='#40c5f1'>发帖人：</font>"+reportList.getPostWriterId()));
		holder.tvTitle.setText(Html.fromHtml("<font color='#40c5f1'>帖子标题：</font>"+reportList.getPostName()));
		holder.tvContent.setText(Html.fromHtml("<font color='#DE5347'>楼层回帖信息：</font>"+reportList.getComplaintContent()));
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
		private ImageView imgChoose;
		TextView tvUserName,tvTitle,tvContent;
	 }
}
