package com.wsq.AssistantQ.handle;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @author CYann
 * @date 2018-02-26 21:24
 */
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e){
        if(e instanceof MyException){
            MyException myException = (MyException) e;
            return Result.error(myException.getCode() , myException.getMessage());
        }
        else {
            System.out.println(e);
            return Result.error(ResultEnum.ERROR_UNKONW.getCode(),ResultEnum.ERROR_UNKONW.getMsg());
        }
    }

}
