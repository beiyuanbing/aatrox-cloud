package com.aatrox;

import io.dubbo.springboot.DubboAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {DubboAutoConfiguration.class})
@EnableFeignClients("com.aatrox")
public class ServiceDubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceDubboProviderApplication.class, args);
    }

}
