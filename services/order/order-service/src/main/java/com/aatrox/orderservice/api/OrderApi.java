package com.aatrox.orderservice.api;

import com.aatrox.orderapilist.fegin.OrderFegin;
import com.aatrox.orderapilist.form.OrderInfoQueryForm;
import com.aatrox.orderapilist.model.OrderInfoModel;
import com.aatrox.orderservice.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/orderInfo")
public class OrderApi implements OrderFegin {

    @Resource
    private OrderInfoService orderInfoService;

    @Override
    @PostMapping("/selectById")
    public OrderInfoModel selectById(@RequestBody Integer id){
        return orderInfoService.getById(id);
    }

    @Override
    @PostMapping("/selectAll")
    public List<OrderInfoModel> selectAll(){
        return orderInfoService.list();
    }

    @Override
    @PostMapping("/insertOrderInfo")
    public OrderInfoModel insertOrderInfo(@RequestBody OrderInfoModel record){
        orderInfoService.saveOrUpdate(record);
        return record;
    }

    @Override
    @PostMapping("/updateOrderInfo")
    public OrderInfoModel updateOrderInfo(@RequestBody OrderInfoModel record){
        orderInfoService.updateById(record);
        return record;
    }

    @Override
    @PostMapping("/deleteById")
    public int deleteById(@RequestBody Integer id){
        return orderInfoService.removeById(id)?1:0;
    }

}
