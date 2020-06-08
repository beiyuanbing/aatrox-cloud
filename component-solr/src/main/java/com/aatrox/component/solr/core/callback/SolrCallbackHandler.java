package com.aatrox.component.solr.core.callback;

import com.aatrox.common.bean.Pagination;
import com.aatrox.component.solr.core.criteria.SearchCriteria;
import com.aatrox.component.solr.core.template.SolrTemplate;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class SolrCallbackHandler<T> implements SolrCallback {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected SolrTemplate<T> solrTemplate;
    protected SolrQuery solrQuery;
    protected SearchCriteria criteria;
    private Pagination<T> pagination;

    public SolrCallbackHandler(SolrTemplate<T> solrTemplate, SearchCriteria criteria, Pagination<T> pagination) {
        this.solrTemplate = solrTemplate;
        this.solrQuery = criteria.makeSolrQuery();
        this.criteria = criteria;
        this.pagination = pagination;
    }

    @Override
    public Object doInSolr(SolrClient solrServer) throws Exception {
        this.solrQuery.setStart(this.pagination.getCurrentPageStartIndex());
        this.solrQuery.setRows(this.pagination.getPageSize());
        if (this.logger.isDebugEnabled()) {
            this.logger.info("[solr]:" + this.solrQuery.toString());
        }

        QueryResponse response = solrServer.query(this.solrQuery, SolrRequest.METHOD.POST);
        List<T> items = this.parseQueryResponse(response);
        this.pagination.setItems(items);
        return this.pagination;
    }

    public List<T> parseQueryResponse(QueryResponse response) {
        SolrDocumentList solrDocumentList = response.getResults();
        this.setHighlighting(solrDocumentList, response.getHighlighting());
        List items = null;

        try {
            items = this.solrTemplate.solrDocumentsToEntities(solrDocumentList);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        this.pagination.setRecordCount(Long.valueOf(response.getResults().getNumFound()).intValue());
        return items;
    }

    private void setHighlighting(SolrDocumentList solrDocumentList, Map<String, Map<String, List<String>>> highlightMap) {
        if (highlightMap != null) {
            try {
                List<String> fieldList = this.criteria.getHighlightFields();
                if (fieldList == null || fieldList.size() == 0) {
                    return;
                }

                solrDocumentList.stream().filter((doc) -> {
                    return doc.containsKey("id");
                }).forEach((doc) -> {
                    String id = doc.getFieldValue("id").toString();
                    Map<String, List<String>> resultMap = (Map) highlightMap.get(id);
                    if (resultMap != null && resultMap.size() > 0) {
                        fieldList.stream().filter((field) -> {
                            return doc.containsKey(field);
                        }).forEach((field) -> {
                            List<String> list = (List) resultMap.get(field);
                            if (list != null && list.size() > 0) {
                                doc.setField(field, list.get(0));
                            }

                        });
                    }

                });
            } catch (Exception var4) {
                var4.printStackTrace();
            }

        }
    }
}

