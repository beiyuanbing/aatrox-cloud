package com.aatrox.apilist.form;

import java.io.Serializable;

public class RequestResult<T> implements Serializable {
    private boolean success = true;
    private T body;
    private String errMessage;
    private String errCode;

    public RequestResult() {
    }

    public RequestResult(T body) {
        this.body = body;
    }

    public static <T> RequestResult ok(T body) {
        return new RequestResult(body);
    }

    public RequestResult(boolean success, T body) {
        this.success = success;
        this.body = body;
    }

    public RequestResult(boolean success, T body, String errMessage) {
        this.success = success;
        this.body = body;
        this.errMessage = errMessage;
    }

    public RequestResult(boolean success, T body, String errMessage, String errCode) {
        this.success = success;
        this.body = body;
        this.errMessage = errMessage;
        this.errCode = errCode;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T pickBody() {
        if (!this.success) {
            if (this.errCode != null) {
                throw new RequestResultException(this.errMessage, this.errCode);
            } else {
                throw new RequestResultException(this.errMessage);
            }
        } else {
            return this.body;
        }
    }

    public T getBody() {
        return this.body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getErrMessage() {
        return this.errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}

