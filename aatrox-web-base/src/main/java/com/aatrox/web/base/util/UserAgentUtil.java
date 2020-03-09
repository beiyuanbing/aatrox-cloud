package com.aatrox.web.base.util;

import com.aatrox.threads.LocalRunnable;
import com.aatrox.threads.ThreadCachePool;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public class UserAgentUtil {
    private static final Logger logger = LoggerFactory.getLogger(UserAgentUtil.class);
    private static UserAgentUtil instance;
    private GenericObjectPool<UserAgentAnalyzer> pool;
    private static int poolSize = 2;

    private UserAgentUtil() {
    }

    public static UserAgent analyzer(String userAgent) {
        UserAgentAnalyzer uaa = null;

        UserAgent var3;
        try {
            uaa = (UserAgentAnalyzer)instance.pool.borrowObject(500L);
            UserAgent var2 = uaa.parse(userAgent);
            return var2;
        } catch (Exception var7) {
            var3 = new UserAgent();
        } finally {
            if (uaa != null) {
                instance.pool.returnObject(uaa);
            }

        }

        return var3;
    }

    public static synchronized void init(Environment env) {
        if (instance == null) {
            instance = new UserAgentUtil();
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMinIdle(poolSize);
            poolConfig.setMaxIdle(poolSize);
            poolConfig.setMaxTotal(poolSize);
            instance.pool = new GenericObjectPool(new UserAgentUtil.UserAgentPooledObjectFactory(), poolConfig);
            boolean prepare = (Boolean)env.getProperty("basic.user.agent.analyzer.init", Boolean.class, false);
            if (prepare) {
                ThreadCachePool.execute(new LocalRunnable() {
                    @Override
                    protected void doRun() {
                        try {
                            UserAgentUtil.logger.info("/--------------------userAgent 解析池加载中-----------------------/");
                            UserAgentUtil.instance.pool.preparePool();
                            UserAgentUtil.logger.info("/--------------------userAgent 解析池加载完毕，解析器个数:(" + UserAgentUtil.poolSize + ")-----------------------/");
                        } catch (Exception var2) {
                        }

                    }
                });
            }
        }

    }

    static class UserAgentPooledObjectFactory extends BasePooledObjectFactory<UserAgentAnalyzer> {
        UserAgentPooledObjectFactory() {
        }

        @Override
        public UserAgentAnalyzer create() throws Exception {
            UserAgentAnalyzer uaa = ((UserAgentAnalyzer.UserAgentAnalyzerBuilder)UserAgentAnalyzer.newBuilder().hideMatcherLoadStats()).withCache(1000).build();
            uaa.parse("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11");
            return uaa;
        }

        @Override
        public PooledObject<UserAgentAnalyzer> wrap(UserAgentAnalyzer obj) {
            return new DefaultPooledObject(obj);
        }
    }
}

