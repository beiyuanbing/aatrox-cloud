package com.aatrox.springmvc.converter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 处理 jackson 序列化/反序列 json 字符串
 * 1、对于 service 服务，序列化/反序列都是使用 jackson
 * 2、对于 web 服务，反序列化使用的是 jackson，序列化 json 使用的是 fastjson (手动调用 api)
 *
 * @program: aatrox-cloud
 * @description:
 * @author: aatrox
 * @create: 2021-04-08 15:02
 **/
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // List 中已经默认初始化了 MappingJackson2HttpMessageConverter 参考代码
        // org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport.addDefaultHttpMessageConverters
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = converters.stream()
                .filter(c -> c instanceof MappingJackson2HttpMessageConverter)
                .map(c -> (MappingJackson2HttpMessageConverter) c)
                .findFirst()
                .orElse(new MappingJackson2HttpMessageConverter());
//FastJsonHttpMessageConverter()

        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // json中多余的属性不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //枚举多了的报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        // 枚举类型，空值，或者不在枚举定义范围内，返回 null
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);


        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);  // 将 date 序列化为 timestamp

        // 生成json时，将所有Long转换成String
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        //时间格式化
        simpleModule.addDeserializer(Date.class, new FastJsonDateDeserializer(Date.class));
        objectMapper.registerModule(simpleModule);
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        GetDateFormatAnnotationFormatterFactory annoFormater = new GetDateFormatAnnotationFormatterFactory();
        registry.addFormatterForFieldAnnotation(annoFormater);
    }


}
