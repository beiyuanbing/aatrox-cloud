package com.aatrox.component.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
    private static final String format_yyyy_MM_ddHH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    private static final String format_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    private static final String format_yyyyMMdd = "yyyyMMdd";
    private static final String format_yyyy_MM_dd = "yyyy-MM-dd";
    private static final String format_ddMMMyy = "ddMMMyy";
    private static final String format_freedom = "freedom";
    private static ThreadLocal<Map<String, SimpleDateFormat>> threadLocal = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        protected Map<String, SimpleDateFormat> initialValue() {
            Map<String, SimpleDateFormat> dateFormatMap = new HashMap();
            SimpleDateFormat sdf_yyyy_MM_ddHH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat sdfyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf_ddMMMyy = new SimpleDateFormat("ddMMMyy", Locale.US);
            SimpleDateFormat sdf_freedom = new SimpleDateFormat();
            dateFormatMap.put("yyyy-MM-dd HH:mm:ss", sdf_yyyy_MM_ddHH_mm_ss);
            dateFormatMap.put("yyyyMMddHHmmss", sdf_yyyyMMddHHmmss);
            dateFormatMap.put("yyyyMMdd", sdfyyyyMMdd);
            dateFormatMap.put("yyyy-MM-dd", sdf_yyyy_MM_dd);
            dateFormatMap.put("ddMMMyy", sdf_ddMMMyy);
            dateFormatMap.put("freedom", sdf_freedom);
            return dateFormatMap;
        }
    };

    public DateUtils() {
    }

    public static Date getZeroOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.set(14, 999);
        return cal.getTime();
    }

    public static int maxDayInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(5);
    }

    public static int getTodayLeftSeconds() {
        Date now = new Date();
        return (int) secondBetween(now, getZeroOfDay(addDay(now, 1)));
    }

    public static long getTodayLeftMillis() {
        Date now = new Date();
        return millisBetween(now, getZeroOfDay(addDay(now, 1)));
    }

    public static int getLeftSeconds(int day) {
        if (day <= 1) {
            return 0;
        } else {
            Date now = new Date();
            return (int) secondBetween(now, getZeroOfDay(addDay(now, day)));
        }
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(5, 1);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date addYear(Date date, int year) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(1, year);
        date = c.getTime();
        return date;
    }

    public static Date addMonth(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(2, month);
        date = c.getTime();
        return date;
    }

    public static Date addDay(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(5, day);
        date = c.getTime();
        return date;
    }

    public static Date addHour(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(11, hours);
        return cal.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(12, minute);
        return cal.getTime();
    }

    public static int daysBetween(Date startDate, Date endDate) {
        return (int) ((endDate.getTime() - startDate.getTime()) / 86400000L);
    }

    public static long secondBetween(Date startTime, Date endTime) {
        Long sTime = startTime.getTime();
        Long eTime = endTime.getTime();
        return (long) Long.valueOf((eTime - sTime) / 1000L).intValue();
    }

    public static long millisBetween(Date startTime, Date endTime) {
        Long sTime = startTime.getTime();
        Long eTime = endTime.getTime();
        return eTime - sTime;
    }

    public static Date getIntervalDayToMonthEnd(Date date, int interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, maxDayInMonth(date) - interval);
        return calendar.getTime();
    }

    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(5);
    }

    public static String getDayOfWeekNum(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(7);
        switch (dayOfWeek) {
            case 1:
                return "7";
            case 2:
                return "1";
            case 3:
                return "2";
            case 4:
                return "3";
            case 5:
                return "4";
            case 6:
                return "5";
            case 7:
                return "6";
            default:
                return "7";
        }
    }

    public static int parseMonth(String monthStr) {
        if ("JAN".equalsIgnoreCase(monthStr)) {
            return 0;
        } else if ("FEB".equalsIgnoreCase(monthStr)) {
            return 1;
        } else if ("MAR".equalsIgnoreCase(monthStr)) {
            return 2;
        } else if ("APR".equalsIgnoreCase(monthStr)) {
            return 3;
        } else if ("MAY".equalsIgnoreCase(monthStr)) {
            return 4;
        } else if ("JUN".equalsIgnoreCase(monthStr)) {
            return 5;
        } else if ("JUL".equalsIgnoreCase(monthStr)) {
            return 6;
        } else if ("AUG".equalsIgnoreCase(monthStr)) {
            return 7;
        } else if ("SEP".equalsIgnoreCase(monthStr)) {
            return 8;
        } else if ("OCT".equalsIgnoreCase(monthStr)) {
            return 9;
        } else if ("NOV".equalsIgnoreCase(monthStr)) {
            return 10;
        } else {
            return "DEC".equalsIgnoreCase(monthStr) ? 11 : -1;
        }
    }

    public static String formatDateToString(Date date, String format) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) ((Map) threadLocal.get()).get("freedom");
        simpleDateFormat.applyPattern(format);
        return date == null ? null : simpleDateFormat.format(date);
    }

    public static Date formatStringToDate(String date, String format) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) ((Map) threadLocal.get()).get("freedom");
        simpleDateFormat.applyPattern(format);
        if (date == null) {
            return null;
        } else {
            try {
                return simpleDateFormat.parse(date);
            } catch (ParseException var4) {
                return null;
            }
        }
    }

    public static String formatDDMMMYYToyyyyMMdd(String date) throws ParseException {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyyMMdd")).format(((SimpleDateFormat) ((Map) threadLocal.get()).get("ddMMMyy")).parse(date));
    }

    public static String formatDDMMMYYToyyyy_MM_dd(String date) throws ParseException {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyy-MM-dd")).format(((SimpleDateFormat) ((Map) threadLocal.get()).get("ddMMMyy")).parse(date));
    }

    public static Date formatDDMMMYYToDate(String date) throws ParseException {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("ddMMMyy")).parse(date);
    }

    public static String formatDateToDDMMMYY(Date date) {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("ddMMMyy")).format(date).toUpperCase();
    }

    public static String formatyyyyMMddToDDMMMYY(String date) throws ParseException {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("ddMMMyy")).format(((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyyMMdd")).parse(date));
    }

    public static String formatyyyyMMddToyyyy_MM_dd(String date) throws ParseException {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyy-MM-dd")).format(((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyyMMdd")).parse(date));
    }

    public static Date formatyyyyMMddToDate(String date) throws ParseException {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyyMMdd")).parse(date);
    }

    public static String formatDateToyyyyMMdd(Date date) {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyyMMdd")).format(date);
    }

    public static String formatyyyy_MM_ddToDDMMMYY(String date) throws ParseException {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("ddMMMyy")).format(((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyy-MM-dd")).parse(date));
    }

    public static String formatyyyy_MM_ddToyyyyMMdd(String date) throws ParseException {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyyMMdd")).format(((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyy-MM-dd")).parse(date));
    }

    public static Date formatyyyy_MM_ddToDate(String date) throws ParseException {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyy-MM-dd")).parse(date);
    }

    public static String formatDateToyyyy_MM_dd(Date date) {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyy-MM-dd")).format(date);
    }

    public static String formatDateToyyyyMMddHHmmss(Date date) {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyyMMddHHmmss")).format(date);
    }

    public static String formatDateToyyyy_MM_dd_HH_mm_ss(Date date) {
        return ((SimpleDateFormat) ((Map) threadLocal.get()).get("yyyy-MM-dd HH:mm:ss")).format(date);
    }

    public static boolean isNull(Date date) {
        return date == null;
    }
}

