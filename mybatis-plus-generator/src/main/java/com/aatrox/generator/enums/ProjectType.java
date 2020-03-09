package com.aatrox.generator.enums;

/**
 * @author aatrox
 * @desc 自动生成的项目类型
 * @date 2019/8/28
 */
public enum ProjectType {
    DEAULUT("默认的MybatisPlus项目"),
    DIY("简单项目"),
    CLOUND("SpringClound的项目");

    private String desc;

    ProjectType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public ProjectType setDesc(String desc) {
        this.desc = desc;
        return this;
    }
}
