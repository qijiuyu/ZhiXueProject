package com.example.administrator.zhixueproject.adapter.college;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.ColleteVips;
import java.util.List;

public class ColleteVipAdapter extends BaseAdapter{

	private Context context;
	private List<ColleteVips.ColleteVipsBean.collegeGradeListBean> listAll;
	private ColleteVips.ColleteVipsBean.collegeGradeListBean collegeGradeListBean;
	public ColleteVipAdapter(Context context, List<ColleteVips.ColleteVipsBean.collegeGradeListBean> listAll) {
		super();
		this.context = context;
		this.listAll=listAll;
	}

	@Override
	public int getCount() {
		return listAll==null ? 0 : listAll.size();
	}

	@Override
	public ColleteVips.ColleteVipsBean.collegeGradeListBean getItem(int position) {
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
			view = LayoutInflater.from(context).inflate(R.layout.collete_vip_item, null);
			holder.imgVip=(ImageView)view.findViewById(R.id.iv_vip_grade);
			holder.tvIfLive=(TextView)view.findViewById(R.id.tv_if_live);
			holder.tvLiveNum=(TextView)view.findViewById(R.id.tv_live_num);
			holder.tvMonthMoney=(TextView)view.findViewById(R.id.tv_monthly_fee);
			holder.tvYearMoney=(TextView)view.findViewById(R.id.tv_year_fee);
			holder.tvPersonNum=(TextView)view.findViewById(R.id.tv_person_num);
			holder.tvTopIc=(TextView)view.findViewById(R.id.tv_topic_num);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		collegeGradeListBean=listAll.get(position);
		if(collegeGradeListBean.getCollegeLivePostYn()==0){
            holder.tvIfLive.setText("否");
        }else{
            holder.tvIfLive.setText("是");
        }
        holder.tvLiveNum.setText(collegeGradeListBean.getCollegeLiveNum()+"");
        holder.tvMonthMoney.setText(collegeGradeListBean.getCollegeGradeMprice()+"");
        holder.tvYearMoney.setText(collegeGradeListBean.getCollegeGradeYprice()+"");
        holder.tvPersonNum.setText(collegeGradeListBean.getCollegeLimitStu()+"");
        holder.tvTopIc.setText(collegeGradeListBean.getCollegeLimitTopic()+"");
		Glide.with(context).load(collegeGradeListBean.getCollegeGradeImg()).override(35,12).centerCrop().into(holder.imgVip);
		return view;
	}


	 private class ViewHolder{
	    ImageView imgVip;
		TextView tvIfLive,tvLiveNum,tvMonthMoney,tvYearMoney,tvPersonNum,tvTopIc;
	 }
}
