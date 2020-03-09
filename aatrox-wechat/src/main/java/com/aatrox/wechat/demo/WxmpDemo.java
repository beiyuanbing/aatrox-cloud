package com.aatrox.wechat.demo;

import com.aatrox.component.redis.RedisCache;
import com.aatrox.wechat.wxmp.AatroxWxmpProperties;
import com.aatrox.wechat.wxmp.WxmpConfigInfo;
import com.aatrox.wechat.wxmp.service.WechatService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
@Import({WxmpConfigInfo.class})
public class WxmpDemo {
    public WxmpDemo() {
    }

    @Bean(
            name = {"wxmpService"}
    )
    public WechatService wxmpService(AatroxWxmpProperties properties, RedisCache redisCache) {
        WxmpConfigInfo config = (WxmpConfigInfo) properties.getConfig().get("demo");
        WechatService wxmpService = new WechatService(config, redisCache);
        return wxmpService;
    }
}