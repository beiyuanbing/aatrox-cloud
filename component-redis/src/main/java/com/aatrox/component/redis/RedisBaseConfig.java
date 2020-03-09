package com.aatrox.component.redis;

import com.aatrox.component.redis.config.RedisConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author yasuo
 */
@ComponentScan({"com.aatrox.component.redis"})
@Import({RedisConfig.class})
public class RedisBaseConfig {
}
