package com.aatrox.component.redis.impl;


import com.aatrox.common.utils.DateUtil;
import com.aatrox.component.redis.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * RedisCacheImpl
 *
 * @author Hou Peiqin
 * @update Hyuga
 */
@Service("redisCache")
public class RedisCacheImpl implements RedisCache {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String systemUnique = "CLOUD_ANONYMOUS";

    @Override
    public Object get(String cacheType,String key) {
        try {
            return redisTemplate.opsForValue().get(wrapKey(cacheType,key));
        } catch (Exception e) {
            logger.warn("获取缓存异常：" + e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean hasKey(String cacheType,String key) {
        try {
            return redisTemplate.hasKey(wrapKey(cacheType,key));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void expire(String cacheType,String key, int timeout) {
        try {
            String keyValue = wrapKey(cacheType,key);
            redisTemplate.expire(keyValue, timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    @Override
    public long incr(String cacheType,String key) {
        String keyHere = wrapKey(cacheType,key);
        try {
            long count = redisTemplate.opsForValue().increment(keyHere, 1L);
            return count;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return 0L;
    }

    @Override
    public long incr(String cacheType,String key, int second) {
        String keyHere = wrapKey(cacheType,key);
        try {
            long count = redisTemplate.opsForValue().increment(keyHere, 1L);
            expire(cacheType,key, second);
            return count;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return 0L;
    }

    @Override
    public long incr(String cacheType,String key, long incrValue) {
        String keyHere = wrapKey(cacheType,key);
        try {
            return redisTemplate.opsForValue().increment(keyHere, incrValue);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0L;
    }

    @Override
    public long incr(String cacheType,String key, long incrValue, int second) {
        String keyHere = wrapKey(cacheType,key);
        try {
            long count = redisTemplate.opsForValue().increment(keyHere, incrValue);
            expire(cacheType,key, second);
            return count;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return 0L;
    }

    @Override
    public long getIncr(String cacheType,String key) {
        String keyValue = wrapKey(cacheType,key);
        try {
            RedisAtomicLong entityIdCounter = new RedisAtomicLong(keyValue,
                    redisTemplate.getConnectionFactory());
            return entityIdCounter.longValue();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0L;
    }

    @Override
    public void put(String cacheType,String key, Object value) {
        try {
            redisTemplate.opsForValue().set(wrapKey(cacheType,key), value, DateUtil.secondsLeftToday(), TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.warn("赋值缓存异常：" + e.getMessage());
        }
    }

    @Override
    public void put(String cacheType,String key, Object value, int second) {
        if (value != null && second > 0 && !StringUtils.isEmpty(key)) {
            try {
                redisTemplate.opsForValue().set(wrapKey(cacheType,key), value, second, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                logger.warn("赋值缓存异常：" + e.getMessage());
            }
        }
    }

    @Override
    public void update(String cacheType,String key, Object value) {
        try {
            redisTemplate.opsForValue().set(wrapKey(cacheType,key), value, DateUtil.secondsLeftToday(), TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("更新缓存异常：" + e.getMessage());
        }
    }

    @Override
    public void remove(String cacheType,String key) {
        try {
            redisTemplate.delete(wrapKey(cacheType,key));
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("移除缓存异常：" + e.getMessage());
        }
    }

    @Override
    public void clear() {
    }

   /* private String wrapKey(String cacheType,String key) {
            return MD5Util.getMD5(key.getBytes());
    }*/

    @Override
    public void putByNottl(String cacheType,String key, Object value) {
        if (value != null && !StringUtils.isEmpty(key)) {
            try {
                redisTemplate.opsForValue().set(wrapKey(cacheType,key), value, DateUtil.secondsLeftToday(), TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                logger.warn("赋值缓存异常：" + e.getMessage());
            }
        }
    }

    @Override
    public String wrapKey(String cacheType, String key) {
        StringBuilder keyBuilder = new StringBuilder();
        if (StringUtils.isEmpty(cacheType)) {
            keyBuilder.append(this.getSystemUnique()).append(":");
        }else{
            keyBuilder.append(cacheType).append(":");
        }
        keyBuilder.append(key);
        return keyBuilder.toString();
    }

    public String getSystemUnique() {
        return this.systemUnique;
    }
}
