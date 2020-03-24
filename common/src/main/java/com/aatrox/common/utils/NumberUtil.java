package com.aatrox.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author aatrox
 * @desc
 * @date 2019/9/4
 */
public class NumberUtil {

    public static Integer toInteger(String str){
       return toInteger(str,false);
    }

    public static Integer toInteger(String str,boolean zero) {
        if(StringUtils.isEmpty(str)){
            return zero?0:null;
        }
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String trim = m.replaceAll("").trim();
        if(StringUtils.isEmpty(trim)){
            return zero?0:null;
        }else{
            return Integer.valueOf(trim);
        }
    }

    /***
     * 删除小数后面的0
     * @param str
     * @return
     */
    public static String removeLastZero(String str) {
        if (!str.contains(".")) {
            return str;
        }
        //把结尾0替换成""
        str = str.replaceAll("0+?$", "");
        //结尾的小数点替换成空
        return str.replaceAll("[.]$", "");
    }

    public static void main(String[] args) {
        System.out.println(removeLastZero("130.00"));
    }

}
