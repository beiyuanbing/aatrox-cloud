package com.aatrox.web.base.session;

import com.aatrox.ContextHolder;
import com.aatrox.ContextHolder.ContextKey;
import com.aatrox.component.redis.RedisCache;
import com.aatrox.component.redis.cacheKey.InfraCacheKey;
import com.aatrox.web.base.spring.ApplicationContextRegister;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public class SessionService {
    private static SessionService instance = null;
    private static Object lock = new Object();
    private static int EXPIRE_TIME = 1800;
    private RedisCache redisCache = (RedisCache) ApplicationContextRegister.getApplicationContext().getBean("redisCache", RedisCache.class);

    public static SessionService getInstance() {
        if (instance == null) {
            synchronized(lock) {
                if (instance == null) {
                    instance = new SessionService();
                }
            }
        }

        return instance;
    }

    private SessionService() {
        Environment env = (Environment)ApplicationContextRegister.getApplicationContext().getBean(Environment.class);
        Integer expireTime = (Integer)env.getProperty("redisSession.expireTime", Integer.class);
        if (expireTime != null) {
            EXPIRE_TIME = expireTime;
        }

    }

    public Map<String, Object> getSession(String id) {
        Map<String, Object> session = null;
        if (session == null) {
            session = (Map)this.redisCache.get(InfraCacheKey.SESSION.getBaseType(), id);
        }

        if (session == null) {
            session = new HashMap();
            this.saveSession(id, (Map)session);
        }

        return (Map)session;
    }

    public void saveSession(String id, Map<String, Object> session) {
        int expireTime = this.pickExpireTime();
        this.redisCache.put(InfraCacheKey.SESSION.getBaseType(), id, session, expireTime);
    }

    public int pickExpireTime() {
        Object expireAppTime = ContextHolder.getContextData(SessionService.SessionExpireKey.class);
        return expireAppTime != null ? (Integer)expireAppTime : EXPIRE_TIME;
    }

    public void removeSession(String id) {
        if (id != null) {
            this.redisCache.remove(InfraCacheKey.SESSION.getBaseType(), id);
        }

    }

    public void expireSession(String sessionId) {
        int expireTime = this.pickExpireTime();
        this.redisCache.expire(InfraCacheKey.SESSION.getBaseType(), sessionId, expireTime);
    }

    public static enum SessionExpireKey implements ContextKey {
        SessionExpireKey() {
        }
    }
}
