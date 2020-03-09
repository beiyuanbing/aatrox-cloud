package com.aatrox.common.bean;

import io.swagger.annotations.ApiModelProperty;

public class PageForm {
    @ApiModelProperty(
            value = "页容量参数，默认20",
            example = "20"
    )

    private int pageSize = 20;
    @ApiModelProperty(
            value = "当前页，默认1",
            example = "1"
    )

    private int currentPage = 1;
    @ApiModelProperty(
            value = "是否查询总数",
            hidden = true
    )
    private boolean queryRecordCount = true;

    public PageForm() {
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isQueryRecordCount() {
        return this.queryRecordCount;
    }

    public void setQueryRecordCount(boolean queryRecordCount) {
        this.queryRecordCount = queryRecordCount;
    }
}
