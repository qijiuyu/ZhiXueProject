package com.example.administrator.zhixueproject.adapter.college;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.college.AddNoticeActivity;
import com.example.administrator.zhixueproject.activity.college.NoticeListActivity;
import com.example.administrator.zhixueproject.bean.Notice;
import java.util.List;

public class NoticeListAdapter extends BaseAdapter{

	private Activity activity;
	private List<Notice.NoticeList> listAll;
	private Notice.NoticeList noticeList;
	public NoticeListAdapter(Activity activity, List<Notice.NoticeList> listAll) {
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
			view = LayoutInflater.from(activity).inflate(R.layout.notice_item, null);
			holder.tvTitle=(TextView)view.findViewById(R.id.tv_announce_title);
			holder.tvEdit=(TextView)view.findViewById(R.id.tv_edit);
			holder.tvTime=(TextView)view.findViewById(R.id.tv_announce_time);
			holder.tvDel=(TextView)view.findViewById(R.id.tv_delete);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		noticeList=listAll.get(position);
		holder.tvTitle.setText(noticeList.getNoticeTitle());
		holder.tvTime.setText(noticeList.getNoticeCreationtime());
		holder.tvEdit.setTag(noticeList);
		holder.tvDel.setTag(noticeList.getNoticeId());
		//编辑
		holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				if(null==v.getTag()){
					return;
				}
				Notice.NoticeList noticeList= (Notice.NoticeList) v.getTag();
				Intent intent=new Intent(activity, AddNoticeActivity.class);
				intent.putExtra("noticeList",noticeList);
				activity.startActivityForResult(intent,2);
            }
        });
		//删除
		holder.tvDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(null==v.getTag()){
            		return;
				}
            	final String noticeId=v.getTag().toString();
            	if(!TextUtils.isEmpty(noticeId)){
					((NoticeListActivity)activity).deleteNotice(Long.parseLong(noticeId));
				}
            }
        });
		return view;
	}


	 private class ViewHolder{
		TextView tvTitle,tvEdit,tvTime,tvDel;
	 }
}
