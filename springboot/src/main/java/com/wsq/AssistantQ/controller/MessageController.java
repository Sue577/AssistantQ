package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.MessageModel;
import com.wsq.AssistantQ.service.MessageService;
import com.wsq.AssistantQ.service.UserService;
import com.wsq.AssistantQ.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 14:25
 */
@RestController
@CrossOrigin
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    //管理员新增通知 返回新增后的通知列表
    @PostMapping(value = "/admin/add")
    public Result add(@RequestBody MessageModel messageModel) {
        messageService.add(messageModel);

        //返回所有通知列表信息
        return Result.success(findAllMessage().getData());
    }

    //管理员根据通知编号删除通知 返回删除后的通知列表
    @PostMapping(value = "/admin/deleteByObjectId")
    public Result deleteByObjectId(@RequestBody MessageModel messageModel) {
        messageService.deleteByObjectId(messageModel);

        //返回所有通知列表信息
        return Result.success(findAllMessage().getData());
    }

    //管理员根据通知编号动态更改通知信息 返回更改后的通知列表
    @PostMapping(value = "/admin/modify")
    public Result modify(@RequestBody MessageModel messageModel) {
        messageService.modify(messageModel);

        //返回所有通知列表信息
        return Result.success(findAllMessage().getData());
    }

    //管理员查找所有通知 返回通知列表
    @GetMapping(value = "/admin/findAllMessage")
    public Result findAllMessage() {
        List<MessageModel> list = messageService.findAllMessage();
        return Result.success(list);
    }

    //管理员根据通知接收者ID查找通知 返回通知列表
    @GetMapping(value = "/admin/findByMsgReceiverId")
    public Result findByMsgReceiverId(@RequestBody MessageModel messageModel) {
        List<MessageModel> list = messageService.findByMsgReceiverId(messageModel.getMsgReceiverId());

        return Result.success(list);
    }

    //教师和学生查看自己通知信息 返回自己的通知列表
    @GetMapping(value = "/findMy")
    public Result findMy() {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取接收者ID 等同于用户信息表中获取用户ID
        String msgReceiverId = userService.findByObjectId(objectId).getUserId();

        List<MessageModel> list = messageService.findByMsgReceiverId(msgReceiverId);

        return Result.success(list);
    }
}
