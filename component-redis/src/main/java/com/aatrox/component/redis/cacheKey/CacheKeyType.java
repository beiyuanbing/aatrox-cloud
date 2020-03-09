package com.aatrox.component.redis.cacheKey;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public interface CacheKeyType {
    String SESSION_BASE = "SESSION_BASE";

    String getBaseType();

    String getCacheKey();
}
