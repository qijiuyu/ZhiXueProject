package com.example.administrator.zhixueproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.administrator.zhixueproject.R;

/**
 * @author PeterGee
 * @date 2018/10/23
 */
public class GlideCirclePictureUtil {

    /**
     * 设置圆头像
     *
     * @param mContext
     * @param url
     * @param target
     */
    public static void setCircleImg(final Context mContext, String url, final ImageView target) {
        Glide.with(mContext).load(url).asBitmap().error(R.mipmap.unify_circle_head).
                into(new BitmapImageViewTarget(target) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        target.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

}
