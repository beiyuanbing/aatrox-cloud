package com.aatrox.wechat.wxpay.v2.pay;

import com.aatrox.wechat.wxpay.WxPayService;
import com.aatrox.wechat.wxpay.v2.request.BasePayRequest;
import com.aatrox.wechat.wxpay.v2.request.BasePayResponse;

import java.util.Date;

/**
 * @author aatrox
 * @desc JSAPI支付（或小程序支付)
 * @date 2020/6/30
 */
public class JsApiPayRequest extends BasePayRequest<BasePayResponse> {

    //交易类型
    private String trade_type = "JSAPI";

    public JsApiPayRequest(String out_trade_no, String body, double price, Date empireTime, String spbill_create_ip,
                           String url) {
        super(out_trade_no, body, price, empireTime, spbill_create_ip, url);
    }

    @Override
    public BasePayResponse doRequest(WxPayService wxPayService) {
        BasePayResponse response = doRequest(wxPayService,BasePayResponse.class);
        return response;
    }

    @Override
    protected String requestPath() {
        return "https://api.mch.weixin.qq.com/pay/unifiedorder";
    }
}
