package com.aatrox.common.utils;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 操作String的常用工具类
 */
public class CommonStringUtil {
    public static final String EMPTY = "";

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * 带逗号分隔的数字转换为NUMBER类型
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Number stringToNumber(String str) throws ParseException {
        if (str == null || str.equals("")) {
            return null;
        }
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        dfs.setMonetaryDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###,###,###.##", dfs);
        return df.parse(str);
    }

    /**
     * 从文件名中获取后缀名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                filename = filename.substring(dot + 1);
                return filename == null ? filename : filename.toLowerCase();
            }
        }
        return filename == null ? filename : filename.toLowerCase();
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * br去除方法
     *
     * @param text
     * @return
     */
    public static String processBrNbsp(String text) {
        if (text == null) {
            return null;
        }
        text = text.replaceAll("\r\n", "<br>");
        text = text.replaceAll("\n", "<br>");
        text = text.replaceAll("\r", "<br>");
        text = text.replaceAll(" ", "&nbsp;");
        return text;
    }

    /**
     * 标签去除去除方法
     *
     * @return
     */
    public static String clearHtmlElement(String target) {
        if (target == null) {
            return target;
        }
        target = target.trim();
        target = target.replace("<", "&lt;");
        target = target.replace(">", "&gt;");
        target = target.replace("\n", "<br>");
        target = target.replace("\"", "&quot;");
        target = target.replace("\'", "&#039;");
        return target.replaceAll("</?[^>]+>", "").replaceAll("'|exec |;|--|\\/\\*|\\*\\/", "");
    }

    /**
     * 返回string值
     *
     * @param object
     * @return
     */
    public static String valueOf(Object object) {
        return valueOf(object, null);
    }

    /**
     * 返回string值
     *
     * @param object
     * @return
     */
    public static String valueOf(Object object, String defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        return String.valueOf(object);
    }

    /**
     * 判断给子字符串是否为空
     *
     * @param cs
     * @return
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 删除末尾给定字符串
     *
     * @return
     */
    public static String removeEnd(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }
        return str;
    }

    /**
     * 将字符串中的全角字符全部转成半角
     *
     * @param target 目标字符串
     * @return 转换后的字符串
     */
    public static final String fullHalfChange(String target) throws UnsupportedEncodingException {
        StringBuffer outStrBuf = new StringBuffer("");
        String Tstr = "";
        byte[] b = null;
        for (int i = 0; i < target.length(); i++) {
            Tstr = target.substring(i, i + 1);
            // 全角空格转换成半角空格
            if (Tstr.equals("　")) {
                outStrBuf.append(" ");
                continue;
            }
            b = Tstr.getBytes("unicode");
            // 得到 unicode 字节数据
            if (b[2] == -1) {
                // 表示全角？
                b[3] = (byte) (b[3] + 32);
                b[2] = 0;
                outStrBuf.append(new String(b, "unicode"));
            } else {
                outStrBuf.append(Tstr);
            }
        } // end for.
        return outStrBuf.toString();
    }

    /**
     * 去除a标签  和 img 标签
     *
     * @param target
     * @return
     */
    public static final String cleanAIMG(String target) {
        if (!StringUtils.isEmpty(target)) {
            target = target.replaceAll("<a.*?href[^>]*>", "");
            target = target.replaceAll("</a>", "");
            target = target.replaceAll("<img[^>]*/>", " ");
            target = target.replaceAll("<script[^>]*/>", " ");
            target = target.replaceAll("script", "");
            target = target.replaceAll("www", "4w");
            target = target.replaceAll("http://", "tp://");
            target = target.replaceAll("<script[^>]*/>", " ");
            target = target.replaceAll("script", "");
        }
        return target;
    }

    public static String solrMetacharacter(String value) {
        StringBuffer sb = new StringBuffer();
        String regex = "[ ,\\.+\\-&|!()（）{}\\[\\]^\"~*?:(\\)]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            matcher.appendReplacement(sb, "\\\\" + matcher.group());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 过滤html相关标签
     *
     * @param htmlStr
     * @return
     */
    public static String getHtmlString(String htmlStr) {
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;

        Pattern p_html1;
        Matcher m_html1;

        try {
            String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
            String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串
    }

    /**
     * 字符串转int类型
     *
     * @param str
     * @return
     */
    public static int stringToInt(String str) {
        int value = 0;
        if (isEmpty(str)) {
            return value;
        }
        try {
            value = Integer.parseInt(str);
        } catch (Exception e) {
        }
        return value;
    }

    public static String transactSQLInjection(String str) {
        if (!StringUtils.isEmpty(str)) {
            return str.replaceAll("([';])+|(--)+", "");
        }
        return str;
    }

    /**
     * 判断包含数字
     **/
    public static boolean containDigit(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断包含字母
     **/
    public static boolean containAlpha(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 空值处理
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static String ifNull(String value, String defaultValue) {
        if (value == null || "".equals(value) || "null".equalsIgnoreCase(value) || "undefined".equals(value) || value.length() == 0) {
            return defaultValue;
        } else {
            return value;
        }
    }

    /**
     * 空值处理
     *
     * @param value
     * @return
     */
    public static String ifNull(String value) {
        return ifNull(value, "");
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isNull(String value) {
        return (value == null || value.trim().length() == 0);
    }

    /**
     * 判断一个字符是否是数字或者 "." , "-"
     */
    public static boolean isDigital(char c) {
        return (c >= '0' && c <= '9') || c == '.' || c == '-';
    }

    /**
     * 判断一个字符串是否全部是数字或者 "." , "-"组成
     */
    public static boolean isDigitalString(String value) {
        if (isNull(value)) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!isDigital(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符是否是数字0-9
     */
    public static boolean isInt(char c) {
        return (c >= '0' && c <= '9');
    }

    /**
     * 判断一个字符串是否全部是数字0-9组成
     */
    public static boolean isIntString(String value) {
        if (isNull(value)) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!isInt(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符是否是字母
     */
    public static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * 判断一个字符是否是数字或者字符
     */
    public static boolean isAlphaOrDigital(char c) {
        return isDigital(c) || isAlpha(c);
    }

    /**
     * 判断一个字符串是否全部是数字或者字符组成
     */
    public static boolean isAlphaOrDigital(String value) {
        if (isNull(value)) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!isAlphaOrDigital(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符串是否全部是字符组成
     */
    public static boolean isAlphaString(String value) {
        if (isNull(value)) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!isAlpha(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static final char[] ChineseNum = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
    private static final char[] ChineseUnit = {'里', '分', '角', '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟'};

    /**
     * 返回关于钱的中文式大写数字,支仅持到亿
     */
    public static String arabNumToChineseRMB(int moneyNum) {
        StringBuffer res = new StringBuffer();
        int i = 3;
        if (moneyNum == 0) {
            return "零元";
        }
        while (moneyNum > 0) {
            res.insert(0, ChineseUnit[i++]);
            res.insert(0, ChineseNum[moneyNum % 10]);
            moneyNum /= 10;
        }
        return res.toString().replaceAll("零[拾佰仟万]", "零")
                .replaceAll("零+亿", "亿").replaceAll("零+万", "万")
                .replaceAll("零+元", "元").replaceAll("零+", "零");

    }

    /**
     * 整数位支持12位,到仟亿
     * 支持到小数点后3位,如果大于3位,那么会四舍五入到3位
     *
     * @throws Exception
     */
    public static String arabNumToChineseRMB(double moneyNum) throws Exception {
        String res = "";
        String money = String.format("%.3f", moneyNum);
        int i = 0;
        if (moneyNum == 0.0) {
            return "零元";
        }
        String inte = money.split("\\.")[0];
        int deci = Integer.parseInt(money.split("\\.")[1].substring(0, 3));
        while (deci > 0) {
            res = ChineseUnit[i++] + res;
            res = ChineseNum[deci % 10] + res;
            deci /= 10;
        }
        res = res.replaceAll("零[里分角]", "零");
        if (i < 3) {
            res = "零" + res;
        }
        res = res.replaceAll("零+", "零");
        if (res.endsWith("零")) {
            res = res.substring(0, res.length() - 1);
        }
        int intMoney = Integer.parseInt(inte);
        return arabNumToChineseRMB(intMoney) + res;
    }

    /**
     * 根据身份编号获取性别
     *
     * @param idCard 身份编号
     * @return 性别(1 - 男 ， 2 - 女 ， N - 未知)
     */
    public static String getGenderByIdCard(String idCard) {
        String card = verifyIdCard(idCard);
        String sGender = "未知";
        String sCardNum = card.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            sGender = "1";//男
        } else {
            sGender = "2";//女
        }
        return sGender;
    }

    /**
     * 从身份证中提取出生日期
     *
     * @return yyyy-MM-dd,不满足15或18位,返回空
     */
    public static String getBirthdayFromIdNo(String idNo) {
        if (idNo.length() == 15) {
            return new StringBuffer(
                    Integer.parseInt(idNo.substring(6, 8)) + 1900).append("-")
                    .append(idNo.substring(8, 10)).append("-").append(
                            idNo.substring(10, 12)).toString();
        } else if (idNo.length() == 18) {
            return new StringBuffer(idNo.substring(6, 10)).append("-").append(
                    idNo.substring(10, 12)).append("-").append(
                    idNo.substring(12, 14)).toString();
        } else {
            return "";
        }
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCard 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static String getBirthByIdCard(String idCard) {
        String card = verifyIdCard(idCard);
        return card.substring(6, 14);
    }

    private static String verifyIdCard(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            throw new RuntimeException("身份证不得为空");
        }
        String _idCard = idCard.trim();
        if (_idCard.length() != 18) {
            throw new RuntimeException("仅支持18位身份证");
        }
        return _idCard;
    }

    /**
     * 字符串转大写方法
     *
     * @param str                  待转化字符串
     * @param camelCaseToUnderLine 是否将驼峰转化成下划线
     * @return
     */
    public static String toUpperCase(String str, boolean camelCaseToUnderLine) {
        if (camelCaseToUnderLine) {
            boolean firstChar = true;
            StringBuffer sb = new StringBuffer();
            char[] chars = str.toCharArray();
            for (char c : chars) {
                if (Character.isUpperCase(c) && !firstChar) {
                    sb.append("_");
                }
                sb.append(c);
                firstChar = false;
            }
            return sb.toString().toUpperCase();
        } else {
            return str.toUpperCase();
        }
    }

    /**
     * sql like 字符串过滤
     * <p>
     * like '%'||#{keyword}||'%' escape '\'
     */
    public static String sqlLikeStrFilter(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        return str.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
    }


    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (!StringUtils.isEmpty(keyword)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    /**
     * 隐藏字符
     *
     * @param str        原始字符串
     * @param showLength 前后显示字符长度
     * @return
     */
    public static String blurStr(String str, int showLength) {
        if (StringUtils.isEmpty(str)) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            int length = str.length();
            if (length <= showLength) {
                sb.append("*");
            } else if (length <= showLength * 2) {
                sb.append(str, 0, showLength).append("*");
            } else {
                sb.append(str, 0, showLength).append("*").append(str.substring(length - showLength));
            }
            return sb.toString();
        }
    }
}
