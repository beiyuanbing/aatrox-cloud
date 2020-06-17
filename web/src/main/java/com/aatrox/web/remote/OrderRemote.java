package com.aatrox.web.remote;

import com.aatrox.orderapilist.fegin.OrderFegin;
import com.aatrox.orderapilist.model.OrderInfoModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderRemote{

    @Resource
    private OrderFegin orderInfoFegin;

    public OrderInfoModel selectById(Integer id){
        return orderInfoFegin.selectById(id);
    }

    public List<OrderInfoModel> selectAll(){
        return orderInfoFegin.selectAll();

    }
    public OrderInfoModel insertOrderInfo(OrderInfoModel record){
        return orderInfoFegin.insertOrderInfo(record);
    }

    public OrderInfoModel updateOrderInfo(OrderInfoModel record){
        return orderInfoFegin.updateOrderInfo(record);
    }

    public int deleteById(Integer id){
        return orderInfoFegin.deleteById(id);
    }

}