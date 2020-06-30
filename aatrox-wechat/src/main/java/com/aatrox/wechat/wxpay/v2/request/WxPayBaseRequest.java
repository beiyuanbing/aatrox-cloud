package com.aatrox.wechat.wxpay.v2.request;

import com.aatrox.wechat.wxpay.util.HttpsRequest;
import com.aatrox.wechat.wxpay.util.Signature;
import com.aatrox.wechat.util.XMLParser;
import com.aatrox.wechat.wxpay.WxPayConfigInfo;
import com.aatrox.wechat.wxpay.WxPayService;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author aatrox
 * @desc
 * @date 2020/6/30
 */
public abstract class WxPayBaseRequest<T extends WxPayBaseResponse> {
    private Logger logger = LoggerFactory.getLogger(WxPayBaseRequest.class);
    /**
     * 微信开放平台审核通过的应用APPID
     **/
    protected String appid;
    /**
     * 微信支付分配的商户号
     **/
    protected String mch_id;
    /**
     * 随机字符串，不长于32位。推荐随机数生成算法
     **/
    protected String nonce_str = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    /**
     * 签名，详见签名生成算法
     **/
    protected String sign;

    @JSONField(serialize = false)
    @XStreamOmitField
    protected String bizContent;

    @JSONField(serialize = false)
    @XStreamOmitField
    protected WxPayConfigInfo wxPayConfig;

    public abstract T doRequest(WxPayService wxPayService);

    protected final T doRequest(WxPayService wxPayService,Class<T> clazz){
        bizContent = JSONObject.toJSONString(this);
        wxPayConfig = wxPayService.getWxPayConfig();
        appid = this.wxPayConfig.getAppId();
        mch_id = this.wxPayConfig.getMchId();

        String method = logRequest();

        try{
            setPostSign(this);
            HttpsRequest request = initHttpsRequest();
            /**调用请求获取返回数据**/
            String result = this.getPostResult(request);
            T response = XMLParser.getResponseFromXML(result,clazz);
            if(!response.isSuccess()){
                throw new RuntimeException(response.getReturn_msg());
            }
            String localSign = Signature.getSignFromXmlStr(result, this.wxPayConfig.getPartnerKey());
            if(!localSign.equals(response.getSign())){
                throw new RuntimeException("签名不匹配");
            }
            return response;
        }catch (Exception e){
            logger.error(method, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getPostResult(HttpsRequest request){

        return request.sendPostXml(requestPath(), this);
    }

    private HttpsRequest initHttpsRequest() throws Exception {
        HttpsRequest hr = new HttpsRequest();
        hr.setRequestConfig(RequestConfig.custom().setSocketTimeout(wxPayConfig.getSocketTimeout())
                .setConnectTimeout(wxPayConfig.getConnectTimeout()).build());
        initClient(hr);
        return hr;
    }

    protected void initClient(HttpsRequest hr) throws Exception{
        hr.setHttpClient(HttpClients.custom().build());
    }

    /**
     * 生成微信支付签名
     **/
    private void setPostSign(WxPayBaseRequest post) throws IllegalAccessException {
        addParam(wxPayConfig);
        sign = Signature.getSign(post,wxPayConfig.getPartnerKey());
    }

    protected void addParam(WxPayConfigInfo wxPayConfig){
        //do nothing
    }

    protected abstract String requestPath();

    private String logRequest() {
        String method = new StringBuilder()
                .append("[").append(this.getClass().getSimpleName()).append("]").toString();
        logger.info(method + bizContent);
        return method;
    }

    public String getAppid() {
        return appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public String getSign() {
        return sign;
    }
}
