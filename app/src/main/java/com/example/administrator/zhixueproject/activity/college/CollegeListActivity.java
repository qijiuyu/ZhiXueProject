package com.example.administrator.zhixueproject.activity.college;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.college.ColleteVipAdapter;
import com.example.administrator.zhixueproject.bean.College;
import com.example.administrator.zhixueproject.bean.ColleteVips;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 学院列表
 */
public class CollegeListActivity extends BaseActivity implements TextView.OnEditorActionListener{

    private ListView listView;
    private EditText etKey;
    private List<College.CollegeDatas> listAll=new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText("选择学院");
        etKey=(EditText)findViewById(R.id.et_key);
        listView=(ListView)findViewById(R.id.listView);
        final View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        ((ViewGroup) listView.getParent()).addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(view);
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CollegeListActivity.this.finish();
            }
        });
    }


    private Handler mHandler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            clearTask();
            switch (msg.what){
                case HandlerConstant1.GET_COLLEGE_LIST_SUCCESS:
                     College college= (College) msg.obj;
                     if(null==college){
                         break;
                     }
                     if(college.isStatus() && null!=college.getData()){
                         listAll.addAll(college.getData().getCollegelist());
                         listView.setAdapter(new CollegeAdapter());
                         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                 College.CollegeDatas collegeDatas=listAll.get(position);
                                 Intent intent=new Intent();
                                 intent.putExtra("collegeDatas",collegeDatas);
                                 setResult(2,intent);
                                 finish();
                             }
                         });

                     }else{
                         showMsg(college.getErrorMsg());
                     }
                     break;
                case HandlerConstant1.REQUST_ERROR:
                    clearTask();
                    showMsg(getString(R.string.net_error));
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 查询学院列表
     */
    private void getData(){
        showProgress("数据加载中");
        HttpMethod1.getCollegeList(etKey.getText().toString().trim(),mHandler);
    }


    /**
     * 搜索键触发事件
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH){
            //隐藏软键盘
            lockKey(etKey);
            if(TextUtils.isEmpty(etKey.getText().toString().trim())){
                showMsg("请输入要搜索的关键字！");
                return false;
            }
            getData();
        }
        return false;
    }


    public class CollegeAdapter extends BaseAdapter {

        public CollegeAdapter() {
            super();
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
                view = LayoutInflater.from(mContext).inflate(R.layout.college_list_item, null);
                holder.tvName=(TextView) view.findViewById(R.id.tv_name);
                view.setTag(holder);
            }else{
                holder=(ViewHolder)view.getTag();
            }
            holder.tvName.setText(listAll.get(position).getCollegeName());
            return view;
        }


        private class ViewHolder{
            TextView tvName;
        }
    }
}
