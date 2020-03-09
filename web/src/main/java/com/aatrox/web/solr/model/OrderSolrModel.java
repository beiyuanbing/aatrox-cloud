package com.aatrox.web.solr.model;


import org.apache.solr.client.solrj.beans.Field;

import java.util.List;

/**
 * @author aatrox
 * @desc 对接的Solr的实体
 * @date 2019-08-12
 */
public class OrderSolrModel {

    @Field(OrderSolrField.ID)
    private String id;
    @Field(OrderSolrField.NAME)
    private String name;
    @Field(OrderSolrField.SUBWAY)
    private List<String> subway;
    @Field(OrderSolrField.RATIO)
    private List<Double> ratio;

    public String getId() {
        return id;
    }

    public OrderSolrModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public OrderSolrModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getSubway() {
        return subway;
    }

    public OrderSolrModel setSubway(List<String> subway) {
        this.subway = subway;
        return this;
    }

    public List<Double> getRatio() {
        return ratio;
    }

    public OrderSolrModel setRatio(List<Double> ratio) {
        this.ratio = ratio;
        return this;
    }
}
