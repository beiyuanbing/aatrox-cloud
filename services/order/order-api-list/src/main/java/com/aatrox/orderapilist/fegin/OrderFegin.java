package com.aatrox.orderapilist.fegin;


import com.aatrox.orderapilist.constants.ZoneStants;
import com.aatrox.orderapilist.model.OrderInfoModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;


@FeignClient(value = "service-order", contextId ="OrderFegin" )
public interface OrderFegin {
    @PostMapping("/orderInfo/selectById")
    OrderInfoModel selectById(Integer id);

    @PostMapping("/orderInfo/selectAll")
    List<OrderInfoModel> selectAll();

    @PostMapping("/orderInfo/insertOrderInfo")
    OrderInfoModel insertOrderInfo(OrderInfoModel record);


    @PostMapping("/orderInfo/updateOrderInfo")
    OrderInfoModel updateOrderInfo(OrderInfoModel record);

    @PostMapping("/orderInfo/deleteById")
    int deleteById(Integer id);
}
