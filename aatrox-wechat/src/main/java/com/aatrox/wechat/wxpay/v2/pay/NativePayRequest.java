package com.aatrox.wechat.wxpay.v2.pay;

import com.aatrox.wechat.wxpay.WxPayService;
import com.aatrox.wechat.wxpay.v2.request.BasePayRequest;
import com.aatrox.wechat.wxpay.v2.request.BasePayResponse;
import com.aatrox.wechat.wxpay.v2.response.NativePayResponse;

import java.util.Date;

/**
 * @author aatrox
 * @desc  二维码微信支付封装对象
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1">更多明细</a>
 * @date 2020/7/1
 */
public class NativePayRequest extends BasePayRequest<NativePayResponse> {

    //交易类型
    private String trade_type = "APP";

    public NativePayRequest(String out_trade_no, String body, double price, Date empireTime, String spbill_create_ip,
                         String url) {
        super(out_trade_no, body, price, empireTime, spbill_create_ip, url);
    }

    @Override
    public NativePayResponse doRequest(WxPayService wxPayService) {
        NativePayResponse response = doRequest(wxPayService, NativePayResponse.class);
        return response;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public NativePayRequest setTrade_type(String trade_type) {
        this.trade_type = trade_type;
        return this;
    }

    @Override
    protected String requestPath() {
        return "https://api.mch.weixin.qq.com/pay/unifiedorder";
    }
}