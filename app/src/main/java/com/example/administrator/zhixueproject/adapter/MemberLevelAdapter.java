package com.example.administrator.zhixueproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.MemBerLevel;
import java.util.List;
public class MemberLevelAdapter extends BaseAdapter{

    private Context context;
    private List<MemBerLevel.MemberLevelList> list;
    private MemBerLevel.MemberLevelList memberLevelList;
    public MemberLevelAdapter(Context context, List<MemBerLevel.MemberLevelList> list) {
        super();
        this.context = context;
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
            view = LayoutInflater.from(context).inflate(R.layout.member_level_item, null);
            holder.tvName=(TextView)view.findViewById(R.id.tv_level);
            holder.etJiFen=(EditText)view.findViewById(R.id.et_jifen);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        memberLevelList=list.get(position);
        holder.tvName.setText(memberLevelList.getUserCollegegradeName());
        holder.etJiFen.setText(memberLevelList.getUserCollegegradePoints()+"");
        return view;
    }


    private class ViewHolder{
        private TextView tvName;
        private EditText etJiFen;
    }
}
