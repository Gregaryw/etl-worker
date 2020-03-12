package com.dk.etl.exception;

import com.dk.etl.exception.custom.CustomDefinedException;
import com.dk.etl.extra.exception.InitException;
import com.dk.etl.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:Harlan
 * @Date:2019/12/21
 * @Version: v.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalDefaultException {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result defaultExceptionHandler(HttpServletRequest req, Exception e){
        log.error("全局参数出现异常{}",e);
        return  Result.error(e.getMessage());
    }

    @ExceptionHandler(CustomDefinedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result customDefinedExceptionHandler(CustomDefinedException e){
        return Result.error();
    }

    @ExceptionHandler(InitException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result kettleInitException(InitException e){
        log.error("执行kettle任务失败》》{}",e.getMessage());
        return Result.error();
    }

    @ExceptionHandler(KettleException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result kettleException(KettleException e){
        log.error("执行kettle任务失败》》{}",e.getMessage());
        return Result.error();
    }

}
