package com.aatrox.springmvc.converter;


import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @program:
 * @description: spring接收对象时，Get请求，前端返回空字符，默认接收为null
 * @author: ch
 * @create: 2021-03-12 18:49
 **/
@Component
public class GetStringConverter implements Converter<String, String> {

    @Override
    public String convert(String source) {
        String value = source.trim();
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return value;
    }
}