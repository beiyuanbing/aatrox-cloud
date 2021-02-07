package com.aatrox.base.excel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * excel.xml的excel标签 导出excel使用
 * 此类是提供自己查询数据集合的调用的excel方法导出
 * @author aatrox
 */
@ApiModel(description = "excel关系dto")
public class ExcelDataDto {
    @ApiModelProperty(value = "excel标签的key id")
    private String tagKeyId;

    @ApiModelProperty(value = "数据集合")
    private List dataList;

    @ApiModelProperty(value = "excel文件名是否需要以拼接时间字串")
    private boolean fileNameDateEnd=false;

    public String getTagKeyId() {
        return tagKeyId;
    }

    public ExcelDataDto setTagKeyId(String tagKeyId) {
        this.tagKeyId = tagKeyId;
        return this;
    }

    public List getDataList() {
        return dataList;
    }

    public ExcelDataDto setDataList(List dataList) {
        this.dataList = dataList;
        return this;
    }

    public boolean isFileNameDateEnd() {
        return fileNameDateEnd;
    }

    public ExcelDataDto setFileNameDateEnd(boolean fileNameDateEnd) {
        this.fileNameDateEnd = fileNameDateEnd;
        return this;
    }
}
