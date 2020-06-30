package com.aatrox.common.utils;

import java.math.BigDecimal;

/**
 * @author aatrox
 * @desc
 * @date 2020/6/30
 */
public class MathUtil {
    public static double doubleMul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
}
