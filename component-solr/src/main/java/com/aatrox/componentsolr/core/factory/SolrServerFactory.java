package com.aatrox.componentsolr.core.factory;

import com.aatrox.common.utils.ListUtil;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class SolrServerFactory {
    private static final Logger logger = LoggerFactory.getLogger(SolrServerFactory.class);
    private Map<String, SolrClient> solrServers = Collections.synchronizedMap(new HashMap());

    public SolrServerFactory() {
    }

    public SolrClient getCommonsHttpSolrServer(final String solrUrl) {
        if (!this.solrServers.containsKey(solrUrl)) {
            try {
                //低版本的用法 注释的
                // SolrClient solrServer = (new HttpSolrClient(solrUrl));
                SolrClient solrServer = (new HttpSolrClient.Builder(solrUrl)).build();
                this.solrServers.put(solrUrl, solrServer);
                logger.info("SolrServer start successfully : " + solrUrl);
            } catch (Exception var3) {
                throw new RuntimeException(var3);
            }
        }

        return (SolrClient) this.solrServers.get(solrUrl);
    }

    public SolrClient getShardSolrServer(String solrServerUrl, String shardsUrl, String uniqueKeyField) {
        String serverKey = solrServerUrl + ":" + shardsUrl;
        if (!this.solrServers.containsKey(serverKey)) {
            try {
                SolrClient solrServer = new ShardSolrServer(solrServerUrl, shardsUrl, uniqueKeyField);
                this.solrServers.put(serverKey, solrServer);
                logger.info("SolrServer start successfully : " + serverKey);
            } catch (Exception var6) {
                throw new RuntimeException(var6);
            }
        }

        return (SolrClient) this.solrServers.get(serverKey);
    }

    public SolrClient getCloudSolrServer(final String zkHost, final String zkRoot, final String defaultCollection, final Integer zkClientTimeout, final Integer zkConnectTimeout) {
        String key = zkHost + defaultCollection;
        if (!this.solrServers.containsKey(key)) {
            try {
                //低版本的用法 注释的
                //org.apache.solr.client.solrj.impl.CloudSolrClient builder = new org.apache.solr.client.solrj.impl.CloudSolrClient.Builder(ListUtil.NEW(new String[]{zkHost}), Optional.ofNullable(zkRoot));
                //CloudSolrClient solrServer = new CloudSolrClient(zkHost);
                //高版本都是直接使用这种builder的模式,现在lombok都提供了builder的结构了
                org.apache.solr.client.solrj.impl.CloudSolrClient.Builder builder = new org.apache.solr.client.solrj.impl.CloudSolrClient.Builder(ListUtil.NEW(new String[]{zkHost}), Optional.ofNullable(zkRoot));
                CloudSolrClient solrServer = builder.build();
                solrServer.setDefaultCollection(defaultCollection);
                solrServer.setZkClientTimeout(zkClientTimeout);
                solrServer.setZkConnectTimeout(zkConnectTimeout);
                this.solrServers.put(key, solrServer);
                logger.info("SolrServer start succefully : " + zkHost);
            } catch (Exception var9) {
                throw new RuntimeException(var9);
            }
        }

        return (SolrClient) this.solrServers.get(key);
    }
}


