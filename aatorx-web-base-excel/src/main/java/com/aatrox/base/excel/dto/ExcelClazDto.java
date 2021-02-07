package com.aatrox.base.excel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 实体对象 转excel 的时候使用
 * @author aatrox
 */
@ApiModel(description = "excel 关系dto")
public class ExcelClazDto<T> {
    @ApiModelProperty(value = "excel标签的key id")
    private Class<T> claz;

    @ApiModelProperty(value = "数据集合")
    private List<T> dataList;

    @ApiModelProperty(value = "忽略的字段名")
    List<String> ignoreFieldList;


    @ApiModelProperty(value = "excel文件名是否需要以拼接时间字串")
    private boolean fileNameDateEnd=false;

    public Class<T> getClaz() {
        return claz;
    }

    public ExcelClazDto<T> setClaz(Class<T> claz) {
        this.claz = claz;
        return this;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public ExcelClazDto<T> setDataList(List<T> dataList) {
        this.dataList = dataList;
        return this;
    }

    public List<String> getIgnoreFieldList() {
        return ignoreFieldList;
    }

    public ExcelClazDto<T> setIgnoreFieldList(List<String> ignoreFieldList) {
        this.ignoreFieldList = ignoreFieldList;
        return this;
    }

    public boolean isFileNameDateEnd() {
        return fileNameDateEnd;
    }

    public ExcelClazDto<T> setFileNameDateEnd(boolean fileNameDateEnd) {
        this.fileNameDateEnd = fileNameDateEnd;
        return this;
    }
}
