package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Notice extends BaseBean {

    private NoticeData data;

    public NoticeData getData() {
        return data;
    }

    public void setData(NoticeData data) {
        this.data = data;
    }

    public static class NoticeData implements Serializable{

        private List<NoticeList> noticeList=new ArrayList<>();

        public List<NoticeList> getNoticeList() {
            return noticeList;
        }

        public void setNoticeList(List<NoticeList> noticeList) {
            this.noticeList = noticeList;
        }
    }

    public static class NoticeList implements Serializable{
        private String noticeCreationtime;
        private String noticeInfo;
        private String noticeTitle;
        private long noticeId;

        public String getNoticeCreationtime() {
            return noticeCreationtime;
        }

        public void setNoticeCreationtime(String noticeCreationtime) {
            this.noticeCreationtime = noticeCreationtime;
        }

        public String getNoticeInfo() {
            return noticeInfo;
        }

        public void setNoticeInfo(String noticeInfo) {
            this.noticeInfo = noticeInfo;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        public long getNoticeId() {
            return noticeId;
        }

        public void setNoticeId(long noticeId) {
            this.noticeId = noticeId;
        }
    }
}
