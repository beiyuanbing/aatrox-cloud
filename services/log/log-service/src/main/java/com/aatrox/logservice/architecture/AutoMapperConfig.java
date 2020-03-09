package com.aatrox.logservice.architecture;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoMapperConfig {
   @Bean(name="autoMapperScanner")
    public MapperScannerConfigurer autoMapperScanner(){
        MapperScannerConfigurer orgMapperScanner = new MapperScannerConfigurer();
        orgMapperScanner.setBasePackage("com.aatrox.logservice.dao");
        return orgMapperScanner;
    }
}
