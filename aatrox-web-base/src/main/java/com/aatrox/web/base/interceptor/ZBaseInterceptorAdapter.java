package com.aatrox.web.base.interceptor;

import com.aatrox.apilist.form.JsonReturnCodeEnum;
import com.aatrox.web.base.util.CommonWebUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public class ZBaseInterceptorAdapter extends HandlerInterceptorAdapter {
    public ZBaseInterceptorAdapter() {
    }

    public String[] getIpAddr(HttpServletRequest request) {
        return CommonWebUtil.getIpAddr(request);
    }

    public String getString(String name) {
        return this.getString(name, (String)null);
    }

    public String getString(String name, String defaultValue) {
        return CommonWebUtil.getString(name, defaultValue);
    }

    public Boolean getBoolean(String name, boolean defaultValue) {
        return CommonWebUtil.getBoolean(name, defaultValue);
    }

    public void printJsonMsg(HttpServletResponse response, ZBaseInterceptorAdapter.ResponseBody body) {
        try {
            response.setContentType("application/json");
            response.setStatus(body.getStatus());
            response.setCharacterEncoding("UTF-8");
            Map<String, Object> result = new HashMap();
            result.put("status", body.getCode());
            result.put("message", body.getMessage());
            result.put("result", body.getLoginUrl());
            String resultMsg = JSON.toJSONString(result);
            response.getWriter().write(resultMsg);
        } catch (IOException var5) {
        }

    }

    protected void doDealAlert(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + message + "');window.history.go(-1)</script>");
        out.flush();
        out.close();
    }

    protected <T extends Annotation> T getAnnotation(Object handler, Class<T> clazz, boolean methodFirst) {
        if (!(handler instanceof HandlerMethod)) {
            return null;
        } else {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            T annotationMethod = null;
            if (methodFirst) {
                annotationMethod = handlerMethod.getMethod().getAnnotation(clazz);
            }

            if (annotationMethod != null) {
                return annotationMethod;
            } else {
                T annotationClazz = handlerMethod.getMethod().getDeclaringClass().getAnnotation(clazz);
                return annotationClazz;
            }
        }
    }

    protected <T extends Annotation> T getAnnotation(Object handler, Class<T> clazz) {
        return this.getAnnotation(handler, clazz, true);
    }

    public static class ResponseBody {
        private int status = 401;
        private String code;
        private String message;
        private String loginUrl;

        public ResponseBody() {
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setCode(JsonReturnCodeEnum code) {
            this.code = code.getStatus();
            this.message = code.getMessage();
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getLoginUrl() {
            return this.loginUrl;
        }

        public void setLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
        }
    }
}
