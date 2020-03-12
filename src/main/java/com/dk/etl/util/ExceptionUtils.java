package com.dk.etl.util;


import com.dk.etl.exception.BaseException;

/**
 * 构建异常系统工具类
 * @Author: HarlanW
 * @Date: 2019/4/4 0004
 * @Version: 1.0
 */
public class ExceptionUtils {
    private ExceptionUtils() {
    }

    /**
     * 返回一个新的异常，统一构建，方便统一处理
     *
     * @param msg 消息
     * @param t   异常信息
     * @return 返回异常
     */
    public static BaseException build(String msg, Throwable t, Object... params) {
        return new BaseException(StringUtils.format(msg, params), t);
    }

    /**
     * 重载的方法
     *
     * @param msg 消息
     * @return 返回异常
     */
    public static BaseException build(String msg, Object... params) {
        return new BaseException(StringUtils.format(msg, params));
    }

    /**
     * 重载的方法
     *
     * @param t 异常
     * @return 返回异常
     */
    public static BaseException build(Throwable t) {
        return new BaseException(t);
    }

}
