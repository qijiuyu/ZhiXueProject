package com.example.administrator.zhixueproject.fragment.college;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.fragment.BaseFragment;

/**
 * 帖子举报fragment
 * Created by Administrator on 2018/1/3 0003.
 */

public class TopicReportFragment extends BaseFragment{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collete_info, container, false);
        return view;
    }


}
