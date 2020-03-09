package com.aatrox.web.base.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public class EncodeFilter implements Filter {
    public EncodeFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
    }

    @Override
    public void destroy() {
    }
}
