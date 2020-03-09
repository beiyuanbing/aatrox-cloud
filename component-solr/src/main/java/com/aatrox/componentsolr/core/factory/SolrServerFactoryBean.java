package com.aatrox.componentsolr.core.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class SolrServerFactoryBean implements FactoryBean<SolrServerFactory>, InitializingBean, ApplicationListener<ApplicationEvent> {
    private static final Logger logger = LoggerFactory.getLogger(SolrServerFactoryBean.class);
    private SolrServerFactory solrServerFactory;

    public SolrServerFactoryBean() {
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.solrServerFactory = new SolrServerFactory();
        logger.info("******** SolrServerFactory init finished ***********");
    }

    @Override
    public SolrServerFactory getObject() {
        if (this.solrServerFactory == null) {
            try {
                this.afterPropertiesSet();
            } catch (Exception var2) {
            }
        }

        return this.solrServerFactory;
    }

    @Override
    public Class<?> getObjectType() {
        return this.solrServerFactory == null ? SolrServerFactory.class : this.solrServerFactory.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

