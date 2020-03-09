package com.aatrox.common.utils.objectdiff;

/**
 * 结果集
 * Created by yoara on 2018/1/4.
 */
public class DiffResult {
    /**
     * 处理结果，true表示正确返回，false表示异常
     */
    private boolean result;
    /**
     * 异常信息
     */
    private String errMessage;
    /**
     * 返回结果
     */
    private String data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
