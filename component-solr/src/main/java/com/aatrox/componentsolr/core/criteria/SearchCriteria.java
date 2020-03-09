package com.aatrox.componentsolr.core.criteria;


import com.aatrox.common.utils.ListUtil;
import com.aatrox.componentsolr.core.facet.FacetQueryInfo;
import com.aatrox.componentsolr.core.facet.FacetQueryParam;
import com.aatrox.componentsolr.core.facet.SolrFacetBean;
import com.aatrox.componentsolr.core.group.GroupQueryInfo;
import com.aatrox.componentsolr.core.group.SolrGroupBean;
import com.aatrox.componentsolr.core.stats.StatsQueryInfo;
import org.apache.solr.client.solrj.SolrQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class SearchCriteria implements Serializable {
    private String queryType;
    private String defType;
    private String fl;
    private String pf;
    private String qf;
    private String bf;
    private SearchCriteria.GeoDistParam geodistWith;
    private GroupQueryInfo groupQueryInfo;
    private FacetQueryInfo facetQueryInfo;
    private StatsQueryInfo statsQueryInfo;
    private List<String> highlightFields;
    private StringBuffer query = new StringBuffer();
    private List<String> filterQuery = new ArrayList();
    private List<SolrQuery.SortClause> sortList = new ArrayList();

    public SearchCriteria() {
    }

    public void addQuery(SearchCriteria.FieldValuePair pair, SearchCriteria.OpeType opeType) {
        this.addQuery(pair.field + ":" + pair.value, opeType);
    }

    public void addQuerys(List<FieldValuePair> pairs, SearchCriteria.OpeType innerOpeType, SearchCriteria.OpeType opeType) {
        StringBuffer sb = new StringBuffer("(");

        SearchCriteria.FieldValuePair pair;
        for (Iterator var5 = pairs.iterator(); var5.hasNext(); sb.append(pair.field).append(":").append(pair.value)) {
            pair = (SearchCriteria.FieldValuePair) var5.next();
            if (sb.length() > 1) {
                sb.append(" ").append(innerOpeType).append(" ");
            }
        }

        sb.append(")");
        this.addQuery(sb.toString(), opeType);
    }

    public void addRangeQuery(String field, String start, String end, SearchCriteria.OpeType opeType) {
        StringBuffer range = new StringBuffer();
        range.append("[").append(start).append(" TO ").append(end).append("]");
        this.addQuery(field + ":" + range.toString(), opeType);
    }

    public void addLocationQuery(String field, String value, String distance, SearchCriteria.OpeType opeType) {
        StringBuffer sb = new StringBuffer();
        sb.append("{!geofilt pt=").append(value);
        sb.append(" sfield=").append(field);
        sb.append(" d=").append(distance);
        sb.append("}");
        this.addQuery(sb.toString(), opeType);
    }

    public void addQuery(String qStr, SearchCriteria.OpeType opeType) {
        if (this.query.length() != 0) {
            this.query.append(" ").append(opeType).append(" ");
        }

        this.query.append(qStr);
    }

    public void addFilterQuery(String field, String value) {
        this.addFilterQuery(field + ":" + value);
    }

    public void addFilterQuery(String fqStr) {
        this.filterQuery.add(fqStr);
    }

    public void addSort(String field, SolrQuery.ORDER order) {
        this.sortList.add(SolrQuery.SortClause.create(field, order));
    }

    public SolrQuery makeSolrQuery() {
        SolrQuery solrQuery = new SolrQuery();
        if (this.customQuery(solrQuery)) {
            solrQuery.setQuery(this.query.length() > 0 ? this.query.toString() : "*:*");
            List<String> var10000 = this.filterQuery;
            Objects.requireNonNull(solrQuery);
            var10000.forEach((xva$0) -> {
                solrQuery.addFilterQuery(new String[]{xva$0});
            });
            List<SolrQuery.SortClause> var10001 = this.sortList;
            Objects.requireNonNull(solrQuery);
            var10001.forEach(solrQuery::addSort);
            if (null != this.defType) {
                solrQuery.setParam("defType", new String[]{this.defType});
            }

            if (null != this.queryType) {
                solrQuery.setRequestHandler(this.queryType);
            }

            if (null != this.pf) {
                solrQuery.setParam("pf", new String[]{this.pf});
            }

            if (null != this.qf) {
                solrQuery.setParam("qf", new String[]{this.qf});
            }

            if (null != this.bf) {
                solrQuery.setParam("bf", new String[]{this.bf});
            }

            if (null != this.fl) {
                solrQuery.setParam("fl", new String[]{this.fl});
            }

            if (this.geodistWith != null) {
                solrQuery.set("pt", new String[]{this.geodistWith.getPt()});
                solrQuery.set("sfield", new String[]{this.geodistWith.getField()});
            }

            if (this.groupQueryInfo != null) {
                this.groupQueryInfo.makeGroupIndex(solrQuery);
            }

            if (this.facetQueryInfo != null) {
                this.facetQueryInfo.makeFacetIndex(solrQuery);
            }

            if (this.statsQueryInfo != null) {
                this.statsQueryInfo.makeStatsIndex(solrQuery);
            }
        }

        return solrQuery;
    }

    public GroupQueryInfo getGroupQueryInfo() {
        return this.groupQueryInfo;
    }

    public FacetQueryInfo getFacetQueryInfo() {
        return this.facetQueryInfo;
    }

    public StatsQueryInfo getStatsQueryInfo() {
        return this.statsQueryInfo;
    }

    public void setGroupQueryInfo(List<String> groupFields, Integer groupLimit) {
        if (groupFields != null) {
            List<SolrGroupBean> groupFieldBeanList = new ArrayList();
            Iterator var4 = groupFields.iterator();

            while (var4.hasNext()) {
                String field = (String) var4.next();
                groupFieldBeanList.add(new SolrGroupBean(field));
            }

            this.groupQueryInfo = new GroupQueryInfo(groupFieldBeanList, groupLimit);
        }
    }

    public void setFacetQueryInfo(List<FieldValuePair> pairs, FacetQueryParam param) {
        if (pairs != null && pairs.size() != 0) {
            List<SolrFacetBean> facetFields = new ArrayList();
            Iterator var4 = pairs.iterator();

            while (var4.hasNext()) {
                SearchCriteria.FieldValuePair pair = (SearchCriteria.FieldValuePair) var4.next();
                facetFields.add(new SolrFacetBean(pair.getField(), pair.getValue()));
            }

            this.facetQueryInfo = new FacetQueryInfo(facetFields, param);
        }
    }

    public void setStatsQueryInfo(List<String> fields) {
        if (fields != null && fields.size() != 0) {
            this.statsQueryInfo = new StatsQueryInfo((String[]) ListUtil.toArray(fields, new String[0]));
        }
    }

    protected boolean customQuery(SolrQuery solrQuery) {
        return true;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getDefType() {
        return this.defType;
    }

    public void setDefType(String defType) {
        this.defType = defType;
    }

    public String getFl() {
        return this.fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getQf() {
        return this.qf;
    }

    public void setQf(String qf) {
        this.qf = qf;
    }

    public String getPf() {
        return this.pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getBf() {
        return this.bf;
    }

    public void setBf(String bf) {
        this.bf = bf;
    }

    public void setGeodistWith(SearchCriteria.GeoDistParam geodistWith) {
        this.geodistWith = geodistWith;
    }

    public List<String> getHighlightFields() {
        return this.highlightFields;
    }

    public void setHighlightFields(List<String> highlightFields) {
        this.highlightFields = highlightFields;
    }

    public static class FieldValuePair implements Serializable {
        private String field;
        private String value;

        public FieldValuePair(String field, String value) {
            this.field = field;
            this.value = value;
        }

        public String getField() {
            return this.field;
        }

        public String getValue() {
            return this.value;
        }
    }

    public static class GeoDistParam implements Serializable {
        private String field;
        private String pt;

        public String getField() {
            return this.field;
        }

        public String getPt() {
            return this.pt;
        }

        public GeoDistParam(String field, String pt) {
            this.field = field;
            this.pt = pt;
        }
    }

    public static enum OpeType {
        AND,
        OR,
        NOT;

        private OpeType() {
        }
    }
}
