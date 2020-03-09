package com.aatrox.apilist.complex;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComplexFeign {
    String fallbackMethod() default "";
}