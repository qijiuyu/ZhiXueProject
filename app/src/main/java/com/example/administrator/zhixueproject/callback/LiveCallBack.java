package com.example.administrator.zhixueproject.callback;

import com.example.administrator.zhixueproject.bean.live.Live;

public interface LiveCallBack {

    public void deleteLive(long postId);

    void itemClick(Live.LiveList liveList);
}
