package com.aatrox.common.bean;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class Pagination<T> implements Serializable {
    private static final long serialVersionUID = -2526095345442029659L;
    protected static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGE_SIZE = 20;
    protected List<T> items;
    protected int recordCount;
    protected int pageSize;
    protected int currentPage;
    @ApiModelProperty(
            hidden = true
    )
    private boolean queryRecordCount;

    public Pagination() {
        this(20, 1);
    }

    public Pagination(int pageSize, int page) {
        this.pageSize = 20;
        this.currentPage = 1;
        this.queryRecordCount = true;
        this.initPage(pageSize, page);
    }

    public Pagination(int pageSize, int page, boolean queryRecordCount) {
        this.pageSize = 20;
        this.currentPage = 1;
        this.queryRecordCount = true;
        this.initPage(pageSize < 1 ? 20 : pageSize, page < 1 ? 1 : page);
        this.queryRecordCount = queryRecordCount;
    }

    private void initPage(int pageSize, int page) {
        this.pageSize = pageSize < 1 ? 20 : pageSize;
        this.currentPage = page < 1 ? 1 : page;
    }

    public static boolean hasResults(Pagination<?> page) {
        return null != page && null != page.getItems() && !page.getItems().isEmpty();
    }

    public List<T> getItems() {
        return this.items;
    }

    public int getRecordCount() {
        return this.recordCount;
    }

    public int getCurrentPageStartIndex() {
        return (this.currentPage - 1) * this.pageSize;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setRecordCount(int totalCount) {
        this.recordCount = totalCount;
    }

    public int getPageCount() {
        return this.recordCount % this.pageSize == 0 ? this.recordCount / this.pageSize : this.recordCount / this.pageSize + 1;
    }

    public int getPreviousPage() {
        return this.currentPage > 1 ? this.currentPage - 1 : 1;
    }

    public int getNextPage() {
        return this.currentPage < this.getPageCount() ? this.currentPage + 1 : this.getPageCount();
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

