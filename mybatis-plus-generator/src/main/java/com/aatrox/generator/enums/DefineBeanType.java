package com.aatrox.generator.enums;

import com.aatrox.generator.constants.GeneratorConstants;
import com.baomidou.mybatisplus.core.toolkit.StringPool;

/**
 * @author aatrox
 * @desc 自定义类型type
 * @date 2019/8/28
 */
public enum DefineBeanType {
    MODEL("com.aatrox.model", GeneratorConstants.MODEL_TEMPLATE, StringPool.DOT_JAVA),
    INSERTFORM("com.aatrox.form", GeneratorConstants.INSERTFORM_TEMPLATE, StringPool.DOT_JAVA),
    EDITFORM("com.aatrox.form", GeneratorConstants.EDITFORM_TEMPLATE, StringPool.DOT_JAVA),
    DAO("com.aatrox.dao",GeneratorConstants.DAO_TEMPLATE, StringPool.DOT_JAVA),
    XML("com.aatrox.xml",GeneratorConstants.XML_TEMPLATE, StringPool.DOT_XML),
    SERVICE("com.aatrox.service",GeneratorConstants.SERVER_TEMPLATE, StringPool.DOT_JAVA),
    SERVICEIMPL("com.aatrox.service.impl",GeneratorConstants.SERVERIMPL_TEMPLATE, StringPool.DOT_JAVA),
    FEGIN("com.aatrox.fegin",GeneratorConstants.FEGIN_TEMPLATE, StringPool.DOT_JAVA),
    API("com.aatrox.api",GeneratorConstants.API_TEMPLATE, StringPool.DOT_JAVA),
    REMOTE("com.aatrox.remote",GeneratorConstants.REMOTE_TEMPLATE, StringPool.DOT_JAVA),
    CONTROLLER("com.aatrox.controller",GeneratorConstants.CONTROLLER_TEMPLATE, StringPool.DOT_JAVA);


    DefineBeanType(String beanPackage, String templatePath,String fileType) {
        this.beanPackage = beanPackage;
        this.templatePath = templatePath;
        this.fileType = fileType;
    }

    private String beanPackage;

    private String templatePath;

    private String fileType;

    public String getBeanPackage() {
        return beanPackage;
    }

    public DefineBeanType setBeanPackage(String beanPackage) {
        this.beanPackage = beanPackage;
        return this;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public DefineBeanType setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public DefineBeanType setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }
}
