package com.aatrox.component.solr.stats;

import org.apache.solr.client.solrj.SolrQuery;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class StatsQueryInfo {
    private String[] statsFields;

    public StatsQueryInfo(String[] statsFields) {
        this.statsFields = statsFields;
    }

    public void makeStatsIndex(SolrQuery solrQuery) {
        if (this.statsFields != null && this.statsFields.length > 0) {
            solrQuery.addGetFieldStatistics(this.statsFields);
        }

    }
}
