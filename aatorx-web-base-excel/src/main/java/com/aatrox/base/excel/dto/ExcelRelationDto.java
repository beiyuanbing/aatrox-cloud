package com.aatrox.base.excel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * excel.xml的excel标签 导出excel使用
 * @author aatrox
 */
@ApiModel(description = "excel关系dto")
public class ExcelRelationDto {
    @ApiModelProperty(value = "excel标签的key id")
    private String tagKeyId;

    @ApiModelProperty(value = "请求参数,可以为空")
    private Object queryObject;

    @ApiModelProperty(value = "excel文件名是否需要以拼接时间字串")
    private boolean fileNameDateEnd=false;

    public String getTagKeyId() {
        return tagKeyId;
    }

    public ExcelRelationDto setTagKeyId(String tagKeyId) {
        this.tagKeyId = tagKeyId;
        return this;
    }

    public Object getQueryObject() {
        return queryObject;
    }

    public ExcelRelationDto setQueryObject(Object queryObject) {
        this.queryObject = queryObject;
        return this;
    }

    public boolean isFileNameDateEnd() {
        return fileNameDateEnd;
    }

    public ExcelRelationDto setFileNameDateEnd(boolean fileNameDateEnd) {
        this.fileNameDateEnd = fileNameDateEnd;
        return this;
    }
}
