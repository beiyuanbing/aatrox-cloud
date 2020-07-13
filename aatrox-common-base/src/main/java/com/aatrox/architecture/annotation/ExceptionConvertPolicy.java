package com.aatrox.architecture.annotation;

/**
 * @author aatrox
 * @desc
 * @date 2020/7/13
 */
@FunctionalInterface
public interface ExceptionConvertPolicy {
    /**
     * 用于转换的异常
     * @param throwable
     */
    void convert(Throwable throwable);
}
