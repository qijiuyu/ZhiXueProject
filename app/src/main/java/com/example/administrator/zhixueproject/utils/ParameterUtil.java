package com.example.administrator.zhixueproject.utils;

import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.fragment.college.CollegeInfoFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 全局参数
 */
public class ParameterUtil {

   public static Map<String,String> getParamter(Map<String,String> map){
       //用户id
       if(null!=MyApplication.userInfo){
           final UserBean userBean= MyApplication.userInfo.getData().getUser();
           map.put("c",String.valueOf(userBean.getUserId()));
       }

       //学院id
       if(null==map.get("collegeId") && null!= MyApplication.homeBean){
           map.put("collegeId",String.valueOf(MyApplication.homeBean.getCollegeId()));
           LogUtils.e("collegeId==== "+ String.valueOf(MyApplication.homeBean.getCollegeId()));
       }

       final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       map.put("timestamp",simpleDateFormat.format(new Date()));
       return map;
   }
}
