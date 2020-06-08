package com.aatrox.web.solr;

import com.aatrox.component.solr.core.factory.SolrServerFactory;
import com.aatrox.component.solr.core.searcher.AbstractSearcher;
import com.aatrox.web.solr.model.OrderSolrModel;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class OrderSolrSerach extends AbstractSearcher<OrderSolrModel> {
    public OrderSolrSerach(SolrServerFactory solrServerFactory) {
        super(solrServerFactory);
    }
}
