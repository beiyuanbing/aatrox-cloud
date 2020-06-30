package com.aatrox.wechat.wxpay;

/**
 * @author aatrox
 * @desc
 * @date 2020/6/30
 */
public class WxPayConfigInfo {
    /**私有Key**/
    private String partnerKey;

    /**微信分配的公众账号ID（企业号corpid即为此appId）**/
    private String appId;

    /**微信支付分配的商户号**/
    private String mchId;

    /**通知回调地址前缀，用于拼装完整路径**/
    private String notifyUrlPre;

    /**WAP站的地址**/
    private String wapUrl;

    /**WAP站的地址**/
    private String wapName;

    /**退款证书路径**/
    private String credentialsPath;

    /**H5支付后跳转页面**/
    private String redirectUrl;

    /**连接等待超时时间，默认10秒**/
    private int socketTimeout = 10000;

    /**连接建立超时时间，默认10秒**/
    private int connectTimeout = 10000;

    public String getPartnerKey() {
        return partnerKey;
    }

    public WxPayConfigInfo setPartnerKey(String partnerKey) {
        this.partnerKey = partnerKey;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public WxPayConfigInfo setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getMchId() {
        return mchId;
    }

    public WxPayConfigInfo setMchId(String mchId) {
        this.mchId = mchId;
        return this;
    }

    public String getNotifyUrlPre() {
        return notifyUrlPre;
    }

    public WxPayConfigInfo setNotifyUrlPre(String notifyUrlPre) {
        this.notifyUrlPre = notifyUrlPre;
        return this;
    }

    public String getWapUrl() {
        return wapUrl;
    }

    public WxPayConfigInfo setWapUrl(String wapUrl) {
        this.wapUrl = wapUrl;
        return this;
    }

    public String getWapName() {
        return wapName;
    }

    public WxPayConfigInfo setWapName(String wapName) {
        this.wapName = wapName;
        return this;
    }

    public String getCredentialsPath() {
        return credentialsPath;
    }

    public WxPayConfigInfo setCredentialsPath(String credentialsPath) {
        this.credentialsPath = credentialsPath;
        return this;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public WxPayConfigInfo setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public WxPayConfigInfo setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public WxPayConfigInfo setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }
}
