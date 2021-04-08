package com.aatrox.web.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

/**
 * {webAppName}
 *
 * @author {author}
 */
@EnableSwagger2
@Configuration
@Profile("dev")
public class Swagger2Configuration {

    @Resource
    private Environment env;

    // 定义分隔符,配置Swagger多包
    private static final String SPLITOR = ";";

    @Bean
    public Docket createRestApi() {

        Predicate<RequestHandler> selector1 = RequestHandlerSelectors.basePackage("com.kfang.web.newhouse.manager.controller");
        Predicate<RequestHandler> selector2 = RequestHandlerSelectors.basePackage("com.kfang.web.newhouse.controller");

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(Predicates.or(selector1,selector2))
                .paths(PathSelectors.any())
                .build();
    }


    /**
     * 切割扫描的包生成Predicate<RequestHandler>
     * @param basePackage
     * @return
     */
    public static Predicate<RequestHandler> scanBasePackage(final String basePackage) {
        if(StringUtils.isBlank(basePackage)){
            throw new NullPointerException("basePackage不能为空，多个包扫描使用"+SPLITOR+"分隔");
        }
        String[] controllerPack = basePackage.split(SPLITOR);
        Predicate<RequestHandler> predicate = null;
        for (int i = controllerPack.length -1; i >= 0 ; i--) {
            String strBasePackage = controllerPack[i];
            if(StringUtils.isNotBlank(strBasePackage)){
                Predicate<RequestHandler> tempPredicate = RequestHandlerSelectors.basePackage(strBasePackage);
                predicate = predicate == null ? tempPredicate : Predicates.or(tempPredicate,predicate);
            }
        }
        if(predicate == null){
            throw new NullPointerException("basePackage配置不正确，多个包扫描使用"+SPLITOR+"分隔");
        }
        return predicate;
    }

//  @Bean
//  public Docket createByVersion() {
//      String version = "xxxxx";
//      return new Docket(DocumentationType.SWAGGER_2)
//              .apiInfo(apiInfo())
//              .select()
//              .groupName(version)
//              .apis(input->{
//                  Optional<SwaggerApiGroup> clazzAnno = input.findControllerAnnotation(SwaggerApiGroup.class);
//                  Optional<SwaggerApiGroup> methodAnno = input.findAnnotation(SwaggerApiGroup.class);
//                  if(methodAnno.isPresent()){
//                      if(Arrays.asList(methodAnno.get().group()).contains(version)){
//                          return true;
//                      }
//                  }else if(clazzAnno.isPresent()){
//                      if(Arrays.asList(clazzAnno.get().group()).contains(version)){
//                          return true;
//                      }
//                  }
//                  return false;
//              })
//              .paths(PathSelectors.any())
//              .build();
//  }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("新房案场端API")
                .version("1.0.0")
                .description("新房案场端API接口")
                .build();
    }

}
