package com.aatrox.common.utils.push;

/**
 * 推送数据返回
 *
 * @author aatrox
 * @desc
 * @date 2019-08-08
 */
public class PushModelResponse {
    /**
     * 状态码
     **/
    private int code = 200;
    /**
     * 消息
     **/
    private String msg = "success";
    /***发送数据**/
    private String sendRequest;
    /**
     * 推送返回结果
     **/
    private String pushResult;

    public int getCode() {
        return code;
    }

    public PushModelResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public PushModelResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getPushResult() {
        return pushResult;
    }

    public PushModelResponse setPushResult(String pushResult) {
        this.pushResult = pushResult;
        return this;
    }

    public String getSendRequest() {
        return sendRequest;
    }

    public PushModelResponse setSendRequest(String sendRequest) {
        this.sendRequest = sendRequest;
        return this;
    }

    @Override
    public String toString() {
        return "PushModelResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", sendRequest='" + sendRequest + '\'' +
                ", pushResult='" + pushResult + '\'' +
                '}';
    }
}
