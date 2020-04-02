package com.aatrox.common.utils;


import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @desc
 * @auth beiyuanbing
 * @date 2019-05-07 09:25
 **/
public class StringUtils extends org.springframework.util.StringUtils {
    /**
     * Join all the elements of a string array into a single
     * String.
     * <p>
     * If the given array empty an empty string
     * will be returned.  Null elements of the array are allowed
     * and will be treated like empty Strings.
     *
     * @param array Array to be joined into a string.
     * @return Concatenation of all the elements of the given array.
     * @throws NullPointerException if array is null.
     * @since ostermillerutils 1.05.00
     */
    public static String join(String[] array) {
        return join(array, "");
    }

    public static String join(List<String> list,String delimiter){
        if(CollectionUtils.isEmpty(list)){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if(i!=0){
                stringBuilder.append(delimiter);
            }
            stringBuilder.append(list.get(i));
        }
        return stringBuilder.toString();
    }

    public static String join(List<String> list){
        return join(list, "");
    }

    /**
     * 超过border的字符串部门用胜率号代替
     *
     * @param str
     * @param border
     * @return
     */
    public static String getEllipsisStr(String str, Integer border) {
        if (org.springframework.util.StringUtils.isEmpty(str) || str.length() <= border) {
            return str;
        }
        return str.substring(0, border) + "...";
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() < 1 || "null".equalsIgnoreCase(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String... obj) {
        if (obj == null || obj.length == 0) {
            return true;
        }
        for (String s : obj) {
            if (isNotEmpty(s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(String... obj) {
        if (obj == null || obj.length == 0) {
            return false;
        }
        for (String anObj : obj) {
            if (org.springframework.util.StringUtils.isEmpty(anObj)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Join all the elements of a string array into a single
     * String.
     * <p>
     * If the given array empty an empty string
     * will be returned.  Null elements of the array are allowed
     * and will be treated like empty Strings.
     *
     * @param delimiter String to place between array elements.
     * @return Concatenation of all the elements of the given array with the the delimiter in between.
     * @throws NullPointerException if array or delimiter is null.
     * @since ostermillerutils 1.05.00
     */
/*    public static String join(List<String> list, String delimiter) {
        if(list==null||list.size()==0){
            return "";
        }
        String[] array =new String[list.size()];
        list.toArray(array);
        return join(array,delimiter);

    }*/
    public static String join(String[] array, String delimiter) {
        // Cache the length of the delimiter
        // has the side effect of throwing a NullPointerException if
        // the delimiter is null.
        int delimiterLength = delimiter.length();

        // Nothing in the array return empty string
        // has the side effect of throwing a NullPointerException if
        // the array is null.
        if (array.length == 0) {
            return "";
        }

        // Only one thing in the array, return it.
        if (array.length == 1) {
            if (array[0] == null) {
                return "";
            }
            return array[0];
        }

        // Make a pass through and determine the size
        // of the resulting string.
        int length = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                length += array[i].length();
            }
            if (i < array.length - 1) {
                length += delimiterLength;
            }
        }

        // Make a second pass through and concatenate everything
        // into a string buffer.
        StringBuffer result = new StringBuffer(length);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                result.append(array[i]);
            }
            if (i < array.length - 1) {
                result.append(delimiter);
            }
        }

        return result.toString();
    }

    /**
     * 获取原号码的显示结果
     *
     * @param phoneNum 手机原号码
     * @return
     */
    public static String getViewPhoneNum(String phoneNum, String phoneFormat, String phoneFormatHk) {
        String viewPhoneNum = "";
        String firstFormatter, secondFormatter;
        if (isNotEmpty(phoneNum)) {
            if (phoneNum.startsWith("1")) {
                firstFormatter = phoneFormat;
                secondFormatter = phoneFormatHk;
            } else {
                firstFormatter = phoneFormatHk;
                secondFormatter = phoneFormat;
            }

            if (isNotEmpty(firstFormatter)) {
                viewPhoneNum = getViewPhoneNum(phoneNum, firstFormatter);
            }
            if (isEmpty(viewPhoneNum) && isNotEmpty(secondFormatter)) {
                viewPhoneNum = getViewPhoneNum(phoneNum, secondFormatter);
            }
            if (isEmpty(viewPhoneNum)) {
                viewPhoneNum = getPhoneFormat(phoneNum);
            }
        }
        return viewPhoneNum;
    }

    /**
     * 获取原号码的显示结果
     *
     * @param phoneNum   手机原号码
     * @param displayWay 手机显示方式
     * @return
     */
    public static String getViewPhoneNum(String phoneNum, String displayWay) {

        char[] displayChars = displayWay.toCharArray();
        char[] phoneNumChars = phoneNum.toCharArray();
        int i = 0;
        for (char displayChar : displayChars) {
            if (displayChar == '0') {
                if (i <= phoneNumChars.length - 1) {
                    phoneNumChars[i] = '*';
                }
            }
            i++;
        }
        return new String(phoneNumChars);
    }

    @SuppressWarnings("all")
    public static String getPhoneFormat(String str) {
        try {
            return getNumberStr(str).replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        } catch (Exception e) {
            return "";
        }
    }

    public static String getNumberStr(String str) {
        if (org.springframework.util.StringUtils.isEmpty(str)) {
            return str;
        }
        try {
            return str.replaceAll("[^0-9]", "");
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {


        List list2 = new ArrayList();
        list2.isEmpty();
        System.out.println(list2.size());
        System.out.println(list2.isEmpty());
    }
}
