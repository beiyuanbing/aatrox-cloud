package com.aatrox.orderapilist.model;


/**
 * 订单系统
 * @author apple
 * Create at 2019-05-17 10:05
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 消息Model
 * Created by byb on 2019-05-20
 */
@ApiModel(description = "订单系统")
public class OrderInfoModel {
    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "订单名称")
    private String name;

    @ApiModelProperty(value = "订单创建时间")
    private Date createTime;

    public OrderInfoModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public OrderInfoModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public OrderInfoModel setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public String toString() {
        return "OrderInfoModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}