package com.example.administrator.zhixueproject.adapter.college;

import android.app.Activity;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.college.AddCooperateActivity;
import com.example.administrator.zhixueproject.activity.college.BuyInessInActivity;
import com.example.administrator.zhixueproject.activity.college.BuyInessOutActivity;
import com.example.administrator.zhixueproject.bean.BuyIness;
import com.example.administrator.zhixueproject.utils.DateUtil;

import java.util.List;

public class BuyInessOutAdapter extends BaseAdapter{

	private Activity activity;
	private List<BuyIness.BusInessList> listAll;
	private BuyIness.BusInessList busInessList;
	public BuyInessOutAdapter(Activity activity, List<BuyIness.BusInessList> listAll) {
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
			holder.imageView=(ImageView)view.findViewById(R.id.iv_business_pic);
			holder.tvTopic=(TextView)view.findViewById(R.id.tv_businessIn_topic);
			holder.tvCollege=(TextView)view.findViewById(R.id.tv_businessIn_college);
			holder.tvTime=(TextView)view.findViewById(R.id.tv_businessIn_endtime);
			holder.tvEdit=(TextView)view.findViewById(R.id.tv_edit);
			holder.tvDel=(TextView)view.findViewById(R.id.tv_delete);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		busInessList=listAll.get(position);
		String imgUrl=busInessList.getTopicImg();
		holder.imageView.setTag(R.id.imageid,imgUrl);
		if(holder.imageView.getTag(R.id.imageid)!=null && imgUrl==holder.imageView.getTag(R.id.imageid)){
			Glide.with(activity).load(imgUrl).override(105,77).centerCrop().into(holder.imageView);
		}
		if(!TextUtils.isEmpty(busInessList.getTopicName())){
			SpannableString spannableString = new SpannableString("售出话题 ".concat(busInessList.getTopicName()));
			spannableString.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color
					.color_999999)), 0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			spannableString.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color
					.color_333333)), 5,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.tvTopic.setText(spannableString.toString());
		}
		holder.tvCollege.setText(busInessList.getCollegeName());
		holder.tvTime.setText(DateUtil.gethour(busInessList.getBuytopicEndtime()));
		holder.tvEdit.setTag(busInessList);
		holder.tvDel.setTag(busInessList);
		//取消代理
		holder.tvDel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(null==v.getTag()){
					return;
				}
				final BuyIness.BusInessList busInessList= (BuyIness.BusInessList) v.getTag();
				((BuyInessOutActivity)activity).deleteBuyIness(busInessList);
			}
		});
		//编辑代理
		holder.tvEdit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(null==v.getTag()){
					return;
				}
				final BuyIness.BusInessList busInessList= (BuyIness.BusInessList) v.getTag();
				Intent intent=new Intent(activity, AddCooperateActivity.class);
				intent.putExtra("busInessList",busInessList);
				activity.startActivityForResult(intent,2);
			}
		});
		return view;
	}


	 private class ViewHolder{
		private ImageView imageView;
		TextView tvTopic,tvCollege,tvTime,tvEdit,tvDel;
	 }
}
