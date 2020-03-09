package com.aatrox.web.base.filter;

import com.aatrox.web.base.interceptor.CrossOriginInterceptor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public class CrossOriginFilter implements Filter {
    private boolean open;

    public CrossOriginFilter(boolean open) {
        this.open = open;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CrossOriginInterceptor.makeCross(this.open, (HttpServletRequest)servletRequest, (HttpServletResponse)servletResponse);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}