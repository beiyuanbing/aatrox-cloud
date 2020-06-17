package com.aatrox.orderservice.dao;


import com.aatrox.orderapilist.model.OrderInfoModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author Aatrox
 * @since 2020-06-17
 */
@Mapper
public interface OrderInfoDao extends BaseMapper<OrderInfoModel>{
}