package com.aatrox.component.solr.stats;

import com.aatrox.component.solr.callback.SolrCallbackHandler;
import com.aatrox.component.solr.criteria.SearchCriteria;
import com.aatrox.component.solr.template.SolrTemplate;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class StatsCallbackHandler<T> extends SolrCallbackHandler<T> {
    private StatsSolrPagination pagination;

    public StatsCallbackHandler(SolrTemplate<T> solrTemplate, SearchCriteria criteria, StatsSolrPagination<T> pagination) {
        super(solrTemplate, criteria, pagination);
        this.pagination = pagination;
    }

    @Override
    public List<T> parseQueryResponse(QueryResponse response) {
        StatsSolrPagination<T> page = this.pagination;
        if (response.getFieldStatsInfo() != null) {
            page.setStatsFields(new ArrayList(response.getFieldStatsInfo().values()));
        }

        return super.parseQueryResponse(response);
    }
}
