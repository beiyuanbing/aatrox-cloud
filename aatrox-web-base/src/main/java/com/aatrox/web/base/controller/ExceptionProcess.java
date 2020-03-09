package com.aatrox.web.base.controller;

import com.aatrox.apilist.form.JsonCommonCodeEnum;
import com.aatrox.apilist.form.RequestResultException;
import com.aatrox.common.utils.StringUtils;
import com.aatrox.web.base.interceptor.RequestContextHolder;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.annotation.Resource;

/**
 * @author aatrox
 * @desc
 * @date 2019/9/2
 */
@ControllerAdvice
public class ExceptionProcess extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Resource
    private Environment env;

    public ExceptionProcess() {
    }

    @ResponseBody
    @ExceptionHandler({MultipartException.class})
    public String handleMultipart(Throwable t) {
        String errMsg = "系统限制最大上传尺寸为" + this.env.getProperty("spring.http.multipart.max-file-size");
        this.addAccessErrMsg(errMsg);
        return this.returnWithCustomMessage(errMsg, JsonCommonCodeEnum.E0020);
    }

//    @ResponseBody
//    @ExceptionHandler({InitRequestException.class})
//    public String initRequestException(InitRequestException e) {
//        this.log.error("方法签名错误：" + e.getMessage(), e);
//        this.addAccessErrMsg(e.getMessage());
//        return this.returnWrong(JsonCommonCodeEnum.E0005);
//    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public String exception(Exception e) {
        this.log.error("Controller 错误信息:" + e.getMessage(), e);
        this.addAccessErrMsg(e.getMessage());
        return this.returnWrong(JsonCommonCodeEnum.E0005);
    }

    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    public String runtimeException(RuntimeException e) {
        this.log.error("Controller 错误信息:" + e.getMessage(), e);
        String msg = e.getMessage();
        if (e instanceof HystrixRuntimeException && msg.contains("timed-out")) {
            return this.returnWrong(JsonCommonCodeEnum.E0018);
        } else {
            if (StringUtils.isNotEmpty(msg) && ( msg.contains("com.") || msg.contains("org."))) {
                msg = "系统处理异常，请稍后再试";
            }

            this.addAccessErrMsg(e.getMessage());
            return this.returnWithCustomMessage(msg, JsonCommonCodeEnum.E0005);
        }
    }

    @ResponseBody
    @ExceptionHandler({RequestResultException.class})
    public String requestResultException(RequestResultException e) {
        this.log.error("remote 错误信息:" + e.getMessage(), e);
        if (e.getCode() != null) {
            return this.returnWithCustomMessage(e.getMessage(), e.getCode());
        } else {
            String msg = e.getMessage();
            if (StringUtils.isNotEmpty(msg) && ( msg.contains("springframework."))) {
                msg = "系统处理异常，请稍后再试";
            }

            String code = e.getCode() != null ? e.getCode() : JsonCommonCodeEnum.E0001.getStatus();
            this.addAccessErrMsg(e.getMessage());
            return this.returnWithCustomMessage(msg, code);
        }
    }

    @ResponseBody
    @ExceptionHandler({BindException.class})
    public String validException1(BindException e) {
        String errMsg = ((ObjectError)e.getAllErrors().get(0)).getDefaultMessage();
        this.addAccessErrMsg(errMsg);
        return this.returnWithCustomMessage(errMsg, JsonCommonCodeEnum.E0002);
    }

    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public String validException2(MethodArgumentNotValidException e) {
        String errMsg = ((ObjectError)e.getBindingResult().getAllErrors().get(0)).getDefaultMessage();
        this.addAccessErrMsg(errMsg);
        return this.returnWithCustomMessage(errMsg, JsonCommonCodeEnum.E0002);
    }

    private void addAccessErrMsg(String errMsg) {
        RequestContextHolder.setErrMsg(errMsg);
    }
}
