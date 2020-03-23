package com.aatrox.common.excel.base;

import java.lang.annotation.*;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sheet {

    String name() default "";

}