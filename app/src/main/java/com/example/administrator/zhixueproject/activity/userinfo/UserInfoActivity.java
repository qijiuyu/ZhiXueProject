package com.example.administrator.zhixueproject.activity.userinfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.pop.PopIco;
import com.example.administrator.zhixueproject.utils.AddImageUtils;

/**
 * 个人信息
 *
 * @author petergee
 * @date 2018/9/21
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivHeadIcon;
    private RelativeLayout relHead;
    private Uri mOutputUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
    }

    private void initView() {
        ivHeadIcon = (ImageView) findViewById(R.id.iv_head_icon);
        ivHeadIcon.setOnClickListener(this);
        RelativeLayout relModifyPhone = (RelativeLayout) findViewById(R.id.rl_modify_phone);
        relModifyPhone.setOnClickListener(this);
        RelativeLayout relModifyMail = (RelativeLayout) findViewById(R.id.rl_modify_mailbox);
        relModifyMail.setOnClickListener(this);
        RelativeLayout relModifyPwd = (RelativeLayout) findViewById(R.id.rl_modify_pwd);
        relModifyPwd.setOnClickListener(this);
        RelativeLayout relUserName = (RelativeLayout) findViewById(R.id.rl_user_name);
        relUserName.setOnClickListener(this);
        RelativeLayout relSign = (RelativeLayout) findViewById(R.id.rl_sign);
        relSign.setOnClickListener(this);
        RelativeLayout relPersonalBg = (RelativeLayout) findViewById(R.id.rl_personal_bg);
        relPersonalBg.setOnClickListener(this);
        TextView tvLogOut = (TextView) findViewById(R.id.tv_login_out);
        tvLogOut.setOnClickListener(this);
        relHead = (RelativeLayout) findViewById(R.id.rel_head);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head_icon://更换头像
                addPic();
                break;
            case R.id.rl_modify_phone://修改绑定手机
                setClass(ModifyPhoneActivity.class);
                break;
            case R.id.rl_modify_mailbox://修改绑定邮箱
                //  ModifyNewMailboxUI.start(this);
                break;
            case R.id.rl_modify_pwd://修改密码
                //  ModifyPwdUI.start(this);
                break;
            case R.id.rl_user_name://用户名
                //  EditUsernameUI.start(this);
                break;
            case R.id.rl_sign://签名
                //  EditSignUI.start(this);
                break;
            case R.id.rl_personal_bg://个性背景
                // PersonalBgUI.start(this);
                break;
            case R.id.tv_login_out://退出登录
             /*   CustomDialogUtils.getInstance().createCustomDialog(this, getResources().getString(R.string.is_login_out),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MyApplication.cleanUserInfo();
                                //清空登录记录
                                SPUtils.put(PersonalInfoUI.this, LoginUI.LOGIN_FLAG, LoginUI.LOGIN_OUT);
                                UIManager.getInstance().popAllActivity();
                                LoginUI.start(PersonalInfoUI.this);
//                                finish();
                            }
                        });*/
                break;
            default:
                break;

        }

    }

    /**
     * 上传图片
     */
    private void addPic() {
        PopIco popIco = new PopIco(ivHeadIcon, this);
        popIco.showAsDropDown(relHead);
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
                        mOutputUri = AddImageUtils.cropPhoto(UserInfoActivity.this);
                    }
                    break;
                case AddImageUtils.REQUEST_CAPTURE://拍照
                    mOutputUri = AddImageUtils.cropPhoto(UserInfoActivity.this);
                    break;
                case AddImageUtils.REQUEST_PICTURE_CUT://裁剪完成
                    if (data != null) {
                        Glide.with(this).load(mOutputUri.toString()).error(R.mipmap.unify_circle_head).into(ivHeadIcon);
                    }
                    break;
            }

        }
    }
}
