package com.aatrox.architecture.annotation;

import org.apache.xmlbeans.impl.piccolo.util.DuplicateKeyException;

/**
 * @author aatrox
 * @desc
 * @date 2020/7/13
 */
public class DefaultExceptionConverter implements ExceptionConvertPolicy{
    private String message;

    public DefaultExceptionConverter(String message) {
        this.message = message;
    }

    @Override
    public void convert(Throwable throwable) {
        if(throwable.getCause() instanceof DuplicateKeyException){
            throw new RuntimeException(message);
        }
        throw new RuntimeException(throwable);

    }
}
