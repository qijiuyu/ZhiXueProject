package com.example.administrator.zhixueproject.http.api;

import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.UploadFile;
import com.example.administrator.zhixueproject.bean.live.SelectLecturersBean;
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

    /**
     *  获取讲师列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_TEACHER_LIST)
    Call<SelectLecturersBean> getLecturersList(@FieldMap Map<String,String> map);

    /**
     *  发布帖子
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.ADD_POST)
    Call<UploadFile> addPost(@FieldMap Map<String,String> map);

    /**
     *  修改帖子
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.UPDATE_POST)
    Call<UploadFile> updatePost(@FieldMap Map<String,String> map);

    /**
     *  添加活动
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.ADD_ACTIVITY)
    Call<UploadFile> addActivity(@FieldMap Map<String,String> map);

    /**
     *  修改活动
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.UPDATE_ACTIVITY)
    Call<UploadFile> updateActivity(@FieldMap Map<String,String> map);
}
