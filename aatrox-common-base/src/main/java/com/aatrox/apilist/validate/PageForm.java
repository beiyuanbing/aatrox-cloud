package com.aatrox.apilist.validate;

import com.aatrox.model.Pagination;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

public class PageForm extends DataAuthForm {
    @ApiModelProperty(
            value = "页容量参数，默认20",
            example = "20"
    )
    @Min(
            message = "页容量参数不得为空",
            value = 1L
    )
    private int pageSize = 20;
    @ApiModelProperty(
            value = "当前页，默认1",
            example = "1"
    )
    @Min(
            message = "当前页参数不得为空",
            value = 1L
    )
    private int currentPage = 1;
    @ApiModelProperty(
            value = "是否查询总数",
            hidden = true
    )
    private boolean queryRecordCount = true;

    public PageForm() {
    }

    public Pagination makePagination() {
        return new Pagination(this.pageSize, this.currentPage, this.queryRecordCount);
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

