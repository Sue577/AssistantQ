package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.UserModel;
import com.wsq.AssistantQ.service.UserService;
import com.wsq.AssistantQ.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WSQ
 * @date 2019/3/28 15:45
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class LoginoutController {

    @Autowired
    private UserService userService;

    //所有人登录 返回用户类型、状态、sessionId
    @PostMapping(value = "/login")
    public Result login (@RequestBody UserModel userModel, HttpSession session){
        UserModel loginUser = userService.findByUserIdAndUserPwdAndDelTimeIsNull(userModel.getUserId(),userModel.getUserPwd());
        if(loginUser != null){
            session.setAttribute("ID", loginUser.getObjectId());
            session.setAttribute("TYPE", loginUser.getUserType());
            loginUser.setUserPwd(null);
            Map result = new HashMap();
            result.put("userType", loginUser.getUserType());
            result.put("status", "ok");
            result.put("SessionId", session.getId());
            return Result.success(result);
        } else {
            Map result = new HashMap();
            result.put("status", "error");
            return Result.loginerror(result);
        }
    }
}
