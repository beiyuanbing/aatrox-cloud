package com.aatrox.eurekaserver.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EurekaStateChangeListener {

    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        //服务下线事件
        log.info("服务:{}|{}挂了", event.getAppName(), event.getServerId());
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        //服务注册事件
        log.info("服务:{}|{}注册成功了", event.getInstanceInfo().getAppName(), event.getInstanceInfo().getIPAddr());
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        //服务续约事件
        log.info("心跳检测:{}|{}", event.getInstanceInfo().getAppName(), event.getInstanceInfo().getIPAddr());
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        //注册中心启动事件
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        //Server启动
    }
}
