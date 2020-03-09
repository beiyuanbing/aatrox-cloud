package com.aatrox.quartzserver.entity.enums;

/**
 * 任务状态
 * 暂停还是开启
 */
public enum StatusEnum {
    NORMAL("正常"),
    PAUSED("停止");
    private String desc;

    StatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public StatusEnum setDesc(String desc) {
        this.desc = desc;
        return this;
    }
}
