package com.aatrox.wechat.wxpay.v2.pay;

import com.aatrox.wechat.wxpay.WxPayService;
import com.aatrox.wechat.wxpay.v2.request.BasePayRequest;
import com.aatrox.wechat.wxpay.v2.request.BasePayResponse;
import com.aatrox.wechat.wxpay.v2.response.H5PayResponse;
import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author aatrox
 * @desc
 * @date 2020/7/1
 */
public class H5PayRequest extends BasePayRequest<H5PayResponse> {

    /**交易类型**/
    private String trade_type = "MWEB";

    /**场景信息    {"h5_info": {"type":"Wap","wap_url": "WAP网站URL地址","wap_name": "WAP网站名"}}**/
    private String scene_info;

    /**回调传参**/
    @JSONField(serialize = false)
    @XStreamOmitField
    private String redirectInfo;

    public H5PayRequest(String out_trade_no, String body, double price, Date empireTime, String spbill_create_ip,
                        String url) {
        super(out_trade_no, body, price, empireTime, spbill_create_ip, url);
    }

    @Override
    public H5PayResponse doRequest(WxPayService wxPayService) {
        H5PayResponse response = doRequest(wxPayService,H5PayResponse.class);
        String redirect_url = wxPayConfig.getRedirectUrl() + (StringUtils.isBlank(redirectInfo)?"": "?"+redirectInfo);
        try {
            response.setMweb_url(response.getMweb_url() + "&redirect_url=" + URLEncoder.encode(redirect_url, "utf-8"));
        } catch (UnsupportedEncodingException e) {}
        return response;
    }

    @Override
    protected String requestPath() {
        return null;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public H5PayRequest setTrade_type(String trade_type) {
        this.trade_type = trade_type;
        return this;
    }

    public String getScene_info() {
        return scene_info;
    }

    public H5PayRequest setScene_info(String scene_info) {
        this.scene_info = scene_info;
        return this;
    }

    public String getRedirectInfo() {
        return redirectInfo;
    }

    public H5PayRequest setRedirectInfo(String redirectInfo) {
        this.redirectInfo = redirectInfo;
        return this;
    }
}
