package com.dk.etl.exception.custom;

/**
 * 自定义异常
 * @Author:Harlan
 * @Date:2019/12/21
 * @Version: v.0.0
 */
public class CustomDefinedException  extends RuntimeException {

    public CustomDefinedException(String message) {
        super(message);
    }
}
