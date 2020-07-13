package com.aatrox.architecture.aspect;

import com.aatrox.architecture.annotation.ExceptionConvertPolicy;
import com.aatrox.architecture.annotation.ExceptionMessageConvert;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author aatrox
 * @desc
 * @date 2020/7/13
 */
@Aspect
@Slf4j
public class ExceptionConvertAspect {
    @Around(value="@annotation(com.aatrox.architecture.annotation.ExceptionMessageConvert)")
    public Object convert(ProceedingJoinPoint pjp){
        Object result=null;
        try {
            result=pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method method = signature.getMethod();
            ExceptionMessageConvert exceptionAnnotation = method.getAnnotation(ExceptionMessageConvert.class);
            Class policyClaz = exceptionAnnotation.policy();
            String errorMsg=exceptionAnnotation.errMsg();
            ExceptionConvertPolicy policy=null;
            try {
                policy=(ExceptionConvertPolicy)policyClaz.getConstructor(String.class).newInstance(errorMsg);
            } catch (Exception e) {
                throw  new RuntimeException(throwable);
            }
            policy.convert(throwable);
        }finally {
            return result;
        }
    }
}
