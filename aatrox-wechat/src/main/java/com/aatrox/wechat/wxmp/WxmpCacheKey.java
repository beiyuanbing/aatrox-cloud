package com.aatrox.wechat.wxmp;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
public enum WxmpCacheKey {
    WX_MINI_PROGRAM_ACCESS_TOKEN("SESSION_BASE"),
    WX_MINI_PROGRAM_ACCESS_TOKEN_ENDTIME("SESSION_BASE");

    private String baseType;

    private WxmpCacheKey(String baseType) {
        this.baseType = baseType;
    }

    public String getCacheKey() {
        return this.name();
    }

    public String getBaseType() {
        return this.baseType;
    }
}
