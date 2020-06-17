package com.aatrox.orderservice.dao;

import java.util.List;

import com.aatrox.orderapilist.model.GoodModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* <p>
    * 商品信息 Mapper 接口
    * </p>
*
* @author Aatrox
* @since 2020-06-17
*/
@Mapper
public interface GoodDao extends BaseMapper<GoodModel>{
}

