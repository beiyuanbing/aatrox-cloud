package com.aatrox.orderapilist.fegin;

import java.util.List;

import com.aatrox.orderapilist.model.GoodModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
* <p>
    * 商品信息 服务类
    * </p>
*
* @author Aatrox
* @since 2020-06-17
*/
@FeignClient(value = "service-order", contextId = "GoodFegin")
public interface GoodFegin {

    @PostMapping("/goodInfo/selectById")
    GoodModel selectById(Integer id);

    @PostMapping("/goodInfo/selectAll")
    List<GoodModel> selectAll();

    @PostMapping("/goodInfo/insertGoodInfo")
    GoodModel insertGoodInfo(GoodModel record);


    @PostMapping("/goodInfo/updateGoodInfo")
    GoodModel updateGoodInfo(GoodModel record);

    @PostMapping("/goodInfo/deleteById")
    int deleteById(Integer id);

    @PostMapping("/goodInfo/reduceStock")
    void reduceStock(GoodModel good);
}

