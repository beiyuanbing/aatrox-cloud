package com.aatrox.generator.util;

/**
 * @author aatrox
 * @desc
 * @date 2019/8/29
 */
public class CommonStringUtil {
    /**
     * 将字符串首字母转大写
     * @param str
     * @return
     */
    public static String captureName(String str) {
        str=str.toLowerCase();
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}
