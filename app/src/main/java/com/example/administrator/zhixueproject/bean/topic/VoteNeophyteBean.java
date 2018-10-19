package com.example.administrator.zhixueproject.bean.topic;


import com.example.administrator.zhixueproject.bean.BaseBean;

import java.util.List;

public class VoteNeophyteBean extends BaseBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * voteDetailList : [{"voteSsecName":"","voteCreattime":"2017-11-23 14:51","postType":421,"userPhone":"13638622846","VoteName":"1123投票添加测试","userEmail":"","userName":"九天揽月"},{"voteSsecName":"","voteCreattime":"2017-11-23 14:51","postType":421,"userPhone":"13638622846","VoteName":"1123投票添加测试","userEmail":"","userName":"九天揽月"}]
         * totalPage : 1
         * isUpdated : false
         * totalRecord : 2
         * timestamp : 1511491867157
         */

        private int totalPage;
        private boolean isUpdated;
        private int totalRecord;
        private String timestamp;
        private List<VoteDetailListBean> voteDetailList;

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

        public List<VoteDetailListBean> getVoteDetailList() {
            return voteDetailList;
        }

        public void setVoteDetailList(List<VoteDetailListBean> voteDetailList) {
            this.voteDetailList = voteDetailList;
        }
    }

}
