package com.aatrox.quartzserver.entity;

/**
 * @author yasuo
 */
public class ReturnMsg {
    public String code = "200";

    public String msg;

    public Object data;


    public ReturnMsg(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ReturnMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ReturnMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public ReturnMsg setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ReturnMsg setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ReturnMsg setData(Object data) {
        this.data = data;
        return this;
    }

    public static ReturnMsg SUCCESS(String code, String msg) {
        return new ReturnMsg(code, msg);
    }

    public static ReturnMsg SUCCESS(String msg) {
        return new ReturnMsg("200", msg);
    }

    public static ReturnMsg FAIL(String msg, Object data) {
        return new ReturnMsg("200", msg, data);
    }

    public static ReturnMsg FAIL(String code, String msg) {
        return new ReturnMsg(code, msg);
    }

    public static ReturnMsg FAIL(String msg) {
        return new ReturnMsg("500", msg);
    }
}
