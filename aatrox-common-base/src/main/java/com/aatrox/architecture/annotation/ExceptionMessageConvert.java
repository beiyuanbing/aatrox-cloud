package com.aatrox.architecture.annotation;

import java.lang.annotation.*;

/**
 * @author aatrox
 * @desc 异常枚举类转换
 * @date 2020/7/13
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionMessageConvert {
    Class policy() default DefaultExceptionConverter.class;

    String errMsg();
}
