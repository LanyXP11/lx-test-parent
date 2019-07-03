package com.lx.common.exception;

import com.lx.common.enums.ErrorCodeEnum;

/**
 * Created by chenjiang on 2019/7/2
 */
@SuppressWarnings("all")
public class MessageException extends RuntimeException {

    private Integer code = ErrorCodeEnum.SYSTEM_ERROR.getCode();

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

    protected MessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getCode() {
        return code;
    }
}
