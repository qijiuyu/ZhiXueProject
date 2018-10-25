package com.example.administrator.zhixueproject.adapter.memberManage;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.memberManage.SignInterceptBean;

import java.util.List;

/**
 * Created by yy on 2017/11/8.
 */

public class SignXAdapter extends BaseQuickAdapter<SignInterceptBean,BaseViewHolder> {
    public SignXAdapter(@LayoutRes int layoutResId, @Nullable List<SignInterceptBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignInterceptBean item) {
        int width = ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth();
        int x_width = width - 180;
        TextView tv_x_intercept = helper.getView(R.id.tv_x_intercept);
        ViewGroup.LayoutParams params = tv_x_intercept.getLayoutParams();
        params.width =x_width/30;
        tv_x_intercept.setLayoutParams(params);
        helper.setText(R.id.tv_x_intercept,item.getIntercept());
    }
}
