package com.aatrox.wechat.wxmp;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/6
 */
//@Configuration
@ConfigurationProperties(prefix = "aatrox.wechat.wxmp")
public class AatroxWxmpProperties {
    private Map<String, WxmpConfigInfo> config = new HashMap();

    public Map<String, WxmpConfigInfo> getConfig() {
        return config;
    }

    public AatroxWxmpProperties setConfig(Map<String, WxmpConfigInfo> config) {
        this.config = config;
        return this;
    }
}
