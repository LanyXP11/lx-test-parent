package com.lx.common.constant;

import com.lx.common.enums.ErrorCodeEnum;
import lombok.Data;

/**
 * Created by chenjiang on 2019/7/2
 */
@Data
public final class LxError {
    private int code = 9999;

    private String message;

    public LxError(final String message) {
        this(-1, message);
    }

    public LxError(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public LxError(ErrorCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    public LxError(final String code, final String message) {
        this.code = Integer.valueOf(code);
        this.message = message;
    }

}
