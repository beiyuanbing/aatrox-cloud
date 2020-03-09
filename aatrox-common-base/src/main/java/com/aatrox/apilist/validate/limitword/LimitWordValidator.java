package com.aatrox.apilist.validate.limitword;

import com.aatrox.common.utils.CommonStringUtil;
import com.aatrox.common.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LimitWordValidator implements ConstraintValidator<LimitWord, String> {
    private int[] length;
    private LimitWordType type;

    public LimitWordValidator() {
    }

    @Override
    public void initialize(LimitWord constraintAnnotation) {
        this.length = constraintAnnotation.length();
        this.type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return false;
        } else {
            int[] var3 = this.length;
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                int l = var3[var5];
                if (l == value.length()) {
                    switch (this.type) {
                        case ALPHA:
                            return CommonStringUtil.isAlphaString(value);
                        case NUMERIC:
                            return CommonStringUtil.isNumeric(value);
                        case NUMERIC_ALPHA:
                            return CommonStringUtil.isAlphaOrDigital(value);
                    }
                }
            }

            return false;
        }
    }
}

