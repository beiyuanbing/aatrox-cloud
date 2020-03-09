package com.aatrox.common.utils;

import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @desc 时间的工具类
 * @auth beiyuanbing
 * @date 2019-05-06 10:47
 **/
public class DateUtil {
    public static final String formate_string_yyyyMMdd = "yyyyMMdd";

    public static final String formate_yyyyMMddhhmmss = "yyyyMMddHHmmss";

    public static final String formate_string_hhmmss = "HHmmss";
    public static final String DEFAULT_FORMATE = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMATE = "yyyy-MM-dd";
    public static final String FMT_SOLR ="yyyy-MM-dd'T'HH:mm:ss'Z'" ;

    public static String getDateString(Date d, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(d==null?new Date():d);
    }

    public static String getDateString(Date d) {
        if(d==null){
            return null;
        }
        return getDateString(d,DEFAULT_FORMATE);
    }

    public static Date converStringToDate(String dateStr, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static int secondsLeftToday() {
        long secondsLeftToday = 86400 - DateUtils.getFragmentInSeconds(Calendar.getInstance(), Calendar.DATE);
        return Integer.valueOf(secondsLeftToday + "");
    }

    public static Date getStartDate(Date date){
        date=date==null?new Date():date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    public static Date getEndDate(Date date){
        date=date==null?new Date():date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        return calendar.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(12, minute);
        return cal.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getDateString(getStartDate(new Date())));
        System.out.println(getDateString(getEndDate(new Date())));
    }
}
