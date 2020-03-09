package com.aatrox.component.redis.cacheKey;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public enum InfraCacheKey implements CacheKeyType {
    SESSION("SESSION_BASE"),
    REDIS_LOCK("INFRA"),
    REDIS_CACHED_DATA("INFRA"),
    REDIS_CACHED_DATA_THRESHOLD("INFRA"),
    CACHE_CSRF_TOKEN("SESSION_BASE"),
    CACHE_ENCRYPT_RSA("SESSION_BASE"),
    CACHE_ENCRYPT_AES("SESSION_BASE");

    private String baseType;

    private InfraCacheKey(String baseType) {
        this.baseType = baseType;
    }

    @Override
    public String getCacheKey() {
        return this.name();
    }

    @Override
    public String getBaseType() {
        return this.baseType;
    }
}