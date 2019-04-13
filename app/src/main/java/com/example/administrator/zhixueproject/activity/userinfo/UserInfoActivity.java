package com.example.administrator.zhixueproject.activity.userinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.activity.college.AboutActivity;
import com.example.administrator.zhixueproject.activity.college.RecentEarningActivity;
import com.example.administrator.zhixueproject.activity.login.LoginActivity;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.UploadFile;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.HttpConstant;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.PopIco;
import com.example.administrator.zhixueproject.utils.AddImageUtils;
import com.example.administrator.zhixueproject.utils.SPUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 个人信息
 *
 * @author petergee
 * @date 2018/9/21
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivHeadIcon;
    private TextView tvUserName,tvSign;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.personal_info));
        ivHeadIcon = (ImageView) findViewById(R.id.iv_head_icon);
        tvUserName=(TextView)findViewById(R.id.tv_username);
        tvSign=(TextView)findViewById(R.id.tv_personal_sign);
        ivHeadIcon.setOnClickListener(this);
        findViewById(R.id.rl_modify_phone).setOnClickListener(this);
        findViewById(R.id.rl_modify_mailbox).setOnClickListener(this);
        findViewById(R.id.rl_modify_pwd).setOnClickListener(this);
        findViewById(R.id.rl_user_name).setOnClickListener(this);
        findViewById(R.id.rl_sign).setOnClickListener(this);
        findViewById(R.id.rl_personal_bg).setOnClickListener(this);
        findViewById(R.id.tv_login_out).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
        findViewById(R.id.rl_shouyi).setOnClickListener(this);
        findViewById(R.id.rl_about).setOnClickListener(this);
    }


    private String userImg;
    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what){
                //上传头像
                case HandlerConstant1.UPLOAD_HEAD_SUCCESS:
                     final UploadFile uploadFile= (UploadFile) msg.obj;
                     if(null==uploadFile){
                         return;
                     }
                     if(uploadFile.isStatus()){
                         //修改用户头像
                         userImg=uploadFile.getData().getUrl();
                         editUserImg();
                     }else{
                         showMsg(uploadFile.getErrorMsg());
                     }
                     break;
                //修改头像成功
                case HandlerConstant2.MODIFY_USER_INFO_SUCCESS:
                     BaseBean baseBean= (BaseBean) msg.obj;
                    if(null==baseBean){
                        return;
                    }
                    if(baseBean.isStatus()){
                        MyApplication.userInfo.getData().getUser().setUserImg(userImg);
                        showUserInfo();
                    }else{
                        showMsg(baseBean.getErrorMsg());
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head_icon://更换头像
                addPic();
                break;
            case R.id.rl_modify_phone://修改绑定手机
                setClass(EditPhoneActivity.class);
                break;
            case R.id.rl_modify_mailbox://修改绑定邮箱
                 setClass(EditEmailActivity.class);
                break;
            case R.id.rl_modify_pwd://修改密码
                setClass(UpdatePwdActivity.class);
                break;
            case R.id.rl_user_name://用户名
                setClass(EditUserNameActivity.class);
                break;
            case R.id.rl_sign://签名
                setClass(EditSignActivity.class);
                break;
            case R.id.rl_shouyi:
                setClass(RecentEarningActivity.class);
                 break;
            case R.id.rl_about:
                setClass(AboutActivity.class);
                 break;
            case R.id.rl_personal_bg://个性背景
                break;
            case R.id.tv_login_out://退出登录
                MyApplication.spUtil.removeMessage(SPUtil.USER_INFO);
                MyApplication.spUtil.removeMessage(SPUtil.TOKEN);
                MyApplication.userInfo=null;
                Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutIntent);
                finish();
                break;
            case R.id.lin_back:
                 finish();
                 break;
            default:
                break;

        }

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
                        AddImageUtils.openCamera(UserInfoActivity.this);
                        break;
                    case R.id.tv_pop_ico_photo:
                        AddImageUtils.selectFromAlbum(UserInfoActivity.this);
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
                            AddImageUtils.handleImageOnKitKat(data, UserInfoActivity.this);
                        } else {
                            AddImageUtils.handleImageBeforeKitKat(data, UserInfoActivity.this);
                        }
                        AddImageUtils.cropPhoto(UserInfoActivity.this);
                    }
                    break;
                case AddImageUtils.REQUEST_CAPTURE://拍照
                     AddImageUtils.cropPhoto(UserInfoActivity.this);
                    break;
                case AddImageUtils.REQUEST_PICTURE_CUT://裁剪完成
                    if (data != null) {
                        final File file=new File(AddImageUtils.outputUri);
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


    /**
     * 显示用户信息
     */
    private void showUserInfo(){
        final UserBean userBean=MyApplication.userInfo.getData().getUser();
        Glide.with(mContext).load(userBean.getUserImg()).override(50,50).error(R.mipmap.head_bg).into(ivHeadIcon);
        tvUserName.setText(userBean.getUserName());
        tvSign.setText(userBean.getUserIntro());
    }


    /**
     * 修改用户头像
     */
    private void editUserImg(){
        showProgress(getString(R.string.loding));
        HttpMethod2.modifyUserInfo(null, null, null, null, null,userImg, mHandler);
    }


    @Override
    protected void onResume() {
        super.onResume();
        showUserInfo();
    }
}
