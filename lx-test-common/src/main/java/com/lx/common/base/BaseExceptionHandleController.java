package com.lx.common.base;

import com.lx.common.constant.LxError;
import com.lx.common.constant.LxResponse;
import com.lx.common.enums.ErrorCodeEnum;
import com.lx.common.exception.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chenjiang on 2019/7/2
 */
@SuppressWarnings("all")
@ControllerAdvice
@Slf4j
public final class BaseExceptionHandleController {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Object ExceptionHandle(Exception exception) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }
        return new LxResponse().setError(new LxError(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Object handleException(HttpRequestMethodNotSupportedException exception) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage());
        }
        return new LxResponse().setError(new LxError(ErrorCodeEnum.SYSTEM_METHOD_ERROR.getCode(), ErrorCodeEnum.SYSTEM_METHOD_ERROR.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public Object handleException(HttpMediaTypeNotSupportedException exception) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage());
        }
        return new LxResponse().setError(new LxError(ErrorCodeEnum.SYSTEM_CONTENT_TYPE_ERROR.getCode(), ErrorCodeEnum.SYSTEM_CONTENT_TYPE_ERROR.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Object handleException(MissingServletRequestParameterException exception) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage());
        }
        return new LxResponse().setError(new LxError(ErrorCodeEnum.SYSTEM_PARAMETER_NOTFOUND.getCode(), ErrorCodeEnum.SYSTEM_PARAMETER_NOTFOUND.getMessage()));
    }

    @ExceptionHandler(value = MessageException.class)
    @ResponseBody
    public Object customizeException(MessageException messageexception) {
        if (log.isErrorEnabled()) {
            log.error(messageexception.getMessage());
        }
        return new LxResponse().setError(new LxError(messageexception.getCode(), messageexception.getMessage()));
    }
}
