package com.aatrox.component.solr;

import org.apache.solr.common.SolrDocument;

import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public interface Searcher<T> {
    void save(T entity);

    void saveList(List<T> entityList);

    void update(SolrUpdateParam update);

    void deleteById(String id);

    void deleteByIds(List<String> ids);

    void deleteByQuery(final String query);

    void optimize(int maxSegments);

    void deleteAll();

    T solrDocumentToEntity(SolrDocument solrDocument) throws Exception;
}
