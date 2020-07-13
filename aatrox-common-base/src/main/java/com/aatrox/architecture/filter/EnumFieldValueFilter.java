package com.aatrox.architecture.filter;

import com.aatrox.architecture.annotation.EnumToStr;
import com.alibaba.fastjson.serializer.ValueFilter;

import java.lang.reflect.Field;

/**
 * @author aatrox
 * @desc 枚举对象使用的值替换为描述
 * @date 2020/7/13
 */
public class EnumFieldValueFilter implements ValueFilter {
    @Override
    public Object process(Object object, String name, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(name);
            EnumToStr annotation = field.getAnnotation(EnumToStr.class);
            if(annotation!=null && field.getType().isEnum()){
                Class<?> enumClass = field.getType();
                field=enumClass.getField(annotation.descField());
                field.setAccessible(true);
                return field.get(value);
            }else{
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
    }
}
