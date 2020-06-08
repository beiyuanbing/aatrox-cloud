package com.aatrox.component.solr.core.stats;

import com.aatrox.common.bean.Pagination;
import org.apache.solr.client.solrj.response.FieldStatsInfo;

import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class StatsSolrPagination<T> extends Pagination<T> {
    private List<FieldStatsInfo> statsFields;

    public StatsSolrPagination() {
    }

    public StatsSolrPagination(int pageSize, int page) {
        super(pageSize, page);
    }

    public List<FieldStatsInfo> getStatsFields() {
        return this.statsFields;
    }

    public void setStatsFields(List<FieldStatsInfo> statsFields) {
        this.statsFields = statsFields;
    }
}