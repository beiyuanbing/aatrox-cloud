package com.aatrox.common.utils;

import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class MD5Util {
    private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    //盐，用于混交md5
    private static final String slat = "&%5123***&&%%$$#@";
    /**
     * 生成md5
     * @param
     * @return
     */
    public static String getMD5Simple(String str) {
        String base = str +"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public MD5Util() {
    }

    public static String getMD5(String source){
        if(StringUtils.isEmpty(source)){
            return null;
        }
        return getMD5(source.getBytes());
    }

    public static String getMD5(byte[] source) {
        String result = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte[] tmp = md.digest();
            char[] str = new char[32];
            int k = 0;

            for (int i = 0; i < 16; ++i) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            result = new String(str);
        } catch (Exception var8) {
        }

        return result;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(getMD5("123456"));
        System.out.println( DigestUtils.md5DigestAsHex("123456".getBytes()));
    }
}
