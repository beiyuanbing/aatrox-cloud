package com.aatrox.component.solr.facet;

import com.aatrox.component.solr.callback.SolrCallbackHandler;
import com.aatrox.component.solr.criteria.SearchCriteria;
import com.aatrox.component.solr.template.SolrTemplate;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class FacetCallbackHandler<T> extends SolrCallbackHandler<T> {
    private FacetSolrPagination pagination;

    public FacetCallbackHandler(SolrTemplate<T> solrTemplate, SearchCriteria criteria, FacetSolrPagination<T> pagination) {
        super(solrTemplate, criteria, pagination);
        this.pagination = pagination;
    }

    @Override
    public List<T> parseQueryResponse(QueryResponse response) {
        FacetSolrPagination<T> page = this.pagination;
        List<SolrFacetBean> facetFields = this.criteria.getFacetQueryInfo().getFacetFields();
        if (null != facetFields) {
            Map<String, Integer> facetQuery = response.getFacetQuery();
            Iterator var5 = facetFields.iterator();

            while (var5.hasNext()) {
                SolrFacetBean solrEnum = (SolrFacetBean) var5.next();
                Integer count = (Integer) facetQuery.get(solrEnum.createFacetQuery());
                solrEnum.setQueryCount(count == null ? 0 : count);
                FacetField ff = response.getFacetField(solrEnum.getFacetField());
                solrEnum.setFieldCount(ff.getValues());
            }
        }

        page.setFacetFields(facetFields);
        return super.parseQueryResponse(response);
    }
}
