package com.aatrox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceDubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceDubboConsumerApplication.class, args);
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        //ConfigurableApplicationContext run = SpringApplication.run(ServiceDubboConsumerApplication.class, args);
/*        OrderDubboConsumerService cityService = run.getBean(OrderDubboConsumerService.class);
        cityService.printOrder();*/
    }

}
