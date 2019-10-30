package com.example.administrator.zhixueproject.bean.live;

import com.example.administrator.zhixueproject.bean.BaseBean;

/**
 * @author PeterGee
 * @date 2019/10/30
 */
public class SeeNumBean extends BaseBean {
    private InnerBean data;

    public InnerBean getData() {
        return data;
    }

    public void setData(InnerBean data) {
        this.data = data;
    }

    public static class InnerBean{
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
