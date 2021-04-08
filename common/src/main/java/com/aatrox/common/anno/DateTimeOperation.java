package com.aatrox.common.anno;

/**
 * @program: aatrox-cloud
 * @description:
 * @author: aatrox
 * @create: 2021-04-08 15:01
 **/
public enum DateTimeOperation {
    DEFAULT("默认Pattern"), FIRST_DATE("00:00:00"), LAST_DATE("23:59:59");;

    private String desc;

    DateTimeOperation(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
