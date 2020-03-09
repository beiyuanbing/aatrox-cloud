package com.aatrox.model;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelPropertyDef {
    String value();

    boolean required();
}
