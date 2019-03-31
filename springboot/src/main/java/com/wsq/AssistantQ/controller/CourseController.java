package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.CourseModel;
import com.wsq.AssistantQ.service.CourseService;
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
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;

    //管理员新增课程 返回新增后的课程列表
    @PostMapping(value = "/admin/add")
    public Result add(@RequestBody CourseModel courseModel) {
        courseService.add(courseModel);

        //返回所有的课程列表
        return Result.success(findAllCourse().getData());
    }

    //管理员根据课程编号删除课程 返回删除后的课程列表
    @PostMapping(value = "/admin/deleteByObjectId")
    public Result deleteByObjectId(@RequestBody CourseModel courseModel) {
        courseService.deleteByObjectId(courseModel);

        //返回所有的课程列表
        return Result.success(findAllCourse().getData());
    }

    //管理员根据课程编号动态更改课程信息 返回更改后的课程列表
    @PostMapping(value = "/admin/modify")
    public Result modify(@RequestBody CourseModel courseModel) {
        courseService.modify(courseModel);

        //返回所有的课程列表
        return Result.success(findAllCourse().getData());
    }

    //管理员查找所有课程 返回课程列表
    @GetMapping(value = "/admin/findAllCourse")
    public Result findAllCourse() {
        List<CourseModel> list = courseService.findAllCourse();
        return Result.success(list);
    }

    //管理员根据课程名称查找课程 返回课程信息
    @GetMapping(value = "/admin/findByCourName")
    public Result findByCourName(@RequestBody CourseModel courseModel) {
        CourseModel courseResult = courseService.findByCourName(courseModel.getCourName());

        return Result.success(courseResult);
    }

    //教师新增课程 返回新增后的自己的课程列表
    @PostMapping(value = "/teacher/addMy")
    public Result addMy(@RequestBody CourseModel courseModel) {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取提交者ID 等同于用户信息表中获取用户ID
        String courSubmitterId=userService.findByObjectId(objectId).getUserId();

        //添加提交者ID
        courseModel.setCourSubmitterId(courSubmitterId);

        courseService.add(courseModel);

        //返回自己的课程列表
        return Result.success(findMy().getData());
    }

    //教师根据课程编号删除课程 返回删除后的自己的课程列表
    @PostMapping(value = "/teacher/deleteMyByObjectId")
    public Result deleteMyByObjectId(@RequestBody CourseModel courseModel) {
        courseService.deleteByObjectId(courseModel);

        //返回自己的课程列表
        return Result.success(findMy().getData());
    }

    //教师根据课程编号动态更改课程信息 返回更改后的自己的课程列表
    @PostMapping(value = "/teacher/modifyMy")
    public Result modifyMy(@RequestBody CourseModel courseModel) {
        courseService.modify(courseModel);

        //返回自己的课程列表
        return Result.success(findMy().getData());
    }

    //教师查看自己课程信息 返回自己的课程列表
    @GetMapping(value = "/teacher/findMy")
    public Result findMy() {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取提交者ID 等同于用户信息表中获取用户ID
        String courSubmitterId=userService.findByObjectId(objectId).getUserId();

        List<CourseModel> list = courseService.findByCourSubmitterId(courSubmitterId);
        return Result.success(list);
    }
}
