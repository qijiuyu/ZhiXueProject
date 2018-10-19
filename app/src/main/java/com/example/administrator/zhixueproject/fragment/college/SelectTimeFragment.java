package com.example.administrator.zhixueproject.fragment.college;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.callback.CustomListener;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.view.time.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 选择时间fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class SelectTimeFragment extends BaseFragment implements View.OnClickListener{

    private TextView tvStartTime,tvEndTime;
    private TimePickerView pvCustomTime;
    private String mStartTime,mEndTime;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_time, container, false);
        tvStartTime=(TextView)view.findViewById(R.id.tv_start_time);
        tvEndTime=(TextView)view.findViewById(R.id.tv_end_time);
        tvStartTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        view.findViewById(R.id.tv_commit).setOnClickListener(this);
        view.findViewById(R.id.tv_back).setOnClickListener(this);
        //初始化时间选择
        initCustomTimePicker();
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //选择开始时间
            case R.id.tv_start_time:
                 pvCustomTime.show(tvStartTime);
                 break;
            //选择结束时间
            case R.id.tv_end_time:
                 pvCustomTime.show(tvEndTime);
                 break;
            //提交
            case R.id.tv_commit:
                 final String startTime=tvStartTime.getText().toString().trim();
                 final String endTime=tvEndTime.getText().toString().trim();
                 if(TextUtils.isEmpty(startTime)){
                     showMsg("请选择查询起始日期！");
                     return;
                 }
                if(TextUtils.isEmpty(endTime)){
                    showMsg("请选择查询结束日期！");
                    return;
                }
                mOnInquireTimeListener.closeInquireTimeListener(mStartTime, mEndTime);
                 break;
            //返回
            case R.id.tv_back:
                mOnInquireTimeListener.closeInquireTimeListener(mStartTime, mEndTime);
                 break;
                 default:
                     break;
        }
    }


    /**
     * 初始化时间选择
     */
    private void initCustomTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(3000, 12, 31);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (v == tvStartTime) {
                    mStartTime = getTime(date);
                    tvStartTime.setText(mStartTime);
                } else if (v == tvEndTime) {
                    mEndTime = getTime(date);
                    tvEndTime.setText(mEndTime);
                }
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_sign_time, new CustomListener() {
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
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
                        View view_bg = v.findViewById(R.id.view_bg);
                        view_bg.setBackgroundColor(getResources().getColor(R.color.translete));
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(getResources().getColor(R.color.color_dbdbdb))
                .setTextColorCenter(getResources().getColor(R.color.color_333333))
                .gravity(Gravity.CENTER)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    /**
     * 关闭查询时间弹窗的接口回调
     */
    private OnInquireTimeListener mOnInquireTimeListener;

    public interface OnInquireTimeListener {
        void closeInquireTimeListener(String startTime, String endTime);
    }

    public OnInquireTimeListener getOnInquireTimeListener() {
        return mOnInquireTimeListener;
    }

    public void setOnInquireTimeListener(OnInquireTimeListener onInquireTimeListener) {
        mOnInquireTimeListener = onInquireTimeListener;
    }

}
