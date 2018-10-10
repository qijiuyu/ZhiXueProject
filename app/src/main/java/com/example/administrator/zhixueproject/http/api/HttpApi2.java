package com.example.administrator.zhixueproject.http.api;

import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.topic.PostsCourseBean;
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
    Call<TopicsListBean> getTopicList(@FieldMap Map<String,String> map);

    /**
     * 修改个人资料
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.MODIFY_USER_INFO)
    Call<BaseBean> modifyUserInfo(@FieldMap Map<String,String> map);

    /**
     *  话题上下架
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.IS_UP_OR_DOWN)
    Call<TopicsListBean> isUpOrDown(@FieldMap Map<String,String> map);

    /**
     *  添加话题
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.ADD_TOPIC)
    Call<TopicsListBean> addTopic(@FieldMap Map<String,String> map);

    /**
     *  修改话题
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.UPDATE_TOPIC)
    Call<TopicsListBean> updateTopic(@FieldMap Map<String,String> map);

    /**
     *  话题排序
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.UPDATE_SORT)
    Call<TopicsListBean> updateSort(@FieldMap Map<String,String> map);

    /**
     *  获取帖子列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_POST_LIST)
    Call<PostsCourseBean> getPostList(@FieldMap Map<String,String> map);
}
