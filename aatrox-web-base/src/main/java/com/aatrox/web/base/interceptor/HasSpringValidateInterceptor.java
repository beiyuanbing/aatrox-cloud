package com.aatrox.web.base.interceptor;

import com.aatrox.ContextHolder;
import com.aatrox.web.base.validation.HasSpringValidateContextKey;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public class HasSpringValidateInterceptor extends ZBaseInterceptorAdapter {
    public HasSpringValidateInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        } else {
            if (this.hasValidate((HandlerMethod)handler)) {
                ContextHolder.setContextData(HasSpringValidateContextKey.class, true);
            } else {
                ContextHolder.setContextData(HasSpringValidateContextKey.class, false);
            }

            return super.preHandle(request, response, handler);
        }
    }

    private boolean hasValidate(HandlerMethod handler) {
        Annotation[][] annos = handler.getMethod().getParameterAnnotations();
        if (annos != null) {
            Annotation[][] var3 = annos;
            int var4 = annos.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Annotation[] annotation1 = var3[var5];
                if (annotation1 != null) {
                    Annotation[] var7 = annotation1;
                    int var8 = annotation1.length;

                    for(int var9 = 0; var9 < var8; ++var9) {
                        Annotation anno = var7[var9];
                        if (anno instanceof Validated) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}

