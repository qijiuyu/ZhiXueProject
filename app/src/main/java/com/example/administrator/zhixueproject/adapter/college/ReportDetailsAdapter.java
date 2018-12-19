package com.example.administrator.zhixueproject.adapter.college;

import android.app.Activity;
import android.text.TextUtils;
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

		final String[] types=convert(listBean.getComplaintInfoType()).split(",");
		if(null!=types){
			StringBuffer stringBuffer=new StringBuffer("举报原因：");
			for (int i=0;i<types.length;i++){
				switch (types[i]){
					case "0":
						stringBuffer.append("侵权，");
						break;
					case "1":
						stringBuffer.append("淫秽色情，");
						break;
					case "2":
						stringBuffer.append("内容与标题不符，");
						break;
					case "3":
						stringBuffer.append("敏感信息，");
						break;
					case "4":
						stringBuffer.append("垃圾广告，");
						break;
				}
				final String strType=stringBuffer.toString();
				holder.tvType.setText(strType.substring(0,strType.length()-1));
			}
		}

		holder.tvContent.setText(listBean.getComplaintInfo());
		return view;
	}


	private static String convert(int s){
		String s1 = "";
		while(true){
			s1=s1+(s%10)+",";
			if(s<10){
				break;
			}
			s=s/10;
		}

		return s1.substring(0, s1.length()-1);
	}

	 private class ViewHolder{
		private ImageView imageView;
		TextView tvUserName,tvType,tvContent;
	 }
}
