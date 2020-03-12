package com.dk.etl.extra.exception;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:28
 * @Version:1.0
 */

public class InitException extends Exception {
    private int errorCode = 101;
    private String errorDesc;

    public InitException(String errorDesc) {
        super(errorDesc);
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return this.errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
