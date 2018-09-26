package com.example.administrator.zhixueproject.utils;

/**
 * sha1加密
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Encrypt {

    public static String getSHA(String val) throws NoSuchAlgorithmException{
       MessageDigest md5=MessageDigest.getInstance("SHA-1");
            md5.update(val.getBytes());
            byte[] m=md5.digest();//加密
            return getString(m);
    }


    private static String getString(byte[]b){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<b.length;i++){
        sb.append(b[i]);
        }
        return sb.toString();
    }


    /**
     * 字符串转换为Ascii
     * @param value
     * @return
     */
    public static String stringToAscii(String value)
    {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(i != chars.length - 1)
            {
                sbu.append((int)chars[i]).append(",");
            }
            else {
                sbu.append((int)chars[i]);
            }
        }
        return sbu.toString();

    }
}
