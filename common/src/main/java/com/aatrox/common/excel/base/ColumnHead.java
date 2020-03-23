package com.aatrox.common.excel.base;

import java.lang.annotation.*;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColumnHead {

    /**
     * 标识属性对应的 excel 表头列的名称
     *
     * @return
     */
    String title();

    /**
     * 定义execl表头的列宽
     * @return
     */
    int width() default 10;

    /**
     * 定义execl列的顺序
     * @return
     */
    int order() default Integer.MAX_VALUE;

}