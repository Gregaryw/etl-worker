package com.dk.etl.exception;

/**
 * @Author: HarlanW
 * @Date: 2019/4/4 0004
 * @Version: 1.0
 */
public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable throwable) {
        super(throwable);
    }

    public BaseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
