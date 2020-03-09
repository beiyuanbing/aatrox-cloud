package com.aatrox.componentsolr.config;

import com.aatrox.componentsolr.core.factory.SolrServerFactoryBean;
import org.springframework.context.annotation.Bean;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class SolrLocalConfiguration {
    public SolrLocalConfiguration() {
    }

    @Bean(
            name = {"solrServerFactory"}
    )
    public SolrServerFactoryBean solrServerFactory() {
        return new SolrServerFactoryBean();
    }
}