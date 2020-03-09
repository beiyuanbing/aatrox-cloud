package com.aatrox.web.base.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-15
 */
public class CookieUtil {
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static Cookie addCookie(HttpServletResponse resp, String cookieName, String cookieValue, int second) {
        return addCookie(resp, cookieName, cookieValue, null, null, second);
    }

    public static Cookie addCookie(HttpServletResponse resp, String cookieName, String cookieValue, String domain, int second) {
        return addCookie(resp, cookieName, cookieValue, domain, null, second);
    }

    public static Cookie addCookie(HttpServletResponse resp, String cookieName, String cookieValue, String domain, String path, int second) {
        Cookie ck = new Cookie(cookieName, cookieValue);
        ck.setMaxAge(second);
        if (StringUtils.isNotEmpty(domain)) {
            ck.setDomain(domain);
        }
        if (StringUtils.isNotEmpty(path)) {
            ck.setPath(path);
        }
        resp.addCookie(ck);
        return ck;
    }
}
