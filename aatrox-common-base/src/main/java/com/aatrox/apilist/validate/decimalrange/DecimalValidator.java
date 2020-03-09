package com.aatrox.apilist.validate.decimalrange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class DecimalValidator implements ConstraintValidator<DecimalRange, BigDecimal> {
    private BigDecimal min;
    private BigDecimal max;
    private int scale;
    private boolean needCheck;

    public DecimalValidator() {
    }

    @Override
    public void initialize(DecimalRange constraintAnnotation) {
        this.min = BigDecimal.valueOf(constraintAnnotation.min());
        this.max = BigDecimal.valueOf(constraintAnnotation.max());
        this.scale = constraintAnnotation.scale();
        this.needCheck = constraintAnnotation.needCheck();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (this.needCheck) {
            if (value == null) {
                return false;
            }

            int valueScale = value.scale();
            if (valueScale > this.scale) {
                return false;
            }

            if (value.subtract(this.min).doubleValue() < 0.0D || value.subtract(this.max).doubleValue() > 0.0D) {
                return false;
            }
        }

        return true;
    }
}

