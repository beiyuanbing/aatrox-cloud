package com.aatrox.orderapilist.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 商品信息
 * </p>
 *
 * @author Aatrox
 * @since 2020-06-17
 */
@ApiModel(value="GoodModel对象", description="商品信息")
@TableName(value = "t_good_info")
public class GoodModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("stock")
    private Integer stock;

    @TableField("price")
    private Double price;

    public Integer getId() {
        return id;
    }

    public GoodModel setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }

    public GoodModel setName(String name) {
        this.name = name;
        return this;
    }
    public Integer getStock() {
        return stock;
    }

    public GoodModel setStock(Integer stock) {
        this.stock = stock;
        return this;
    }
    public Double getPrice() {
        return price;
    }

    public GoodModel setPrice(Double price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "GoodModel{" +
                "id=" + id +
                ", name=" + name +
                ", stock=" + stock +
                ", price=" + price +
                "}";
    }
}
