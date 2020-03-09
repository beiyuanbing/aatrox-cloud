package com.aatrox.web.common;

import java.util.Arrays;

/**
 * 环境配置的枚举类
 */
public enum Env {

    DEV("开发环境"),
    PROD("生产环境");

    Env(String desc) {
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public Env setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public static Env getEnv(String profile) {
        return Arrays.stream(Env.values()).filter(e -> e.name().equalsIgnoreCase(profile)).findFirst().orElse(Env.DEV);
    }
}
