package com.aatrox.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Configuration
public class MyDubboConfig {
    @Resource
    private Environment environment;

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(environment.getProperty("spring.dubbo.application.name"));
        return applicationConfig;
    }

 /*<dubbo:registry protocol="zookeeper" address="127.0.0.1:2181"></dubbo:registry> */

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol(environment.getProperty("dubbo.registry.protocol"));
        registryConfig.setAddress(environment.getProperty("spring.dubbo.registry.address"));
        return registryConfig;
    }

 /*<dubbo:protocol name="dubbo" port="20882"></dubbo:protocol> */

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(environment.getProperty("spring.dubbo.protocol.name"));
        protocolConfig.setPort(20880);
        return protocolConfig;
    }
}
