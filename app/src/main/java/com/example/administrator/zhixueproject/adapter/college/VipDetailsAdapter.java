package com.example.administrator.zhixueproject.adapter.college;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.VipDetails;
import com.example.administrator.zhixueproject.utils.DateUtil;
import java.util.List;

public class VipDetailsAdapter extends BaseAdapter{

	private Context context;
	private List<VipDetails.VipDtailsList> listAll;
	private VipDetails.VipDtailsList vipDtailsList;
	public VipDetailsAdapter(Context context, List<VipDetails.VipDtailsList> listAll) {
		super();
		this.context = context;
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
			view = LayoutInflater.from(context).inflate(R.layout.vip_details_item, null);
			holder.tvVipName=(TextView)view.findViewById(R.id.tv_vip_level);
			holder.tvNum=(TextView)view.findViewById(R.id.tv_vip_deadline);
			holder.tvState=(TextView)view.findViewById(R.id.tv_vip_state);
			holder.tvType=(TextView)view.findViewById(R.id.tv_vip_type);
			holder.tvTime=(TextView)view.findViewById(R.id.tv_vip_time);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		vipDtailsList=listAll.get(position);
		holder.tvVipName.setText(vipDtailsList.getVipGradeName());
		holder.tvTime.setText(vipDtailsList.getCreateTime());
		holder.tvNum.setText(vipDtailsList.getTime()+"");
		if(vipDtailsList.getGradeType()==0){
			holder.tvType.setText("按年");
		}else{
			holder.tvType.setText("按月");
		}
		switch (vipDtailsList.getStatus()){
			case 0:
				 holder.tvState.setText("购买中");
				 break;
			case 1:
				 holder.tvState.setText("通过");
				 break;
			case 2:
				 holder.tvState.setText("拒绝");
				 break;
				 default:
				 	break;
		}
		return view;
	}


	 private class ViewHolder{
		TextView tvVipName,tvNum,tvState,tvType,tvTime;
	 }
}
