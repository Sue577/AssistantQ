package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.CurrentUserModel;
import com.wsq.AssistantQ.model.UserModel;
import com.wsq.AssistantQ.service.CurrentUserService;
import com.wsq.AssistantQ.service.UserService;
import com.wsq.AssistantQ.util.Result;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CYann
 * @date 2018-02-26 20:30
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CurrentUserService currentUserService;

    //用户信息展示
    @GetMapping(value = "/listuserinfor")
    public Result listUserInfor(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");
        CurrentUserModel item = currentUserService.findByObjectId(objectId);
        UserModel userinfor = userService.findByStuNumber(item.getStuNumber());
        Map result = new HashMap();
        result.put("stuName", userinfor.getStuName());
        result.put("stuNumber",userinfor.getStuNumber());
        result.put("currentPhone",userinfor.getCurrentPhone());
        result.put("currentEmail",userinfor.getCurrentEmail());
        result.put("stuMajor",userinfor.getStuMajor());
        result.put("stuEndYear",userinfor.getStuEndYear());
        return Result.success(result);
    }

    //查看用户信息展示
    @PostMapping(value = "/showuserinfor")
    public Result showUserInfor(@RequestBody UserModel userModel){
        UserModel userinfor = userService.findByStuNumber(userModel.getStuNumber());
        // UserModel userinfor = userService.findByStuNumber(stuNumber);
        Map result = new HashMap();
        result.put("stuName", userinfor.getStuName());
        result.put("stuNumber",userinfor.getStuNumber());
        result.put("currentPhone",userinfor.getCurrentPhone());
        result.put("currentEmail",userinfor.getCurrentEmail());
        result.put("stuMajor",userinfor.getStuMajor());
        result.put("stuEndYear",userinfor.getStuEndYear());
        return Result.success(result);
    }



    //用户更改基础信息-联系方式
    @PostMapping(value = "/changeuserinfor")
    public Result changeUserInfor(UserModel userModel){
        userService.updateInfor(userModel);
        return Result.success();
    }


}
