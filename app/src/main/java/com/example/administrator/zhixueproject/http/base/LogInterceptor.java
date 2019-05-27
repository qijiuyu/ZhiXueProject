package com.example.administrator.zhixueproject.http.base;


import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.UserBean;
import com.example.administrator.zhixueproject.http.api.HttpApi1;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.ParameterUtil;
import com.example.administrator.zhixueproject.utils.SPUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by lyn on 2017/4/13.
 */

public class LogInterceptor implements Interceptor {


    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        LogUtils.e(String.format("request %s on %s%n%s", request.url(), request.method(), request.headers()));
        if (request.method().equals("POST")) {
            request = addParameter(request);
        }
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        String body = response.body().string();
        if(getCode(body)==900001){
            Request request2=request;
            try {
                String message=getAccessToken();
                final JSONObject jsonObject=new JSONObject(message);
                if(jsonObject.getBoolean("status")){
                    final JSONObject jsonObject2=new JSONObject(jsonObject.getString("data"));
                    MyApplication.spUtil.addString(SPUtil.TOKEN,jsonObject2.getString("token"));
                    LogUtils.e(jsonObject2.getString("token")+"________");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            //继续执行上个请求
            Response response2 = chain.proceed(request2);
            body = response2.body().string();
        }

        LogUtils.e(String.format("response %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, body));
        return response.newBuilder().body(ResponseBody.create(response.body().contentType(), body)).build();
    }


    /***
     * 添加公共参数
     */
    public Request addParameter(Request request) throws IOException {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        FormBody formBody;
        Map<String, String> requstMap = new HashMap<>();
        if (request.body().contentLength() > 0 && request.body() instanceof FormBody) {
            formBody = (FormBody) request.body();
            //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
            for (int i = 0; i < formBody.size(); i++) {
                  requstMap.put(formBody.name(i), formBody.value(i).replace("+","%2B"));
                  LogUtils.e(request.url() + "参数:" + formBody.name(i) + "=" + formBody.value(i));
            }
        }
        requstMap = ParameterUtil.getParamter(requstMap);
        //添加公共参数
        for (String key : requstMap.keySet()) {
            bodyBuilder.addEncoded(key, requstMap.get(key));
        }
        formBody = bodyBuilder.build();
        LogUtils.e("Authentication===  "+MyApplication.spUtil.getString(SPUtil.TOKEN));
        request = request.newBuilder().addHeader("Authentication", MyApplication.spUtil.getString(SPUtil.TOKEN)).post(formBody).build();
        return request;
    }


    /**
     * 获取AccessToken
     */
    public String getAccessToken() throws IOException {
        final UserBean userBean=MyApplication.userInfo.getData().getUser();
        Map<String, String> map = new HashMap<>();
        map.put("userId", userBean.getUserId()+"");
        String message = Http.getRetrofit().create(HttpApi1.class).autoLogin(map).execute().body().string();
        return message;
    }

    public int getCode(String json) {
        int code = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            if(!jsonObject.isNull("errorCode")){
                code=jsonObject.getInt("errorCode");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

}
