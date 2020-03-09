package com.aatrox.component.redis.lock;


import com.aatrox.component.redis.RedisCache;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 分布式锁
 *
 * @author admin
 */
public class RedisLock {
    private static String REDIS_LOCK = "REDIS_LOCK";
    private RedisCache redisCache;
    private AtomicBoolean isLock;
    private String redisLockKey="REDIS_LOCK";
    /**
     * 默认加锁时长
     */
    private final static int DEFAULT_CACHE_TIME = 2 * 60;
    /**
     * 默认重试次数
     */
    private final static int DEFAULT_TRY_COUNT = 3;


    public boolean isLock() {
        return isLock.get();
    }

    public RedisLock(RedisCache redisCache) {
        this.redisCache = redisCache;
        isLock = new AtomicBoolean(false);
    }

    /**
     * 分布式加锁，默认取得锁时间为{@link #DEFAULT_CACHE_TIME}
     *
     * @param tryTime 尝试次数
     * @return 判断是否获得了锁资源
     **/
    public boolean tryLock(String lockKey, int tryTime) {
        return tryLock(lockKey, tryTime, null);
    }

    public boolean tryLock(String lockKey) {
        return tryLock(lockKey, DEFAULT_TRY_COUNT, null);
    }

    /**
     * @param tryTime    尝试次数
     * @param lockSecond 加锁时间，可为空，默认为{@link #DEFAULT_CACHE_TIME}
     * @return 判断是否获得了锁资源
     **/
    public boolean tryLock(String lockKey, int tryTime, Integer lockSecond) {
        while (tryTime > 0) {
            boolean getLock = lock(lockKey, lockSecond);
            tryTime--;
            if (!getLock) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 分布式加锁，默认取得锁时间为{@link #DEFAULT_CACHE_TIME}
     *
     * @return 判断是否获得了锁资源
     **/
    public boolean lock(String lockKey) {
        return lock(lockKey, null);
    }

    /**
     * 分布式加锁
     *
     * @param second 加锁时间，可为空，默认为{@link #DEFAULT_CACHE_TIME}
     * @return 判断是否获得了锁资源
     **/
    public boolean lock(String lockKey, Integer second) {
        if (!isLock.get()) {
            //处理单点并发
            isLock.compareAndSet(false, redisLock(lockKey, second));
        }
        return isLock.get();
    }

    /**
     * 分布式解锁
     *
     * @param force 强制解锁
     **/
    public void unLock(String lockKey, boolean force) {
        if (force) {
            unRedisLock(lockKey);
        } else {
            unLock(lockKey);
        }
    }

    /**
     * 分布式解锁
     **/
    public void unLock(String lockKey) {
        if (isLock.get()) {
            unRedisLock(lockKey);
        }
    }

    /**
     * 分布式加锁
     *
     * @param second 加锁时间，默认为{@link #DEFAULT_CACHE_TIME}
     **/
    private boolean redisLock(String lockKey, Integer second) {
        if (second == null) {
            second = DEFAULT_CACHE_TIME;
        }
        long lockNum = redisCache.incr(redisLockKey,REDIS_LOCK + "_" + lockKey, second);
        //如果存储的锁资源大于1，即已经被其他渠道加了锁
        if (lockNum > 1) {
            return false;
        } else {
            redisCache.expire(redisLockKey,REDIS_LOCK + "_" + lockKey, second);
            return true;
        }
    }

    /**
     * 分布式解锁
     **/
    private void unRedisLock(String lockKey) {
        redisCache.remove(redisLockKey,REDIS_LOCK + "_" + lockKey);
        isLock.set(false);
    }

    public String getRedisLockKey() {
        return redisLockKey;
    }

    public RedisLock setRedisLockKey(String redisLockKey) {
        this.redisLockKey = redisLockKey;
        return this;
    }
}
