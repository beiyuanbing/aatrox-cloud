package com.aatrox.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCardUtil {
    public static final String VALIDITY = "该身份证有效！";
    public static final String LACKDIGITS = "身份证号码长度应该为15位或18位。";
    public static final String LASTOFNUMBER = "身份证15位号码都应为数字;18位号码除最后一位外，都应为数字。";
    public static final String INVALIDBIRTH = "身份证出生日期无效。";
    public static final String INVALIDSCOPE = "身份证生日不在有效范围。";
    public static final String INVALIDMONTH = "身份证月份无效";
    public static final String INVALIDDAY = "身份证日期无效";
    public static final String CODINGERROR = "身份证地区编码错误。";
    public static final String INVALIDCALIBRATION = "身份证校验码无效，不是合法的身份证号码";
    private static String[] VERIFY_CODE = new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
    private static String[] POWER = new String[]{"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
    private static Map<String, String> areaCode;

    public IdCardUtil() {
    }

    public static boolean checkCardValidate(String IDStr) {
        return "该身份证有效！".equals(idCardValidate(IDStr));
    }

    public static String idCardValidate(String IDStr) {
        String tipInfo = "该身份证有效！";
        String Ai = "";
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            tipInfo = "身份证号码长度应该为15位或18位。";
            return tipInfo;
        } else {
            if (IDStr.length() == 18) {
                Ai = IDStr.substring(0, 17);
            } else if (IDStr.length() == 15) {
                String var10000 = IDStr.substring(0, 6);
                Ai = var10000 + "19" + IDStr.substring(6, 15);
            }

            if (!isNumeric(Ai)) {
                tipInfo = "身份证15位号码都应为数字;18位号码除最后一位外，都应为数字。";
                return tipInfo;
            } else {
                String strYear = Ai.substring(6, 10);
                String strMonth = Ai.substring(10, 12);
                String strDay = Ai.substring(12, 14);
                if (!isDate(strYear + "-" + strMonth + "-" + strDay)) {
                    tipInfo = "身份证出生日期无效。";
                    return tipInfo;
                } else {
                    GregorianCalendar gc = new GregorianCalendar();
                    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        if (gc.get(1) - Integer.parseInt(strYear) > 150 || gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime() < 0L) {
                            tipInfo = "身份证生日不在有效范围。";
                            return tipInfo;
                        }
                    } catch (NumberFormatException var9) {
                        var9.printStackTrace();
                    } catch (ParseException var10) {
                        var10.printStackTrace();
                    }

                    if (Integer.parseInt(strMonth) <= 12 && Integer.parseInt(strMonth) != 0) {
                        if (Integer.parseInt(strDay) <= 31 && Integer.parseInt(strDay) != 0) {
                            if (areaCode.get(Ai.substring(0, 2)) == null) {
                                tipInfo = "身份证地区编码错误。";
                                return tipInfo;
                            } else if (!isVerifyCode(Ai, IDStr)) {
                                tipInfo = "身份证校验码无效，不是合法的身份证号码";
                                return tipInfo;
                            } else {
                                return tipInfo;
                            }
                        } else {
                            tipInfo = "身份证日期无效";
                            return tipInfo;
                        }
                    } else {
                        tipInfo = "身份证月份无效";
                        return tipInfo;
                    }
                }
            }
        }
    }

    private static boolean isVerifyCode(String Ai, String IDStr) {
        int sum = 0;

        int modValue;
        for (modValue = 0; modValue < 17; ++modValue) {
            sum += Integer.parseInt(String.valueOf(Ai.charAt(modValue))) * Integer.parseInt(POWER[modValue]);
        }

        modValue = sum % 11;
        String strVerifyCode = VERIFY_CODE[modValue];
        Ai = Ai + strVerifyCode;
        return IDStr.length() != 18 || Ai.equals(IDStr);
    }

    private static boolean isNumeric(String strnum) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(strnum);
        return isNum.matches();
    }

    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
        Matcher m = pattern.matcher(strDate);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(idCardValidate("330381198608090011"));
    }

    static {
        Map<String, String> map = new HashMap();
        map.put("11", "北京");
        map.put("12", "天津");
        map.put("13", "河北");
        map.put("14", "山西");
        map.put("15", "内蒙古");
        map.put("21", "辽宁");
        map.put("22", "吉林");
        map.put("23", "黑龙江");
        map.put("31", "上海");
        map.put("32", "江苏");
        map.put("33", "浙江");
        map.put("34", "安徽");
        map.put("35", "福建");
        map.put("36", "江西");
        map.put("37", "山东");
        map.put("41", "河南");
        map.put("42", "湖北");
        map.put("43", "湖南");
        map.put("44", "广东");
        map.put("45", "广西");
        map.put("46", "海南");
        map.put("50", "重庆");
        map.put("51", "四川");
        map.put("52", "贵州");
        map.put("53", "云南");
        map.put("54", "西藏");
        map.put("61", "陕西");
        map.put("62", "甘肃");
        map.put("63", "青海");
        map.put("64", "宁夏");
        map.put("65", "新疆");
        map.put("71", "台湾");
        map.put("81", "香港");
        map.put("82", "澳门");
        map.put("91", "国外");
        areaCode = map;
    }
}

