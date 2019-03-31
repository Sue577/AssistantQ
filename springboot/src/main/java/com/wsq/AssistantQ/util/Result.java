package com.wsq.AssistantQ.util;

import com.wsq.AssistantQ.enums.ResultEnum;

/**
 * @author WSQ
 * @date 2018-02-26 21:26
 */

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Result success(Object object){
        Result result =new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static Result success(){
        return success(null);
    }
    public static Result error(int code , String msg){
        Result result =new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    public static Result loginerror(Object object){
        Result result =new Result();
        result.setCode(ResultEnum.ERROR_102.getCode());
        result.setMsg(ResultEnum.ERROR_102.getMsg());
        result.setData(object);
        return result;
    }
}
