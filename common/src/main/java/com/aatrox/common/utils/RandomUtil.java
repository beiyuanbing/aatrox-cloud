package com.aatrox.common.utils;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
public class RandomUtil {
    public RandomUtil() {
    }

    public static String makeRandomNumberString(int count) {
        return String.valueOf(makeRandomNumber(count));
    }

    public static int makeRandomNumber(int count) {
        if (count <= 0) {
            throw new RuntimeException("must greater than 0");
        } else {
            int pow = (int)Math.pow(10.0D, (double)(count - 1));
            return (int)((Math.random() * 9.0D + 1.0D) * (double)pow);
        }
    }

    public static void main(String[] args) {
        Integer a=null;
        System.out.println(String.valueOf(a));
    }
}