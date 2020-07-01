package com.aatrox.wechat.wxpay.v3.request;

/**
 * @author aatrox
 * @desc
 * @date 2020/6/30
 */
public class WxPayBaseResponse {
    public static final String SUCCESS_CODE = "SUCCESS";

    public static final String FAIL_CODE = "FAIL";
    /**返回状态码,通信标识**/
    private String return_code;

    /**返回信息**/
    private String return_msg;

    /**错误代码**/
    private String err_code;

    /**错误代码描述**/
    private String err_code_des;

    /**小程序ID**/
    private String appid;

    /**商户号**/
    private String mch_id;

    /**随机字符串**/
    private String nonce_str;

    /**业务结果**/
    private String result_code;

    /**签名**/
    private String sign;

    /**商户订单号**/
    private String out_trade_no;

    /**微信订单号**/
    private String transaction_id;

    public static String getSuccessCode() {
        return SUCCESS_CODE;
    }

    public static String getFailCode() {
        return FAIL_CODE;
    }

    public String getReturn_code() {
        return return_code;
    }

    public WxPayBaseResponse setReturn_code(String return_code) {
        this.return_code = return_code;
        return this;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public WxPayBaseResponse setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
        return this;
    }

    public String getErr_code() {
        return err_code;
    }

    public WxPayBaseResponse setErr_code(String err_code) {
        this.err_code = err_code;
        return this;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public WxPayBaseResponse setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
        return this;
    }

    public String getAppid() {
        return appid;
    }

    public WxPayBaseResponse setAppid(String appid) {
        this.appid = appid;
        return this;
    }

    public String getMch_id() {
        return mch_id;
    }

    public WxPayBaseResponse setMch_id(String mch_id) {
        this.mch_id = mch_id;
        return this;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public WxPayBaseResponse setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
        return this;
    }

    public String getResult_code() {
        return result_code;
    }

    public WxPayBaseResponse setResult_code(String result_code) {
        this.result_code = result_code;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public WxPayBaseResponse setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public WxPayBaseResponse setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
        return this;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public WxPayBaseResponse setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
        return this;
    }

    public boolean isSuccess(){
        return SUCCESS_CODE.equals(return_code);
    }
}
