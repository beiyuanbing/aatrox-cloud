package com.aatrox.orderservice.service;

import com.aatrox.orderapilist.form.OrderInfoQueryForm;
import com.aatrox.orderapilist.model.OrderInfoModel;
import com.aatrox.orderservice.dao.OrderInfoDao;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OrderInfoService{

    @Resource
    private OrderInfoDao orderInfoDao;


    public int deleteByPrimaryKey(Integer fid) {
        return orderInfoDao.deleteByPrimaryKey(fid);
    }


    public int insert(OrderInfoModel record) {
        return orderInfoDao.insert(record);
    }


    public int insertSelective(OrderInfoModel record) {
        return orderInfoDao.insertSelective(record);
    }


    public OrderInfoModel selectByPrimaryKey(Integer fid) {
        return orderInfoDao.selectByPrimaryKey(fid);
    }


    public int updateByPrimaryKeySelective(OrderInfoModel record) {
        return orderInfoDao.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(OrderInfoModel record) {
        return orderInfoDao.updateByPrimaryKey(record);
    }

    public Page<OrderInfoModel> selectPage(OrderInfoQueryForm queryForm) {
        Page<OrderInfoModel> page = new Page<>(queryForm.getCurrentPage(), queryForm.getPageSize());
        page.setRecords(orderInfoDao.selectPage(queryForm, page));
        return page;
    }


    public List<OrderInfoModel> selectByParam(Map<String, Object> paramsMap) {
        return orderInfoDao.selectByParam(paramsMap);
    }
}
