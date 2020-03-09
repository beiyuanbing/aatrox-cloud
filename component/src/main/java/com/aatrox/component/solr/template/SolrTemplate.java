package com.aatrox.component.solr.template;

import com.aatrox.common.bean.Pagination;
import com.aatrox.component.solr.Searcher;
import com.aatrox.component.solr.SolrUpdateParam;
import com.aatrox.component.solr.callback.SolrCallback;
import com.aatrox.component.solr.callback.SolrCallbackHandler;
import com.aatrox.component.solr.criteria.SearchCriteria;
import com.aatrox.component.solr.facet.FacetCallbackHandler;
import com.aatrox.component.solr.facet.FacetSolrPagination;
import com.aatrox.component.solr.group.GroupCallbackHandler;
import com.aatrox.component.solr.group.GroupSolrPagination;
import com.aatrox.component.solr.searcher.AbstractSearcher;
import com.aatrox.component.solr.stats.StatsCallbackHandler;
import com.aatrox.component.solr.stats.StatsSolrPagination;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class SolrTemplate<T> {
    private static final Logger logger = Logger.getLogger(SolrTemplate.class);
    private SolrClient solrServer;
    private Searcher<T> searcher;

    public SolrTemplate(AbstractSearcher<T> searcher) {
        this.searcher = searcher;
        this.solrServer = searcher.getSolrServer();
    }

    public Object execute(SolrCallback solrCallback) {
        try {
            return solrCallback.doInSolr(this.solrServer);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public Pagination<T> query(SearchCriteria criteria, Pagination<T> pagination) {
        return (Pagination) this.execute(new SolrCallbackHandler(this, criteria, pagination));
    }

    public Pagination<T> queryGroup(SearchCriteria criteria, GroupSolrPagination<T> pagination) {
        return (Pagination) this.execute(new GroupCallbackHandler(this, criteria, pagination));
    }

    public Pagination<T> queryFacet(SearchCriteria criteria, FacetSolrPagination<T> pagination) {
        return (Pagination) this.execute(new FacetCallbackHandler(this, criteria, pagination));
    }

    public Pagination<T> queryStats(SearchCriteria criteria, StatsSolrPagination<T> pagination) {
        return (Pagination) this.execute(new StatsCallbackHandler(this, criteria, pagination));
    }

    public List<T> solrDocumentsToEntities(SolrDocumentList solrDocumentList) throws Exception {
        List<T> entities = new ArrayList();
        if (null != solrDocumentList) {
            Iterator var3 = solrDocumentList.iterator();

            while (var3.hasNext()) {
                SolrDocument solrDocument = (SolrDocument) var3.next();
                entities.add(this.searcher.solrDocumentToEntity(solrDocument));
            }
        }

        return entities;
    }

    public void save(final T entity) {
        this.saveList(new ArrayList<T>() {
            {
                this.add(entity);
            }
        });
    }

    public void save(final T entity, SolrTemplate.CommitParameter param) {
        this.saveList(new ArrayList<T>() {
            {
                this.add(entity);
            }
        }, param);
    }

    public void saveList(final List<T> entities) {
        this.execute((solrServer) -> {
            solrServer.addBeans(entities);
            solrServer.commit(false, false, true);
            logger.info("save entities successfully :" + entities.size());
            return null;
        });
    }

    public void saveList(final List<T> entities, SolrTemplate.CommitParameter param) {
        this.execute((solrServer) -> {
            solrServer.addBeans(entities);
            if (param.isNeedCommit()) {
                solrServer.commit(param.isWaitFlush(), param.isWaitSearcher(), param.isSoftCommit());
                logger.info("save entities successfully :" + entities.size());
            }

            return null;
        });
    }

    public void update(SolrUpdateParam update) {
        if (update != null && update.getParamList().size() != 0) {
            SolrInputDocument doc = new SolrInputDocument(new HashMap<>());
            doc.addField(update.getIdName(), update.getIdValue());
            Iterator var3 = update.getParamList().iterator();

            while (var3.hasNext()) {
                SolrUpdateParam.UpdateParam param = (SolrUpdateParam.UpdateParam) var3.next();
                doc.addField(param.getField(), param.pickUpdateInfo());
            }

            this.execute((solrServer) -> {
                solrServer.add(doc);
                solrServer.commit(false, false, true);
                return null;
            });
        }
    }

    public void deleteById(final String id) {
        this.deleteByIds(new ArrayList<String>() {
            {
                this.add(id);
            }
        });
    }

    public void deleteByIds(final List<String> ids) {
        this.execute((solrServer) -> {
            solrServer.deleteById(ids);
            solrServer.commit(false, false);
            logger.info("deleteByIds successfully : " + ids.size());
            return null;
        });
    }

    public void deleteByQuery(final String query) {
        this.execute((solrServer) -> {
            solrServer.deleteByQuery(query);
            solrServer.commit(false, false);
            logger.info("deleteByQuery successfully : " + query);
            return null;
        });
    }

    public void deleteAll() {
        this.execute((solrServer) -> {
            solrServer.deleteByQuery("*:*");
            solrServer.commit(false, false);
            logger.info("deleteAll successfully");
            return null;
        });
    }

    public void optimize(final int maxSegments) {
        this.execute((solrServer) -> {
            logger.info("Optimize solr start ...");
            long now = System.currentTimeMillis();
            solrServer.optimize(false, false, maxSegments);
            logger.info("Optimize solr end, time = " + (System.currentTimeMillis() - now));
            return null;
        });
    }

    public static class CommitParameter {
        private boolean needCommit;
        private boolean waitFlush;
        private boolean waitSearcher;
        private boolean softCommit = true;

        public CommitParameter() {
        }

        public boolean isWaitFlush() {
            return this.waitFlush;
        }

        public void setWaitFlush(boolean waitFlush) {
            this.waitFlush = waitFlush;
        }

        public boolean isWaitSearcher() {
            return this.waitSearcher;
        }

        public void setWaitSearcher(boolean waitSearcher) {
            this.waitSearcher = waitSearcher;
        }

        public boolean isSoftCommit() {
            return this.softCommit;
        }

        public void setSoftCommit(boolean softCommit) {
            this.softCommit = softCommit;
        }

        public boolean isNeedCommit() {
            return this.needCommit;
        }

        public void setNeedCommit(boolean needCommit) {
            this.needCommit = needCommit;
        }
    }
}

