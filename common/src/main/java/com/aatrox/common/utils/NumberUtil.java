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

}
