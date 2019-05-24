package com.example.administrator.zhixueproject.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * Created by lyn on 2017/3/7.
 */

public class DateUtil {
    /**
     * 格式到天
     *
     * @param time
     * @return
     */
    public static String getDay(long time) {
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

    /**
     * 格式到天
     *
     * @param time
     * @return
     */
    public static String gethour(long time) {
        if (time <= 0) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }


    /**
     * 格式到年
     *
     * @param time
     * @return
     */
    public static String getTime(long time) {
        if (time <= 0) {
            return "";
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(time);
    }

    /**
     * 格式月日
     *
     * @param time
     * @return
     */
    public static String getMd(long time) {
        if (time <= 0) {
            return "";
        }
        return new SimpleDateFormat("MM/dd").format(time);
    }


    /**
     * 把long 转换成 日期 再转换成String类型
     */
    public static String transferLongToDate(long millSec, String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        Date date = new Date(millSec);
        return sdf.format(date);
    }


    /**
     * 判断周几
     *
     * @return
     */
    public static int weekday() {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        return c.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 获得某个月最大天数
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     *  获取格式化后时间
     * @return
     */
    public static String getTime(){
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

    /**
     *  获取格式化后时间
     *
     * @return 年月日
     */
    public static String getTimeHMS(long time){
        Date date=new Date(time);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static boolean IsToday(long time){
        long newTime=System.currentTimeMillis()/1000;
        LogUtils.e(newTime+"+++++++++++++="+time);
        if((time/1000)>newTime){
            return true;
        }
        return false;
    }
}
