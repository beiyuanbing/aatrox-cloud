package com.aatrox.service;

import com.aatrox.entity.OrderInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OrderDubboConsumerService {
    // @Reference(version = "1.0.0", interfaceClass = OrderDubboService.class)
    @Resource
    public OrderDubboService orderDubboService;

    public Object getOrderInfo() {
        OrderInfo orderInfo = orderDubboService.getOrderInfo(Integer.valueOf(1));
        System.out.println(orderInfo.toString());
        return orderInfo;
    }
}
