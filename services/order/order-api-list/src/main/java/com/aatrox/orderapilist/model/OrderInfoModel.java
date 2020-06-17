package com.aatrox.orderapilist.model;


/**
 * 订单系统
 * @author apple
 * Create at 2019-05-17 10:05
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author Aatrox
 * @since 2020-06-17
 */
@TableName(value = "t_order_info")
@ApiModel(value="OrderInfoModel对象", description="订单表")
public class OrderInfoModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("good_id")
    private Integer goodId;

    @TableField("account_id")
    private Integer accountId;

    @TableField("price")
    private Double price;

    public Integer getId() {
        return id;
    }

    public OrderInfoModel setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getGoodId() {
        return goodId;
    }

    public OrderInfoModel setGoodId(Integer goodId) {
        this.goodId = goodId;
        return this;
    }
    public Integer getAccountId() {
        return accountId;
    }

    public OrderInfoModel setAccountId(Integer accountId) {
        this.accountId = accountId;
        return this;
    }
    public Double getPrice() {
        return price;
    }

    public OrderInfoModel setPrice(Double price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "OrderInfoModel{" +
                "id=" + id +
                ", goodId=" + goodId +
                ", accountId=" + accountId +
                ", price=" + price +
                "}";
    }
}