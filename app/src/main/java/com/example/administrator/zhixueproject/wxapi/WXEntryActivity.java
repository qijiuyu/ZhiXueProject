package com.example.administrator.zhixueproject.wxapi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.administrator.zhixueproject.activity.login.LoginActivity;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HttpConstant;
import com.example.administrator.zhixueproject.http.base.Http;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI mWeixinAPI;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeixinAPI = WXAPIFactory.createWXAPI(this, HttpConstant.WX_APPID, true);
        mWeixinAPI.handleIntent(this.getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWeixinAPI.handleIntent(intent, this);//必须调用此句话
    }

    //微信发送的请求将回调到onReq方法
    @Override
    public void onReq(BaseReq req) {
    }

    //发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //发送成功
                SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                if (sendResp != null) {
                    String code = sendResp.code;
                    getAccess_token(code);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //发送取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //发送被拒绝
                break;
            default:
                //发送返回
                break;
        }

    }


    private String openId;
    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final String message=msg.obj.toString();
            switch (msg.what){
                //获取openid accessToken值用于后期操作
                case HandlerConstant1.GET_WX_ACCESS_TOKEN_SUCCESS:
                     try {
                        JSONObject jsonObject = new JSONObject(message);
                        openId = jsonObject.getString("openid").toString().trim();
                        String access_token = jsonObject.getString("access_token").toString().trim();

                        getUserMsg(access_token,openId);
                     } catch (JSONException e) {
                            e.printStackTrace();
                     }
                     break;
                case HandlerConstant1.GET_WX_USER_SUCCESS:
                    try {
                        JSONObject jsonObject = new JSONObject(message);
                        String nickname = jsonObject.getString("nickname");
                        int sex = Integer.parseInt(jsonObject.get("sex").toString());
                        String headimgurl = jsonObject.getString("headimgurl");
                        Intent intent=new Intent(LoginActivity.ACTION_WEIXIN_LOGIN_OPENID);
                        intent.putExtra("openId",openId);
                        sendBroadcast(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                     break;
                     default:
                         break;
            }
        }
    };


    /**
     * 获取openid accessToken值用于后期操作
     * @param code
     */
    private void getAccess_token(final String code) {
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+HttpConstant.WX_APPID+"&secret="+HttpConstant.WX_APPSECRET+"&code="+code+"&grant_type=authorization_code";
        Http.getMonth(url,mHandler,HandlerConstant1.GET_WX_ACCESS_TOKEN_SUCCESS);
    }


    /**
     *  获取微信的个人信息
     * @param access_token
     * @param openid
     */
    private void getUserMsg(final String access_token, final String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        Http.getMonth(path,mHandler,HandlerConstant1.GET_WX_USER_SUCCESS);
    }


//
//    /**
//     * 获取微信的个人信息
//     * @param access_token
//     * @param openid
//     */
//    private void getUserMesg(final String access_token, final String openid) {
//        String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
//                + access_token
//                + "&openid="
//                + openid;
//        LogUtils.log("getUserMesg：" + path);
//        //网络请求，根据自己的请求方式
//        VolleyRequest.get(this, path, "getAccess_token", false, null, new VolleyRequest.Callback() {
//            @Override
//            public void onSuccess(String result) {
//                LogUtils.log("getUserMesg_result:" + result);
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(result);
//                    String nickname = jsonObject.getString("nickname");
//                    int sex = Integer.parseInt(jsonObject.get("sex").toString());
//                    String headimgurl = jsonObject.getString("headimgurl");
//
//                    LogUtils.log("用户基本信息:");
//                    LogUtils.log("nickname:" + nickname);
//                    LogUtils.log("sex:" + sex);
//                    LogUtils.log("headimgurl:" + headimgurl);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                finish();
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });
//    }

}
