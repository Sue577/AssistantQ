package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.UserModel;
import com.wsq.AssistantQ.service.UserService;
import com.wsq.AssistantQ.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WSQ
 * @date 2019/3/28 10:36
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //管理员新增用户 返回新增后的用户列表
    @PostMapping(value = "/admin/add")
    public Result add(@RequestBody UserModel userModel) {
        userService.add(userModel);

        //返回所有用户列表信息
        return Result.success(findAllUser().getData());
    }

    //管理员根据用户编号删除用户 返回删除后的用户列表
    @PostMapping(value = "/admin/deleteByObjectId")
    public Result deleteByObjectId(@RequestBody UserModel userModel) {
        userService.deleteByObjectId(userModel);

        //返回所有用户列表信息
        return Result.success(findAllUser().getData());
    }

    //管理员根据用户ID删除用户 返回删除后的用户列表
    @PostMapping(value = "/admin/deleteByUserId")
    public Result deleteByUserId(@RequestBody UserModel userModel) {
        userService.deleteByUserId(userModel);

        //返回所有用户列表信息
        return Result.success(findAllUser().getData());
    }
    
    //管理员根据用户编号重置用户密码为111111 返回重置后的用户列表
    @PostMapping(value = "/admin/resetPwd")
    public Result resetPwd(@RequestBody UserModel userModel) {
        userService.resetPwd(userModel);

        //返回所有用户列表信息
        return Result.success(findAllUser().getData());
    }

    //管理员根据用户ID重置用户密码为111111 返回重置后的用户列表
    @PostMapping(value = "/admin/resetPwdByUserId")
    public Result resetPwdByUserId(@RequestBody UserModel userModel) {
        userService.resetPwdByUserId(userModel);

        //返回所有用户列表信息
        return Result.success(findAllUser().getData());
    }

    //管理员根据用户编号动态更改用户信息 返回更改后的用户列表
    @PostMapping(value = "/admin/modify")
    public Result modify(@RequestBody UserModel userModel) {
        userService.modify(userModel);

        //返回所有用户列表信息
        return Result.success(findAllUser().getData());
    }

    //管理员查找所有用户 返回用户列表
    @GetMapping(value = "/admin/findAllUser")
    public Result findAllUser() {
        List<UserModel> list = userService.findAllUser();
        return Result.success(list);
    }

    //管理员根据用户ID查找用户 返回用户信息
    @GetMapping(value = "/admin/findByUserId")
    public Result findByUserId(@RequestBody UserModel userModel) {
        UserModel userResult = userService.findByUserId(userModel.getUserId());

        return Result.success(userResult);
    }

    //所有人更改自己用户密码 返回修改后的用户信息
    @PostMapping(value = "/modifyMyPwd")
    public Result modifyMyPwd(@RequestBody UserModel userModel) {
        //获取根据session获取用户objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        userService.modifyMyPwd(userModel,objectId);

        //返回自己的用户信息
        return Result.success(findMy().getData());
    }

    //所有人动态更改自己用户信息 返回修改后的用户信息
    @PostMapping(value = "/modifyMy")
    public Result modifyMy(@RequestBody UserModel userModel) {
        //获取根据session获取用户objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        userService.modifyMy(userModel,objectId);

        //返回自己的用户信息
        return Result.success(findMy().getData());
    }

    //所有人查看自己用户信息 返回用户信息
    @GetMapping(value = "/findMy")
    public Result findMy() {
        //获取根据session获取用户objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        UserModel userModel = userService.findByObjectId(objectId);
        return Result.success(userModel);
    }

}
