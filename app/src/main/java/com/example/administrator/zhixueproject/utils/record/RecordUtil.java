package com.example.administrator.zhixueproject.utils.record;

import android.os.Environment;

/**
 * 类名称：RecordUtil
 * 描述：
 */
public class RecordUtil {
    public static String getSDPath(){
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().getPath();
        } else {
            return "";
        }
    }
    public static String getAudioPath(){
        return getSDPath() +"/AudioVideoRecord/audio";
    }
}
