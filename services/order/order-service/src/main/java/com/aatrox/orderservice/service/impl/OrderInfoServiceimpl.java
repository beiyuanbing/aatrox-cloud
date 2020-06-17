package com.aatrox.orderservice.service.impl;


import com.aatrox.orderapilist.model.OrderInfoModel;
import com.aatrox.orderservice.dao.OrderInfoDao;
import com.aatrox.orderservice.service.OrderInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
* <p>
    * 订单表 服务实现类
    * </p>
*
* @author Aatrox
* @since 2020-06-17
*/
@Service
public class OrderInfoServiceimpl extends ServiceImpl<OrderInfoDao, OrderInfoModel> implements OrderInfoService {
}
