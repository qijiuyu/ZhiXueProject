package com.example.administrator.zhixueproject.adapter.memberManage;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.memberManage.SignIn;
import java.util.List;

/**
 * Created by yy on 2017/11/8.
 */

public class SignDataAdapter extends BaseQuickAdapter<SignIn.SignInList,BaseViewHolder> {
    public SignDataAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    public SignDataAdapter(@LayoutRes int layoutResId, @Nullable List<SignIn.SignInList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignIn.SignInList item) {
        int width = ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth();
        int x_width = width - 180;
        RelativeLayout ll_data_intercept = helper.getView(R.id.ll_data_intercept);
        ViewGroup.LayoutParams params = ll_data_intercept.getLayoutParams();
        params.width =x_width/30;
        ll_data_intercept.setLayoutParams(params);

        ImageView iv_data = helper.getView(R.id.iv_data);
        ViewGroup.LayoutParams params1 = iv_data.getLayoutParams();
        Integer integer = item.getNum();
        if(TextUtils.equals(item.getNum()+"","0")){
            params1.height=0;
        }else {
            params1.height= (integer*115/100)+32;

        }
        iv_data.setLayoutParams(params1);
    }
}
