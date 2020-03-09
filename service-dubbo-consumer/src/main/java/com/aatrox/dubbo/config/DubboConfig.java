package com.aatrox.dubbo.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DubboConfig {
    @Autowired
    private Environment env;

    public DubboConfig() {
    }

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig config = new ApplicationConfig();
        config.setOrganization(this.env.getProperty("dubbo.application.org"));
        config.setOwner(this.env.getProperty("dubbo.application.owner"));
        config.setName(this.env.getProperty("dubbo.application.name"));
        config.setLogger("slf4j");
        return config;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig config = new RegistryConfig();
        config.setProtocol("zookeeper");
        config.setAddress(this.env.getProperty("dubbo.registry.address"));
        config.setTimeout(15000);
        String var10001 = this.env.getProperty("user.home");
        config.setFile(var10001 + "/dubbo-registry/dubbo-registry-" + this.env.getProperty("logger.app") + ".cache");
        return config;
    }

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig config = new ProtocolConfig();
        config.setName("dubbo");
        config.setPort((Integer) this.env.getProperty("dubbo.protocol.port", Integer.class));
        return config;
    }

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig config = new ConsumerConfig();
        config.setCheck(false);
        config.setTimeout(15000);
        return config;
    }

}
