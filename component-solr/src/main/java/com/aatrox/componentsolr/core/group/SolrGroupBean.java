package com.aatrox.componentsolr.core.group;

import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class SolrGroupBean<T> {
    private int docsCount;
    private int ngCount;
    private String groupField;
    private Map<String, SolrGroupResultBean<T>> groupResult;

    public SolrGroupBean(String groupField) {
        this.groupField = groupField;
    }

    public String getGroupField() {
        return this.groupField;
    }

    public int getNgCount() {
        return this.ngCount;
    }

    public void setNgCount(int ngCount) {
        this.ngCount = ngCount;
    }

    public Map<String, SolrGroupResultBean<T>> getGroupResult() {
        return this.groupResult;
    }

    public void setGroupResult(Map<String, SolrGroupResultBean<T>> groupResult) {
        this.groupResult = groupResult;
    }

    public int getDocsCount() {
        return this.docsCount;
    }

    public void setDocsCount(int docsCount) {
        this.docsCount = docsCount;
    }
}

