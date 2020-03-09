package com.aatrox.common.utils.push;

/**
 * @author aatrox
 * @desc 推送实体的request
 * @date 2019-08-08
 */
public class PushModelRequest {
    /**
     * 鉴权的密钥
     **/
    private String masterSecet;

    /**
     * 应用key
     **/
    private String appKey;

    /***用户别名**/
    private String userAlias;

    /**
     * 是否别名前缀
     **/
    private boolean aliasPrefix = true;

    /****平台**/
    private String plateForm;

    /****应用名*/
    private String appName;

    /****通知内容**/
    private String content;

    /****当前环境**/
    private String env = PushConstant.ENVIRONMENT_TEST;

    public String getMasterSecet() {
        return masterSecet;
    }

    public PushModelRequest setMasterSecet(String masterSecet) {
        this.masterSecet = masterSecet;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public PushModelRequest setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public PushModelRequest setUserAlias(String userAlias) {
        this.userAlias = userAlias;
        return this;
    }

    public boolean isAliasPrefix() {
        return aliasPrefix;
    }

    public PushModelRequest setAliasPrefix(boolean aliasPrefix) {
        this.aliasPrefix = aliasPrefix;
        return this;
    }

    public String getPlateForm() {
        return plateForm;
    }

    public PushModelRequest setPlateForm(String plateForm) {
        this.plateForm = plateForm;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public PushModelRequest setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PushModelRequest setContent(String content) {
        this.content = content;
        return this;
    }

    public String getEnv() {
        return env;
    }

    public PushModelRequest setEnv(String env) {
        this.env = env;
        return this;
    }
}
