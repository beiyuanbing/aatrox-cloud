package com.aatrox.wechat.wxmp.request;

import com.aatrox.common.utils.HttpClientUtil;
import com.aatrox.wechat.wxmp.base.WxmpBaseRequest;
import com.aatrox.wechat.wxmp.response.AuthCode2SessionResponse;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.entity.ContentType;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
public class AuthCode2SessionRequest extends WxmpBaseRequest<AuthCode2SessionResponse> {
    private String jsCode;
    private String grantType = "authorization_code";

    public AuthCode2SessionRequest(String jsCode) {
        this.jsCode = jsCode;
    }

    @Override
    protected String requestUrl() {
        StringBuffer sb = new StringBuffer("https://api.weixin.qq.com/sns/jscode2session");
        sb.append("?appid=").append(this.appId).append("&secret=").append(this.secret).append("&js_code=").append(this.jsCode).append("&grant_type=").append(this.grantType);
        return sb.toString();
    }

    @Override
    protected AuthCode2SessionResponse doRequest() {
        HttpClientUtil.HttpResult result = HttpClientUtil.get(this.requestUrl(), ContentType.APPLICATION_JSON.getMimeType());
        return (AuthCode2SessionResponse) JSONObject.parseObject(result.contentToString(), AuthCode2SessionResponse.class);
    }
}

