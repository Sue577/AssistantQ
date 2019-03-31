package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.ApplicationModel;
import com.wsq.AssistantQ.model.RecruitModel;
import com.wsq.AssistantQ.service.ApplicationService;
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
 * @date 2019/3/28 14:24
 */
@RestController
@CrossOrigin
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private UserService userService;
    @Autowired
    private RecruitService recruitService;

    //管理员新增报名 返回新增后的报名列表
    @PostMapping(value = "/admin/add")
    public Result add(@RequestBody ApplicationModel applicationModel) {

        applicationService.add(applicationModel);

        //返回所有的报名列表
        return Result.success(findAllApplication().getData());
    }

    //管理员根据报名编号删除报名 返回删除后的报名列表
    @PostMapping(value = "/admin/deleteByObjectId")
    public Result deleteByObjectId(@RequestBody ApplicationModel applicationModel) {
        applicationService.deleteByObjectId(applicationModel);

        //返回所有的报名列表
        return Result.success(findAllApplication().getData());
    }

    //管理员根据报名编号动态更改报名信息 返回更改后的报名列表
    @PostMapping(value = "/admin/modify")
    public Result modify(@RequestBody ApplicationModel applicationModel) {
        applicationService.modify(applicationModel);

        //返回所有的报名列表
        return Result.success(findAllApplication().getData());
    }

    //管理员查找所有报名 返回报名列表
    @GetMapping(value = "/admin/findAllApplication")
    public Result findAllApplication() {
        List<ApplicationModel> list = applicationService.findAllApplication();
        return Result.success(list);
    }

    //管理员根据提交者姓名查找报名 返回报名列表
    @GetMapping(value = "/admin/findByApplSubmitterName")
    public Result findByApplSubmitterName(@RequestBody ApplicationModel applicationModel) {
        List<ApplicationModel> list = applicationService.findByApplSubmitterName(applicationModel.getApplSubmitterName());

        return Result.success(list);
    }

    //管理员根据报名审核状态查找报名 返回报名列表
    @GetMapping(value = "/admin/findByApplStatus")
    public Result findByApplStatus(@RequestBody ApplicationModel applicationModel) {
        List<ApplicationModel> list = applicationService.findByApplStatus(applicationModel.getApplStatus());

        return Result.success(list);
    }

    //学生新增报名 返回新增后的自己的报名列表
    @PostMapping(value = "/student/addMy")
    public Result addMy(@RequestBody ApplicationModel applicationModel) {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取提交者ID 等同于用户信息表中获取用户ID
        String applSubmitterId=userService.findByObjectId(objectId).getUserId();

        //添加提交者ID
        applicationModel.setApplSubmitterId(applSubmitterId);

//        //获取审核者ID 等同于招聘信息表中获取提交者ID
//        String applAuditorId=recruitService.findByObjectId(applicationModel.getApplRecruitId()).getRecrSubmitterId();
//
//        //添加审核者ID
//        applicationModel.setApplAuditorId(applAuditorId);

        applicationService.add(applicationModel);

        //返回自己的报名列表
        return Result.success(findMy().getData());
    }

    //学生根据报名编号删除报名 返回删除后的自己的报名列表
    @PostMapping(value = "/student/deleteMyByObjectId")
    public Result deleteMyByObjectId(@RequestBody ApplicationModel applicationModel) {
        applicationService.deleteByObjectId(applicationModel);

        //返回自己的报名列表
        return Result.success(findMy().getData());
    }

    //学生根据报名编号动态更改报名信息 返回更改后的自己的报名列表
    @PostMapping(value = "/student/modifyMy")
    public Result modifyMy(@RequestBody ApplicationModel applicationModel) {
        applicationService.modify(applicationModel);

        //返回自己的报名列表
        return Result.success(findMy().getData());
    }

    //学生根据招聘编号和提交者ID查询查看自己报名信息 返回自己的报名信息
    @GetMapping(value = "/student/findByApplRecruitIdAndApplSubmitterId")
    public Result findByApplRecruitIdAndApplSubmitterId(@RequestBody RecruitModel recruitModel) {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取提交者ID 等同于用户信息表中获取用户ID
        String applSubmitterId=userService.findByObjectId(objectId).getUserId();

        //根据招聘编号和提交者ID查询报名信息
        ApplicationModel applicationResult = applicationService.findByApplRecruitIdAndApplSubmitterId(recruitModel.getRecrSubmitterId(),applSubmitterId);
        return Result.success(applicationResult);
    }

    //学生查看自己报名信息 返回自己的报名列表
    @GetMapping(value = "/student/findMy")
    public Result findMy() {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取提交者ID 等同于用户信息表中获取用户ID
        String applSubmitterId=userService.findByObjectId(objectId).getUserId();

        List<ApplicationModel> list = applicationService.findByApplSubmitterId(applSubmitterId);
        return Result.success(list);
    }

    //教师根据报名编号动态更改自己需要审核的报名信息 返回更改后的自己审核的报名列表
    @PostMapping(value = "/teacher/modifyMyAudit")
    public Result modifyMyAudit(@RequestBody ApplicationModel applicationModel) {
        applicationService.modify(applicationModel);

        //返回自己审核的报名列表
        return Result.success(findMyAudit().getData());
    }

    //教师查看自己需要审核的报名信息 返回自己审核的报名列表
    @GetMapping(value = "/teacher/findMyAudit")
    public Result findMyAudit() {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取审核者ID 等同于用户信息表中获取用户ID
        String applAuditorId=userService.findByObjectId(objectId).getUserId();

        List<ApplicationModel> list = applicationService.findByApplAuditorId(applAuditorId);
        return Result.success(list);
    }
}
