package com.aatrox.orderservice.architecture;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoMapperConfig {
    @Bean(name="autoMapperScanner")
    public MapperScannerConfigurer autoMapperScanner(){
        MapperScannerConfigurer orgMapperScanner = new MapperScannerConfigurer();
        orgMapperScanner.setBasePackage("com.aatrox.orderservice.dao");
        return orgMapperScanner;
    }
}
