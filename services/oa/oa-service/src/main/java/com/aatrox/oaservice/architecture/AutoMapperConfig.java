package com.aatrox.oaservice.architecture;

import com.aatrox.architecture.aspect.ExceptionConvertAspect;
import org.springframework.context.annotation.Bean;

public class AutoMapperConfig {
//    @Bean(name="autoMapperScanner")
//    public MapperScannerConfigurer autoMapperScanner(){
//        MapperScannerConfigurer orgMapperScanner = new MapperScannerConfigurer();
//        orgMapperScanner.setBasePackage("com.aatrox.oaservice.dao");
//        return orgMapperScanner;
//    }

    /**
     * 切面声明
     * @return
     */
    @Bean
    public ExceptionConvertAspect exceptionConvertAspect(){
        return new ExceptionConvertAspect();
    }
}
