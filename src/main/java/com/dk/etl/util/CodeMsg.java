package com.dk.etl.util;

public class CodeMsg {

    private int code;
    private String msg;

    //通用的错误码
    public static CodeMsg CLASS_NO_FOUND_ERROR = new CodeMsg(500401, "发生错误，找不到类：%s");
    public static CodeMsg METHOD_NO_FOUND_ERROR = new CodeMsg(500402, "发生错误，找不到方法：%s");
    public static CodeMsg METHOD_NO_ACCESS_ERROR = new CodeMsg(500403, "安全权限异常，无权操作方法：%s");
    public static CodeMsg METHOD_RUN_ERROR = new CodeMsg(500404, "调用方法'%s'时发生异常%s");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500405, "服务端异常，%s");
    public static CodeMsg PARAM_ERROR = new CodeMsg(500406, "参数校验异常：%s"); //用占位符传入一个参数


    /*
     * 私有化构造函数并且使得所有set 方法失效，接口健壮性
     */
    private CodeMsg(int code, String msg ) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public CodeMsg fillArgs(Object...objects){
        int code = this.code;
        String message = String.format(this.msg,objects);
        return new CodeMsg(code,message);
    }

}