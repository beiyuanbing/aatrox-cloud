package com.aatrox.wechat.wxpay;

import com.aatrox.wechat.wxpay.v2.request.WxPayBaseRequest;
import com.aatrox.wechat.wxpay.v2.request.WxPayBaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author aatrox
 * @desc  支付核心处理类
 * @date 2020/6/30
 */
public class WxPayService {

    private Logger logger = LoggerFactory.getLogger(WxPayService.class);

    private WxPayConfigInfo wxPayConfig;

    public WxPayService(WxPayConfigInfo wxPayConfig){
        this.wxPayConfig = wxPayConfig;
    }

    public WxPayConfigInfo getWxPayConfig() {
        return wxPayConfig;
    }

    public String getWxPayConfigAppId() {
        return wxPayConfig.getAppId();
    }

    public <T extends WxPayBaseResponse> T doRequest(WxPayBaseRequest<T> request){
        return request.doRequest(this);
    }

    /**
     * 退款回调
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_16&index=11">更多明细</a>
     */




    /**
     * 支付回调
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_7&index=8">更多明细</a>
     */
}
