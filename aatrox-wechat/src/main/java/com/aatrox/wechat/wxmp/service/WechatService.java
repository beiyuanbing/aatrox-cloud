package com.aatrox.wechat.wxmp.service;

import com.aatrox.common.utils.DateUtil;
import com.aatrox.common.utils.RandomUtil;
import com.aatrox.component.redis.RedisCache;
import com.aatrox.wechat.wxmp.WxmpCacheKey;
import com.aatrox.wechat.wxmp.WxmpConfigInfo;
import com.aatrox.wechat.wxmp.base.WxmpBaseRequest;
import com.aatrox.wechat.wxmp.base.WxmpBaseResponse;
import com.aatrox.wechat.wxmp.request.AccessTokenRequest;
import com.aatrox.wechat.wxmp.response.AccessTokenResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
public class WechatService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private int delay = 180;
    private String appId;
    private String secret;
    private RedisCache redisCache;

    public WechatService(WxmpConfigInfo config, RedisCache redisCache) {
        if (config != null && !StringUtils.isBlank(config.getAppid()) && !StringUtils.isBlank(config.getSecret())) {
            this.appId = config.getAppid();
            this.secret = config.getSecret();
            this.redisCache = redisCache;
            this.initAuthGetAccessToken();
            ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
            scheduledExecutor.scheduleWithFixedDelay(new WechatService.CheckerRunner(), (long) RandomUtil.makeRandomNumber(2), (long) this.delay, TimeUnit.SECONDS);
        } else {
            throw new RuntimeException("请配置微信小程序的appid和secret");
        }
    }

    public <T extends WxmpBaseResponse> T doRequest(WxmpBaseRequest<T> request) {
        if (request instanceof AccessTokenRequest) {
            throw new RuntimeException("请使用" + WechatService.class.getSimpleName() + "进行accessToken初始化");
        } else {
            return request.doRequest(this);
        }
    }

    public String initAuthGetAccessToken() {
        AccessTokenRequest request = new AccessTokenRequest();
        AccessTokenResponse response = (AccessTokenResponse) request.doRequest(this);
        if (response.isSuccess()) {
            this.redisCache.put(WxmpCacheKey.WX_MINI_PROGRAM_ACCESS_TOKEN.name(), this.appId, response.getAccess_token());
            long endTime = DateUtil.addMinute(new Date(), (response.getExpires_in() - 300) / 60).getTime();
            this.redisCache.put(WxmpCacheKey.WX_MINI_PROGRAM_ACCESS_TOKEN.name(), this.appId, endTime);
        }

        return response.getAccess_token();
    }

    public String pickAccessToken() {
        String accessToken = (String) this.redisCache.get(WxmpCacheKey.WX_MINI_PROGRAM_ACCESS_TOKEN.name(), this.appId);
        if (StringUtils.isBlank(accessToken)) {
            accessToken = this.initAuthGetAccessToken();
        }

        return accessToken;
    }

    public String getAppId() {
        return appId;
    }

    public String getSecret() {
        return secret;
    }

    private class CheckerRunner implements Runnable {
        private CheckerRunner() {
        }

        @Override
        public void run() {
            try {
                Long end = (Long) WechatService.this.redisCache.get(WxmpCacheKey.WX_MINI_PROGRAM_ACCESS_TOKEN.name(), WechatService.this.appId);
                if (end == null || end != null && end - System.currentTimeMillis() < 300000L) {
                    WechatService.this.initAuthGetAccessToken();
                }
            } catch (Exception var2) {
                WechatService.this.logger.error(var2.getMessage(), var2);
            }

        }
    }
}
