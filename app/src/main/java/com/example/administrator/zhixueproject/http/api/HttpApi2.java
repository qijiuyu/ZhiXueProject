package com.example.administrator.zhixueproject.http.api;

import com.example.administrator.zhixueproject.bean.BaseBean;
import com.example.administrator.zhixueproject.bean.Medal;
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
import com.example.administrator.zhixueproject.http.HttpConstant;
import java.util.Map;

import okhttp3.ResponseBody;
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

    /**
     *  添加投票
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.ADD_VOTE)
    Call<UploadFile> addVote(@FieldMap Map<String,String> map);


    /**
     * 评论帖子
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.COMMENT_POST)
    Call<BaseBean> commentPost(@FieldMap Map<String,String> map);

    /**
     * 评论楼层
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.COMMENT_REPLY)
    Call<BaseBean> commentReply(@FieldMap Map<String,String> map);

    /**
     * 获取帖子详情
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_POST_DETAIL)
    Call<PostsDetailsBean>getPostDetail(@FieldMap Map<String,String> map);

    /**
     * 获取有偿提问帖子详情
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_YOUCHANG_DETAIL)
    Call<PostsDetailsBean>getYouChangDetail(@FieldMap Map<String,String> map);

    /**
     * 获取活动列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_ACTIVITY_LIST)
    Call<ActionManageBean>getActivityList(@FieldMap Map<String,String> map);


    /**
     * 获取活动列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.DELETE_ACTIVITY)
    Call<ActionManageBean>deleteActivity(@FieldMap Map<String,String> map);

    /**
     * 获取活动参与者列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_ACTIVITY_USER_LIST)
    Call<ActionNeophyteBean>getActivityUserList(@FieldMap Map<String,String> map);

    /**
     * 获取投票活动列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_VOTE_LIST)
    Call<VoteManageBean>getVoteList(@FieldMap Map<String,String> map);

    /**
     * 删除投票活动
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.DELETE_VOTE)
    Call<VoteManageBean>deleteVote(@FieldMap Map<String,String> map);

    /**
     * 删除帖子
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.DELETE_POST)
    Call<PostsCourseBean>deletePost(@FieldMap Map<String,String> map);
    /**
     * 获取投票详情页
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_VOTE_DETAIL)
    Call<VoteNeophyteBean>getVoteDetail(@FieldMap Map<String,String> map);

    /**
     * 获取c端会列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_VIP_LIST)
    Call<MemberManagerBean>getVipList(@FieldMap Map<String,String> map);

    /**
     * 踢出会员
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.KICK_OUT_VIP)
    Call<MemberManagerBean>kickOutVip(@FieldMap Map<String,String> map);

    /**
     * 获取会员详情
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_VIP_INFO)
    Call<MemberDetailBean>getVipInfo(@FieldMap Map<String,String> map);

    /**
     * 获取勋章列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_MEDAL_LIST)
    Call<MedalBean> getMedalList(@FieldMap Map<String, String> map);

    /**
     * 保存会员信息
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.SAVE_VIP)
    Call<MemberSettingBean> saveVip(@FieldMap Map<String, String> map);

    /**
     * 获取申请会员列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_APPLY_VIP_LIST)
    Call<MemberApplyBean> getApplyVipList(@FieldMap Map<String, String> map);

    /**
     * 拒绝/同意会员申请
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.APPLY_VIP_PASS)
    Call<MemberApplyBean> applyVipPass(@FieldMap Map<String, String> map);

    /**
     * 获取踢出的会员列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_DELYN_VIP_LIST)
    Call<KickOutMemberBean> getDelynVipList(@FieldMap Map<String, String> map);

    /**
     * 邀请会员
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.DELYN_VIP_INVITE)
    Call<KickOutMemberBean> delynVipInvite(@FieldMap Map<String, String> map);

    /**
     *  获取黑名单
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.GET_BLACK_LIST)
    Call<BlackListBean> getBlackList(@FieldMap Map<String, String> map);

    /**
     *  移除黑名单
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.REMOVE_BLACK_LIST)
    Call<BlackListBean> removeBlackList(@FieldMap Map<String, String> map);

    /**
     * 删除投票参与者
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.DELETE_VOTE_MEMBER)
    Call<VoteNeophyteBean>deleteVoteMember(@FieldMap Map<String,String> map);

    /**
     * 添加浏览量
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConstant.UPDATE_POST_COLL)
    Call<BaseBean>updatePostColl(@FieldMap Map<String,String> map);
}
