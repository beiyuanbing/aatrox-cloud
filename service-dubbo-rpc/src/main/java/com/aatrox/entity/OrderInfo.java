package com.aatrox.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class OrderInfo implements Serializable {
    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "订单名称")
    private String name;


    public OrderInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public OrderInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "OrderInfoModel{"
                + ",id=" + id
                + ",name=" + name
                + "}";
    }
}
