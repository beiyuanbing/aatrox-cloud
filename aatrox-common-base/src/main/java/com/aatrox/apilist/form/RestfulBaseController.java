package com.aatrox.apilist.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RestfulBaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public RestfulBaseController() {
    }

    @ExceptionHandler({RequestResultException.class})
    public RequestResult requestResultException(RequestResultException e) {
        return e.getCodeEnum() != null ? new RequestResult(false, (Object) null, e.getMessage(), e.getCodeEnum().getStatus()) : new RequestResult(false, (Object) null, e.getMessage(), e.getCode());
    }

    @ExceptionHandler({Exception.class})
    public RequestResult exception(Exception e) {
        this.logger.error("api 错误信息:" + e.getMessage(), e);
        String msg = e.getMessage();
        String code = null;
        if (StringUtils.isEmpty(msg)) {
            msg = "空指针异常，请检查";
        } else if (msg.contains("DuplicateKeyException")) {
            msg = "存在重复的参数与已有数据冲突，请修改";
            code = JsonCommonCodeEnum.E0020.getStatus();
        }

        RequestResult requestResult = new RequestResult(false, (Object) null, msg, code);
        return requestResult;
    }

    @ExceptionHandler({BindException.class})
    public RequestResult validException1(BindException e) {
        RequestResult requestResult = new RequestResult(false, (Object) null, ((ObjectError) e.getAllErrors().get(0)).getDefaultMessage(), JsonCommonCodeEnum.E0002.getStatus());
        return requestResult;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public RequestResult validException2(MethodArgumentNotValidException e) {
        RequestResult requestResult = new RequestResult(false, (Object) null, ((ObjectError) e.getBindingResult().getAllErrors().get(0)).getDefaultMessage(), JsonCommonCodeEnum.E0002.getStatus());
        return requestResult;
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}

