package com.aatrox.wechat.wxpay.v2.request;

/**
 * @author aatrox
 * @desc
 * @date 2020/6/30
 */
public class BasePayResponse extends WxPayBaseResponse {
    /**
     * 交易类型
     **/
    private String trade_type = "";
    /**
     * 预支付交易会话标识
     **/
    private String prepay_id = "";

    public String getTrade_type() {
        return trade_type;
    }

    public BasePayResponse setTrade_type(String trade_type) {
        this.trade_type = trade_type;
        return this;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public BasePayResponse setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
        return this;
    }
}
