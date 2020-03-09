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
@RequestMapping("/order")
public class OrderApi implements OrderFegin {

    @Resource
    private OrderInfoService orderInfoService;
    @Override
    @PostMapping("/deleteByPrimaryKey")
    public int deleteByPrimaryKey(@RequestBody Integer id) {
        return orderInfoService.deleteByPrimaryKey(id);
    }

    @Override
    @PostMapping("/insert")
    public int insert(@RequestBody OrderInfoModel record) {
        return orderInfoService.insert(record);
    }

    @Override
    @PostMapping("/insertSelective")
    public int insertSelective(@RequestBody OrderInfoModel record) {
        return orderInfoService.insertSelective(record);
    }

    @Override
    @PostMapping("/selectByPrimaryKey")
    public OrderInfoModel selectByPrimaryKey(@RequestBody Integer id) {
        return orderInfoService.selectByPrimaryKey(id);
    }

    @Override
    @PostMapping("/updateByPrimaryKeySelective")
    public int updateByPrimaryKeySelective(@RequestBody OrderInfoModel record) {
        return orderInfoService.updateByPrimaryKeySelective(record);
    }

    @Override
    @PostMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(@RequestBody OrderInfoModel record) {
        return orderInfoService.updateByPrimaryKey(record);
    }

    @Override
    @PostMapping("/selectPage")
    public Page<OrderInfoModel> selectPage(@RequestBody OrderInfoQueryForm queryForm) {
        return orderInfoService.selectPage(queryForm);
    }

    @Override
    @PostMapping("/selectByParam")
    public List<OrderInfoModel> selectByParam(@RequestBody(required=false) Map<String, Object> paramsMap) {
        return orderInfoService.selectByParam(paramsMap);
    }
}
