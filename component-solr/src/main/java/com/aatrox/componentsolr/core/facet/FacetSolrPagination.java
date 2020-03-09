package com.aatrox.componentsolr.core.facet;

import com.aatrox.common.bean.Pagination;

import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class FacetSolrPagination<T> extends Pagination<T> {
    private List<SolrFacetBean> facetFields;

    public FacetSolrPagination() {
    }

    public FacetSolrPagination(int pageSize, int page) {
        super(pageSize, page);
    }

    public List<SolrFacetBean> getFacetFields() {
        return this.facetFields;
    }

    public void setFacetFields(List<SolrFacetBean> facetFields) {
        this.facetFields = facetFields;
    }
}
