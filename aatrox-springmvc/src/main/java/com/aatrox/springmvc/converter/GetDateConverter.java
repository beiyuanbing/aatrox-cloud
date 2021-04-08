package com.aatrox.springmvc.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @program: aatrox-cloud
 * @description: 同一时间序列化工具
 * @author: aatrox
 * @create: 2021-04-08 14:57
 **/
@Component
public class GetDateConverter implements Converter<String, Date> {
    static Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");

    public static boolean isNumeric(String str) {
        return pattern.matcher(str).matches();
    }

    public static boolean isTimestamp(String str) {
        return str != null && str.length() == 13 && isNumeric(str);
    }

    @Override
    public Date convert(String source) {
        String value = source.trim();
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        if (isTimestamp(value)) {
            return new Date(Long.valueOf(value));
        }
        for (DateFormatEnum dateFormatEnum : DateFormatEnum.values()) {
            if (value.matches(dateFormatEnum.regex)) {
                return parseDate(value, dateFormatEnum.pattern);
            }
        }
        throw new IllegalArgumentException("Invalid String value '" + source + "'");
    }

//    @Test
//    public void test() {
//        Date convert = new GetDateConverter().convert("2021-03-24T11:10:38.177Z");
//        Date convert1 = new GetDateConverter().convert("2021-03-25T02:26:31.000+0000");
//        Date convert2 = new GetDateConverter().convert("2017-02-12T12:42:38.068Z");
//        System.out.println();
//    }


    /**
     * 格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public Date parseDate(String dateStr, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    enum DateFormatEnum {
        NO1("^\\d{4}-\\d{1,2}-\\d{1,2}T{1}\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}[+|-|Z].*$", "yyyy-MM-dd'T'HH:mm:ss.SSSX", "Feign默认/Postman默认"),
        NO2("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$", "yyyy-MM-dd HH:mm:ss", "标准默认"),
        NO3("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd", "常见默认"),
        NO4("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd", "常见默认"),
        NO5("^\\d{4}-\\d{1,2}$", "yyyy-MM", "常见默认"),
        NO6("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$", "yyyy-MM-dd HH:mm", "常见默认"),
        ;

        String regex;
        String pattern;
        String remark;

        DateFormatEnum(String regex, String pattern, String remark) {
            this.regex = regex;
            this.pattern = pattern;
            this.remark = remark;
        }

        public String getRegex() {
            return regex;
        }

        public void setRegex(String regex) {
            this.regex = regex;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }
    }


}
