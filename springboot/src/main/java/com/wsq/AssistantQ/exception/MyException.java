package com.wsq.AssistantQ.exception;

import com.wsq.AssistantQ.enums.ResultEnum;

/**
 * @author CYann
 * @date 2018-02-26 21:20
 */
public class MyException extends RuntimeException {
    private int code;
    public MyException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code =resultEnum.getCode();
    }
    public int getCode(){
        return code;
    }
    public void setCode(){
        this.code =code;
    }
}
