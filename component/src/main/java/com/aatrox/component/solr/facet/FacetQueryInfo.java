package com.aatrox.component.solr.facet;

import com.aatrox.common.utils.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.Iterator;
import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class FacetQueryInfo {
    private List<SolrFacetBean> facetFields;
    private FacetQueryParam param;

    public FacetQueryInfo(List<SolrFacetBean> facetFields, FacetQueryParam param) {
        this.facetFields = facetFields;
        this.param = param == null ? new FacetQueryParam() : param;
    }

    public void makeFacetIndex(SolrQuery solrQuery) {
        solrQuery.setFacet(true);
        Iterator var2 = this.facetFields.iterator();

        while (var2.hasNext()) {
            SolrFacetBean facetField = (SolrFacetBean) var2.next();
            solrQuery.addFacetField(new String[]{facetField.getFacetField()});
            String fq = facetField.createFacetQuery();
            if (StringUtils.isNotEmpty(fq)) {
                solrQuery.addFacetQuery(facetField.createFacetQuery());
            }
        }

        solrQuery.setFacetMinCount(this.param.getMinCount());
    }

    public FacetQueryParam getParam() {
        return this.param;
    }

    public void setParam(FacetQueryParam param) {
        this.param = param;
    }

    public List<SolrFacetBean> getFacetFields() {
        return this.facetFields;
    }
}