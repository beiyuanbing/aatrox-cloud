package com.aatrox.wechat.wxpay.v2.pay;

import com.aatrox.wechat.wxpay.v2.request.BasePayRequest;
import com.aatrox.wechat.wxpay.v2.request.BasePayResponse;
import com.aatrox.wechat.wxpay.WxPayService;


import java.util.Date;

/**
 * @author aatrox
 * @desc JSAPI支付（或小程序支付)
 * @date 2020/6/30
 */
public class AppPayRequest extends BasePayRequest<BasePayResponse> {

    //交易类型
    private String trade_type = "APP";

    public AppPayRequest(String out_trade_no, String body, double price, Date empireTime, String spbill_create_ip,
                         String url) {
        super(out_trade_no, body, price, empireTime, spbill_create_ip, url);
    }

    @Override
    public BasePayResponse doRequest(WxPayService wxPayService) {
        BasePayResponse response = doRequest(wxPayService,BasePayResponse.class);
        return response;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public AppPayRequest setTrade_type(String trade_type) {
        this.trade_type = trade_type;
        return this;
    }

    @Override
    protected String requestPath() {
        return "https://api.mch.weixin.qq.com/pay/unifiedorder";
    }
}
