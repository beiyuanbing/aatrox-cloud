package com.aatrox.orderservice.service.impl;

import com.aatrox.orderapilist.model.GoodModel;
import com.aatrox.orderservice.dao.GoodDao;
import com.aatrox.orderservice.service.GoodService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.ObjectUtils;

/**
* <p>
    * 商品信息 服务实现类
    * </p>
*
* @author Aatrox
* @since 2020-06-17
*/
@Service
public class GoodServiceimpl extends ServiceImpl<GoodDao, GoodModel> implements GoodService {


    @Override
    public void reduceStock(GoodModel good) {
        GoodModel goodModel = this.baseMapper.selectById(good.getId());
        if (ObjectUtils.isEmpty(good)) {
            throw new RuntimeException("商品：" + goodModel.getId() + ",不存在.");
        }
        if (goodModel.getStock() - goodModel.getStock() < 0) {
            throw new RuntimeException("商品：" + goodModel.getId() + "库存不足.");
        }
        good.setStock(goodModel.getStock() - good.getStock());
        baseMapper.updateById(good);

    }
}
