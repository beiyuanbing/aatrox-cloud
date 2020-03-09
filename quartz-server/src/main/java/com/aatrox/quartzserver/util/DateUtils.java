package com.aatrox.quartzserver.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @desc 时间的工具类
 * @auth beiyuanbing
 * @date 2019-05-06 10:47
 **/
public class DateUtils {
    public static final String formate_string_yyyyMMdd = "yyyyMMdd";

    public static final String formate_string_yyyyMMddhhmmss = "yyyyMMddHHmmss";

    public static final String formate_string_hhmmss = "HHmmss";

    public static final String default_format = "yyyy-MM-dd HH:mm:ss";

    public static String getDateString(Date d, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    public static String getDateString(Date d) {
        DateFormat df = new SimpleDateFormat(default_format);
        return df.format(new Date());
    }

    public static Date converStringToDate(String dateStr, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}
