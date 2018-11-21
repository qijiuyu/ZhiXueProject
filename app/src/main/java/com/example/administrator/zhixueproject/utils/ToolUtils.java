package com.example.administrator.zhixueproject.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.zhixueproject.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ToolUtils {

    /**
     * 获取图片全路径
     *
     * @param mContext
     * @param imageUrl
     * @return
     */
    public static String getPicLoad(Context mContext, String imageUrl) {


        if (imageUrl != null) {
            if (imageUrl.startsWith("http")) {
                return imageUrl;
            } else {
                return mContext.getString(R.string.service_host_image).concat(imageUrl);
            }
        } else {
            return "";
        }
    }

    /**
     *
     * @param context
     * @param imageView
     * @param delWidth  要减去的宽度
     * @param picWidth  图片尺寸宽度
     * @param picHeigth  图片尺寸高度
     */
    public static void setImageWidthHeigth(Context context, ImageView imageView, int delWidth, int picWidth, int picHeigth){
        //获取iv_dynamic_pic_one在屏幕中显示的宽度
        int width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth()-delWidth;
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width =width;
        params.height = picHeigth*width/picWidth;
        imageView.setLayoutParams(params);
    }
    public static void setImageWidthHeigth(ImageView imageView, int width, int picWidth, int picHeigth){
        //获取iv_dynamic_pic_one在屏幕中显示的宽度
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width =width;
        params.height = picHeigth*width/picWidth;
        imageView.setLayoutParams(params);
    }

    /**
     * 搜索关键字标红
     *
     * @param title
     * @param keyword
     * @return
     */
    public static SpannableString matcherSearchTitle(Context mContext, String title, String keyword) {
        SpannableString s = new SpannableString(title);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_ff0000)), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    public static String imgStyleHtml(String html){
       // String imgStyle = "<style> img{width:auto; height:auto;}iframe{width:auto; height:auto;}</style>";
        String imgStyle2 = "<style> img{max-width:100%; height:auto;}iframe{max-width:100%; height:auto;}</style>";
        String img_html;
        if(TextUtils.isEmpty(html)){
            img_html ="";
        }else{
            img_html = html;
        }
        img_html = imgStyle2+img_html;
        return img_html;
    }
    /**
     * 使用正则表达式 把html标签中的style属性全部替换成""
     */
    public static String replaceImgStyle(String html){
        String reg = "style=\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(html);
        return matcher.replaceAll("");
    }

    /**
     * 去掉html中的换行，缩进等
     * @param html
     * @return
     */
    public static String replaceEnter(String html){
        String reg = "\\\\s*|\\t|\\r|\\n";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(html);
        return matcher.replaceAll("");
    }

    /**
     * 去掉html中的所有的标签
     * @param html
     * @return
     */
    public static String replaceTag(String html){
        String reg = "<[^>]*>";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(html);
        return matcher.replaceAll("");
    }

    public static String getNoEmptyString(String text){
        if(TextUtils.isEmpty(text)){
            return "";
        }else {
            return text;
        }
    }

    /**
     * 改变字体颜色
     *
     * @param textView
     * @param text
     * @param start
     * @param end
     */
    public static void changeTextColor(TextView textView, String text, int start, int end) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        //设置指定位置文字的颜色
        style.setSpan(new ForegroundColorSpan(textView.getContext().getResources().getColor(R.color.color_48c6ef)), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(style);
    }
}
