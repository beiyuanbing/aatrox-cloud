package com.aatrox.service.impl;

import com.aatrox.common.utils.ReflectionUtils;
import com.aatrox.entity.OrderInfo;
import com.aatrox.orderapilist.fegin.OrderFegin;
import com.aatrox.orderapilist.model.OrderInfoModel;
import com.aatrox.service.OrderDubboService;
import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;

@Service(version = "1.0.0", interfaceClass = OrderDubboService.class)
public class OrderDubboServiceImpl implements OrderDubboService {
    @Resource
    private OrderFegin orderFegin;

    @Override
    public OrderInfo getOrderInfo(Integer id) {
//        OrderInfoModel orderInfoModel = orderFegin.selectByPrimaryKey(Integer.valueOf(1));
//        OrderInfo orderInfo = new OrderInfo();
//        System.out.println("123");
//        ReflectionUtils.copyProperties(orderInfo, orderInfoModel);
//        return orderInfo;
        return null;
    }
}
