package com.aatrox.apilist.validate.idcard;

import com.aatrox.common.utils.IdCardUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdCardValidator implements ConstraintValidator<IdCard, String> {
    public IdCardValidator() {
    }

    @Override
    public void initialize(IdCard constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isEmpty(value) ? false : IdCardUtil.checkCardValidate(value);
    }
}
