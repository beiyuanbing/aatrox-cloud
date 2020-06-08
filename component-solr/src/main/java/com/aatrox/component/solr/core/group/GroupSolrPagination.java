package com.aatrox.component.solr.core.group;

import com.aatrox.common.bean.Pagination;

import java.util.List;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class GroupSolrPagination<T> extends Pagination<T> {
    private static final long serialVersionUID = 3159747442189949328L;
    private List<SolrGroupBean> groupFields;
    private Map<String, List<T>> itemMap;

    public GroupSolrPagination() {
    }

    public GroupSolrPagination(int pageSize, int page) {
        super(pageSize, page);
    }

    public List<SolrGroupBean> getGroupFields() {
        return this.groupFields;
    }

    public void setGroupFields(List<SolrGroupBean> groupFields) {
        this.groupFields = groupFields;
    }

    public Map<String, List<T>> getItemMap() {
        return this.itemMap;
    }

    public void setItemMap(Map<String, List<T>> itemMap) {
        this.itemMap = itemMap;
    }

    @Override
    public List<T> getItems() {
        throw new RuntimeException("wrong method called!!please use GroupSolrPagination.getGroupFields()");
    }

    @Override
    public int getRecordCount() {
        throw new RuntimeException("wrong method called!!please use GroupSolrPagination.getGroupFields()");
    }
}

