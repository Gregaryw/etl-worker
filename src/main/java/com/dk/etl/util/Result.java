package com.dk.etl.util;


import com.dk.etl.enums.ResultEnum;

import java.util.Map;

public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    /**
     * 成功时调用
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    public static <T> Result<T> success(){
        return new Result<T>(ResultEnum.SUCCESS);
    }

    /**
     * 失败时调用
     * @param codeMsg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }

    public static <T> Result<T> error(String msg){
        return new Result<T>(1,msg);
    }

    public static <T> Result<T> error(){
        return new Result<T>(ResultEnum.ERROR);
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result build(int code, String msg, Map<String, ?> data){
        return ResponseParamBuilderHandler.build(code,msg,data);
    }

    /**

     * 采用静态内部类实现单例模式创建对象
     */

    private static class ResponseParamBuilderHandler{
        private  static Result<Object> responseParam = new Result<>();
        public static Result build(int code, String msg, Map<String,?> data){
            responseParam.setCode(code);
            responseParam.setMsg(msg);
            responseParam.setData(data);
            return  responseParam;
        }
    }

    public Result() {
    }

    /**
     * 成功时调用的函数
     * @param data
     */
    private Result(T data){
        this.code = 0;    //默认0是成功
        this.msg = "success";
        this.data = data;
    }

    /**
     * 失败的构造函数
     * @param codeMsg
     */
    private Result(CodeMsg codeMsg) {
        if(codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }

    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}