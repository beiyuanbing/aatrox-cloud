package com.aatrox.common.utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author aatrox
 * @desc beanutils可用于初始化一些bean集合等
 * @date 2019-08-16
 */
public class BeanUtils {
    /**
     * 直接获取初始化后的对象
     *
     * @param source
     * @param substituteObjectClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(T source, Class<T> substituteObjectClass) {
        if (null == source) {
            if (substituteObjectClass == String.class) {
                return null;
            }
            return instance(substituteObjectClass);
        }
        return source;
    }

    /**
     * 初始化
     *
     * @param source
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T instance(Class<T> source) {

        if (source == List.class) {
            return (T) Collections.emptyList();
        }
        if (source == Map.class) {
            return (T) Collections.emptyMap();
        }

        try {
            //jdk 9版本和以下可以使用注释了的方法
            //return source.newInstance();
            return source.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
