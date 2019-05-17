package com.wsq.AssistantQ.aspect;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author WSQ
 * @date 2019/3/28 15:21
 */
@Aspect
@Component
public class LoginAspect {
    private final static Logger logger = LoggerFactory.getLogger(LoginAspect.class);
    @Pointcut("execution(public * com.wsq.AssistantQ.controller.*.*(..)) && !execution(public * com.wsq.AssistantQ.controller.LoginoutController.*(..))")
    public void point(){}

    //登录校验
    @Before("point()")
    public void doBefore() {
        // logger.info("before!!!!");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("ID");
        // String type = (String) session.getAttribute("TYPE");
        // System.out.println(type);
        if (id == null) {
            throw new MyException(ResultEnum.ERROR_100);
        }
    }

    @After("point()")
    public void doAfter() {
        //logger.info("after!!!!");
    }
}
