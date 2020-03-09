package com.aatrox.component.redis.info;

import org.springframework.core.env.Environment;

public class RedisConnectInfo {
    public boolean useCluster;
    public String envUrl;
    public Integer envPort;
    public String envPassword;
    public Integer dbIndex;

    public RedisConnectInfo(Environment env, boolean useCluster, String envUrl, String envPort, String envPassword) {
        this.useCluster = useCluster;
        this.envUrl = env.getProperty(envUrl);
        this.envPort = (Integer) env.getProperty(envPort, Integer.class);
        this.envPassword = env.getProperty(envPassword);
        this.dbIndex = (Integer) env.getProperty("basic.redis.dbIndex", Integer.class, 0);
        System.out.println("===init redis[url]:" + this.envUrl + ",[port]:" + this.envPort + ",[useCluster]:" + useCluster + ",[dbIndex]:" + this.dbIndex);
    }


}
