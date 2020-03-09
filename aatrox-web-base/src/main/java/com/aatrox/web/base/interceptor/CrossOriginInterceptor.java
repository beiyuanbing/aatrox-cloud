package com.aatrox.web.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public class CrossOriginInterceptor extends ZBaseInterceptorAdapter {
    private boolean open;

    public CrossOriginInterceptor(boolean open) {
        this.open = open;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return makeCross(this.open, request, response);
    }

    public static boolean makeCross(boolean open, HttpServletRequest request, HttpServletResponse response) {
        if (open) {
            String origin = request.getHeader("Origin");
            if (origin != null) {
                response.setHeader("Access-Control-Allow-Origin", origin);
                response.setHeader("Access-Control-Allow-Credentials", "true");
            } else {
                response.setHeader("Access-Control-Allow-Origin", "*");
            }

            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        }

        return true;
    }
}

