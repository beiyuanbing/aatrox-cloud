package com.aatrox.apilist.validate.decimalrange;

import org.jvnet.staxex.StAxSOAPBody;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {DecimalValidator.class}
)
public @interface DecimalRange {
    String message() default "数据范围不通过";

    Class<?>[] groups() default {};

    Class<? extends StAxSOAPBody.Payload>[] payload() default {};

    double min() default 0.0D;

    double max() default 9.999999999E9D;

    int scale() default 1;

    boolean needCheck() default true;

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        DecimalRange[] value();
    }
}

