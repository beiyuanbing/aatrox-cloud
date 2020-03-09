package com.aatrox.apilist.validate.phone;

import com.aatrox.common.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    private static final Pattern p = Pattern.compile("^[1][0-9]{10}$");

    public PhoneValidator() {
    }

    @Override
    public void initialize(Phone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isEmpty(value) ? false : checkPhone(value);
    }

    public static boolean checkPhone(String value) {
        value = value.replaceAll("-", "").replaceAll(" ", "");
        Matcher m = p.matcher(value);
        return m.find();
    }
}
