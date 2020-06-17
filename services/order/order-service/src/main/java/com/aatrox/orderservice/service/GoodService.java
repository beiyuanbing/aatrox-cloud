package com.aatrox.orderservice.service;

import java.util.List;

import com.aatrox.orderapilist.model.GoodModel;
import com.baomidou.mybatisplus.extension.service.IService;
/**
* <p>
    * 商品信息 服务类
    * </p>
*
* @author Aatrox
* @since 2020-06-17
*/
public interface GoodService extends IService<GoodModel> {

    void reduceStock(GoodModel good);

}

