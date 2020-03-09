package com.aatrox.apilist.form;

public enum JsonCommonCodeEnum implements JsonReturnCodeEnum {
    C0000("处理成功"),
    E0000("未知服务器错误"),
    E0001("处理失败"),
    E0002("参数不全或无效"),
    E0004("服务器正忙，请稍后"),
    E0005("服务器异常!"),
    E0006("参数格式不符合要求"),
    E0007("用户权限未通过校验"),
    E0008("用户未登录"),
    E0009("CSRF校验不通过"),
    E0010("RSA加密校验不通过"),
    E0011("上传失败!"),
    E0013("上传图片大小超过最大限制!"),
    E0014("excel上传异常!"),
    E0016("附件为空!"),
    E0017("内部访问限制"),
    E0018("服务请求超时，请稍后再试"),
    E0019("用户状态已更新，请重新登录"),
    E0020("数据重复");

    private String msg;

    private JsonCommonCodeEnum(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

    @Override
    public String getStatus() {
        return name();
    }
}

