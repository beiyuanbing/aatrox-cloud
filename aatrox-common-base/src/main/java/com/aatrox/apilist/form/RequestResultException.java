package com.aatrox.apilist.form;

public class RequestResultException extends RuntimeException {
    private String code;
    private JsonReturnCodeEnum codeEnum;

    public RequestResultException(String message) {
        this(message, (String) null, (JsonReturnCodeEnum) null);
    }

    protected RequestResultException(String message, String code) {
        this(message, code, (JsonReturnCodeEnum) null);
    }

    public RequestResultException(JsonReturnCodeEnum codeEnum) {
        this(codeEnum.getMessage(), codeEnum.getStatus(), codeEnum);
    }

    public RequestResultException(String message, JsonReturnCodeEnum codeEnum) {
        this(message, codeEnum.getStatus(), codeEnum);
    }

    private RequestResultException(String message, String code, JsonReturnCodeEnum codeEnum) {
        super(message);
        this.code = code;
        this.codeEnum = codeEnum;
    }

    public String getCode() {
        return this.code;
    }

    public JsonReturnCodeEnum getCodeEnum() {
        return this.codeEnum;
    }
}

