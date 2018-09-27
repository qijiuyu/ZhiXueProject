package com.example.administrator.zhixueproject.activity.college;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.Medal;
import com.example.administrator.zhixueproject.bean.UploadFile;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.fragment.college.CollegeInfoFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HttpConstant;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.pop.PopIco;
import com.example.administrator.zhixueproject.utils.AddImageUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 编辑或添加勋章
 */
public class AddMedalActivity extends BaseActivity implements View.OnClickListener{

    private EditText etTitle,etContent;
    private TextView tvLength;
    private ImageView imgPic;
    private Medal.MedalList medalList;
    //勋章图片地址
    private String outputUri;
    private String medalTypeMig;
    //勋章id
    private long medalTypeId;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medal);
        initView();
        showData();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.add_medal));
        etTitle=(EditText)findViewById(R.id.et_medal_title);
        imgPic=(ImageView)findViewById(R.id.iv_add_pic);
        etContent=(EditText)findViewById(R.id.et_medal_info);
        tvLength=(TextView)findViewById(R.id.tv_medalInfo_length);
        etContent.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                final String str=s.toString();
                tvLength.setText(str.length()+"/30");

            }
        });
        findViewById(R.id.tv_setting_save).setOnClickListener(this);
        findViewById(R.id.iv_add_pic).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
    }


    /**
     * 显示要编辑的数据
     */
    private void showData(){
        medalList= (Medal.MedalList) getIntent().getSerializableExtra("medalList");
        if(null==medalList){
            return;
        }
        medalTypeId=medalList.getMedalTypeId();
        etTitle.setText(medalList.getMedalTypeName());
        etContent.setText(medalList.getMedalTypeInfo());
        tvLength.setText(medalList.getMedalTypeInfo().length()+"/30");
        medalTypeMig=medalList.getMedalTypeMig();
        Glide.with(mContext).load(medalTypeMig).override(91,91).centerCrop().into(imgPic);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //选择照片
            case R.id.iv_add_pic:
                 addPic();
                 break;
            //提交
            case R.id.tv_setting_save:
                 final String title=etTitle.getText().toString().trim();
                 final String content=etContent.getText().toString().trim();
                 if(TextUtils.isEmpty(title)){
                     showMsg("请输入勋章标题！");
                     return;
                 }
                 if(TextUtils.isEmpty(content)){
                    showMsg("请输入勋章描述！");
                    return;
                 }
                 if(TextUtils.isEmpty(medalTypeMig)){
                    showMsg("请选择勋章图片！");
                    return;
                 }
                 showProgress(getString(R.string.loding));
                 final UserBean userBean= MyApplication.userInfo.getData().getUser();
                 HttpMethod1.saveMedal(userBean.getUserId(), CollegeInfoFragment.homeBean.getCollegeId(),medalTypeId,title,content,medalTypeMig,mHandler);
                 break;
            case R.id.lin_back:
                 finish();
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
                //上传勋章图片
                case HandlerConstant1.UPLOAD_HEAD_SUCCESS:
                    final UploadFile uploadFile= (UploadFile) msg.obj;
                    if(null==uploadFile){
                        return;
                    }
                    if(uploadFile.isStatus()){
                        medalTypeMig=uploadFile.getData().getUrl();
                        Glide.with(mContext).load(medalTypeMig).centerCrop().into(imgPic);
                    }else{
                        showMsg(uploadFile.getErrorMsg());
                    }
                    break;
                //提交
                case HandlerConstant1.SAVE_MEDAL_SUCCESS:
                     final String message= (String) msg.obj;
                     if(TextUtils.isEmpty(message)){
                         return;
                     }
                     try {
                         final JSONObject jsonObject=new JSONObject(message);
                         if(!jsonObject.getBoolean("status")){
                             showMsg(jsonObject.getString("errorMsg"));
                             return;
                         }
                         final JSONObject jsonObject1=new JSONObject(jsonObject.getString("data"));
                         medalList=MyApplication.gson.fromJson(jsonObject1.getString("medalType"),Medal.MedalList.class);
                         Intent intent=new Intent(mContext,MedalListActivity.class);
                         intent.putExtra("medalList",medalList);
                         setResult(1,intent);
                         finish();
                     }catch (Exception e){
                         e.printStackTrace();
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
                        AddImageUtils.openCamera(AddMedalActivity.this);
                        break;
                    case R.id.tv_pop_ico_photo:
                        AddImageUtils.selectFromAlbum(AddMedalActivity.this);
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
                            AddImageUtils.handleImageOnKitKat(data, AddMedalActivity.this);
                        } else {
                            AddImageUtils.handleImageBeforeKitKat(data, AddMedalActivity.this);
                        }
                        outputUri=AddImageUtils.cropPhoto(AddMedalActivity.this);
                    }
                    break;
                case AddImageUtils.REQUEST_CAPTURE://拍照
                    outputUri=AddImageUtils.cropPhoto(AddMedalActivity.this);
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
