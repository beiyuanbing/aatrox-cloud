package com.aatrox.web.remote;

import javax.annotation.Resource;

import com.aatrox.orderapilist.fegin.GoodFegin;
import com.aatrox.orderapilist.model.GoodModel;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* <p>
    * 商品信息 服务实现类
    * </p>
*
* @author Aatrox
* @since 2020-06-17
*/
@Service
public class GoodRemote{

    @Resource
    private GoodFegin goodFegion;

    public GoodModel selectById(Integer id){
        return goodFegion.selectById(id);
    }

    public List<GoodModel> selectAll(){
        return goodFegion.selectAll();

    }
    public GoodModel insertGoodInfo(GoodModel record){
        return goodFegion.insertGoodInfo(record);
    }

    public GoodModel updateGoodInfo(GoodModel record){
        return goodFegion.updateGoodInfo(record);
    }

    public int deleteById(Integer id){
        return goodFegion.deleteById(id);
    }

    public void reduceStock(GoodModel good){
         goodFegion.reduceStock(good);
    }

}
