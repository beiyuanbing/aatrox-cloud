package com.aatrox.component.solr.core.group;

import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class SolrGroupResultBean<T> {
    private long numFound;
    private long start;
    private String groupValue;
    private List<T> groupResult;

    public SolrGroupResultBean() {
    }

    public long getNumFound() {
        return this.numFound;
    }

    public void setNumFound(long numFound) {
        this.numFound = numFound;
    }

    public long getStart() {
        return this.start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public String getGroupValue() {
        return this.groupValue;
    }

    public void setGroupValue(String groupValue) {
        this.groupValue = groupValue;
    }

    public List<T> getGroupResult() {
        return this.groupResult;
    }

    public void setGroupResult(List<T> groupResult) {
        this.groupResult = groupResult;
    }
}
