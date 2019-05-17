package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.TeacherModel;
import com.wsq.AssistantQ.service.TeacherService;
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
 * @date 2019/3/28 14:27
 */
@RestController
@CrossOrigin
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;

    //管理员新增教师 返回新增后的教师列表
    @PostMapping(value = "/admin/add")
    public Result add(@RequestBody TeacherModel teacherModel) {
        teacherService.add(teacherModel);

        //返回所有教师列表
        return Result.success(findAllTeacher().getData());
    }

    //管理员根据教师编号删除教师 返回删除后的教师列表
    @PostMapping(value = "/admin/deleteByObjectId")
    public Result deleteByObjectId(@RequestBody TeacherModel teacherModel) {
        teacherService.deleteByObjectId(teacherModel);

        //返回所有教师列表
        return Result.success(findAllTeacher().getData());
    }

    //管理员根据教师ID删除教师 返回删除后的教师列表
    @PostMapping(value = "/admin/deleteByTeachId")
    public Result deleteByTeachId(@RequestBody TeacherModel teacherModel) {
        teacherService.deleteByStuId(teacherModel);

        //返回所有教师列表
        return Result.success(findAllTeacher().getData());
    }

    //管理员根据教师编号动态更改教师信息 返回更改后的教师列表
    @PostMapping(value = "/admin/modify")
    public Result modify(@RequestBody TeacherModel teacherModel) {
        teacherService.modify(teacherModel);

        //返回所有教师列表
        return Result.success(findAllTeacher().getData());
    }

    //管理员查找所有教师 返回教师列表
    @GetMapping(value = "/admin/findAllTeacher")
    public Result findAllTeacher() {
        List<TeacherModel> list = teacherService.findAllTeacher();
        return Result.success(list);
    }

    //管理员根据教师ID查找教师 返回教师信息
    @PostMapping(value = "/admin/findByTeachId")
    public Result findByTeachId(@RequestBody TeacherModel teacherModel) {
        TeacherModel teacherResult = teacherService.findByTeachId(teacherModel.getTeachId());

        return Result.success(teacherResult);
    }

    //教师动态更改自己教师信息 返回修改后的教师信息
    @PostMapping(value = "/modifyMy")
    public Result modifyMy(@RequestBody TeacherModel teacherModel) {
        //获取根据session获取用户objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取教师ID 等同于用户信息表中获取用户ID
        String teachId=userService.findByObjectId(objectId).getUserId();

        teacherService.modifyMy(teacherModel,teachId);

        //返回自己的教师信息
        return Result.success(findMy().getData());
    }

    //教师查看自己教师信息 返回教师信息
    @GetMapping(value = "/findMy")
    public Result findMy() {
        //获取根据session获取用户objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取教师ID 等同于用户信息表中获取用户ID
        String teachId=userService.findByObjectId(objectId).getUserId();

        TeacherModel teacherModel = teacherService.findByTeachId(teachId);
        return Result.success(teacherModel);
    }
}
