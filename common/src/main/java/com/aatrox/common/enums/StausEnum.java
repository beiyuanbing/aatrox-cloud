package com.aatrox.common.enums;

public enum StausEnum {
    ENABLE("可用");

    private String desc;

    StausEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public StausEnum setDesc(String desc) {
        this.desc = desc;
        return this;
    }
}
