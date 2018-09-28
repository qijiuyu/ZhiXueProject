package com.example.administrator.zhixueproject.utils;

import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.fragment.college.CollegeInfoFragment;

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
       if(null!= CollegeInfoFragment.homeBean){
           map.put("collegeId",String.valueOf(CollegeInfoFragment.homeBean.getCollegeId()));
       }
       return map;
   }
}
