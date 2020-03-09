package com.aatrox.component.solr.factory;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;

import java.io.IOException;
import java.util.*;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class ShardSolrServer extends HttpSolrClient {
    private static final long serialVersionUID = -7228909984319129451L;
    private Map<Integer, SolrClient> shards = Collections.synchronizedMap(new HashMap());
    private String shardsUrl;
    private String uniqueKeyField = "id";

    public ShardSolrServer(String solrServerUrl) {
        super(solrServerUrl);
    }

    public ShardSolrServer(String solrServerUrl, String shardsUrl, String uniqueKeyField) {
        super(solrServerUrl);
        if (shardsUrl != null) {
            this.shardsUrl = shardsUrl;
            String[] shardUrls = shardsUrl.split(",");

            for (int i = 0; i < shardUrls.length; ++i) {
                String shardUrl = shardUrls[i];
                if (!shardUrl.startsWith("http://")) {
                    shardUrl = "http://" + shardUrl;
                    HttpSolrClient shardSolrServer = new HttpSolrClient(shardUrl);
                    this.shards.put(i, shardSolrServer);
                }
            }
        }

        if (uniqueKeyField != null) {
            this.uniqueKeyField = uniqueKeyField;
        }

    }

    @Override
    public UpdateResponse add(Collection<SolrInputDocument> docs) throws SolrServerException, IOException {
        Map<Integer, SolrClient> updatedSolrServers = new HashMap();
        Iterator var3 = docs.iterator();

        while (var3.hasNext()) {
            SolrInputDocument solrInputDocument = (SolrInputDocument) var3.next();
            int shardId = solrInputDocument.get(this.uniqueKeyField).hashCode() % this.shards.size();
            SolrClient shardServer = (SolrClient) this.shards.get(shardId);
            shardServer.add(solrInputDocument);
            updatedSolrServers.put(shardId, shardServer);
        }

        var3 = updatedSolrServers.values().iterator();

        while (var3.hasNext()) {
            SolrClient solrServer = (SolrClient) var3.next();
            solrServer.commit(false, false);
        }

        return null;
    }

    @Override
    public QueryResponse query(SolrParams params) throws SolrServerException, IOException {
        if (this.shardsUrl != null && !this.shardsUrl.trim().equals("")) {
            ((SolrQuery) params).setParam("shards", new String[]{this.shardsUrl});
        }

        return super.query(params);
    }

    @Override
    public UpdateResponse deleteById(List<String> ids) throws SolrServerException, IOException {
        Map<Integer, SolrClient> updatedSolrServers = new HashMap();
        Iterator var3 = ids.iterator();

        while (var3.hasNext()) {
            String id = (String) var3.next();
            int shardId = id.hashCode() % this.shards.size();
            SolrClient shardServer = (SolrClient) this.shards.get(shardId);
            shardServer.deleteById(id);
            updatedSolrServers.put(shardId, shardServer);
        }

        var3 = updatedSolrServers.values().iterator();

        while (var3.hasNext()) {
            SolrClient solrServer = (SolrClient) var3.next();
            solrServer.commit(false, false);
        }

        return null;
    }

    @Override
    public UpdateResponse deleteByQuery(String query) throws SolrServerException, IOException {
        Iterator it = this.shards.values().iterator();

        SolrClient solrServer;
        while (it.hasNext()) {
            solrServer = (SolrClient) it.next();
            solrServer.deleteByQuery(query);
        }

        it = this.shards.values().iterator();

        while (it.hasNext()) {
            solrServer = (SolrClient) it.next();
            solrServer.commit(false, false);
        }

        return null;
    }

    @Override
    public UpdateResponse optimize(boolean waitFlush, boolean waitSearcher, int maxSegments) throws SolrServerException, IOException {
        Iterator it = this.shards.values().iterator();

        SolrClient solrServer;
        while (it.hasNext()) {
            solrServer = (SolrClient) it.next();
            solrServer.optimize(waitFlush, waitSearcher, maxSegments);
        }

        it = this.shards.values().iterator();

        while (it.hasNext()) {
            solrServer = (SolrClient) it.next();
            solrServer.commit(false, false);
        }

        return null;
    }

    @Override
    public UpdateResponse commit() throws SolrServerException, IOException {
        return null;
    }

    @Override
    public UpdateResponse commit(boolean waitFlush, boolean waitSearcher) throws SolrServerException, IOException {
        return null;
    }
}

