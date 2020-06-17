package com.aatrox.web.controller;

import com.aatrox.component.redis.RedisCache;
import com.aatrox.oa.apilist.model.AccountModel;
import com.aatrox.orderapilist.model.GoodModel;
import com.aatrox.orderapilist.model.OrderInfoModel;
import com.aatrox.web.base.controller.BaseController;
import com.aatrox.web.remote.AccountRemote;
import com.aatrox.web.remote.GoodRemote;
import com.aatrox.web.remote.LogRemote;
import com.aatrox.web.remote.OrderRemote;
import com.aatrox.web.solr.OrderSolrSerach;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//import com.aatrox.web.mq.producer.MsgQueueSender;

@RestController
@RequestMapping("/order")
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
    @Resource
    private LogRemote logRemote;
    //@Resource
    //private MsgQueueSender msgQueueSender;
    private static final String LOCK_KEY = "LOCK_KEY";

    @Resource
    private GoodRemote goodRemote;

    @Resource
    private AccountRemote accountRemote;

    @Autowired
    private Environment environment;

    @PostMapping("/submitOrder")
    @GlobalTransactional
    public String submitOrder(
            @RequestParam("goodId") Integer goodId,
            @RequestParam("accountId") Integer accountId,
            @RequestParam("buyCount") int buyCount) {

        GoodModel good = goodRemote.selectById(goodId);

        Double orderPrice = buyCount * good.getPrice();

        goodRemote.reduceStock(new GoodModel().setId(goodId).setStock(buyCount));

        accountRemote.deduction(new AccountModel().setId(accountId).setMoney(orderPrice));

        orderRemote.insertOrderInfo(new OrderInfoModel().setAccountId(accountId).setGoodId(goodId).setPrice(orderPrice));
        return "下单成功.";
    }

}
