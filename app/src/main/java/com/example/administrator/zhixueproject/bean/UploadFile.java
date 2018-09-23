package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;

/**
 * 上传文件
 * Created by Administrator on 2018/9/23.
 */

public class UploadFile extends BaseBean {

    public UrlBean data;

    public UrlBean getData() {
        return data;
    }

    public void setData(UrlBean data) {
        this.data = data;
    }

    public static class UrlBean implements Serializable{
        public String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
