package com.aatrox.orderapilist.fegin;


import com.aatrox.orderapilist.constants.ZoneStants;
import com.aatrox.orderapilist.form.OrderInfoQueryForm;
import com.aatrox.orderapilist.model.OrderInfoModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;


@FeignClient(value = ZoneStants.FEGIN_URL,contextId ="OrderFegin" )
public interface OrderFegin {
    @PostMapping("/order/deleteByPrimaryKey")
    int deleteByPrimaryKey(Integer fid);

    @PostMapping("/order/insert")
    int insert(OrderInfoModel record);

    @PostMapping("/order/insertSelective")
    int insertSelective(OrderInfoModel record);

    @PostMapping("/order/selectByPrimaryKey")
    OrderInfoModel selectByPrimaryKey(Integer fid);

    @PostMapping("/order/selectByParam")
    List<OrderInfoModel> selectByParam(Map<String,Object> paramsMap);

    @PostMapping("/order/updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(OrderInfoModel record);

    @PostMapping("/order/updateByPrimaryKey")
    int updateByPrimaryKey(OrderInfoModel record);

    @PostMapping("/order/selectPage")
    Page<OrderInfoModel> selectPage(OrderInfoQueryForm queryForm);
}
