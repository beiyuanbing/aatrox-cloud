package com.aatrox.web.base.util;

import com.aatrox.common.utils.CommonStringUtil;
import com.aatrox.common.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-15
 */
public class CommonWebUtil {
    public static final String COOKIE_ID = "cookieId";
    private static Logger log = LoggerFactory.getLogger(CommonWebUtil.class);

    public static int getInt(String name, int defaultValue) {
        String resultStr = getRequestParameter(name);
        if (resultStr != null) {
            try {
                return Integer.parseInt(resultStr);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static BigDecimal getBigDecimal(String name, BigDecimal defaultValue) {
        String resultStr = getRequestParameter(name);
        if (resultStr != null) {
            try {
                return BigDecimal.valueOf(Double.parseDouble(resultStr));
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }


    public static String getString(String name, String defaultValue) {
        return getString(getRequest(), name, defaultValue);
    }


    public static String getString(HttpServletRequest request, String name) {
        return getString(request, name, null);
    }


    public static String getString(HttpServletRequest request, String name, String defaultValue) {
        String resultStr = request.getParameter(name);
        if (resultStr == null || "".equals(resultStr) || "null".equals(resultStr) || "undefined".equals(resultStr)) {
            return defaultValue;
        }
        return resultStr;
    }


    public static Cookie getCookie(String cookieName) {
        if (CommonStringUtil.isEmpty(cookieName)) {
            return null;
        }
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            int len = cookies.length;
            for (int i = 0; i < len; i++) {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }


    public static String[] getIpAddr(HttpServletRequest request) {
        return getIpAddrStr(request).split(",");
    }


    public static String getIpAddrStr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isNotEmpty(ip)) {
            ip = ip.replaceAll(" ", "");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static Boolean getBoolean(String name, boolean defaultValue) {
        String resultStr = getRequestParameter(name);
        if (resultStr == null || "".equals(resultStr.trim()) || "null".equals(resultStr.toLowerCase().trim()) || "undefined".equals(resultStr.toLowerCase().trim())) {
            return Boolean.valueOf(defaultValue);
        }
        if (resultStr.trim().equals("1") || resultStr.trim().equalsIgnoreCase("true")) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }


    public static double getDouble(String name, double defaultValue) {
        String resultStr = getRequestParameter(name);
        if (resultStr != null) {
            try {
                return Double.parseDouble(resultStr);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static Date getDate(String date, String format) {
        String dateStr = getString(date, null);
        if (dateStr == null || "".equals(dateStr)) {
            return null;
        }
        return DateUtil.converStringToDate(dateStr, format);
    }


    private static String getRequestParameter(String name) {
        HttpServletRequest request = getRequest();
        return request.getParameter(name);
    }


    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }


    public static ServletContext getServletContext() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }


    public static Map<String, String> getParamterMap() {
        return getParameterMap(getRequest());
    }


    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues != null && paramValues.length > 0) {
                String paramValue = paramValues[0];
                map.put(paramName, paramValue);
            }
        }
        return map;
    }


    public static String getHostContext() {
        return getHostContext(getRequest());
    }


    public static String getHostContext(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String uri = request.getRequestURI();

        return url.delete(url.length() - uri.length(), url.length())
                .append(request.getContextPath())
                .append("/").toString();
    }

    public static String getCookieId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if (StringUtils.equals(cookie.getName(), "cookieId")) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
