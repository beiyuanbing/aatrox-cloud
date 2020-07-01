package com.aatrox.wechat.wxpay.v2.response;

import com.aatrox.wechat.wxpay.v2.request.BasePayResponse;

/**
 * @author aatrox
 * @desc
 * @date 2020/7/1
 */
public class H5PayResponse extends BasePayResponse {
    /**支付跳转url**/
    private String mweb_url;

    public String getMweb_url() {
        return mweb_url;
    }

    public H5PayResponse setMweb_url(String mweb_url) {
        this.mweb_url = mweb_url;
        return this;
    }
}
