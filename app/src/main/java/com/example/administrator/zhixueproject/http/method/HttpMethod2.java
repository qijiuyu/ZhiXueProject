package com.example.administrator.zhixueproject.http.method;

import android.os.Handler;
import android.text.TextUtils;
import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.UploadFile;
import com.example.administrator.zhixueproject.bean.live.SelectLecturersBean;
import com.example.administrator.zhixueproject.bean.memberManage.BlackListBean;
import com.example.administrator.zhixueproject.bean.memberManage.KickOutMemberBean;
import com.example.administrator.zhixueproject.bean.memberManage.MedalBean;
import com.example.administrator.zhixueproject.bean.memberManage.MemberApplyBean;
import com.example.administrator.zhixueproject.bean.memberManage.MemberDetailBean;
import com.example.administrator.zhixueproject.bean.memberManage.MemberManagerBean;
import com.example.administrator.zhixueproject.bean.memberManage.MemberSettingBean;
import com.example.administrator.zhixueproject.bean.topic.ActionManageBean;
import com.example.administrator.zhixueproject.bean.topic.ActionNeophyteBean;
import com.example.administrator.zhixueproject.bean.topic.PostsCourseBean;
import com.example.administrator.zhixueproject.bean.topic.PostsDetailsBean;
import com.example.administrator.zhixueproject.bean.topic.TopicsListBean;
import com.example.administrator.zhixueproject.bean.topic.VoteManageBean;
import com.example.administrator.zhixueproject.bean.topic.VoteNeophyteBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.api.HttpApi2;
import com.example.administrator.zhixueproject.http.base.BaseRequst;
import com.example.administrator.zhixueproject.http.base.Http;
import com.example.administrator.zhixueproject.utils.LogUtils;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpMethod2 extends BaseRequst {

    private static String PAGE = "page";
    private static String LIMIT = "limit";
    private static String TIME = "timestamp";

    /**
     * 获取话题列表
     *
     * @param page      页号
     * @param limit     每页显示的条数
     * @param handler
     */
    public static void getTopicList(String topicType ,String page, String limit, final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        if(!TextUtils.isEmpty(topicType)){
            map.put("topicType",topicType );
        }
        map.put(PAGE, page);
        map.put(LIMIT, limit);
        Http.getRetrofit().create(HttpApi2.class).getTopicList(map).enqueue(new Callback<TopicsListBean>() {
            public void onResponse(Call<TopicsListBean> call, Response<TopicsListBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                     sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<TopicsListBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                 sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }

    /**
     * 话题上下架
     *
     * @param topicId    话题id
     * @param topicUseyn 是否上下架 （0否，1是）
     * @param handler
     */
    public static void isUpOrDowm(String topicId, final String topicUseyn, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("topicId", topicId);
        map.put("topicUseyn", topicUseyn);
        Http.getRetrofit().create(HttpApi2.class).isUpOrDown(map).enqueue(new Callback<TopicsListBean>() {
            @Override
            public void onResponse(Call<TopicsListBean> call, Response<TopicsListBean> response) {
                try {
                    if (Integer.valueOf(topicUseyn) == 1) {
                        // 上架
                        sendMessage(handler, HandlerConstant2.IS_UP_OR_DOWN_SUCCESS, response.body());
                    } else {
                        // 下架
                        sendMessage(handler, HandlerConstant2.IS_UP_OR_DOWN_SUCCESS2, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<TopicsListBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 话题排序
     *
     * @param topicId1 话题id1
     * @param topicId2 话题id1
     * @param handler
     */
    public static void updateSort(String topicId1, String topicId2, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("topicId1", topicId1);
        map.put("topicId2", topicId2);
        Http.getRetrofit().create(HttpApi2.class).updateSort(map).enqueue(new Callback<TopicsListBean>() {
            @Override
            public void onResponse(Call<TopicsListBean> call, Response<TopicsListBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.UPDATE_SORT_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据报错异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<TopicsListBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 添加话题
     *
     * @param topicName
     * @param topicPayType
     * @param topicIsTop
     * @param topicType
     * @param topicUseyn
     * @param topicImg
     * @param topicPrice
     * @param topicVipName
     * @param ids
     * @param handler
     */
    public static void addTopic( String topicName, String topicPayType,  String topicType,String topicIsTop,
                                String topicUseyn, String topicImg, String topicPrice, String topicVipName,
                                String ids, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("topicName", topicName);
        map.put("topicPayType", topicPayType);
        map.put("topicIsTop", topicIsTop);
        map.put("topicType", topicType);
        map.put("topicUseyn", topicUseyn);
        map.put("topicImg", topicImg);
        map.put("topicPrice", topicPrice);
        map.put("topicVipName", topicVipName);
        map.put("ids", ids);

        Http.getRetrofit().create(HttpApi2.class).addTopic(map).enqueue(new Callback<TopicsListBean>() {
            @Override
            public void onResponse(Call<TopicsListBean> call, Response<TopicsListBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.ADD_TOPIC_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<TopicsListBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 修改话题
     *
     * @param topicId
     * @param topicName
     * @param topicPayType
     * @param topicIsTop
     * @param topicType
     * @param topicUseyn
     * @param topicImg
     * @param topicPrice
     * @param topicVipName
     * @param ids
     * @param handler
     */
    public static void updateTopic(String topicId, String topicName, String topicPayType, String topicType,String topicIsTop,
                                    String topicUseyn, String topicImg, String topicPrice, String topicVipName,
                                   String ids, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("topicId", topicId);
        map.put("topicName", topicName);
        map.put("topicPayType", topicPayType);
        map.put("topicIsTop", topicIsTop);
        map.put("topicType", topicType);
        map.put("topicUseyn", topicUseyn);
        map.put("topicImg", topicImg);
        map.put("topicPrice", topicPrice);
        map.put("topicVipName", topicVipName);
        map.put("ids", ids);

        Http.getRetrofit().create(HttpApi2.class).updateTopic(map).enqueue(new Callback<TopicsListBean>() {
            @Override
            public void onResponse(Call<TopicsListBean> call, Response<TopicsListBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.UPDATE_TOPIC_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<TopicsListBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 获取帖子列表
     *
     * @param type
     * @param postType
     * @param postTopicId
     * @param key
     * @param page
     * @param limit
     * @param timestamp
     * @param handler
     */
    public static void getPostList(String type, String postType, String postTopicId, String key,
                                   String page, String limit, String timestamp, final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("postType", postType);
        map.put("postTopicId", postTopicId);
        map.put("key", key);
        map.put("page", page);
        map.put("limit", limit);
        map.put("timestamp", timestamp);
        Http.getRetrofit().create(HttpApi2.class).getPostList(map).enqueue(new Callback<PostsCourseBean>() {
            @Override
            public void onResponse(Call<PostsCourseBean> call, Response<PostsCourseBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<PostsCourseBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 获取讲师列表
     *
     * @param key
     * @param page
     * @param limit
     * @param timestamp
     * @param handler
     */
    public static void getLecturersList( String key, String page, String limit,
                                        String timestamp, final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("key", key);
        map.put("page", page);
        map.put("limit", limit);
        map.put("page", page);
        map.put("limit", limit);
        map.put("timestamp", timestamp);
        Http.getRetrofit().create(HttpApi2.class).getLecturersList(map).enqueue(new Callback<SelectLecturersBean>() {
            @Override
            public void onResponse(Call<SelectLecturersBean> call, Response<SelectLecturersBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<SelectLecturersBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }


    /**
     * 添加帖子
     *
     * @param postType
     * @param postName
     * @param postTopicId
     * @param postWriterId
     * @param postIsFree   是否免费 1=免费；2=付费；
     * @param postPrice
     * @param postIsTop    是否置顶 0否，1是
     * @param postContent
     * @param handler
     */
    public static void addPost(String postType, String postName, String postTopicId, String postWriterId,
                               String postIsFree, String postPrice, String postIsTop, String postContent, final Handler handler) {

        if ("1".equals(postIsFree)) {
            postPrice = "0";
        }

        if (TextUtils.isEmpty(postIsTop)) {
            postIsTop = "0";
        }

        Map<String, String> map = new HashMap<>();
        map.put("postType", postType);
        map.put("postName", postName);
        map.put("postTopicId", postTopicId);
        map.put("postWriterId", postWriterId);
        map.put("postIsFree", postIsFree);
        map.put("postPrice", postPrice);
        map.put("postIsTop", postIsTop);
        map.put("postContent", postContent);
        Http.getRetrofit().create(HttpApi2.class).addPost(map).enqueue(new Callback<UploadFile>() {
            @Override
            public void onResponse(Call<UploadFile> call, Response<UploadFile> response) {
                try {
                    sendMessage(handler, HandlerConstant2.ADD_POST_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<UploadFile> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 更新帖子
     *
     * @param postId
     * @param postName
     * @param postIsFree
     * @param postPrice
     * @param postIsTop
     * @param postContent
     * @param handler
     */
    public static void updatePost(String postId, String postName, String postIsFree, String postPrice, String postIsTop, String postContent, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("postId", postId);
        map.put("postName", postName);
        map.put("postIsFree", postIsFree);
        map.put("postPrice", postPrice);
        map.put("postIsTop", postIsTop);
        map.put("postContent", postContent);
        Http.getRetrofit().create(HttpApi2.class).updatePost(map).enqueue(new Callback<UploadFile>() {
            @Override
            public void onResponse(Call<UploadFile> call, Response<UploadFile> response) {
                try {
                    sendMessage(handler, HandlerConstant2.UPDATE_POST_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<UploadFile> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 添加活动
     *
     * @param topicId
     * @param activityImg
     * @param activityName
     * @param activityType
     * @param activityIsTop
     * @param activityWriterId
     * @param startTime
     * @param endTime
     * @param activityContent
     * @param handler
     */
    public static void addActivity(String topicId, String activityImg, String activityName, String activityType, String activityIsTop,
                                   String activityWriterId, String startTime, String endTime, String activityContent, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("topicId", topicId);
        map.put("activityImg", activityImg);
        map.put("activityName", activityName);
        map.put("activityType", activityType);
        map.put("activityIsTop", activityIsTop);
        map.put("activityWriterId", activityWriterId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("activityContent", activityContent);
        Http.getRetrofit().create(HttpApi2.class).addActivity(map).enqueue(new Callback<UploadFile>() {
            @Override
            public void onResponse(Call<UploadFile> call, Response<UploadFile> response) {
                try {
                    sendMessage(handler, HandlerConstant2.ADD_ACTIVITY_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<UploadFile> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 修改活动
     *
     * @param topicId
     * @param activityImg
     * @param activityName
     * @param activityType
     * @param activityIsTop
     * @param activityWriterId
     * @param startTime
     * @param endTime
     * @param activityContent
     * @param handler
     */
    public static void updateActivity(String topicId, String activityId, String activityImg, String activityName, String activityType,
                                      String activityIsTop, String activityWriterId, String startTime, String endTime, String activityContent, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("topicId", topicId);
        map.put("activityId", activityId);
        map.put("activityImg", activityImg);
        map.put("activityName", activityName);
        map.put("activityType", activityType);
        map.put("activityIsTop", activityIsTop);
        map.put("activityWriterId", activityWriterId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("activityContent", activityContent);
        Http.getRetrofit().create(HttpApi2.class).updateActivity(map).enqueue(new Callback<UploadFile>() {
            @Override
            public void onResponse(Call<UploadFile> call, Response<UploadFile> response) {
                try {
                    sendMessage(handler, HandlerConstant2.UPDATE_ACTIVITY_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<UploadFile> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 添加投票
     *
     * @param topicId
     * @param voteName
     * @param topicType        活动类型(1:包含课程 2：大家谈)
     * @param voteIsTop        是否置顶(0否，1是)
     * @param voteWriterId
     * @param startTime
     * @param endTime
     * @param voteSecNames
     * @param isMultipleChoice
     * @param handler
     */
    public static void addVote(String topicId, String voteName, String topicType, String voteIsTop, String voteWriterId,
                               String startTime, String endTime, String voteSecNames, boolean isMultipleChoice,String content,String postId, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("topicId", topicId);
        map.put("voteName", voteName);
        map.put("topicType", topicType);
        map.put("voteIsTop", voteIsTop);
        map.put("voteWriterId", voteWriterId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("voteSecNames", voteSecNames);
        map.put("isMultipleChoice", String.valueOf(isMultipleChoice));
        map.put("content", content);
        map.put("postId", postId);
        Http.getRetrofit().create(HttpApi2.class).addVote(map).enqueue(new Callback<UploadFile>() {
            @Override
            public void onResponse(Call<UploadFile> call, Response<UploadFile> response) {
                try {
                    sendMessage(handler, HandlerConstant2.ADD_VOTE_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<UploadFile> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 修改个人资料
     *
     * @param userName  用户名
     * @param mobile    手机号
     * @param email     新密码
     * @param code      验证码（修改绑定手机号和邮箱时，必填）
     * @param userIntro 个人简介
     * @param handler
     */
    public static void modifyUserInfo(String userName, String mobile, String email, String code, String userIntro,String userImg, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        if(!TextUtils.isEmpty(userName)){
            map.put("userName", userName);
        }
        if(!TextUtils.isEmpty(mobile)){
            map.put("mobile", mobile);
        }
        if(!TextUtils.isEmpty(email)){
            map.put("email", email);
        }
        if(!TextUtils.isEmpty(code)){
            map.put("code", code);
        }
        if(!TextUtils.isEmpty(userIntro)){
            map.put("userIntro", userIntro);
        }
        if(!TextUtils.isEmpty(userImg)){
            map.put("userImg", userImg);
        }
        Http.getRetrofit().create(HttpApi2.class).modifyUserInfo(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.MODIFY_USER_INFO_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<BaseBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }

    /**
     * 评论帖子
     *
     * @param postId
     * @param type      1.回复帖子 2.回复作业
     * @param floorData
     * @param handler
     */
    public static void commentPost(String postId, String type, String floorData, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("postId", postId);
        map.put("type", type);
        map.put("floorData", floorData);
        Http.getRetrofit().create(HttpApi2.class).commentPost(map).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.COMMENT_POST_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 回复楼层
     *
     * @param postId
     * @param floorId
     * @param commentUserId
     * @param beCommentUserId
     * @param talkInfo
     * @param handler
     */
    public static void commentReply(String postId, String floorId, String commentUserId, String beCommentUserId,
                                    String talkInfo, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("postId", postId);
        map.put("floorId", floorId);
        map.put("commentUserId", commentUserId);
        map.put("beCommentUserId", beCommentUserId);
        map.put("talkInfo", talkInfo);
        Http.getRetrofit().create(HttpApi2.class).commentReply(map).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.COMMENT_REPLY_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 获取帖子详情
     *
     * @param postId
     * @param page
     * @param limit
     * @param timestamp
     * @param handler
     */
    public static void getPostDetail(String postId, String page, String limit, String timestamp,
                                     final int index, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("postId", postId);
        map.put("page", page);
        map.put("limit", limit);
        map.put("timestamp", timestamp);
        Http.getRetrofit().create(HttpApi2.class).getPostDetail(map).enqueue(new Callback<PostsDetailsBean>() {
            @Override
            public void onResponse(Call<PostsDetailsBean> call, Response<PostsDetailsBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<PostsDetailsBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 获取有偿提问帖子详情
     *
     * @param postId
     * @param page
     * @param limit
     * @param timestamp
     * @param index
     * @param handler
     */
    public static void getYouChangDetail(String postId, String page, String limit, String timestamp,
                                         final int index, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("postId", postId);
        map.put("page", page);
        map.put("limit", limit);
        map.put("timestamp", timestamp);
        Http.getRetrofit().create(HttpApi2.class).getYouChangDetail(map).enqueue(new Callback<PostsDetailsBean>() {
            @Override
            public void onResponse(Call<PostsDetailsBean> call, Response<PostsDetailsBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<PostsDetailsBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 获取活动列表
     *
     * @param page
     * @param limit
     * @param timestamp
     * @param index
     * @param handler
     */
    public static void getActivityList(String timestamp, String page, String limit,
                                       final int index, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("page", page);
        map.put("limit", limit);
        Http.getRetrofit().create(HttpApi2.class).getActivityList(map).enqueue(new Callback<ActionManageBean>() {
            @Override
            public void onResponse(Call<ActionManageBean> call, Response<ActionManageBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<ActionManageBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 删除活动
     *
     * @param activityId
     * @param handler
     */
    public static void deleteActivity(String activityId, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("activityId", activityId);
        Http.getRetrofit().create(HttpApi2.class).deleteActivity(map).enqueue(new Callback<ActionManageBean>() {
            @Override
            public void onResponse(Call<ActionManageBean> call, Response<ActionManageBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.DELETE_ACTIVITY_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<ActionManageBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 获取活动参与者列表
     *
     * @param timestamp
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getActivityUserList(String activityId, String timestamp, String page, String limit,
                                           final int index, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("activityId", activityId);
        map.put("timestamp", timestamp);
        map.put("page", page);
        map.put("limit", limit);
        Http.getRetrofit().create(HttpApi2.class).getActivityUserList(map).enqueue(new Callback<ActionNeophyteBean>() {
            @Override
            public void onResponse(Call<ActionNeophyteBean> call, Response<ActionNeophyteBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<ActionNeophyteBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 获取投票列表
     *
     * @param timestamp
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getVoteList(String timestamp, String page, String limit,
                                   final int index, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("page", page);
        map.put("limit", limit);
        Http.getRetrofit().create(HttpApi2.class).getVoteList(map).enqueue(new Callback<VoteManageBean>() {
            @Override
            public void onResponse(Call<VoteManageBean> call, Response<VoteManageBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<VoteManageBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 删除投票
     *
     * @param voteId
     * @param handler
     */
    public static void deleteVote(String voteId, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("voteId", voteId);
        Http.getRetrofit().create(HttpApi2.class).deleteVote(map).enqueue(new Callback<VoteManageBean>() {
            @Override
            public void onResponse(Call<VoteManageBean> call, Response<VoteManageBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.DELETE_VOTE_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<VoteManageBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     *  删除帖子
     * @param postId
     * @param handler
     */
    public static void deletePost(String postId, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("postId", postId);
        Http.getRetrofit().create(HttpApi2.class).deletePost(map).enqueue(new Callback<TopicsListBean>() {
            @Override
            public void onResponse(Call<TopicsListBean> call, Response<TopicsListBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.DELETE_POST_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<TopicsListBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 获取投票详情页
     *
     * @param voteId
     * @param timestamp
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getVoteUserList(String voteId, String timestamp, String page, String limit,
                                       final int index, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("postId", voteId);
        map.put("timestamp", timestamp);
        map.put("page", page);
        map.put("limit", limit);
        Http.getRetrofit().create(HttpApi2.class).getVoteDetail(map).enqueue(new Callback<VoteNeophyteBean>() {
            @Override
            public void onResponse(Call<VoteNeophyteBean> call, Response<VoteNeophyteBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<VoteNeophyteBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 获取c端会员列表
     *
     * @param attendUsername
     * @param userCollegegradeId
     * @param timestamp
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getVipList(String attendUsername, String userCollegegradeId, String timestamp, String page,
                                  String limit, final int index, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("attendUsername", attendUsername);
        map.put("userCollegegradeId", userCollegegradeId);
        map.put("timestamp", timestamp);
        map.put("page", page);
        map.put("limit", limit);
        Http.getRetrofit().create(HttpApi2.class).getVipList(map).enqueue(new Callback<MemberManagerBean>() {
            @Override
            public void onResponse(Call<MemberManagerBean> call, Response<MemberManagerBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<MemberManagerBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 踢出会员
     *
     * @param attendId
     * @param handler
     */
    public static void kickOutVip(String attendId, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("attendId", attendId);
        Http.getRetrofit().create(HttpApi2.class).kickOutVip(map).enqueue(new Callback<MemberManagerBean>() {
            @Override
            public void onResponse(Call<MemberManagerBean> call, Response<MemberManagerBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.KICK_OUT_VIP_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<MemberManagerBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     *  获取会员详情
     * @param attendId  会员ID
     * @param postType  帖子分类(2：大家谈、3：有偿提问)
     * @param timestamp
     * @param page
     * @param index
     * @param handler
     */
    public static void getVipInfo(String attendId, String postType, String timestamp, String page, String limit,final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("attendId", attendId);
        map.put("postType", postType);
        map.put("timestamp", timestamp);
        map.put("page", page);
        map.put("limit", limit);
        Http.getRetrofit().create(HttpApi2.class).getVipInfo(map).enqueue(new Callback<MemberDetailBean>() {
            @Override
            public void onResponse(Call<MemberDetailBean> call, Response<MemberDetailBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<MemberDetailBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 获取勋章列表
     * @param timestamp
     * @param page
     * @param handler
     */
    public static void getMedalList(String timestamp,int page,int limit,final int index,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("timestamp",timestamp);
        map.put("page",page+"");
        map.put("limit",limit+"");
        Http.getRetrofit().create(HttpApi2.class).getMedalList(map).enqueue(new Callback<MedalBean>() {
            public void onResponse(Call<MedalBean> call, Response<MedalBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<MedalBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }

    /**
     *  保存会员信息
     * @param attendId   会员ID
     * @param attendUsername  会员名称
     * @param attendType  会员身份(0：学生、1：管理、2：老师、默认为0)
     * @param attendAllowYn   是否拉黑(是否允许再加入：1：是、2：否)
     * @param attendTalkLimit  是否禁言(0：否、1：是)
     * @param attendTalkTime  禁言时间
     * @param medalTypeIds   勋章类型ID
     * @param handler
     */
    public static void saveVip(String attendId,String attendUsername,String attendType,String attendAllowYn,
                              String attendTalkLimit, String attendTalkTime,String medalTypeIds,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("attendId",attendId);
        map.put("attendUsername",attendUsername);
        map.put("attendType",attendType);
        map.put("attendAllowYn",attendAllowYn);
        map.put("attendTalkLimit",attendTalkLimit);
        map.put("attendTalkTime",attendTalkTime);
        map.put("medalTypeIds",medalTypeIds);
        Http.getRetrofit().create(HttpApi2.class).saveVip(map).enqueue(new Callback<MemberSettingBean>() {
            public void onResponse(Call<MemberSettingBean> call, Response<MemberSettingBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.SAVE_VIP_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<MemberSettingBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }

    /**
     * 获取会员申请列表
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getApplyVipList(int page, String limit,
                                       final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("page",page+"");
        map.put("limit",limit);
        Http.getRetrofit().create(HttpApi2.class).getApplyVipList(map).enqueue(new Callback<MemberApplyBean>() {
            public void onResponse(Call<MemberApplyBean> call, Response<MemberApplyBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<MemberApplyBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }

    /**
     * 拒绝/通过会员申请
     * @param attendIds
     * @param attendPassYn
     * @param index
     * @param handler
     */
    public static void applyVipPass(String attendIds, String attendPassYn, final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("attendIds",attendIds);
        map.put("attendPassYn",attendPassYn+"");
        Http.getRetrofit().create(HttpApi2.class).applyVipPass(map).enqueue(new Callback<MemberApplyBean>() {
            public void onResponse(Call<MemberApplyBean> call, Response<MemberApplyBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<MemberApplyBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }

    /**
     *  获取被踢出的会员成功
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getDelynVipList(String page, String limit, final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("page",page);
        map.put("limit",limit);
        Http.getRetrofit().create(HttpApi2.class).getDelynVipList(map).enqueue(new Callback<KickOutMemberBean>() {
            public void onResponse(Call<KickOutMemberBean> call, Response<KickOutMemberBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<KickOutMemberBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }

    /**
     * 邀请会员
     * @param attendId
     * @param handler
     */
    public static void delynVipInvite(String attendId,  final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("attendId",attendId);
        Http.getRetrofit().create(HttpApi2.class).delynVipInvite(map).enqueue(new Callback<KickOutMemberBean>() {
            public void onResponse(Call<KickOutMemberBean> call, Response<KickOutMemberBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.DELYN_VIP_INVITE_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<KickOutMemberBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }

    /**
     * 获取黑名单
     * @param page
     * @param limit
     * @param index
     * @param handler
     */
    public static void getBlackList(String page, String limit, final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("page",page);
        map.put("limit",limit);
        Http.getRetrofit().create(HttpApi2.class).getBlackList(map).enqueue(new Callback<BlackListBean>() {
            public void onResponse(Call<BlackListBean> call, Response<BlackListBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<BlackListBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }

    /**
     * 移除黑名单
     * @param attendId  申请id
     * @param handler
     */
    public static void removeBlackList(String attendId,  final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("attendId",attendId);
        Http.getRetrofit().create(HttpApi2.class).removeBlackList(map).enqueue(new Callback<BlackListBean>() {
            public void onResponse(Call<BlackListBean> call, Response<BlackListBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.REMOVE_BLACK_LIST_SUCCESS, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<BlackListBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });
    }


    /**
     * 删除投票参与者
     *
     * @param
     * @param handler
     */
    public static void deleteVoteMember(String voteResultId, final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("voteResultId", voteResultId);
        Http.getRetrofit().create(HttpApi2.class).deleteVoteMember(map).enqueue(new Callback<VoteNeophyteBean>() {
            @Override
            public void onResponse(Call<VoteNeophyteBean> call, Response<VoteNeophyteBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.DELETE_VOTE_MEMBER_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                    sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<VoteNeophyteBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
                sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

    /**
     * 添加浏览量
     *
     * @param
     * @param handler
     */
    public static void updatePostColl(String postId , final Handler handler) {

        Map<String, String> map = new HashMap<>();
        map.put("postId", postId);
        Http.getRetrofit().create(HttpApi2.class).updatePostColl(map).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                try {
                    sendMessage(handler, HandlerConstant2.UPDATE_POST_COLL_SUCCESS, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("查询数据异常："+e.getMessage());
                   // sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                LogUtils.e("查询数据报错："+t.getMessage());
               // sendMessage(handler, HandlerConstant1.REQUST_ERROR, null);
            }
        });

    }

}
