package com.aatrox.component.solr.core.callback;

import org.apache.solr.client.solrj.SolrClient;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public interface SolrCallback {
    Object doInSolr(SolrClient solrClient) throws Exception;
}
