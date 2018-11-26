package com.example.administrator.zhixueproject.utils;

import java.text.DecimalFormat;

public class Utils {

    /**
     * 验证密码
     * @param pwd
     * @return
     */
    public static boolean isPwd(String pwd) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        //假设有一个字符串
        for (int i = 0; i < pwd.length(); i++) { //循环遍历字符串
            if (Character.isDigit(pwd.charAt(i))) {     //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(pwd.charAt(i))) {   //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        if (isDigit && isLetter) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 保留小数的double数据
     * @param d
     * @return
     */
    public static String setDouble(double d,int type){
        DecimalFormat df=null;
        switch (type){
            case 1:
                df = new DecimalFormat("0.0");
                break;
            case 2:
                df = new DecimalFormat("0.00");
                break;
            case 3:
                df = new DecimalFormat("0.000");
                break;
            case 4:
                df = new DecimalFormat("0.0000");
                break;
        }
        return df.format(d);
    }
}
