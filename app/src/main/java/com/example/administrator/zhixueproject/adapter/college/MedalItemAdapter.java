package com.example.administrator.zhixueproject.adapter.college;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.college.AddMedalActivity;
import com.example.administrator.zhixueproject.activity.college.MedalListActivity;
import com.example.administrator.zhixueproject.bean.Medal;

import java.util.List;

public class MedalItemAdapter extends BaseAdapter{

    private Activity activity;
    private List<Medal.MedalList> list;
    private Medal.MedalList medalList;
    public MedalItemAdapter(Activity activity, List<Medal.MedalList> list) {
        super();
        this.activity = activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list==null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
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
            view = LayoutInflater.from(activity).inflate(R.layout.medal_list_item, null);
            holder.imageView=(ImageView)view.findViewById(R.id.iv_medal_pic);
            holder.tvName=(TextView)view.findViewById(R.id.tv_medal_name);
            holder.tvDes=(TextView)view.findViewById(R.id.tv_medal_describe);
            holder.tvEdit=(TextView)view.findViewById(R.id.tv_edit);
            holder.tvDel=(TextView)view.findViewById(R.id.tv_delete);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        medalList=list.get(position);
        String imgUrl=medalList.getMedalTypeMig();
        holder.imageView.setTag(R.id.imageid,imgUrl);
        if(holder.imageView.getTag(R.id.imageid)!=null && imgUrl==holder.imageView.getTag(R.id.imageid)){
            Glide.with(activity).load(imgUrl).override(17,10).centerCrop().into(holder.imageView);
        }
        holder.tvName.setText(medalList.getMedalTypeName());
        holder.tvDes.setText(medalList.getMedalTypeInfo());
        holder.tvEdit.setTag(medalList);
        holder.tvDel.setTag(medalList);
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(null==v.getTag()){
                    return;
                }
                final Medal.MedalList medalList= (Medal.MedalList) v.getTag();
                Intent intent=new Intent(activity, AddMedalActivity.class);
                intent.putExtra("medalList",medalList);
                activity.startActivityForResult(intent,1);
            }
        });
        holder.tvDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(null==v.getTag()){
                    return;
                }
                final Medal.MedalList medalList= (Medal.MedalList) v.getTag();
                ((MedalListActivity)activity).deleteMedal(medalList.getMedalTypeId());
            }
        });
        return view;
    }


    private class ViewHolder{
        ImageView imageView;
        TextView tvName,tvDes,tvEdit,tvDel;
    }
}
