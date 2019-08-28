package com.example.administrator.zhixueproject.utils;

/**
 * @author qipeng
 * @date 2019/7/31
 * @desc
 */
public class ClickUtil {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    /**
     * 防止快速点击
     * @return true:不是快速点击   false：是快速点击
     */
    public static boolean notFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

}
