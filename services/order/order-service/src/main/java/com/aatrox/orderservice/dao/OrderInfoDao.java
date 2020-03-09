package com.aatrox.orderservice.dao;


import com.aatrox.orderapilist.form.OrderInfoQueryForm;
import com.aatrox.orderapilist.model.OrderInfoModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public interface OrderInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderInfoModel record);

    int insertSelective(OrderInfoModel record);

    OrderInfoModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderInfoModel record);

    int updateByPrimaryKey(OrderInfoModel record);

    List<OrderInfoModel> selectPage(OrderInfoQueryForm queryForm, Page<OrderInfoModel> page);

    List<OrderInfoModel> selectByParam(Map<String, Object> paramsMap);
}