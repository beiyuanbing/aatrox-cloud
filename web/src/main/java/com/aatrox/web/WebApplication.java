package com.aatrox.web;

import com.aatrox.component.redis.RedisBaseConfig;
import com.aatrox.web.base.BaseApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;


@EnableFeignClients("com.aatrox")
@Import({//ActiveMqBaseConfig.class, RabbitmqBaseConfig.class,
        RedisBaseConfig.class})
//开启缓存的使用
//@EnableCaching
public class WebApplication extends BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
