package com.aatrox.web.config;

import com.aatrox.web.common.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 访问地址：项目名/v2/api-docs
 */
@EnableSwagger2
@Configuration
@Profile("dev")
public class Swagger2Config {
    @Autowired
    private Environment env;

    @Bean
    public Docket createRestApi() {
        String basePackage = "saas.live.web.controller";
        if (Env.PROD.name().equalsIgnoreCase(env.getProperty("spring.profiles.active"))) {
            basePackage = basePackage + ".xxx";
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("项目 Api")
                .version("1.0.0")
                .build();
    }
}
