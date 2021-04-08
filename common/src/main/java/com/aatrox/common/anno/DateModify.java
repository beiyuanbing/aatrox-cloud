package com.aatrox.common.anno;

import java.lang.annotation.*;

/**
 * @program: aatrox-cloud
 * @description:
 * @author: aatrox
 * @create: 2021-04-08 15:00
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DateModify {

    /**
     * 操作
     */
    DateTimeOperation value();

    String pattern() default "";
}
