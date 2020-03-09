package com.aatrox.component.redis;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public interface RedisCache {
    Boolean hasKey(String cacheType, String key);


    /**
     * Get an item from the cache, nontransactionally
     *
     * @param key
     * @return the cached object or <tt>null</tt>
     */
    Object get(String cacheType, String key);

    void expire(String cacheType, String key, int timeout);

    long incr(String cacheType, String key);

    long incr(String cacheType, String key, int second);

    long incr(String cacheType, String key, long incrValue);

    long incr(String cacheType, String key, long incrValue, int second);

    long getIncr(String cacheType, String key);

    /**
     * Add an item to the cache, nontransactionally, with
     * failfast semantics
     *
     * @param key
     * @param value
     */
    void put(String cacheType, String key, Object value);

    /**
     * putByNottl
     *
     * @param key
     * @param value
     */
    void putByNottl(String cacheType, String key, Object value);

    /**
     * set
     *
     * @param key
     * @param value
     * @param second
     */
    void put(String cacheType, String key, Object value, int second);

    /**
     * Add an item to the cache
     *
     * @param key
     * @param value
     */
    void update(String cacheType, String key, Object value);

    /**
     * Remove an item from the cache
     *
     * @param key
     */
    void remove(String cacheType, String key);

    /**
     * Clear the cache
     */
    void clear();

    String wrapKey(String cacheType, String key);
}
