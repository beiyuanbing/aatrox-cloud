package com.aatrox.controller;

import com.aatrox.service.OrderDubboConsumerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    private OrderDubboConsumerService orderDubboConsumerService;

    @PostMapping("/getOrderInfo")
    public Object getOrderInfo() {
        Object orderInfo = orderDubboConsumerService.getOrderInfo();
        return orderInfo;
    }
}
