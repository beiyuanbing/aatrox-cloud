package com.aatrox.componentsolr.core.searcher;

import com.aatrox.ContextHolder;
import com.aatrox.common.bean.Pagination;
import com.aatrox.componentsolr.core.Searcher;
import com.aatrox.componentsolr.core.SolrUpdateParam;
import com.aatrox.componentsolr.core.criteria.SearchCriteria;
import com.aatrox.componentsolr.core.facet.FacetSolrPagination;
import com.aatrox.componentsolr.core.factory.SolrServerFactory;
import com.aatrox.componentsolr.core.group.GroupSolrPagination;
import com.aatrox.componentsolr.core.stats.StatsSolrPagination;
import com.aatrox.componentsolr.core.template.SolrTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrDocument;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public abstract class AbstractSearcher<T> implements Searcher<T> {
    protected SolrServerFactory solrServerFactory;
    protected String solrServerUrl;
    protected String zkHost;
    protected String zkRoot;
    protected String solrName;
    protected Integer zkClientTimeout = 10000;
    protected Integer zkConnectTimeout = 10000;
    protected SolrTemplate solrTemplate;

    public AbstractSearcher(SolrServerFactory solrServerFactory) {
        this.solrServerFactory = solrServerFactory;
    }

    public void initSolrCloud(String solrName, String zkHost) {
        this.initSolrCloud(solrName, zkHost, this.zkRoot, this.zkClientTimeout, this.zkConnectTimeout);
    }

    public void initSolrCloud(String solrName, String zkHost, String zkRoot) {
        this.initSolrCloud(solrName, zkHost, zkRoot, this.zkClientTimeout, this.zkConnectTimeout);
    }

    public void initSolrCloud(String solrName, String zkHost, String zkRoot, Integer zkClientTimeout, Integer zkConnectTimeout) {
        if (StringUtils.isNotEmpty(this.solrServerUrl)) {
            throw new RuntimeException("已经设置solrServerUrl，请勿重复设置zkHost");
        } else {
            this.solrName = solrName;
            this.zkHost = zkHost;
            this.zkRoot = zkRoot;
            this.zkClientTimeout = zkClientTimeout;
            this.zkConnectTimeout = zkConnectTimeout;
            this.initSolrTemplate();
        }
    }

    public void initSolrServer(String solrName, String solrServerUrl) {
        if (StringUtils.isNotEmpty(this.zkHost)) {
            throw new RuntimeException("已经设置zkHost，请勿重复设置solrServerUrl");
        } else {
            this.solrName = solrName;
            this.solrServerUrl = solrServerUrl;
            this.initSolrTemplate();
        }
    }

    private void initSolrTemplate() {
        if (this.solrTemplate == null) {
            this.solrTemplate = new SolrTemplate(this);
        }

    }

    public SolrClient getSolrServer() {
        SolrClient solrServer = null;
        if (this.zkHost != null) {
            solrServer = this.solrServerFactory.getCloudSolrServer(this.zkHost, this.zkRoot, this.solrName, this.zkClientTimeout, this.zkConnectTimeout);
        } else {
            if (this.solrServerUrl == null) {
                throw new RuntimeException("solrServerUrl must be specified");
            }

            solrServer = this.solrServerFactory.getCommonsHttpSolrServer(this.solrServerUrl);
        }

        return solrServer;
    }

    protected String getDataSource() {
        return ContextHolder.getDataSource().toLowerCase();
    }

    public Pagination<T> query(SearchCriteria criteria, Pagination<T> pagination) {
        if (criteria.getGroupQueryInfo() != null) {
            if (!(pagination instanceof GroupSolrPagination)) {
                throw new RuntimeException("wrong pagination ,group query need GroupSolrPagination");
            } else {
                return this.solrTemplate.queryGroup(criteria, (GroupSolrPagination) pagination);
            }
        } else if (criteria.getFacetQueryInfo() != null) {
            if (!(pagination instanceof FacetSolrPagination)) {
                throw new RuntimeException("wrong pagination ,facet query need FacetSolrPagination");
            } else {
                return this.solrTemplate.queryFacet(criteria, (FacetSolrPagination) pagination);
            }
        } else if (criteria.getStatsQueryInfo() != null) {
            if (!(pagination instanceof StatsSolrPagination)) {
                throw new RuntimeException("wrong pagination ,stats query need StatsSolrPagination");
            } else {
                return this.solrTemplate.queryStats(criteria, (StatsSolrPagination) pagination);
            }
        } else {
            return this.solrTemplate.query(criteria, pagination);
        }
    }

    public Pagination<T> queryGroup(SearchCriteria criteria, GroupSolrPagination<T> pagination) {
        if (criteria.getGroupQueryInfo() == null) {
            throw new RuntimeException("group query need GroupQueryInfo()");
        } else {
            return this.solrTemplate.queryGroup(criteria, pagination);
        }
    }

    public Pagination<T> queryFacet(SearchCriteria criteria, FacetSolrPagination<T> pagination) {
        if (criteria.getFacetQueryInfo() == null) {
            throw new RuntimeException("group query need FacetQueryInfo");
        } else {
            return this.solrTemplate.queryFacet(criteria, pagination);
        }
    }

    public Pagination<T> queryStats(SearchCriteria criteria, StatsSolrPagination<T> pagination) {
        if (criteria.getStatsQueryInfo() == null) {
            throw new RuntimeException("group query need StatsQueryInfo");
        } else {
            return this.solrTemplate.queryStats(criteria, pagination);
        }
    }

    @Override
    public void save(T entity) {
        this.solrTemplate.save(entity);
    }

    @Override
    public void saveList(List<T> entityList) {
        this.solrTemplate.saveList(entityList);
    }

    @Override
    public void update(SolrUpdateParam update) {
        this.solrTemplate.update(update);
    }

    @Override
    public void deleteById(String id) {
        this.solrTemplate.deleteById(id);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        this.solrTemplate.deleteByIds(ids);
    }

    @Override
    public void deleteByQuery(String query) {
        this.solrTemplate.deleteByQuery(query);
    }

    @Override
    public void optimize(int maxSegments) {
        this.solrTemplate.optimize(maxSegments);
    }

    @Override
    public void deleteAll() {
        this.solrTemplate.deleteAll();
    }

    @Override
    public T solrDocumentToEntity(SolrDocument solrDocument) throws Exception {
        if (solrDocument == null) {
            return null;
        } else {
            Type genType = this.getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            T entity = (T) ((Class) params[0]).getDeclaredConstructor().newInstance();
            Field[] fields = ((Class) params[0]).getDeclaredFields();
            if (fields != null) {
                Field[] var6 = fields;
                int var7 = fields.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    Field field = var6[var8];
                    Object value = solrDocument.getFieldValue(field.getName());
                    if (value != null) {
                        try {
                            field.setAccessible(true);
                            field.set(entity, solrDocument.getFieldValue(field.getName()));
                        } catch (IllegalAccessException var12) {
                        }
                    }
                }
            }

            return entity;
        }
    }
}
