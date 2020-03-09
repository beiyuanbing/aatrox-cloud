package com.aatrox.web.base.config;

import com.aatrox.web.base.filter.CrossOriginFilter;
import com.aatrox.web.base.filter.EncodeFilter;
import com.aatrox.web.base.interceptor.HasSpringValidateInterceptor;
import com.aatrox.web.base.util.UserAgentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
@ComponentScan({"com.aatrox.web.base"})
@Import({RedisConfiguration.class})
public class WebBaseConfig implements WebMvcConfigurer {
    @Autowired
    private Environment env;

    public WebBaseConfig() {
    }

/*    @Bean
    public ValidationPoolBean validationPoolBean() {
        ValidationPoolBean validationPoolBean = new ValidationPoolBean();
        validationPoolBean.setMaxIdle(10);
        validationPoolBean.setMaxTotal(50);
        validationPoolBean.setUsePool(false);
        return validationPoolBean;
    }*/


    /*
    *
    * 跨域的web配置
    * */
    @Bean
    public FilterRegistrationBean crossOriginFilterBean() {
        boolean open = (Boolean)this.env.getProperty("switch.crossOriginInterceptor.isOpen", Boolean.class, true);
        FilterRegistrationBean registration = new FilterRegistrationBean(new CrossOriginFilter(open), new ServletRegistrationBean[0]);
        registration.addUrlPatterns(new String[]{"/*"});
        return registration;
    }

    /*
    * encode的过滤器配置
    * */
    @Bean
    public FilterRegistrationBean encodeFilterBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new EncodeFilter(), new ServletRegistrationBean[0]);
        registration.addUrlPatterns(new String[]{"/*"});
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HasSpringValidateInterceptor()).addPathPatterns(new String[]{"/**"});
    }

    @PostConstruct
    public void init() {
        UserAgentUtil.init(this.env);
    }
}