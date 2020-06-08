package com.aatrox.component.solr.core.facet;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class FacetQueryParam {
    private int minCount;

    public FacetQueryParam() {
    }

    public int getMinCount() {
        return this.minCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }
}