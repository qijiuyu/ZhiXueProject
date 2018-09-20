package com.example.administrator.zhixueproject.http.method;

import android.os.Handler;

import com.example.administrator.zhixueproject.bean.topic.TopicsListBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.api.HttpApi2;
import com.example.administrator.zhixueproject.http.base.BaseRequst;
import com.example.administrator.zhixueproject.http.base.Http;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpMethod2 extends BaseRequst{

    private static String PAGE = "page";
    private static String LIMIT = "limit";
    private static String TIME = "timestamp";

    /**
     * 获取话题列表
     * @param c  用户id
     * @param collegeId  学院id
     * @param timestamp  时间戳
     * @param page       页号
     * @param limit      每页显示的条数
     * @param handler
     */
    public static void getTopicList(String c, String collegeId, String timestamp, String page, String limit, final int index, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("c", c);
        // map.put("collegeId", MUtils.getMUtils().getMem("collegeId","45"));
        map.put("collegeId", "45");
        map.put(TIME, timestamp);
        map.put(PAGE, page);
        map.put(LIMIT, limit);
        Http.getRetrofit().create(HttpApi2.class).getTopicList(map).enqueue(new Callback<TopicsListBean.DataBean>() {
            public void onResponse(Call<TopicsListBean.DataBean> call, Response<TopicsListBean.DataBean> response) {
                try {
                    sendMessage(handler, index, response.body());
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(handler, HandlerConstant2.REQUST_ERROR, null);
                }
            }

            public void onFailure(Call<TopicsListBean.DataBean> call, Throwable t) {
                sendMessage(handler, HandlerConstant2.REQUST_ERROR, null);
            }
        });
    }
}
