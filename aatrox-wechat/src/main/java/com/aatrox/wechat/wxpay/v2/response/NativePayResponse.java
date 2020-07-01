package com.aatrox.wechat.wxpay.v2.response;

import com.aatrox.wechat.wxpay.v2.request.BasePayResponse;

/**
 * @author aatrox
 * @desc
 * @date 2020/7/1
 */
public class NativePayResponse extends BasePayResponse {
    /**trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付**/
    private String code_url;

    public String getCode_url() {
        return code_url;
    }

    public NativePayResponse setCode_url(String code_url) {
        this.code_url = code_url;
        return this;
    }
}
