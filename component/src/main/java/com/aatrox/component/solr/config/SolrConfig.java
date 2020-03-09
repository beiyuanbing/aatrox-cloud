package com.aatrox.component.solr.config;

/**
 * @author aatrox
 * @desc 这个solr的写法也可以用
 * @date 2019-08-13
 */
/*@Configuration
//这个要进入spring solr的包    public static final String SOLR_PACKAGE = "com.aatrox.module.solr";
@EnableSolrRepositories(basePackages = ConfigConstants.SOLR_PACKAGE)
public class SolrConfig {
    @Bean
    public SolrServer solrServer() {
        return new HttpSolrServer(SpringPropertyReader.getProperty("module.solr.user.zkurl"));
    }

    @Bean
    public SolrTemplate solrTemplate() {
        return new SolrTemplate(solrServer());
    }

}*/
