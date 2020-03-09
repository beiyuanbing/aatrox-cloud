package com.aatrox.component.redis.config;

import com.aatrox.component.redis.RedisBaseConfig;
import com.aatrox.component.redis.factory.ConfigFactory;
import com.aatrox.component.redis.info.RedisConnectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * 必须加，使配置生效
 *
 * @author yasuo
 */
@ConditionalOnProperty(value = {"basic.redis.on"}, havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(RedisBaseConfig.class)
public class RedisConfig extends CachingConfigurerSupport {
    @Autowired
    private Environment env;

    /**
     * 生成key的策略
     *
     * @return
     */
    @Override
    @Bean({"keyGenerator"})
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 管理缓存
     */
    @Bean({"cacheManager"})
    public CacheManager cacheManager(JedisConnectionFactory jedisConnectionFactory) {
        //缓存配置对象
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        //设置缓存的默认超时时间：5分钟
        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(5L))
                //如果是空值，不缓存
                .disableCachingNullValues()
                //设置key序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                //设置value序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer((new GenericJackson2JsonRedisSerializer())));
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(jedisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }


    @Bean(
            name = {"redisTemplate"}
    )
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(
            name = {"stringRedisTemplate"}
    )
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(
            name = {"jedisConnectionFactory"}
    )
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory factory = ConfigFactory.makeJedisConnectionFactory(new RedisConnectInfo(this.env, false, "spring.redis.host", "spring.redis.port", "spring.redis.password"), jedisPoolConfig);
        return factory;
    }

    @Bean(
            name = {"jedisPoolConfig"}
    )
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(20000L);
        return config;
    }

    /*@ConditionalOnProperty(
            value = {"basic.redis.on"},
            havingValue = "true",
            matchIfMissing = true
    )
    @ConditionalOnClass({Jedis.class})
    @Configuration
    static class EnableRedis {
        @Autowired
        private Environment env;

        @Bean(
                name = {"redisTemplate"}
        )
        public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
            RedisTemplate redisTemplate = new RedisTemplate();
            redisTemplate.setConnectionFactory(jedisConnectionFactory);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
            return redisTemplate;
        }

        @Bean(
                name = {"stringRedisTemplate"}
        )
        public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
            StringRedisTemplate redisTemplate = new StringRedisTemplate();
            redisTemplate.setConnectionFactory(jedisConnectionFactory);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
            return redisTemplate;
        }

        @Bean(
                name = {"jedisConnectionFactory"}
        )
        public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
            JedisConnectionFactory factory = ConfigFactory.makeJedisConnectionFactory(new RedisConnectInfo(this.env, false, "spring.redis.host", "spring.redis.port", "spring.redis.password"), jedisPoolConfig);
            return factory;
        }

        @Bean(
                name = {"jedisPoolConfig"}
        )
        public JedisPoolConfig jedisPoolConfig() {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(100);
            config.setMaxIdle(5);
            config.setMaxWaitMillis(20000L);
            return config;
        }
    }*/
}
