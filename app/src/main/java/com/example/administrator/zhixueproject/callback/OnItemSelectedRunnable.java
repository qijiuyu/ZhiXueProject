package com.example.administrator.zhixueproject.callback;

import com.example.administrator.zhixueproject.view.time.WheelView;

public class OnItemSelectedRunnable implements Runnable {
    final WheelView loopView;

    public OnItemSelectedRunnable(WheelView loopview) {
        loopView = loopview;
    }

    @Override
    public void run() {
        loopView.onItemSelectedListener.onItemSelected(loopView.getCurrentItem());
    }
}

