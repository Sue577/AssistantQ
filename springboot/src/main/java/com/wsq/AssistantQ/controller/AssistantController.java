package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.AssistantModel;
import com.wsq.AssistantQ.model.CourseModel;
import com.wsq.AssistantQ.service.AssistantService;
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
@RequestMapping("/assistant")
public class AssistantController {

    @Autowired
    private AssistantService assistantService;
    @Autowired
    private UserService userService;

    //管理员新增助教 返回新增后的助教列表
    @PostMapping(value = "/admin/add")
    public Result add(@RequestBody AssistantModel assistantModel) {
        assistantService.add(assistantModel);

        //返回所有助教列表信息
        return Result.success(findAllAssistant().getData());
    }

    //管理员根据助教编号删除助教 返回删除后的助教列表
    @PostMapping(value = "/admin/deleteByObjectId")
    public Result deleteByObjectId(@RequestBody AssistantModel assistantModel) {
        assistantService.deleteByObjectId(assistantModel);

        //返回所有助教列表信息
        return Result.success(findAllAssistant().getData());
    }

    //管理员根据助教编号动态更改助教信息 返回更改后的助教列表
    @PostMapping(value = "/admin/modify")
    public Result modify(@RequestBody AssistantModel assistantModel) {
        assistantService.modify(assistantModel);

        //返回所有助教列表信息
        return Result.success(findAllAssistant().getData());
    }

    //管理员查找所有助教 返回助教列表
    @GetMapping(value = "/admin/findAllAssistant")
    public Result findAllAssistant() {
        List<AssistantModel> list = assistantService.findAllAssistant();
        return Result.success(list);
    }

    //管理员根据助教学生姓名查找助教 返回助教列表
    @PostMapping(value = "/admin/findByAssiStuName")
    public Result findByAssiName(@RequestBody AssistantModel assistantModel) {
        List<AssistantModel> list = assistantService.findByAssiName(assistantModel.getAssiStudentName());

        return Result.success(list);
    }

    //管理员根据助教学生ID查找助教 返回助教列表
    @PostMapping(value = "/admin/findByAssiStudentId")
    public Result findByAssiStudentId(@RequestBody AssistantModel assistantModel) {
        List<AssistantModel> list = assistantService.findByAssiStudentId(assistantModel.getAssiStudentId());

        return Result.success(list);
    }

    //管理员根据助教课程查找助教 返回助教列表
    @PostMapping(value = "/admin/findByAssiCourse")
    public Result findByAssiCourse(@RequestBody AssistantModel assistantModel) {
        List<AssistantModel> list = assistantService.findByAssiCourse(assistantModel.getAssiCourse());

        return Result.success(list);
    }

    //管理员根据助教教师ID查找助教 返回助教列表
    @PostMapping(value = "/admin/findByAssiTeacherId")
    public Result findByAssiTeacherId(@RequestBody AssistantModel assistantModel) {
        List<AssistantModel> list = assistantService.findByAssiTeacherId(assistantModel.getAssiTeacherId());

        return Result.success(list);
    }

    //教师根据助教编号动态更改助教信息 返回更改后自己的助教列表
    @PostMapping(value = "/teacher/modifyMy")
    public Result modifyMy(@RequestBody AssistantModel assistantModel) {
        assistantService.modify(assistantModel);

        //返回自己的助教列表信息
        return Result.success(findMy().getData());
    }

    //教师和学生查看自己助教信息 返回自己的助教列表
    @GetMapping(value = "/findMy")
    public Result findMy() {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取学生/教师ID 等同于用户信息表中获取用户ID
        String ID=userService.findByObjectId(objectId).getUserId();

        List<AssistantModel> list=null;

        //判断是学生还是教师，获取助教信息
        if(userService.findByObjectId(objectId).getUserType().equals("学生")){
            list = assistantService.findByAssiStudentId(ID);
        } else if(userService.findByObjectId(objectId).getUserType().equals("教师")){
            list = assistantService.findByAssiTeacherId(ID);
        }

        return Result.success(list);
    }
}
