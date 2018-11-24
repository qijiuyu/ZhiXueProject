package com.example.administrator.zhixueproject.bean.topic;


import java.util.List;

public class VoteDetailListBean {

        /**
         * voteSsecName :
         * voteCreattime : 2017-11-23 14:51
         * postType : 421
         * userPhone : 13638622846
         * VoteName : 1123投票添加测试
         * userEmail :
         * userName : 九天揽月
         */

        private List<String> voteSsecName;
        private String voteCreattime;
        private int postType;
        private String userPhone;
        private String VoteName;
        private String userEmail;
        private String userName;
        private int voteId;


        public String getVoteCreattime() {
            return voteCreattime;
        }

        public void setVoteCreattime(String voteCreattime) {
            this.voteCreattime = voteCreattime;
        }

        public int getPostType() {
            return postType;
        }

        public void setPostType(int postType) {
            this.postType = postType;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getVoteName() {
            return VoteName;
        }

        public void setVoteName(String VoteName) {
            this.VoteName = VoteName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public List<String> getVoteSsecName() {
        return voteSsecName;
    }

    public void setVoteSsecName(List<String> voteSsecName) {
        this.voteSsecName = voteSsecName;
    }
}
