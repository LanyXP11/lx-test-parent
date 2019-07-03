package com.lx.common.enums;

public enum ErrorCodeEnum {

    SYSTEM_ERROR_REMIND(9998, "为保障您的账户安全，请勿频繁操作"),
    SYSTEM_ERROR(9999, "系统异常，请稍后重试"),
    SYSTEM_PARAMETER_ERROR(7000, "参数异常"),
    SYSTEM_PARAMETER_NOTFOUND(7100, "参数丢失"),
    SYSTEM_PARAMETER_NOT(7220, "参数不能为空"),
    SYSTEM_METHOD_ERROR(7221, "请求方法不正确"),
    SYSTEM_CONTENT_TYPE_ERROR(7222, "content-type不正确"),
    SYSTEM_PARAMETER_NOTRULE(7200, "参数非法");


    private int code;
    private String message;


    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
