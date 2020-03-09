package com.aatrox.wechat;

import com.aatrox.wechat.wxmp.AatroxWxmpProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
@EnableConfigurationProperties({AatroxWxmpProperties.class})
public class WxmpConfig {
    public WxmpConfig() {
    }
}
