package com.aatrox.apilist.validate.size;

import com.aatrox.common.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SizableValidator implements ConstraintValidator<Sizable, String> {
    private int min;
    private int max;
    private boolean checkEmpty;

    public SizableValidator() {
    }

    @Override
    public void initialize(Sizable constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.checkEmpty = constraintAnnotation.checkEmpty();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return !this.checkEmpty;
        } else {
            int length = value.length();
            return this.min <= length && this.max >= length;
        }
    }
}

