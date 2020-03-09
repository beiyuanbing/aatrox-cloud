package com.aatrox.web.remote;

import com.aatrox.orderapilist.fegin.OrderFegin;
import com.aatrox.orderapilist.model.OrderInfoModel;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderRemote {
    @Resource
    private OrderFegin orderFegin;

    @Hystrix
    public Object insert(OrderInfoModel model){
        return orderFegin.insert(model);
    }

    /**
     * 此方法进行导出使用的
     * @param paramsMap
     * @return
     */
    @Hystrix
    public List selectByParam(Map<String,Object> paramsMap){
        if(paramsMap==null){
            paramsMap=new HashMap<>();
        }
        return orderFegin.selectByParam(paramsMap);

    }

    @Hystrix
    public OrderInfoModel query(Integer id) {
        return orderFegin.selectByPrimaryKey(id);
    }
}
