package com.aatrox.web.base.interceptor;

import com.aatrox.apilist.form.ValidationForm;

import java.util.UUID;

/**
 * @author aatrox
 * @desc
 * @date 2019/9/2
 */
public class RequestContext {
    private String sessionId;
    private String requestId = UUID.randomUUID().toString();
    private long timeKey = System.currentTimeMillis();
    private boolean debug = false;
    private String loginAccount;
    private String clientAppName;
    private String errMsg;
    private ValidationForm form;
    private String operatorDesc;

    public RequestContext() {
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String request) {
        this.requestId = request;
    }

    public long getTimeKey() {
        return this.timeKey;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getLoginAccount() {
        return this.loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getClientAppName() {
        return this.clientAppName;
    }

    public void setClientAppName(String clientAppName) {
        this.clientAppName = clientAppName;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public ValidationForm getForm() {
        return this.form;
    }

    public void setForm(ValidationForm form) {
        this.form = form;
    }

    public String getOperatorDesc() {
        return this.operatorDesc;
    }

    public void setOperatorDesc(String operatorDesc) {
        this.operatorDesc = operatorDesc;
    }
}

