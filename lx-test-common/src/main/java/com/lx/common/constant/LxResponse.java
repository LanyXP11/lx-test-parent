package com.lx.common.constant;

/**
 * Created by chenjiang on 2019/7/2
 */
public final class LxResponse<T> {
    private static final long serialVersionUID = 1L;

    private T data;
    private LxError error;

    public LxError getError() {
        return error;
    }

    public LxResponse<T> setError(LxError error) {
        this.error = error;
        return this;
    }

    public boolean isSuccess() {

        if (this.error == null) {
            return true;
        } else {
            return false;
        }

    }

    public LxResponse<T> addError(LxError error) {
        this.error = error;
        return this;
    }

    public LxResponse<T> setError(String errorMsg) {
        this.error = new LxError(errorMsg);
        return this;
    }

    public T getData() {
        return data;
    }

    public LxResponse<T> setData(T data) {
        this.data = data;
        return this;
    }
}
