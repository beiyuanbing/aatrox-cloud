package com.aatrox.web.controller;

import com.aatrox.component.redis.RedisCache;
import com.aatrox.orderapilist.model.OrderInfoModel;
import com.aatrox.web.base.controller.BaseController;
import com.aatrox.web.remote.OrderRemote;
import com.aatrox.web.solr.OrderSolrSerach;
import com.aatrox.web.solr.model.OrderSolrModel;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

//import com.aatrox.web.mq.producer.MsgQueueSender;

@RestController
@RequestMapping("order")
public class OrderController extends BaseController {

    /*    @Autowired
        StringRedisTemplate redisTemplate;*/
    // @Autowired
    //private JedisService jedisService;

    @Resource
    private OrderRemote orderRemote;
    @Resource
    private RedisCache redisCache;
    @Resource
    private OrderSolrSerach orderSolrSerach;
    //@Resource
    //private MsgQueueSender msgQueueSender;
    private static final String LOCK_KEY = "LOCK_KEY";

    @Autowired
    private Environment environment;

    @PostMapping("/insert")
    public Object insert(OrderInfoModel model){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", "1");
        //msgQueueSender.send("mytest", jsonObject);
//        RedisLock redisLock = new RedisLock(redisCache);
//        try {
//            if (redisLock.tryLock(LOCK_KEY)) {
//                String hhh = jedisService.get("hhh");
//                System.out.println(hhh);
//            }
//        } finally {
//            redisLock.unLock(LOCK_KEY);
//        }
        orderSolrSerach.save(new OrderSolrModel().setId(UUID.randomUUID().toString()).setName("测试"));

        return orderRemote.insert(model);
    }

    @PostMapping("/query")
    //此处就是缓存的具体使用了
    @Cacheable(value = "order")
    public Object query(Integer id){
        return returnSuccessInfo(orderRemote.query(id));
    }

}
