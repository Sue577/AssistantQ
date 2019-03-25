package com.wsq.AssistantQ.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CYann
 * @date 2018-02-08 21:33
 */
@RestController
public class helloController {
    @RequestMapping(value = "/hello" , method = RequestMethod.GET)
    public String hello(){
        return "Hello World!";
    }
}
