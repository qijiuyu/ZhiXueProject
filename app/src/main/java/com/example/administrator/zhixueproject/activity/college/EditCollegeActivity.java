package com.example.administrator.zhixueproject.activity.college;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.Home;
import com.example.administrator.zhixueproject.bean.UploadFile;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HttpConstant;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.pop.PopIco;
import com.example.administrator.zhixueproject.utils.AddImageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 开通或编辑学院
 * Created by Administrator on 2018/9/24.
 */

public class EditCollegeActivity extends BaseActivity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{

    private EditText etName,etRegister,etBack,etCard,etDetails;
    private TextView tvMoney;
    private ImageView imgBJ;
    private SeekBar seekBar;
    private RadioButton radioButton1,radioButton2,radioButton3,radioButton4;
    private Home.HomeBean homeBean;
    //学院背景图地址
    private String outputUri;
    private String collegeBackimg;
    //入群设置(1：开放、2：付费、3：审核)
    private int collegeType;
    //设为私群(0：否、2：是)
    private int collegeDelYn=2;
    private List<RadioButton> rbList=new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_college);
        initView();
        showData();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText("编辑学院");
        etName=(EditText)findViewById(R.id.et_college_name);
        etRegister=(EditText)findViewById(R.id.et_registrant);
        etBack=(EditText)findViewById(R.id.et_bank_name);
        etCard=(EditText)findViewById(R.id.et_card_number);
        tvMoney=(TextView)findViewById(R.id.tv_aec_money);
        imgBJ=(ImageView)findViewById(R.id.iv_college_back_img);
        seekBar=(SeekBar)findViewById(R.id.college_seek_bar);
        radioButton1=(RadioButton)findViewById(R.id.item_option1);
        radioButton2=(RadioButton)findViewById(R.id.item_option2);
        radioButton3=(RadioButton)findViewById(R.id.item_option3);
        radioButton4=(RadioButton)findViewById(R.id.item_option4);
        etDetails=(EditText)findViewById(R.id.et_address_detail);
        imgBJ.setOnClickListener(this);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
        radioButton4.setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        rbList.add(radioButton1);rbList.add(radioButton2);
        rbList.add(radioButton3);rbList.add(radioButton4);

    }


    /**
     * 显示数据
     */
    private void showData(){
        homeBean= (Home.HomeBean) getIntent().getSerializableExtra("homeBean");
        if(null==homeBean){
            return;
        }
        etName.setText(homeBean.getCollegeName());
        etRegister.setText(homeBean.getCollegeUser());
        etBack.setText(homeBean.getCollegeAccBankinfo());
        etCard.setText(homeBean.getCollegeAccBank());
        collegeBackimg=homeBean.getCollegeBackimg();
        Glide.with(mContext).load(collegeBackimg).centerCrop().into(imgBJ);
        //是否为私密
        collegeDelYn=homeBean.getCollegeDelYn();
        if(collegeDelYn==2){
            setRadioButton(0);
        }
        //学院开放类型
        collegeType=homeBean.getCollegeType();
        if(collegeType!=0){
            setRadioButton(collegeType);
            //付费金额
            if(collegeType==2){
                tvMoney.setText(homeBean.getCollegePrice()+"元");
            }
        }
        //学院比例
        seekBar.setProgress(homeBean.getScale());
        etDetails.setText(homeBean.getCollegeInfo());
    }


    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                //上传背景图
                case HandlerConstant1.UPLOAD_HEAD_SUCCESS:
                    final UploadFile uploadFile= (UploadFile) msg.obj;
                    if(null==uploadFile){
                        return;
                    }
                    if(uploadFile.isStatus()){
                        collegeBackimg=uploadFile.getData().getUrl();
                        Glide.with(mContext).load(collegeBackimg).centerCrop().into(imgBJ);
                    }else{
                        showMsg(uploadFile.getErrorMsg());
                    }
                    break;
                //编辑学院
                case HandlerConstant1.EDIT_COLLEGE_SUCCESS:
                     final BaseBean baseBean= (BaseBean) msg.obj;
                     if(null==baseBean){
                         return;
                     }
                     if(baseBean.isStatus()){
                         finish();
                     }else{
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //选择背景图
            case R.id.iv_college_back_img:
                 addPic();
                 break;
             //设为私密
            case R.id.item_option1:
                 setRadioButton(0);
                 collegeType=0;
                 break;
             //开放入群
            case R.id.item_option2:
                 setRadioButton(1);
                 collegeType=1;
                break;
            //付费入群
            case R.id.item_option3:
                 setRadioButton(2);
                 collegeType=2;
                 setMoney();
                break;
             //审核入群
            case R.id.item_option4:
                 setRadioButton(3);
                 collegeType=3;
                break;
            case R.id.tv_commit:
                 final String collegeName=etName.getText().toString().trim();
                 final String registerName=etRegister.getText().toString().trim();
                 final String backInfo=etBack.getText().toString().trim();
                 final String backCard=etCard.getText().toString().trim();
                 final String money=tvMoney.getText().toString().trim().replace("元","");
                 final String details=etDetails.getText().toString().trim();
                 if(TextUtils.isEmpty(collegeName)){
                     showMsg("请输入学院名称！");
                     return;
                 }
                if(TextUtils.isEmpty(registerName)){
                    showMsg("请输入注册人！");
                    return;
                }
                if(TextUtils.isEmpty(backInfo)){
                    showMsg("请输入开户行！");
                    return;
                }
                if(TextUtils.isEmpty(backCard)){
                    showMsg("请输入银行卡号！");
                    return;
                }
                if(TextUtils.isEmpty(collegeBackimg)){
                    showMsg("请选择学院背景图！");
                    return;
                }
                if(collegeType==2 && TextUtils.isEmpty(money)){
                     setMoney();
                     return;
                }
                if(TextUtils.isEmpty(details)){
                    showMsg("请输入学院简介！");
                    return;
                }
                 showProgress("数据加载中...");
                 final UserBean userBean= MyApplication.userInfo.getData().getUser();
                 HttpMethod1.editCollege(userBean.getUserId(),homeBean.getCollegeId(),collegeName,registerName,backInfo,backCard,collegeBackimg,seekBar.getProgress(),collegeType,money,collegeDelYn,details,mHandler);
                 break;
            case R.id.tv_cancel:
            case R.id.lin_back:
                 finish();
                 break;
            default:
                break;
        }

    }


    /**
     * Seeker进度监控
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    /**
     * 设置radiobutton
     * @param index
     */
    private void setRadioButton(int index){
        for (int i=0;i<rbList.size();i++){
             if(i==index){
                 rbList.get(i).setChecked(true);
             }else{
                 rbList.get(i).setChecked(false);
             }
        }
        if(index!=2){
            tvMoney.setText(null);
        }
        if(index==0){
            collegeDelYn=2;
        }else{
            collegeDelYn=0;
        }
    }


    /**
     * 设置付费入群的金额
     */
    private void setMoney(){
        View view= LayoutInflater.from(mContext).inflate(R.layout.pop_edit_money,null);
        showPop(view);
        final EditText etMoney=(EditText)view.findViewById(R.id.et_pem_money);
        etMoney.setText(tvMoney.getText().toString().trim());
        view.findViewById(R.id.tv_pem_submit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String money=etMoney.getText().toString().trim();
                if(TextUtils.isEmpty(money)){
                    showMsg("请输入付费金额！");
                }else{
                    closePop();
                    tvMoney.setText(money+"元");
                }
            }
        });
        view.findViewById(R.id.tv_pem_cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                closePop();
            }
        });
    }

    /**
     * 选择图片
     */
    private void addPic() {
        PopIco popIco = new PopIco(this);
        popIco.showAsDropDown();
        popIco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_pop_ico_camera:
                        AddImageUtils.openCamera(EditCollegeActivity.this);
                        break;
                    case R.id.tv_pop_ico_photo:
                        AddImageUtils.selectFromAlbum(EditCollegeActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AddImageUtils.REQUEST_PICK_IMAGE://从相册选择
                    if (data != null) {
                        if (Build.VERSION.SDK_INT >= 19) {
                            AddImageUtils.handleImageOnKitKat(data, EditCollegeActivity.this);
                        } else {
                            AddImageUtils.handleImageBeforeKitKat(data, EditCollegeActivity.this);
                        }
                        outputUri=AddImageUtils.cropPhoto(EditCollegeActivity.this);
                    }
                    break;
                case AddImageUtils.REQUEST_CAPTURE://拍照
                    outputUri=AddImageUtils.cropPhoto(EditCollegeActivity.this);
                    break;
                case AddImageUtils.REQUEST_PICTURE_CUT://裁剪完成
                    if (data != null) {
                        final File file=new File(outputUri);
                        if(!file.isFile()){
                            return;
                        }
                        List<File> list=new ArrayList<>();
                        list.add(file);
                        showProgress("图片上传中");
                        //上传图片
                        HttpMethod1.uploadFile(HttpConstant.UPDATE_FILES,list,mHandler);
                    }
                    break;
                default:
                    break;
            }

        }
    }
}
