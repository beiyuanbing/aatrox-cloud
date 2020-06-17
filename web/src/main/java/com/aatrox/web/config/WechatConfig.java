package com.aatrox.web.config;

//import com.aatrox.component.redis.RedisCache;
//import com.aatrox.wechat.WxmpConfig;
//import com.aatrox.wechat.wxmp.AatroxWxmpProperties;
//import com.aatrox.wechat.wxmp.WxmpConfigInfo;
//import com.aatrox.wechat.wxmp.service.WechatService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
//@Configuration
//@Import(WxmpConfig.class)
//public class WechatConfig {
//
//    @Bean(name = "wechatService")
//    public WechatService wechatService(AatroxWxmpProperties properties, RedisCache redisCache) {
//        WxmpConfigInfo config = properties.getConfig().get("training");
//        WechatService wxmpService = new WechatService(config, redisCache);
//        return wxmpService;
//    }
//}