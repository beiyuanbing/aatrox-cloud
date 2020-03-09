package com.aatrox.web.base.filter;


import com.aatrox.ContextHolder;
import com.aatrox.web.base.session.HttpServletRequestWrapper;
import com.aatrox.web.base.session.SessionService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author aatrox
 * @desc redis的session的过滤器
 * @date 2020/3/2
 */
public class RedisSessionFilter extends HttpServlet implements Filter {

    private static final long serialVersionUID = -365105405910803550L;
    private static final String UN_NEED_SID = "no_sid";
    private static String sessionId = "sid";
    private static String cookieDomain = "";
    private static String cookiePath = "/";
    private static String sessionIdHeaderKey = "";
    private static boolean hasSessionIdHeaderKey;
    private static String sessionSourceHeaderKey = "";
    private static boolean hasSessionSourceHeaderKey;
    private static List<String> passPathPrefix;
    private static List<String> passPathPostfix;
    private static Map<String, Integer> sessionSourceMap = new HashMap();
    public static final String REQUEST_SESSION_TO_CACHE_IMMEDIATELY = "REQUEST_SESSION_TO_CACHE_IMMEDIATELY";

    public static void addSessionSource(String sourceKey, Integer expireTime) {
        sessionSourceMap.put(sourceKey, expireTime);
    }

    public static String getSessionId() {
        return sessionId;
    }



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    private void makeSessionExpireTime(HttpServletRequest request) {
        Integer expireTime = (Integer)sessionSourceMap.get(request.getHeader(sessionSourceHeaderKey));
        if (expireTime != null) {
            ContextHolder.setContextData(SessionService.SessionExpireKey.class, expireTime);
        } else {
            ContextHolder.removeContextData(SessionService.SessionExpireKey.class);
        }

    }

    private String getIdInCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            Cookie[] var4 = cookies;
            int var5 = cookies.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Cookie sCookie = var4[var6];
                if (sCookie.getName().equals(key)) {
                    return sCookie.getValue();
                }
            }
        }

        return null;
    }

    private static void setSidInCookie(HttpServletResponse response, String sid) {
        Cookie sidCookies = new Cookie(sessionId, sid);
        sidCookies.setMaxAge(-1);
        if (StringUtils.isNotEmpty(cookieDomain)) {
            sidCookies.setDomain(cookieDomain);
        }

        sidCookies.setPath(cookiePath);
        sidCookies.setHttpOnly(true);
        response.addCookie(sidCookies);
    }

    private boolean checkPath(String servletPath) {
        Iterator var2;
        String pu;
        if (passPathPrefix != null) {
            var2 = passPathPrefix.iterator();

            while(var2.hasNext()) {
                pu = (String)var2.next();
                if (servletPath.startsWith(pu)) {
                    return true;
                }
            }
        }

        if (passPathPostfix != null) {
            var2 = passPathPostfix.iterator();

            while(var2.hasNext()) {
                pu = (String)var2.next();
                if (servletPath.endsWith(pu)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void refreshSidInCookie(HttpServletRequest request, HttpServletResponse response) {
        String sid = UUID.randomUUID().toString();
        setSidInCookie(response, sid);
        request.setAttribute(sessionId, sid);
        ((HttpServletRequestWrapper)request).refreshSid(sid);
    }

    public static void setSessionToCacheImmediately(HttpServletRequest request) {
        request.setAttribute("REQUEST_SESSION_TO_CACHE_IMMEDIATELY", true);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        String filterSessionId = filterConfig.getInitParameter("sessionId");
        if (StringUtils.isNotBlank(filterSessionId)) {
            sessionId = filterSessionId;
        }

        String filterDomain = filterConfig.getInitParameter("cookieDomain");
        if (StringUtils.isNotBlank(filterDomain)) {
            cookieDomain = filterDomain;
        }

        String filterCookiePath = filterConfig.getInitParameter("cookiePath");
        if (StringUtils.isNotBlank(filterCookiePath)) {
            cookiePath = filterCookiePath;
        }

        String filterSessionIdHeaderKey = filterConfig.getInitParameter("sessionIdHeaderKey");
        if (StringUtils.isNotBlank(filterSessionIdHeaderKey)) {
            sessionIdHeaderKey = filterSessionIdHeaderKey;
            hasSessionIdHeaderKey = true;
        }

        String filterSessionSourceHeaderKey = filterConfig.getInitParameter("sessionSourceHeaderKey");
        if (StringUtils.isNotBlank(filterSessionSourceHeaderKey)) {
            sessionSourceHeaderKey = filterSessionSourceHeaderKey;
            hasSessionSourceHeaderKey = true;
        }

        String passPathPrefixConfig = filterConfig.getInitParameter("passPathPrefix");
        int var10;
        if (StringUtils.isNotBlank(passPathPrefixConfig)) {
            passPathPrefix = new ArrayList();
            String[] var8 = passPathPrefixConfig.split(",");
            int var9 = var8.length;

            for(var10 = 0; var10 < var9; ++var10) {
                String pua = var8[var10];
                if (StringUtils.isNotBlank(pua)) {
                    passPathPrefix.add(pua.trim());
                }
            }
        }

        String passPathPostfixConfig = filterConfig.getInitParameter("passPathPostfix");
        if (StringUtils.isNotBlank(passPathPostfixConfig)) {
            passPathPostfix = new ArrayList();
            String[] var14 = passPathPostfixConfig.split(",");
            var10 = var14.length;

            for(int var15 = 0; var15 < var10; ++var15) {
                String pua = var14[var15];
                if (StringUtils.isNotBlank(pua)) {
                    passPathPostfix.add(pua.trim());
                }
            }
        }

    }

    public static void removeSidInCookie(HttpServletRequest request, HttpServletResponse response) {
        setSidInCookie(response, (String)null);
        SessionService.getInstance().removeSession((String)request.getAttribute(sessionId));
    }

    public static String getSessionIdHeaderKey() {
        return sessionIdHeaderKey;
    }

    public static String getSessionSourceHeaderKey() {
        return sessionSourceHeaderKey;
    }
}
