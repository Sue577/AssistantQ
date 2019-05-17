package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.RecruitModel;
import com.wsq.AssistantQ.service.RecruitService;
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
 * @date 2019/3/28 14:26
 */
@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class RecruitController {
    @Autowired
    private RecruitService recruitService;
    @Autowired
    private UserService userService;

    //管理员新增招聘信息 返回新增后的招聘信息列表
    @PostMapping(value = "/admin/add")
    public Result add(@RequestBody RecruitModel recruitModel) {
        recruitService.add(recruitModel);

        //返回所有的招聘信息列表
        return Result.success(findAllRecruit().getData());
    }

    //管理员根据招聘编号删除招聘信息 返回删除后的招聘信息列表
    @PostMapping(value = "/admin/deleteByObjectId")
    public Result deleteByObjectId(@RequestBody RecruitModel recruitModel) {
        recruitService.deleteByObjectId(recruitModel);

        //返回所有的招聘信息列表
        return Result.success(findAllRecruit().getData());
    }

    //管理员根据招聘编号动态更改招聘信息 返回更改后的招聘信息列表
    @PostMapping(value = "/admin/modify")
    public Result modify(@RequestBody RecruitModel recruitModel) {
        recruitService.modify(recruitModel);

        //返回所有的招聘信息列表
        return Result.success(findAllRecruit().getData());
    }


    //教师新增招聘信息 返回新增后的自己的招聘信息列表
    @PostMapping(value = "/teacher/addMy")
    public Result addMy(@RequestBody RecruitModel recruitModel) {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取提交者ID 等同于用户信息表中获取用户ID
        String recrSubmitterId = userService.findByObjectId(objectId).getUserId();

        //添加提交者ID
        recruitModel.setRecrSubmitterId(recrSubmitterId);

        recruitService.add(recruitModel);

        //返回自己的招聘信息列表
        return Result.success(findMy().getData());
    }

    //教师根据招聘信息编号删除招聘信息 返回删除后的自己的招聘信息列表
    @PostMapping(value = "/teacher/deleteMyByObjectId")
    public Result deleteMyByObjectId(@RequestBody RecruitModel recruitModel) {
        recruitService.deleteByObjectId(recruitModel);

        //返回自己的招聘信息列表
        return Result.success(findMy().getData());
    }

    //教师根据招聘编号动态更改自己招聘信息信息 返回修改后的自己的招聘信息列表
    @PostMapping(value = "/teacher/modifyMy")
    public Result modifyMy(@RequestBody RecruitModel recruitModel) {
        recruitService.modify(recruitModel);

        //返回自己的招聘信息列表
        return Result.success(findMy().getData());
    }

    //教师查看自己招聘信息信息 返回自己的招聘信息列表
    @GetMapping(value = "/teacher/findMy")
    public Result findMy() {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取提交者ID 等同于用户信息表中获取用户ID
        String recrSubmitterId = userService.findByObjectId(objectId).getUserId();

        List<RecruitModel> list = recruitService.findByRecrSubmitterId(recrSubmitterId);
        return Result.success(list);
    }

    //所有人查找所有招聘信息 返回招聘信息列表
    @GetMapping(value = "/findAllRecruit")
    public Result findAllRecruit() {
        List<RecruitModel> list = recruitService.findAllRecruit();
        return Result.success(list);
    }

    //所有人根据招聘信息编号查找招聘信息 返回招聘信息
    @PostMapping(value = "/findByObjectId")
    public Result findByObjectId(@RequestBody RecruitModel recruitModel) {
        RecruitModel recruitResult = recruitService.findByObjectId(recruitModel.getObjectId());
        return Result.success(recruitResult);
    }

    //所有人根据招聘信息标题查找招聘信息 返回招聘信息列表
    @PostMapping(value = "/findByRecrTitle")
    public Result findByRecrTitle(@RequestBody RecruitModel recruitModel) {
        List<RecruitModel> list = recruitService.findByRecrTitle(recruitModel.getRecrTitle());

        return Result.success(list);
    }
}
