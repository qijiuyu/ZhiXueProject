package com.example.administrator.zhixueproject.utils;

import android.util.Log;

/**
 * @author qipeng
 * @date 2019/7/30
 * @desc
 */
public class MyCustomLogUtil {

    //可以全局控制是否打印log日志
    private static boolean isPrintLog = true;
    private static int LOG_MAX_LENGTH = 2000;


    public static void v(String msg) {
        v("MyCustomLogUtil", msg);
    }

    public static void v(String tagName, String msg) {
        if (isPrintLog) {
            while (msg.length() > LOG_MAX_LENGTH) {
                Log.v(tagName, msg.substring(0, LOG_MAX_LENGTH));
                msg = msg.substring(LOG_MAX_LENGTH);
            }
            Log.v(tagName, msg);
        }
    }

    public static void d(String msg) {
        d("MyCustomLogUtil", msg);
    }

    public static void d(String tagName, String msg) {
        if (isPrintLog) {
            while (msg.length() > LOG_MAX_LENGTH) {
                Log.d(tagName, msg.substring(0, LOG_MAX_LENGTH));
                msg = msg.substring(LOG_MAX_LENGTH);
            }
            Log.d(tagName, msg);
        }
    }


    public static void i(String msg) {
        i("MyCustomLogUtil", msg);
    }

    public static void i(String tagName, String msg) {
        if (isPrintLog) {
            while (msg.length() > LOG_MAX_LENGTH) {
                Log.i(tagName, msg.substring(0, LOG_MAX_LENGTH));
                msg = msg.substring(LOG_MAX_LENGTH);
            }
            Log.i(tagName, msg);
        }
    }

    public static void w(String msg) {
        w("MyCustomLogUtil", msg);
    }

    public static void w(String tagName, String msg) {
        if (isPrintLog) {
            while (msg.length() > LOG_MAX_LENGTH) {
                Log.w(tagName, msg.substring(0, LOG_MAX_LENGTH));
                msg = msg.substring(LOG_MAX_LENGTH);
            }
            Log.w(tagName, msg);
        }
    }

    public static void e(String msg) {
        e("MyCustomLogUtil", msg);
    }

    public static void e(String tagName, String msg) {
        if (isPrintLog) {
            while (msg.length() > LOG_MAX_LENGTH) {
                Log.e(tagName, msg.substring(0, LOG_MAX_LENGTH));
                msg = msg.substring(LOG_MAX_LENGTH);
            }
            Log.e(tagName, msg);

        }
    }

}
