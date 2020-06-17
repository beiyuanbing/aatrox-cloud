package com.aatrox.orderservice.api;

import javax.annotation.Resource;
import java.util.List;

import com.aatrox.orderapilist.fegin.GoodFegin;
import com.aatrox.orderapilist.model.GoodModel;
import com.aatrox.orderservice.service.GoodService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 商品信息 服务实现类
    * </p>
*
* @author Aatrox
* @since 2020-06-17
*/
@RestController
@RequestMapping(value = "/goodInfo")
public class GoodApi implements GoodFegin {

    @Resource
    private GoodService goodService;

    @Override
    @PostMapping("/selectById")
    public GoodModel selectById(@RequestBody Integer id){
            return goodService.getById(id);
    }

    @Override
    @PostMapping("/selectAll")
    public List<GoodModel> selectAll(){
            return goodService.list();
    }

    @Override
    @PostMapping("/insertGoodInfo")
    public GoodModel insertGoodInfo(@RequestBody GoodModel record){
        goodService.save(record);
        return record;
    }

    @Override
    @PostMapping("/updateGoodInfo")
    public GoodModel updateGoodInfo(@RequestBody GoodModel record){
        goodService.updateById(record);
        return record;
    }

    @Override
    @PostMapping("/deleteById")
    public int deleteById(@RequestBody Integer id){
            return goodService.removeById(id)?1:0;
    }

    @Override
    @PostMapping("/reduceStock")
    public void reduceStock(@RequestBody GoodModel good){
         goodService.reduceStock(good);
    }




}
