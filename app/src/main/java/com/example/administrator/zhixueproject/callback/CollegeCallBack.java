package com.example.administrator.zhixueproject.callback;

import com.example.administrator.zhixueproject.bean.Colleges;

/**
 * Created by Administrator on 2018/11/25.
 */

public interface CollegeCallBack {

    void quitCollege(String collegeId);

    void onClick(Colleges colleges);
}
