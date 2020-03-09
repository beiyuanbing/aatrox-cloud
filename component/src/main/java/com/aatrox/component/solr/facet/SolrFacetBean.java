package com.aatrox.component.solr.facet;

import org.apache.solr.client.solrj.response.FacetField;

import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class SolrFacetBean {
    private int queryCount;
    List<FacetField.Count> fieldCount;
    private String facetField;
    private String facetFieldValue;

    public String getFacetField() {
        return this.facetField;
    }

    public String getFacetFieldValue() {
        return this.facetFieldValue;
    }

    public SolrFacetBean(String facetField, String facetFieldValue) {
        this.facetField = facetField;
        this.facetFieldValue = facetFieldValue;
    }

    public String createFacetQuery() {
        if (this.facetFieldValue == null) {
            return null;
        } else {
            StringBuilder facetQuery = new StringBuilder();
            facetQuery.append(this.facetField);
            facetQuery.append(":");
            facetQuery.append(this.facetFieldValue);
            return facetQuery.toString();
        }
    }

    public List<FacetField.Count> getFieldCount() {
        return this.fieldCount;
    }

    protected void setFieldCount(List<FacetField.Count> fieldCount) {
        this.fieldCount = fieldCount;
    }

    public int getQueryCount() {
        return this.queryCount;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }
}

