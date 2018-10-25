package com.example.administrator.zhixueproject.activity.memberManage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.memberManage.SignDataAdapter;
import com.example.administrator.zhixueproject.adapter.memberManage.SignXAdapter;
import com.example.administrator.zhixueproject.adapter.memberManage.SignYAdapter;
import com.example.administrator.zhixueproject.bean.memberManage.SignIn;
import com.example.administrator.zhixueproject.bean.memberManage.SignInterceptBean;
import com.example.administrator.zhixueproject.callback.CustomListener;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.utils.DateUtil;
import com.example.administrator.zhixueproject.view.time.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/** 签到管理
 * @author PeterGee
 * @date 2018/10/20
 */
public class SignInManageActivity extends BaseActivity implements View.OnClickListener{

    private RecyclerView rv_y_intercept,rv_data,rv_x_intercept;
    private TextView tvDate,tvChange;
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
    private String time;
    private Calendar mSelectedDate;
    private List<SignInterceptBean> list_y=new ArrayList<>();
    private List<SignInterceptBean> list_x=new ArrayList<>();
    private SignDataAdapter mDataAdapter;
    private TimePickerView pvCustomTime;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initView();
        //初始化纵坐标y
        initY();
        //系统当前时间
        mSelectedDate = Calendar.getInstance();
        //初始化横坐标X
        initX(DateUtil.getDaysOfMonth(mSelectedDate.getTime()));
        //初始化柱状图数据
        initData();
        time=simpleDateFormat.format(new Date());
        tvDate.setText(time);
        //初始化时间选择器
        initCustomTimePicker();
        //查询签到数据
        getData();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.sign_in_manager));
        rv_y_intercept=(RecyclerView)findViewById(R.id.rv_y_intercept);
        rv_data=(RecyclerView)findViewById(R.id.rv_data);
        rv_x_intercept=(RecyclerView)findViewById(R.id.rv_x_intercept);
        tvDate=(TextView)findViewById(R.id.tv_show_date);
        tvChange=(TextView)findViewById(R.id.tv_change_date);
        tvChange.setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
    }


    /**
     * 初始化纵坐标
     */
    public void initY(){
        String[] y_intercept =new String[]{"500","400","300","200","100","Zero"};
        for (int i = 0; i < y_intercept.length; i++) {
            SignInterceptBean bean = new SignInterceptBean();
            bean.setIntercept(y_intercept[i]);
            list_y.add(bean);
        }
        SignYAdapter mYAdapter = new SignYAdapter(R.layout.sign_y_item, list_y);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_y_intercept.setAdapter(mYAdapter);
        rv_y_intercept.setLayoutManager(linearLayoutManager);
    }

    /**
     * 初始化横坐标X
     * @param dayOfMonth  当前月份的总天数
     */
    public void initX(int dayOfMonth){
        list_x=new ArrayList<>();
        for (int i = 1; i < dayOfMonth+1; i++) {
            SignInterceptBean bean = new SignInterceptBean();
            if(i==1||i==5||i==15||i==25||i==dayOfMonth||i==10||i==20){
                bean.setIntercept(i+"");
            }else {
                bean.setIntercept("");
            }
            list_x.add(bean);
        }
        SignXAdapter mXAdapter = new SignXAdapter(R.layout.sign_x_item, list_x);
        LinearLayoutManager linearLayoutManagerX = new LinearLayoutManager(this);
        linearLayoutManagerX.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_x_intercept.setAdapter(mXAdapter);
        rv_x_intercept.setLayoutManager(linearLayoutManagerX);
    }

    /**
     * 初始化柱状图数据
     */
    public void initData(){
        mDataAdapter = new SignDataAdapter(R.layout.sign_data_item);
        LinearLayoutManager linearLayoutManagerData = new LinearLayoutManager(this);
        linearLayoutManagerData.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_data.setAdapter(mDataAdapter);
        rv_data.setLayoutManager(linearLayoutManagerData);
    }




    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                case HandlerConstant1.GET_SIGN_LIST_SUCCESS:
                     final SignIn signIn= (SignIn) msg.obj;
                     if(null==signIn){
                         return;
                     }
                     if(signIn.isStatus()){
                         mDataAdapter.setNewData(signIn.getData().getTotalNum());
                     }else{
                         showMsg(signIn.errorMsg);
                     }
                     break;
            }
        }
    };


    /**
     * 查询签到管理
     */
    private void getData(){
        showProgress(getString(R.string.loding));
        HttpMethod1.getSignList(time,mHandler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_change_date:
                 pvCustomTime.show(false);
                 break;
            case R.id.lin_back:
                 finish();
                 break;
             default:
                 break;
        }
    }


    /**
     * 初始化时间选择
     */
    private void initCustomTimePicker() {
        Calendar now = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(now.get(Calendar.YEAR)-100,1,1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(now.get(Calendar.YEAR)+20,12,31);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                time=simpleDateFormat.format(date);
                tvDate.setText(time);
                int dayOfMonth = DateUtil.getDaysOfMonth(date);
                initX(dayOfMonth);
                //查询签到管理
                getData();
            }
        })
                .setDate(mSelectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_sign_time, new CustomListener() {

                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                        TextView v_bottom = (TextView) v.findViewById(R.id.v_bottom);
                        if (v_bottom != null) {
                            ViewGroup.MarginLayoutParams bottomParams = (ViewGroup.MarginLayoutParams) v_bottom.getLayoutParams();
                            v_bottom.setLayoutParams(bottomParams);
                        }
                    }
                })
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("","","","","","")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(getResources().getColor(R.color.color_dbdbdb))
                .setTextColorCenter(getResources().getColor(R.color.color_333333))
                .gravity(Gravity.CENTER)
                .build();

    }
}
