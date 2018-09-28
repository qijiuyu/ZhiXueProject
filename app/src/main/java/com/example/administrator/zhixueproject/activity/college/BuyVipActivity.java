package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.ColleteVips;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
/**
 * 购买vip
 */
public class BuyVipActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private TextView tvDes;
    private EditText etNum;
    //购买方式（0：按年；1：按月）
    private int gradeType=1;
    private ColleteVips.ColleteVipsBean.collegeGradeListBean collegeGradeListBean;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vip);
        initView();
        collegeGradeListBean= (ColleteVips.ColleteVipsBean.collegeGradeListBean) getIntent().getSerializableExtra("collegeGradeListBean");
    }


    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText("购买VIP");
        RadioGroup radioGroup=(RadioGroup)findViewById(R.id.rg_group_setting);
        tvDes=(TextView)findViewById(R.id.tv_aav_des);
        etNum=(EditText)findViewById(R.id.et_aav_num);
        radioGroup.setOnCheckedChangeListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String number=etNum.getText().toString().trim();
                if(TextUtils.isEmpty(number)){
                    showMsg("请输入购买期限！");
                }else{
                    if(null==collegeGradeListBean){
                        return;
                    }
                    showProgress(getString(R.string.loding));
                    HttpMethod1.buyVip(collegeGradeListBean.getCollegeGradeId(),gradeType,number,mHandler);
                }
            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.item_option1:
                tvDes.setText("个月");
                gradeType=1;
                break;
            case R.id.item_option2:
                tvDes.setText("年");
                gradeType=0;
                break;
            default:
                break;
        }
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                //提交
                case HandlerConstant1.BUY_VIPS_SUCCESS:
                     BaseBean baseBean = (BaseBean) msg.obj;
                     if (null == baseBean) {
                        return;
                     }
                     if (baseBean.isStatus()) {
                         finish();
                         showMsg("购买成功！");
                     } else {
                        showMsg(baseBean.getErrorMsg());
                     }
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    showMsg(getString(R.string.net_error));
                    break;
                default:
                    break;
            }
        }
    };
}
