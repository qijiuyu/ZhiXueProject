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
import com.example.administrator.zhixueproject.bean.Colleges;
import java.util.List;

public class CollegeItemAdapter extends BaseAdapter{

    private Context context;
    private List<Colleges> listAll;
    private Colleges colleges;
    public CollegeItemAdapter(Context context, List<Colleges> listAll) {
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
            view = LayoutInflater.from(context).inflate(R.layout.college_item, null);
            holder.imageView=(ImageView)view.findViewById(R.id.iv_college_img);
            holder.tvName=(TextView)view.findViewById(R.id.tv_college_name);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        colleges=listAll.get(position);
        String imgUrl=colleges.getCollegeLogo();
        holder.imageView.setTag(R.id.imageid,imgUrl);
        if(holder.imageView.getTag(R.id.imageid)!=null && imgUrl==holder.imageView.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).override(60,44).centerCrop().into(holder.imageView);
        }
        holder.tvName.setText(colleges.getCollegeName());
        return view;
    }


    private class ViewHolder{
        ImageView imageView;
        TextView tvName;
    }
}
