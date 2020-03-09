package com.aatrox.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019/11/7
 */
@ApiModel("Solr的查询结构")
public class OrderSolrQueryForm {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("地铁线路")
    private List<String> subway;

    @ApiModelProperty("佣金方案")
    private List<Double> ratio;

    @ApiModelProperty("自定义查询串")
    private String searchQStr;

    public String getId() {
        return id;
    }

    public OrderSolrQueryForm setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public OrderSolrQueryForm setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getSubway() {
        return subway;
    }

    public OrderSolrQueryForm setSubway(List<String> subway) {
        this.subway = subway;
        return this;
    }

    public List<Double> getRatio() {
        return ratio;
    }

    public OrderSolrQueryForm setRatio(List<Double> ratio) {
        this.ratio = ratio;
        return this;
    }

    public String getSearchQStr() {
        return searchQStr;
    }

    public OrderSolrQueryForm setSearchQStr(String searchQStr) {
        this.searchQStr = searchQStr;
        return this;
    }
}
