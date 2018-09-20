package com.example.administrator.zhixueproject.http.api;

import com.example.administrator.zhixueproject.bean.topic.TopicsListBean;
import com.example.administrator.zhixueproject.http.HttpConstant;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpApi2 {
    /**
     * 查询话题列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_TOPIC_LIST)
    Call<TopicsListBean.DataBean> getTopicList(@FieldMap Map<String,String> map);


}
