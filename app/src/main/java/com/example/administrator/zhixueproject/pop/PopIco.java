package com.example.administrator.zhixueproject.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.application.MyApplication;


/**
 * 描述：通用设置
 *
 *
 */
public class PopIco implements OnClickListener{
    private PopupWindow popupWindow;
    /** 回调接口 */
    private OnClickListener onClickListener;
    private Activity activity;

    /**
     *
     * 点击的控件
     */
    public PopIco(Activity activity) {
        this.activity=activity;
        View view = LayoutInflater.from(activity).inflate(R.layout.pop_ico, null);
        // 设置popwindow弹出大小
        popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //sdk > 21 解决 标题栏没有办法遮罩的问题
        popupWindow.setClippingEnabled(false);

        RelativeLayout rl_pop_ico_back = (RelativeLayout) view.findViewById(R.id.rl_pop_ico_back);
        TextView tv_pop_ico_camera = (TextView) view.findViewById(R.id.tv_pop_ico_camera);
        TextView tv_pop_ico_photo = (TextView) view.findViewById(R.id.tv_pop_ico_photo);
        TextView tv_pop_ico_cancel = (TextView) view.findViewById(R.id.tv_pop_ico_cancel);

        rl_pop_ico_back.setOnClickListener(this);
        tv_pop_ico_camera.setOnClickListener(this);
        tv_pop_ico_photo.setOnClickListener(this);
        tv_pop_ico_cancel.setOnClickListener(this);
    }

    public void setOnClickListener(OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }

    /**
     * 下拉式 弹出 pop菜单 parent 右下角
     */
    public void showAsDropDown()
    {

        // 这个是为了点击“返回Back”也能使其消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置弹出位置
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        // 刷新状态
        popupWindow.update();

    }

    /**
     * 隐藏菜单
     */
    public void dismiss()
    {
        popupWindow.dismiss();
    }

    @Override
    public void onClick(View v)
    {
        popupWindow.dismiss();
        if (v.getId() == R.id.rl_back)
            return;
        if (onClickListener != null)
        {
            onClickListener.onClick(v);
        }
    }

}
