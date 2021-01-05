package com.aatrox.wechat.wxpay.v2.request;

import com.aatrox.common.utils.DateUtil;
import com.aatrox.common.utils.MathUtil;
import com.aatrox.wechat.wxpay.WxPayConfigInfo;


import java.util.Date;

/**
 * @author aatrox
 * @desc
 * @date 2020/6/30
 */
public abstract class BasePayRequest<T extends WxPayBaseResponse> extends WxPayBaseRequest<T> {
    /**商品或支付单简要描述(32)**/
    protected String body;
    /**商户订单号,商户系统内部的订单号,32个字符内、可包含字母**/
    protected String out_trade_no;
    /**订单总金额(分),只能为整数，详见支付金额**/
    protected int total_fee;
    /**8.8.8.8	APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。**/
    protected String spbill_create_ip;
    /**超时时间**/
    protected String time_expire;
    /**接收微信支付异步通知回调地址**/
    protected String notify_url;

    public BasePayRequest(String out_trade_no,
                          String body, double price, Date empireTime,
                          String spbill_create_ip, String url) {
        this.out_trade_no = out_trade_no;
        this.body = body;
        this.total_fee = (int) MathUtil.doubleMul(price, 100);
        this.time_expire = DateUtil.format(empireTime, "yyyyMMddHHmmss");
        this.spbill_create_ip = spbill_create_ip;
        this.notify_url = url;
    }

    public String getBody() {
        return body;
    }

    public BasePayRequest<T> setBody(String body) {
        this.body = body;
        return this;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public BasePayRequest<T> setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
        return this;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public BasePayRequest<T> setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
        return this;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public BasePayRequest<T> setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
        return this;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public BasePayRequest<T> setTime_expire(String time_expire) {
        this.time_expire = time_expire;
        return this;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public BasePayRequest<T> setNotify_url(String notify_url) {
        this.notify_url = notify_url;
        return this;
    }

    @Override
    protected void addParam(WxPayConfigInfo wxPayConfig) {
        setNotify_url(wxPayConfig.getNotifyUrlPre()+getNotify_url());
    }

}
