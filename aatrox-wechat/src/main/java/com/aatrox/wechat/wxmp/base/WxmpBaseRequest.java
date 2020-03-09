package com.aatrox.wechat.wxmp.base;

import com.aatrox.wechat.wxmp.service.WechatService;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author aatrox
 * @desc 微信请求的基类
 * @date 2020/1/6
 */
@Data
@Accessors(chain = true)
public abstract class WxmpBaseRequest<T extends WxmpBaseResponse> {
    protected String appId;
    protected String secret;
    protected String accessToken;

    public WxmpBaseRequest() {
    }

    public T doRequest(WechatService wechatService) {
        this.appId = wechatService.getAppId();
        this.secret = wechatService.getSecret();
        if (this.needAccessToken()) {
            this.accessToken = wechatService.pickAccessToken();
        }

        return this.doRequest();
    }

    protected abstract T doRequest();

    protected boolean needAccessToken() {
        return false;
    }

    protected abstract String requestUrl();

    protected String toPostBodyString() {
        return null;
    }
}
