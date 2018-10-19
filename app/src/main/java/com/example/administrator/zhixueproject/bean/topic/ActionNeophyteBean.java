package com.example.administrator.zhixueproject.bean.topic;


import com.example.administrator.zhixueproject.bean.BaseBean;

import java.util.List;


/**
 * 活动参与者bean
 */
public class ActionNeophyteBean extends BaseBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * totalPage : 1
         * activityUserList : [{"activityName":"930活动测试","activityTime":"2017-09-30 10:54","tel":"13111111111","userName":"报名人930"}]
         * isUpdated : false
         * totalRecord : 1
         * timestamp : 1511405085900
         */

        private int totalPage;
        private boolean isUpdated;
        private int totalRecord;
        private String timestamp;
        private List<ActivityUserListBean> activityUserList;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public boolean isIsUpdated() {
            return isUpdated;
        }

        public void setIsUpdated(boolean isUpdated) {
            this.isUpdated = isUpdated;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public List<ActivityUserListBean> getActivityUserList() {
            return activityUserList;
        }

        public void setActivityUserList(List<ActivityUserListBean> activityUserList) {
            this.activityUserList = activityUserList;
        }


    }

//    private String name;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }


}
