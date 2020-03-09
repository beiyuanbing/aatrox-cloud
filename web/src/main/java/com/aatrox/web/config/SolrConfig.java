package com.aatrox.web.config;


import com.aatrox.componentsolr.config.SolrLocalConfiguration;
import com.aatrox.componentsolr.core.factory.SolrServerFactory;
import com.aatrox.web.solr.OrderSolrSerach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @author aatrox
 * @desc 配置的完善对应solr的order实例
 * @date 2019-08-12
 */
@Configuration
@Import({SolrLocalConfiguration.class})
public class SolrConfig {
    @Resource
    private SolrServerFactory solrServerFactory;
    @Resource
    private Environment env;

    @Bean
    public OrderSolrSerach orderSolrSerach() {
        OrderSolrSerach orderSolrSerach = new OrderSolrSerach(solrServerFactory);
        orderSolrSerach.initSolrServer("order", this.env.getProperty("data.solr.host"));
        return orderSolrSerach;
    }

}
