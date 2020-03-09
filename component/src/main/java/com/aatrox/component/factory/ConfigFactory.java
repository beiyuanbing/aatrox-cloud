package com.aatrox.component.factory;

import com.aatrox.component.redis.info.RedisConnectInfo;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

public class ConfigFactory {

    public static JedisConnectionFactory makeJedisConnectionFactory(RedisConnectInfo connectInfo, JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory factory;

        factory = new JedisConnectionFactory();
        factory.setHostName(connectInfo.envUrl);
        factory.setPort(connectInfo.envPort);


        if (!StringUtils.isEmpty(connectInfo.envPassword)) {
            factory.setPassword(connectInfo.envPassword);
        }

        factory.setPoolConfig(jedisPoolConfig);
        factory.setDatabase(connectInfo.dbIndex);
        return factory;
    }
}
