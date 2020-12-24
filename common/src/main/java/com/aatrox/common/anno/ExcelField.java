package com.aatrox.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author aatrox
 * @desc excel相关的注解使用
 * @date 2020/12/17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelField {
    /**
     * excel导出是否忽略
     */
    boolean ignore() default false;
    /**
     * 枚举对象使用方法
     */
    String enumDesc() default "desc";
}

