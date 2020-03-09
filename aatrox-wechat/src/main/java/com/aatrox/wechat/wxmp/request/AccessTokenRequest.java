package com.aatrox.wechat.wxmp.request;

import com.aatrox.common.utils.HttpClientUtil;
import com.aatrox.wechat.wxmp.base.WxmpBaseRequest;
import com.aatrox.wechat.wxmp.response.AccessTokenResponse;
import com.aatrox.wechat.wxmp.service.WechatService;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.entity.ContentType;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
public class AccessTokenRequest extends WxmpBaseRequest<AccessTokenResponse> {
    private String grantType = "client_credential";

    public AccessTokenRequest() {
    }

    @Override
    protected String requestUrl() {
        StringBuffer sb = new StringBuffer("https://api.weixin.qq.com/cgi-bin/token");
        sb.append("?appid=").append(this.appId).append("&secret=").append(this.secret).append("&grant_type=").append(this.grantType);
        return sb.toString();
    }

    @Override
    protected AccessTokenResponse doRequest() {
        boolean safe = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement[] var3 = stackTrace;
        int var4 = stackTrace.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            StackTraceElement stack = var3[var5];
            if (stack.getClassName().equals(WechatService.class.getName())) {
                safe = true;
                break;
            }
        }

        if (!safe) {
            throw new RuntimeException("请使用" + WechatService.class.getSimpleName() + "进行accessToken初始化");
        } else {
            HttpClientUtil.HttpResult result = HttpClientUtil.get(this.requestUrl(), ContentType.APPLICATION_JSON.getMimeType());
            return (AccessTokenResponse) JSONObject.parseObject(result.contentToString(), AccessTokenResponse.class);
        }
    }
}
