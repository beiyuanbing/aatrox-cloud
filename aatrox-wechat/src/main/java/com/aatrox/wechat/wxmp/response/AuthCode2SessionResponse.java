package com.aatrox.wechat.wxmp.response;

import com.aatrox.wechat.wxmp.base.WxmpBaseResponse;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
public class AuthCode2SessionResponse extends WxmpBaseResponse {
    private String openid;
    private String session_key;
    private String unionid;

    public AuthCode2SessionResponse() {
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return this.session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getUnionid() {
        return this.unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
