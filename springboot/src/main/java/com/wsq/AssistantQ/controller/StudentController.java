package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.StudentModel;
import com.wsq.AssistantQ.service.StudentService;
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
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;

    //管理员新增学生 返回新增后的学生列表
    @PostMapping(value = "/admin/add")
    public Result add(@RequestBody StudentModel studentModel) {
        studentService.add(studentModel);

        //返回所有学生列表
        return Result.success(findAllStudent().getData());
    }

    //管理员根据学生编号删除学生 返回删除后的学生列表
    @PostMapping(value = "/admin/deleteByObjectId")
    public Result deleteByObjectId(@RequestBody StudentModel studentModel) {
        studentService.deleteByObjectId(studentModel);

        //返回所有学生列表
        return Result.success(findAllStudent().getData());
    }

    //管理员根据学生ID删除学生 返回删除后的学生列表
    @PostMapping(value = "/admin/deleteByStuId")
    public Result deleteByStuId(@RequestBody StudentModel studentModel) {
        studentService.deleteByStuId(studentModel);

        //返回所有学生列表
        return Result.success(findAllStudent().getData());
    }

    //管理员根据学生编号动态更改学生信息 返回更改后的学生列表
    @PostMapping(value = "/admin/modify")
    public Result modify(@RequestBody StudentModel studentModel) {
        studentService.modify(studentModel);

        //返回所有学生列表
        return Result.success(findAllStudent().getData());
    }

    //管理员查找所有学生 返回学生列表
    @GetMapping(value = "/admin/findAllStudent")
    public Result findAllStudent() {
        List<StudentModel> list = studentService.findAllStudent();
        return Result.success(list);
    }

    //管理员根据学生ID查找学生 返回学生信息
    @PostMapping(value = "/admin/findByStuId")
    public Result findByStudentId(@RequestBody StudentModel studentModel) {
        StudentModel studentResult = studentService.findByStuId(studentModel.getStuId());

        return Result.success(studentResult);
    }

    //学生动态更改自己学生信息 返回修改后的学生信息
    @PostMapping(value = "/modifyMy")
    public Result modifyMy(@RequestBody StudentModel studentModel) {
        //获取根据session获取用户objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取学生ID 等同于用户信息表中获取用户ID
        String stuId=userService.findByObjectId(objectId).getUserId();

        studentService.modifyMy(studentModel,stuId);

        //返回自己的学生信息
        return Result.success(findMy().getData());
    }

    //学生查看自己学生信息 返回学生信息
    @GetMapping(value = "/findMy")
    public Result findMy() {
        //获取根据session获取用户objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取学生ID 等同于用户信息表中获取用户ID
        String stuId=userService.findByObjectId(objectId).getUserId();

        StudentModel studentModel = studentService.findByStuId(stuId);
        return Result.success(studentModel);
    }
    
}
