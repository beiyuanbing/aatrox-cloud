package com.aatrox.generator.bean;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

/**
 * @author aatrox
 * @desc
 * @date 2019/8/27
 */
public class DefineBean {

    //文件名
    private String fileName;

    //包名
    private String beanPackage;

    //文件输出路径
    private String outPath;

    //模板路径
    private String templatePath;

    //变量名
    private String beanType;

    //文件类型
    private String fileType = StringPool.DOT_JAVA;

    public String getFileName() {
        return fileName;
    }

    public DefineBean setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getBeanPackage() {
        return beanPackage;
    }

    public DefineBean setBeanPackage(String beanPackage) {
        this.beanPackage = beanPackage;
        return this;
    }

    public String getOutPath() {
        return outPath;
    }

    public DefineBean setOutPath(String outPath) {
        this.outPath = outPath;
        return this;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public DefineBean setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public DefineBean setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getBeanType() {
        return beanType;
    }

    public DefineBean setBeanType(String beanType) {
        this.beanType = beanType;
        return this;
    }
}
