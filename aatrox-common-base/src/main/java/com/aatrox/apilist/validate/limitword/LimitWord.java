package com.aatrox.apilist.validate.limitword;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {LimitWordValidator.class}
)
public @interface LimitWord {
    String message() default "限制输入校验不通过";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] length();

    LimitWordType type();

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        LimitWord[] value();
    }
}

