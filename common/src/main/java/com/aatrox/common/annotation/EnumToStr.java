package com.aatrox.common.annotation;

import java.lang.annotation.*;

/**
 * @author aatrox
 * @desc 枚举类型转String
 * @date 2020/7/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface EnumToStr {
    /**
     * 枚举对象描述属性字段
     * @return
     */
    String descField() default "desc";
}
